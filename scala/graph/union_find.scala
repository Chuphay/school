class myBad(m:String) extends Exception(m)

class unionGraph(members:Array[String]){
  var internal:Array[Int] = new Array[Int](members.length)
  for (i <- 0 until members.length) internal(i) = i
  
  def printMembers {
    for (x <- members){
      print(x)
      print(" ")
    }
    println("")
  }
  def printStructure {
    for (x <- internal) {
      print(x)
      print(" ")
    }
    println("")
  }

  def union(a:String, b:String){
    if(!members.contains(a)) throw new myBad("no a")
    if(!members.contains(b)) throw new myBad("no b")
    val a_index = __find(a)
    val b_index = __find(b)

    internal(b_index) = a_index
  }

  def __find(a:String):Int =  {
    val temp_index = members.indexWhere( _ == a)
    if(temp_index == internal(temp_index)) temp_index
    else __find(members(internal(temp_index)))
  }




}

object unionTry {

  def main(args: Array[String]) {
    val myGraph = new unionGraph(Array("a", "b", "c", "d", "e", "f", "g",
    "h","i","j","k","l","m"))

    myGraph.printMembers
    //myGraph.union("a","z")

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

    myGraph.printStructure

    println(myGraph.__find("a"))
    println(myGraph.__find("b"))
    println(myGraph.__find("c"))

  }
}
