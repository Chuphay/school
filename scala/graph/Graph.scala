
class BadException(message: String) extends Exception(message)
class myBad(m:String) extends Exception(m)



class Graph(val numRows: Int) {
  //implicit def double2Scalar(d : Double) : Scalar = new Scalar(d)

  /*not sure why it's Matrix, really a graph*/ 
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
    myMatrix(1,2) = 1 //ab
    myMatrix(1,3) = 2 //ac
    myMatrix(1,6) = 3 //af
    myMatrix(1,7) = 4 //ag
    myMatrix(4,5) = 5 //de
    myMatrix(4,6) = 6 //df
    myMatrix(5,6) = 7 //ef
    myMatrix(5,7) = 8 //eg
    myMatrix(8,9) = 9 //hi
    myMatrix(10,11) = 10 //jk
    myMatrix(10,12) = 11 //jl
    myMatrix(10,13) = 12 //jm
    myMatrix(12,13) = 13 //lm

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
 


  }
}
