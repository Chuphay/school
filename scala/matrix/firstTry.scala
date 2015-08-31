import scala.language.implicitConversions

class BadException(message: String) extends Exception(message)

class Scalar(x : Double) {
  def *(y : Matrix) = y * x
} 

class Matrix(val numRows: Int, val numCols: Int) {
  implicit def double2Scalar(d : Double) : Scalar = new Scalar(d)
  var values = new Array[Array[Double]](numRows)
  var i = 0
  for (i <- 0 until numRows) values(i) = new Array[Double](numCols)

  def apply(i:Int,j:Int) = values(i)(j)
  def update(i:Int, j:Int, number:Double) = values(i)(j) = number
  /*def update(that:Matrix) = {
    for (i <- 0 until numRows){
      for (j <- 0 until nemCols){
        this(i,j) = that(i,j)
      }
    }
  }*/


  def printThis(){
    for (i <- 0 until numRows) {
      var j = 0
      for(j <- 0 until numCols) print(values(i)(j)+" ")
      println("")
    }
    println("")
  }

  def multiplyRow(a:Int, b:Double){
    values(a) = values(a).map(l => b*l)
  }
  def addRow(a:Int, b:Int){
    var i = 0
    for(i <- 0 until numCols){
      values(b)(i) = values(a)(i) + values(b)(i)
    }
  }
  def switchRow(a:Int, b:Int){
    val temp = values(a)
    values(a) = values(b)
    values(b) = temp
  }

  def reduce(){
    var i = 0
    for(i <- 0 until numRows){

      if(this(i,i) == 0) {
        var bad = true
        var k = i+1
        for(k <- i+1 until numRows){
          if(this(k,k) !=0){
            bad = false
            switchRow(i,k)
          }
        }
        if(bad) throw new BadException("uhh.. bad pivot")
      }

      var j = i+1
      for(j <- i+1 until numRows){
        if(this(j,i) != 0){
          multiplyRow(i,- this(j,i)/this(i,i))
          addRow(i, j)
        }
      }
      multiplyRow(i,1/this(i,i))
    }
  }
  def solve():Matrix = {
    if(numRows >= numCols) throw new BadException("Not n x n+m matrix")
    this.reduce()
    var out = new Matrix(numRows, numCols - numRows)
    var k = 0
    for (k <- 0 until numCols - numRows) {
      var i = 0
      for(i <- 0 until numRows){
        var position = numRows - i - 1
        var temp = this(position, numRows+k)
        var j = 0
        for(j <- 0 until i){
          temp = temp - out(numRows - j - 1, k)*this(position, numRows -j - 1)
        }
        out(position, k) = temp
      }
    }
    return out
  }

  def T:Matrix = {
    var out = new Matrix(numCols, numRows)
    var i = 0
    var j = 0
    for(i <- 0 until numRows){
      for(j<- 0 until numCols){
        out(j,i) = this(i,j)
      }
    }
    return out
  }

  def +(that:Matrix):Matrix = {
    if(this.numCols != that.numCols) throw new BadException("Cols don't match") 
    if(this.numRows != that.numRows) throw new BadException("Cols don't match")
    val out = new Matrix(numRows, numCols)
    var i = 0; var j =0
    for(i <- 0 until numRows){
      for(j<- 0 until numCols){
        out(i,j) = this(i,j) + that(i,j)
      }
    }
    return out
  }
  def -(that:Matrix):Matrix = {
    if(this.numCols != that.numCols) throw new BadException("Cols don't match") 
    if(this.numRows != that.numRows) throw new BadException("Cols don't match")
    val out = new Matrix(numRows, numCols)
    var i = 0; var j =0
    for(i <- 0 until numRows){
      for(j<- 0 until numCols){
        out(i,j) = this(i,j) - that(i,j)
      }
    }
    return out
  }
  def *(num:Double):Matrix = {
    val out = new Matrix(numRows, numCols)
    var i, j =0
    for(i <- 0 until numRows){
      for(j<- 0 until numCols){
        out(i,j) = num*this(i,j)
      }
    }
    return out
  }

  def *(that:Matrix):Matrix = {
    if(this.numCols != that.numRows) throw new BadException("Cols and rows don't match")
    val out = new Matrix(this.numRows, that.numCols)
    var i, j, k =0
    for(i <- 0 until out.numRows){
      for(j<- 0 until out.numCols){
        var temp = 0.0
        for(k <- 0 until this.numCols){
          temp += this(i,k)*that(k,j)
        }
        out(i,j) = temp
      }
    }
    return out
  }
  def outer(that:Matrix):Matrix = {
    if(that.numRows != 1) throw new BadException("vectors only!")
    if(this.numCols != 1) throw new BadException("vectors only!")
    val out = new Matrix(this.numRows, that.numCols)
    var i, j, k =0
    for(i <- 0 until out.numRows){
      for(j<- 0 until out.numCols){
        out(i,j) = this(i,0)*that(0,j)
      }
    }
    return out
  }

