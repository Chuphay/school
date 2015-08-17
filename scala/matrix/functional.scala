class BadException(message: String) extends Exception(message)

object functional {
  type Row = List[Double]
  type Matrix = List[Row]
  def show(x:Row):Unit = x match {
    case x::q => {
      print(x+" ")
      show(q)
    }
    case Nil => println("")
    case _ => println("Error!!!")
  }
  def Show(x:Matrix):Unit = x match {
    case x::Nil => {show(x);}
    case x::q => {
      show(x)
      Show(q)
    }
    case _ => println("Error!!!")
  }
  def weirdDot(x:Row, y:Row):Double = x match {   
    case a::Nil =>{
      if(!y.tail.isEmpty) throw new BadException("different lengths")
      a*y.head
    }
    case a::q => {
      if(y.tail.isEmpty) throw new BadException("different lengths")
      a*y.head + weirdDot(q, y.tail)
    }
    case _ => throw new BadException("fail!!!")
  }

  def transpose(m:Matrix):Matrix = m match {
    case (x:Row)::Nil => {
      var out:Matrix = List()
      x.foreach(l => println(l))
      x.foreach(l => out = ((l::Nil):Row)::out)
      //out = ((3::Nil):Row)::out
      out
    }
    case _ => throw new BadException("fail!!!")
  }



  

  def main(args: Array[String]) {


    val x:Row = List(1,2,3,4)
    val y:Row = List(4,-3,9,7)
    val z:Row = List(-1,2,0,11,12)
    val a:Matrix = List(x,y,z)
    show(x)
    Show(a)
    println(weirdDot(x,x))
    val b:Matrix = List(x)
    val c = transpose(b)
    c.foreach(println)
    Show(c)
    
  }
}
