val numPattern = "([0-9]+)".r
val allNum = "([\\(\\)\\-0-9\\. \\+\\*%\\/]+)".r
val is = "is".r
val variable = "variable".r
val assign = "assign".r
val definition = "definition".r
var env = scala.collection.mutable.Map("env" -> 0)
env("a") = 1
println(env("a"))

def eval(x:Any):Unit = x match{
  case x:Int => println("Int",x)
  case x:Double => println("Double",x)
  case numPattern(num) => println("cool",num.toInt-4)
  case is() => println("is")
  case variable() => println("variable")
  case "assign" => println("nice")
  case allNum(num) => println("all!   ",num)

  case _ => println("unknown")
}


val zere = new scala.tools.nsc.Interpreter
zere.interpret("3+4")

val myString = "is 45 (3/4 +2)"
println("lets do it")
myString.split(" ").foreach(eval)