  def I:Matrix = {
    if(numCols != numRows) throw new BadException("must be n x n")
    var out = new Matrix(numRows, 2*numRows)
    var i = 0
    for (i <- 0 until numRows) {
      var j = 0
      for(j <- 0 until numRows){
        out(i,j) = this(i,j)
        if( i == j) {
          out(i, j + numRows) = 1
        }
      }
    }
    return out
  }

  def inverse():Matrix = {
    if(numCols != numRows) throw new BadException("must be n x n")
    val n2 = numRows*numRows
    var temp = new Matrix(n2, n2+1)
    var i, j = 0
    for (i <- 0 until numRows){
      for(j <- 0 until numRows){
        if( i == j){ 
          temp(i*numRows+j, n2) = 1
        }
        var k = 0
        for(k <- 0 until numRows){
          temp(j+i*numRows,numRows*i+k) = this(k,j)
        }
      }
    }
    var temp2 = temp.solve()
    val out = new Matrix(numRows, numRows)
    for(i<- 0 until numRows){
      for(j<-0 until numRows){
        out(i,j) = temp2(i*numRows+j,0)
      }
    }
    return out
  }
    

  def det():Double = {
    if(numCols != numRows) throw new BadException("must be n x n")
    var out:Double = 1
    var i = 0
    for(i <- 0 until numRows){

      if(this(i,i) == 0) {
        var bad = true
        var k = i+1
        for(k <- i+1 until numRows){
          if(this(k,k) !=0){
            bad = false
            switchRow(i,k)
            out = -out
          }
        }
        if(bad) throw new BadException("uhh.. bad pivot")
      }
      out = out*this(i,i)
      var j = i+1
      for(j <- i+1 until numRows){
        if(this(j,i) != 0){
          multiplyRow(i,- this(j,i)/this(i,i))
          addRow(i, j)
        }
      }
      multiplyRow(i,1/this(i,i))
    }
    return out
  }

  def doolittle():Matrix = {
    //U will be the original matrix, returned will be L
    if(numCols != numRows) throw new BadException("must be n x n")
    var out:Matrix = new Matrix(numRows, numCols)
    var i = 0
    for(i <- 0 until numRows){
      val a = this(i,i)
      out(i,i) = 1
      if(a == 0) {
        throw new BadException("uhh.. bad pivot")
      }
      var j = i+1
      for(j <- i+1 until numRows){
        if(this(j,i) != 0){
          out(j,i) = this(j,i)/a
          multiplyRow(i,- this(j,i)/this(i,i))
          addRow(i, j)
        }
      }
      multiplyRow(i,a/this(i,i))
    }
    return out
  }

  def power():Tuple2[Double, Matrix] = {
    /*power method to find the larget eigenvalue and its associated vector*/
    if(numCols != numRows) throw new BadException("must be n x n")
    var x, y = new Matrix(numRows,1)
    var i = 0
    var mu:Double = 0 
    for (i <- 0 until numRows) x(i,0) = 1
    for (i<- 0 until 10){
      y = this * x
      mu = 0
      var j = 0
      for(j<-0 until numRows){
        if(mu<y(j,0)) mu = y(j,0)
      }
      x = y*(1.0/mu)
    }
    return (mu, x)
  }
  def isSymmetric:Boolean = {
    if(numCols != numRows) throw new BadException("must be n x n")
    var i,j = 0
    var out = true
    for(i <- 0 until numRows){
      for(j <- i + 1 until numRows){
        if(this(i,j) != this(j,i)) out = false
      }
    }
    out
  }
  def symmPower():Tuple2[Double, Matrix] = {
    if(!this.isSymmetric) throw new BadException("not symmetric")
    var x, y = new Matrix(numRows,1)
    var i = 0
    var mu:Double = 0 
    x(0,0) = 1
    for (i<- 0 until 20){
      y = this * x
      mu = 0
      var temp:Double = 0
      var j = 0
      for(j<-0 until numRows){
        mu += y(j,0)*x(j,0)
        temp += y(j,0)*y(j,0)
      }
      x = y*(1.0/Math.sqrt(temp))
    }
    return (mu, x)
  }

