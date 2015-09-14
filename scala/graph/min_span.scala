class myBad(m:String) extends Exception(m)

class minSpan(members:Array[String]) extends unionGraph(members){

  def span_this(start_node:Int):Double = {

    var active_node = start_node - 1
    var frontier_nodes:List[Int] = connections(active_node)
    var visited:List[Int] = List(active_node)
    var myMap:Map[Int, Double] = Map()
    var out = 0.0

    for (x <- frontier_nodes) {

      val y = values(active_node+1, x+1)
      //println(x, y)
      try {
        val temp:Double = myMap(x)
        if(temp < y)  myMap += (x -> y)
      } catch {
        case ex: NoSuchElementException => {
          myMap += (x -> y)
        }
      }
       
    }

    while (!myMap.isEmpty){
      var small:Double = 100000
      var node:Int = -1
      for(x <- myMap){
        if(x._2 < small){
          small = x._2
          node = x._1
        }
      }
      println("node", node+1, "dist", small)

      for (x <- connections(node)) {
        if(visited contains x) {
        } else {
          val y = values(node+1, x+1)
          try {
            val temp:Double = myMap(x)
            if(temp > y)  myMap += (x -> y)
          } catch {
            case ex: NoSuchElementException => {
              myMap += (x -> y)
            }
          }
        }
      }
      visited = node::visited
      myMap -= node
      out += small

    }

    return out
  }


  def kruskal():Double = {
    var out:Double = 0.0
    var myValues:Map[Tuple2[Int,Int], Double] = values
    while(!myValues.isEmpty){
      var small = 10000.0
      var nodeA = -1
      var nodeB = -1
      for (x <- myValues){
        if(x._2 < small){
          small = x._2
          nodeA = x._1._1
          nodeB = x._1._2
        }
      }
      val tempTuple1 = (nodeA, nodeB)
      val tempTuple2 = (nodeB, nodeA)
      myValues = myValues - tempTuple1 - tempTuple2
      val alphA = this.members(nodeA-1)
      val alphB = this.members(nodeB-1)
      if(this.__find(alphA) != this.__find(alphB)){
        this.union(alphA,alphB)
        out += small
        println("union", alphA, alphB, small)
      } else {

        println("didnt union", alphA, alphB, small)
      }
    }
    return out
  }

}


object span_it {


  def main(args: Array[String]) {

    val alpha = Array("a", "b", "c", "d", "e", "f", "g",
      "h","i","j","k","l","m")
    val myMatrix = new minSpan(alpha);
    myMatrix(1,2) = 1 //ab
   
    myMatrix(1,6) = 2 //af
    myMatrix(1,7) = 6 //ag
    
    myMatrix(2,4) = 2 //bd
    myMatrix(2,3) = 1 //bc
    myMatrix(2,5) = 4 //be
    myMatrix(3,5) = 4 //ce

    myMatrix(4,5) = 2 //de
    myMatrix(4,6) = 1 //df
    myMatrix(5,6) = 2 //ef
    myMatrix(5,7) = 1 //eg

    myMatrix(6,12) = 2 //fl
    myMatrix(7,8) = 3 //gh
    myMatrix(7,10) = 1 //gj
    myMatrix(7,12) = 5 //gl
    myMatrix(8,9) = 2 //hi
    myMatrix(9,11) = 1 //ik
    myMatrix(10,11) = 1 //jk
    myMatrix(10,12) = 3 //jl
    myMatrix(10,13) = 2 //jm
    myMatrix(12,13) = 1 //lm

    myMatrix.printThis()


    println(myMatrix.span_this(11))
    val out = myMatrix.kruskal()
    println(out)

  }
}
