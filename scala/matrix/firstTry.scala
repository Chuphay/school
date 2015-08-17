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


  }
}
