
class BadException(message: String) extends Exception(message)
class myBad(m:String) extends Exception(m)



class Graph(val numRows: Int) {

  var connections = new Array[List[Int]](numRows)
  for (i <- 0 until numRows) connections(i) = List()
  var values = Map[Tuple2[Int,Int], Double]()

  def apply(i:Int,j:Int) = {
    if((i <= 0) || (j <= 0) ) throw new BadException("aaply")
    if(connections(i-1).contains(j-1)) values((i,j))
    else 0
  }
  def update(i:Int, j:Int, number:Double) = {
    /*note, assuming directed unweighted graph*/
    /*above not true anymore */
    if((i <= 0) || (j <= 0) ) throw new BadException("update")
    connections((i-1)) = (j-1)::connections((i-1))
    connections((j-1)) = (i-1)::connections((j-1))
    values += ((i,j) -> number)
    values += ((j,i) -> number)
  }

  def printThis(){
    for (i <- 1 to numRows) {
      for(j <- 1 to numRows) {
        print(this(i,j)+ " ")
      }
      println("")
    }
    println("")
  }
  def __connected(ls:List[Int], out:List[Int]):List[Int] = {
    if(ls.isEmpty) out
    else {
      if(out.contains(ls.head+1)) {
        __connected(ls.tail, out)
      } else {
        var temp:List[Int] = __connected(connections(ls.head), (ls.head+1)::out)
        __connected(ls.tail, temp)
      }
    }
  }
  def connected(node:Int):List[Int] = {
    return __connected(connections(node-1), List[Int](node))
  }
  def connectedStack(node:Int):List[Int] = {
    var myStack = List(node-1)
    var out = List[Int]()
    while(!myStack.isEmpty){
      if(!out.contains(myStack.head+1)){ 
        out = (myStack.head+1)::out
        var temp:List[Int] = connections(myStack.head)
        myStack = temp:::(myStack.tail)
      } else {
        myStack = myStack.tail
      }
    }
    out
  }
  def connectedQue(node:Int):List[Int] = {
    var myQue = new Array[Int](numRows)
    var length:Int = 0
    var start:Int = 0
    var out:List[Int] = Nil
    myQue(start%numRows) = node - 1
    myQue(1) = 3
    length += 1
    while(length > 0){
      val num = myQue(start%numRows)
      start += 1
      length -= 1
      if(!out.contains(num+1)){
        out = (num+1)::out
       var temp:List[Int] = connections(num)
       while(!temp.isEmpty){
         myQue((start+length)%numRows) = temp.head
         length += 1
         temp = temp.tail
       }
      }
    }
    return out
  }

  def bad_min_spanning(start_node:Int):Double = {

    var active_node = start_node - 1
    var frontier_nodes = connections(active_node)
    //class Node(num:Int, weight:Double)
    class Tree(n:Int, w:Double){
      var num_elements:Int = 1
      var num:Int = n
      var weight:Double = w
      var bigger:Tree = this
      var smaller:Tree = this
      def myUpdate(n2:Int, w2:Double){
        num_elements += 1

        if(this.weight < w2){
          println("bigger", this.weight, w2, this.num, n2)
          if(bigger == this) {
            val newTree = new Tree(n2, w2)
            bigger = newTree
          } else {
            println("theoretically")
            bigger.myUpdate(n2, w2)
          }

        } else {
          println("smaller", this.weight, w2, this.num, n2)
          if(smaller == this) {
            if(bigger == this) {
              val bigTree = new Tree(num, weight)
              bigger = bigTree
              num = n2
              weight = w2
            } else {
              bigger.myUpdate(num,weight)
              num = n2
              weight = w2
            }
          } else {
            smaller.myUpdate(n2, w2)
          }
        }
      }
      def popTop():Tree = {
        if(num_elements <= 0) throw new myBad("zero elements")
        num_elements -= 1
        val temp_num = num
        val temp_weight = weight
        var temp_node = bigger
        while (temp_node != temp_node.smaller) {
          temp_node = temp_node.smaller       
        }
        bigger = temp_node.bigger
        num = temp_node.num
        weight = bigger.weight
        return new Tree(temp_num, temp_weight)
      }
    }
    val temp = frontier_nodes.head
    var myTree = new Tree(temp, this(active_node+1, temp + 1))

    for (x <- frontier_nodes.tail) {
      //weights = this(active_node+1, x+1)::weights
      myTree.myUpdate(x, this(active_node+1, x+1))
    }
    println("frontier")
    println(frontier_nodes)
    //println(weights)
    var top = myTree.popTop()
    println("top", top.num, top.weight, top.num_elements)
    top = myTree.popTop()
    println("top", top.num, top.weight, top.num_elements)
    top = myTree.popTop()
    println("top", top.num, top.weight, top.num_elements)
    top = myTree.popTop()
    println("top", top.num, top.weight, top.num_elements)


    return 3.14
  }

}

