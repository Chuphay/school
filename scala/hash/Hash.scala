class Hash {
  val n = 13
  var internals = new Array[List[Int]](n)
  var internal_double = new Array[List[Double]](n)
  for ( i <- 0 until n) {

    internals(i) = Nil
    internal_double(i) = Nil
  }

  def apply(x:Int):Double =  {
    return __find(x)
  }

  def update(x:Int, d:Double) = {
    val zee:Int = x%n
    internals(zee) = x::internals(zee)
    internal_double(zee) = d::internal_double(zee)

  }
  def __find(x:Int):Double = {
    val zee:Int = x%n
    var myList = internals(zee)
    var myDouble = internal_double(zee)
    var out = 0.0
    var flag = false
    while (myList != Nil) {
      if(myList.head == x){
        flag = true
        out = myDouble.head
        myList = myList.tail
        myDouble = myDouble.tail
      } else {
        myList = myList.tail
        myDouble = myDouble.tail
      }
    }
    if(flag){
      return out
    } else {
      throw new Exception("my error message")
    }
  }

}


object tryThis {

  def main(args:Array[String]) = {
    println("on")
    var x = new Hash
    x(1) = 6.34
    x(13) = 11.43
    println(x.__find(1))
    println(x(1))


  }
}
