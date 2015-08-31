import scala.language.implicitConversions

class BadException(message: String) extends Exception(message)

class Scalar(x : Double) {
 // def *(y : Matrix) = y * x
} 

class Matrix(val numRows: Int, val numCols: Int) {
  //implicit def double2Scalar(d : Double) : Scalar = new Scalar(d)

  /*not sure why it's Matrix, really a graph*/ 
  var connections = new Array[List[Double]](numRows)
  for (i <- 0 until numRows) connections(i) = List()
  var values = Map[Tuple2[Int,Int], Double]()


  def apply(i:Int,j:Int) = {
    if((i <= 0) || (j <= 0) ) throw new BadException("aaply")
    if(connections(i-1).contains(j-1)) values((i,j))
    else 0
  }
  def update(i:Int, j:Int, number:Double) = {
    /*note, assuming directed unweighted graph*/
    if((i <= 0) || (j <= 0) ) throw new BadException("update")
    connections((i-1)) = (j-1)::connections((i-1))
    values += ((i,j) -> number)
  }

  def printThis(){
    for (i <- 1 to numRows) {
      for(j <- 1 to numCols) {
        print(this(i,j)+ " ")
      }
      println("")
    }
    println("")
  }
/*

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
          //temp = temp - out(numRows - j - 1, k)*this(position, numRows -j - 1)
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

*/

}


object firstTry {
  implicit def double2Scalar(d : Double) : Scalar = new Scalar(d)

  def main(args: Array[String]) {
    val myMatrix = new Matrix(3,4);
   //myMatrix.values(0)(0) = 1
   //myMatrix.values(0)(1) = 1
   myMatrix(1,3) = 1
    myMatrix(2,3) = 7
    myMatrix(1,1) = 2
  //myMatrix.values.foreach(println)
println(myMatrix(1,1))
   // myMatrix(1,0) = -2
   // myMatrix(1,2) = Math.PI
   // myMatrix.multiplyRow(0,2)
   // myMatrix.addRow(0,1)
    //myMatrix.switchRow(0,2)
   myMatrix.printThis()
   // println(myMatrix(1,2))

 


  }
}