class unionGraph(members:Array[String]) extends Graph(members.length){

  var internal:Array[Int] = new Array[Int](members.length)
  for (i <- 0 until members.length) internal(i) = i
  
  def printMembers() {
    for (x <- members){
      print(x)
      print(" ")
    }
    println("")
  }
  def printStructure() {
    for (x <- internal) {
      print(x)
      print(" ")
    }
    println("")
  }

  def __find(a:String):Int =  {
    val temp_index = members.indexWhere( _ == a)
    if(temp_index == internal(temp_index)) temp_index
    else __find(members(internal(temp_index)))
  }

  def union(a:String, b:String){
    if(!members.contains(a)) throw new myBad("no a")
    if(!members.contains(b)) throw new myBad("no b")
    var a_index = members.indexWhere( _ == a)
    var b_index = members.indexWhere( _ == b)

    this(internal(a_index)+1, internal(b_index) + 1) = 1

    a_index = __find(a)
    b_index = __find(b)

    internal(b_index) = a_index
  }


  def connectedStack(a:String):List[String] = {

    var a_index = members.indexWhere( _ == a)

    var node:Int = a_index + 1 

    var myStack = List(node-1)
    var out = List[Int]()
    while(!myStack.isEmpty){
      if(!out.contains(myStack.head+1)){ 
        out = (myStack.head+1)::out
        var temp:List[Int] = connections(myStack.head)
        myStack = temp:::(myStack.tail)
      } else {
        myStack = myStack.tail
      }
    }

    var string_out = List[String]()
    out.foreach(l => string_out = members(l-1)::string_out)
    out.foreach(println)
    string_out
  }



}


object tryThis {


  def main(args: Array[String]) {
    val myMatrix = new Graph(13);
    //myMatrix.values(0)(0) = 1
    //myMatrix.values(0)(1) = 1
    //myMatrix(1,3) = 80 //ac
   

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
    var z = myMatrix.connected(1)
    println(z)
    z = myMatrix.connectedStack(1)
    println(z)
    z = myMatrix.connectedQue(1)
    println(z)
   // println(myMatrix(1,2))

    val myGraph = new unionGraph(Array("a", "b", "c", "d", "e", "f", "g",
    "h","i","j","k","l","m"))

    myGraph.printMembers()

    println(myGraph.__find("c"))
    myGraph.union("a", "g")
    myGraph.union("a", "b")
    myGraph.union("a", "c")
    myGraph.union("l", "m")
    myGraph.union("j", "m")
    myGraph.union("j", "l")
    myGraph.union("j", "k")
    myGraph.union("e", "d")
    myGraph.union("f", "d")
    myGraph.union("h", "i")
    myGraph.union("f", "e")
    myGraph.union("a", "f")
    myGraph.union("g", "e")

    myGraph.printStructure()

    myGraph.printThis()

    var zz = myGraph.connectedStack("a")
    println(zz)

    myMatrix.bad_min_spanning(1)
 


  }
}