  def invertedPower():Tuple2[Double, Matrix] = {
    /* something is broken ... */
    if(numCols != numRows) throw new BadException("must be n x n")
    var x, y = new Matrix(numRows,1)
    var mu, q = 0.0
    for (i <- 0 until numRows) x(i,0) = 1
    q = ((1.0/numRows) * x.T * this * x)(0,0)
    for (i <- 0 until 10){
      val temp:Matrix = new Matrix(numRows, numCols + 1)
      for(j <- 0 until numRows){
        for(k <- 0 until numCols +1){
          if(k == numCols) {
            temp(j, k) = x(j,0)
          } else if (k == j) {
            temp(j, k) = this(j,k) +(-q)
          } else {
            temp(j, k) = this(j,k)
          }
        }
      }
      y = temp.solve()
      mu = 0
      for(j<-0 until numRows){
        if(mu<y(j,0)) mu = y(j,0)
      }
      x = y * (1.0/mu)
    }
    return (q + (1.0/mu), x)
  }
  def householder() = {
    if(!this.isSymmetric) throw new BadException("not symmetric")
    var v , u, z = new Matrix(numRows,1)
    for(k <- 0 until numRows - 1) {
      var q, a, rsq, prod = 0.0

      for(j <- k+1 until numRows) q += this(j,k)*this(j,k)

      if (this(k+1,k) == 0){
        a = - Math.sqrt(q)
      } else {
        a = - Math.sqrt(q)*this(k+1,k)/Math.abs(this(k+1,k))
      }
      rsq = a*a - a*this(k+1,k)
      v(k,0) = 0
      v(k+1, 0) = this(k+1,k) - a
      for(j <- k+2 until numRows) v(j, 0) = this(j,k)
      u = this*v*(1/rsq)
      prod = ((v.T)*u)(0,0)

      z = u + (-prod/(2*rsq))*v
      val temp:Matrix = this + (-1.0)*(v.outer(z.T) + z.outer(v.T))
      for (i <- 0 until numRows){
        for (j <- 0 until numCols){
          this(i,j) = temp(i,j)
        }
      }
    }
  }
  def internalQR(a:Array[Double], b:Array[Double]):Tuple3[String, Double, Double] = {

    /*wow man, so long and convoluted. No way was I going to get this right*/
    val tol = 0.001
    var shift = 0.0
    val n = a.length
    for(k <- 0 until 1){
      var bb = - (a(n-1) + a(n-2))
      var cc = a(n-1)*a(n-2) - b(n-2)*b(n-2)
      var dd = Math.sqrt((bb*bb - 4*cc))
      var mu1, mu2, s = 0.0
      println("bb", bb, cc, dd)
      if(bb>0){
        mu1 = - 2*cc/(bb+dd)
        mu2 = -(bb+dd)/2
      } else {
        mu1 = (dd-bb)/2
        mu2 = 2*cc/(dd - bb)
      }
      if(n == 2){
        var gamma1 = mu1 + shift
        var gamma2 = mu2 + shift
        return ("two", gamma1, gamma2)
      } else {
        println("mu",mu1,mu2)
        if(Math.abs(mu1 - a(n-1)) >= Math.abs(mu2 - a(n-1))){
          shift += mu2
          s = mu2
        } else {
          shift += mu1
          s = mu1
        }
        println("shift", shift)
        var d = a.map(l => l - s)
        d.foreach(println)
        var x, z = new Array[Double](n)
        var y, c, ss, qq = new Array[Double](n-1)
        var r = new Array[Double](n-2)
        x(0) = d(0)
        y(0) = b(0)
        for(j <- 0 until n-1){
          println("j",j)
          z(j) = Math.sqrt(x(j)*x(j) +b(j)*b(j))
          c(j) = x(j)/z(j)
          ss(j) = b(j)/z(j)
          qq(j) = c(j)*y(j) + ss(j)*d(j)
          x(j+1) = -ss(j)*y(j) + c(j)*d(j)
          if(j<n-2){
            y(j+1) = c(j)*b(j)
            r(j) = ss(j)*b(j)
            println("R", r(j))
          }
        }
        z(n-1) = x(n-1)
        a(0) = ss(0)*qq(0) + c(0)*z(0)
        b(0) = ss(0)*z(1)
        for(j <- 1 until n-1){
          a(j) = ss(j)*qq(j) + c(j-1)*c(j)*z(j)
          b(j) = ss(j)*z(j+1)
        }
        a(n-1) = c(n-2)*z(n-2)
        a.foreach(l => print(l," ")); print("\n")
     
        b.foreach(l => print(l," ")); print("\n")

      }

    }
    return ("Working",-1, -3)
  }

  def qr():Matrix = {
    /*Broken!!!!*/
    /*tridiagonal inputs please, no checks at this point*/
    var out = new Matrix(numRows, 1)
    var a = new Array[Double](numRows)
    var b = new Array[Double](numRows - 1)
    a(0) = this(0,0)
    for(i <- 1 until numRows){
      a(i) = this(i,i)
      b(i-1) = this(i-1,i)
    }

    val (status, evalue1, evalue2) = internalQR(a, b)
    //println(status)
    //println(evalue1.toInt)
    return out
  }


}


