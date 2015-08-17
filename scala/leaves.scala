def num_leaves(x:Any):Int = x match {
  case Nil => 0
  case _:Int => 1
  case x:List[_] => leaves(x.head)+leaves(x.tail)
  case _ => {
    println("fail")
    return -1
  }
}

def map(x:Any, f:(Int) => Int):List[Any] = x match {
  case Nil => Nil; 
  case (s:Int)::q =>f(s)::map(q,f); 
  case s::q => map(s,f)::map(q,f); 
  case _ => {
    println("fail")
    return Nil
  }
}

map(List(1,List(2,3),4),(x:Int)=>x*x)
