//https://www.safaribooksonline.com/library/view/scala-cookbook/9781449340292/ch01s07.html


val nums = "\\d+".r

val s = "((35 2 *) (12 (3 1 *) +) *)"

val matches = nums.findFirstMatchIn(s)

matches match {

  case Some(s) => println(s"Found $s")
  case None => println("no match")
}






def get_total(x:List[Int], n:Int):Int = x match {
  case Nil => 0
  case _ => x.head*n + get_total(x.tail, 10*n)
}

def isDigit(c:String) = {if (nums.findFirstMatchIn(c) != None) true; else false}

var myNum = 0
var myStack:List[Any] = Nil
var keep_track_of_digit:List[Int] = Nil

s.foreach {e => 

  if(isDigit(e.toString) == true) {
    keep_track_of_digit = e.toString.toInt::keep_track_of_digit

  } else {

    if(keep_track_of_digit != Nil){
      myNum = get_total(keep_track_of_digit,1)
      myStack = myNum::myStack
      keep_track_of_digit = Nil
    }

    if((e == '*') || (e == '+')){

      val temp = (e::myStack.head::myStack.tail.head::Nil)
      myStack = myStack.tail.tail
      myStack = temp::myStack

    }
  }
}


println(myStack)


abstract class InSet { def contains(y:Int):Boolean; def incl(y:Int):InSet}

class mySet(val x:Int, val left:InSet, val right:InSet) extends InSet {

  def contains(y:Int):Boolean = {true}
  def incl(y:Int):InSet = new mySet(x, left.incl(y),right)
}

object e extends InSet {def contains(y:Int):Boolean = false; def incl(y:Int):InSet = new mySet(y,e,e)}

val f = new Function[Int,Int] { def apply(x:Int) = x*x}
println(f(7))

println("Sorting ...")
val zee = Array(10,3,-4,17,22,-13,1)
zee.update(0,11)
for (i <- 0 to zee.length - 1){
  val x = zee(i)

  var best_j = i
  var best_y = x
  for((y,j) <- zee.zipWithIndex){
    if(j>i){
      if(y<best_y){

        best_j = j
        best_y = y

      }
    }
  }

  zee.update(i, best_y)
  zee.update(best_j, x)
} 

zee.foreach(e => println(e))

trait myList[T] {

  def isEmpty:Boolean
  def head:T
  def tail:myList[T]

}

class Cons[T] (val head:T, val tail:myList[T]) extends myList[T] {

def isEmpty = false
}

class myNil[T] extends myList[T] {
  def isEmpty = true
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.tail")
}


abstract class Nat{

  def isZero:Boolean
  //def + (that:Nat): Nat
  //def - (that:Nat): Nat  
  def predecessor:Nat 
  def successor: Nat 
}

object Zero extends Nat{

  def isZero = true
  def predecessor = throw new Error("zero pred")
  def successor = new Successor(this)
}

class Successor(n: Nat) extends Nat {
  def isZero = false
  def predecessor = n
  def successor = new Successor(this)
}

val one = Zero.successor
val two = one.successor
println(two.predecessor == one)