object firstTry {
  implicit def double2Scalar(d : Double) : Scalar = new Scalar(d)

  def main(args: Array[String]) {
    val myMatrix = new Matrix(3,4);
    myMatrix.values(0)(0) = 1.0
    myMatrix.values(0)(1) = 2.0
    myMatrix(0,1) = 2.5

    myMatrix(1,0) = -2
    myMatrix(1,2) = Math.PI
    myMatrix.multiplyRow(0,2)
    myMatrix.addRow(0,1)
    myMatrix.switchRow(0,2)
    myMatrix.printThis()
    println(myMatrix(1,2))


    var matrix = new Matrix(4,5)
    matrix.values(0) = Array(2,0,0,0,3)
    matrix.values(1) = Array(1,1.5,0,0,4.5)
    matrix.values(2) = Array(0,-3,0.5,0,-6.6)
    matrix.values(3) = Array(2,-2,1,1,0.8)
    matrix.printThis()
    var answer = matrix.solve()
    //answer.foreach(println)
    answer.printThis()
    matrix.printThis()

    matrix = new Matrix(3,4)
    matrix.values(0) = Array(1,-1,3,2)
    matrix.values(1) = Array(3,-3,1,-1)
    matrix.values(2) = Array(1,1,0,3)
    matrix.printThis()
    answer = matrix.solve()
    answer.printThis()
    matrix.printThis()

    val transposed = matrix.T
    transposed.printThis()

    val other = new Matrix(3,4)
    other.values(0) = Array(1,1,1,1)
    other.printThis()

    val another = other - matrix
    another.printThis()
    val another2 = 2*matrix
    another2.printThis()
    val another3 = matrix*transposed
    another3.printThis()

    var inverse = new Matrix(3,3)
    inverse.values(0) = Array(-2,5,-1)
    inverse.values(1) = Array(4,-1,2)
    inverse.values(2) = Array(-3,3,3)
    inverse *= 1.0/9
    inverse.printThis()
    var I = inverse.I
    I.printThis()
    I.reduce()
    I.printThis()
    answer = I.solve()
    answer.printThis()
    //inverse.solve()
    I = inverse.inverse()
    I.printThis()


    matrix = new Matrix(4,4)
    matrix.values(0) = Array(1,1,0,3)
    matrix.values(1) = Array(2,1,-1,1)
    matrix.values(2) = Array(3,-1,-1,2)
    matrix.values(3) = Array(-1,2,3,-1)
    println(matrix.det())
    println("")
    matrix.values(0) = Array(1,1,0,3)
    matrix.values(1) = Array(2,1,-1,1)
    matrix.values(2) = Array(3,-1,-1,2)
    matrix.values(3) = Array(-1,2,3,-1)
    answer = matrix.doolittle()
    matrix.printThis()
    answer.printThis()
   

    matrix = new Matrix(3,3)
    matrix.values(0) = Array(-4,14,0)
    matrix.values(1) = Array(-5,13,0)
    matrix.values(2) = Array(-1,0,2)
    matrix.printThis()
    var (evalue, evector) = matrix.power()
    println("evalue = ", evalue)
    evector.printThis()

    matrix.values(0) = Array(4,-1,1)
    matrix.values(1) = Array(-1,3,-2)
    matrix.values(2) = Array(1,-2,3)
   
    var eigen = matrix.symmPower()
    println("evalue = ", eigen._1)
    eigen._2.printThis()
    answer = matrix * eigen._2
    (answer +(-eigen._1 * eigen._2)).printThis()

    matrix.values(0) = Array(-4,14,0)
    matrix.values(1) = Array(-5,13,0)
    matrix.values(2) = Array(-1,0,2)
    eigen = matrix.invertedPower()
    println("evalue = ", eigen._1)
    eigen._2.printThis()

    val vec = new Matrix(1,4)
    vec.values(0) = Array(1,2,3,4)
    vec.T.outer(vec).printThis()


    matrix = new Matrix(4,4)
    matrix.values(0) = Array(4,1,-2,2)
    matrix.values(1) = Array(1,2,0,1)
    matrix.values(2) = Array(-2,0,3,-2)
    matrix.values(3) = Array(2,1,-2,-1)

    matrix.householder()
    matrix.printThis()

    matrix = new Matrix(3,3)
    val s2 = Math.sqrt(2)/2
    matrix.values(0) = Array(2,s2,0)
    matrix.values(1) = Array(s2,1,-s2)
    matrix.values(2) = Array(0,-s2,0)

    matrix.qr()
    println(Math.round(-0.90))
    println(Math.abs(-0.99))


  }
}
