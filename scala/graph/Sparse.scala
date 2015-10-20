class myBad(m:String) extends Exception(m)

class Node(z:Double, i:Int, j:Int) {
  val value:Double = z
  val col:Int = i
  val row:Int = j
  var next_col:Node = null
  var next_row:Node = null
}

class Graph(n:Int){
  var dataRow:Array[Node] = new Array[Node](n)
  var dataCol:Array[Node] = new Array[Node](n)

  def printMe = {
    for( i <- 0 until n){
      if(dataRow(i) == null){
        for( j <- 0 until n) print("0.0 ")
      } else {
        var currentNode = dataRow(i)
        var tempData = new Array[Double](n)
        while (currentNode != null) {
          tempData(currentNode.col) = currentNode.value
          currentNode = currentNode.next_col
        }
        println("here")
        tempData.foreach(println)
        for( j <- 0 until n) {
          print(tempData(j) + " ")
        }
      }
      println("")
    }
  }

  def addEdge(value:Double, col:Int, row:Int) = {
    var currentRowNode = dataRow(row)
    var currentColNode = dataCol(col)
    val thisNode = new Node(value, col, row)
    if (currentRowNode == null) {
      dataRow(row) = thisNode
    } else {
      thisNode.next_row = currentRowNode
      dataRow(row) = thisNode
    }
    if (currentColNode == null) {
      dataCol(col) = thisNode
    } else {
      thisNode.next_col = currentColNode
      dataCol(col) = thisNode
    }

  }
}

object trySparse {

  def main(args: Array[String]) {

    println("on")
    val G = new Graph(3)
    G.addEdge(2.7, 0, 2)
    G.addEdge(1.1, 0, 1)
    //G.addEdge(2.0, 1, 2)
    G.printMe


  }
}
