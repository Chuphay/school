class Point(xs:Int, ys:Int){var x = xs; var y = ys}
class Line(fs:Point, ls:Point){var head:Point = fs; var tail:Point = ls} 
class Polygon(pts:Array[Point]){var points:Array[Point] = pts}

def ccw(p0:Point, p1:Point, p2:Point):Int = {
  val dx1:Int = p1.x - p0.x
  val dy1:Int = p1.y - p0.y
  val dx2:Int = p2.x - p0.x
  val dy2:Int = p2.y - p0.y
  if (dx1


  return 0
}


val x = new Point(3,4)
val y = new Point(7,5)
val w = new Point(2,3)
println(x.x) 
val z = new Line(x,y)
println(z.tail.y)
val u = new Polygon(Array(x,y,w))

