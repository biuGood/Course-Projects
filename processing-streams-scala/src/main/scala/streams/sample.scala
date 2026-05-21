package streams


object SampleMain extends App {

  println("test your Reservoir sample implementation here")

//  val c=scala.io.Source.fromFile("./data/test10.txt").getLines().map(item => println("here: " + item))
  val b=scala.io.Source.fromFile("./data/100ordered.ints").getLines().map(x=>x.toLong).toList
  val n=b.size
  val sB=b.sorted
  val Bav=b.sum*1.0/b.size
  print("overal ave="+Bav+"\n")
  val me={if(n%2!=0)sB((n*1.0/2).ceil.toInt-1) else (sB(n/2-1)+sB(n/2))*1.0/2}
  print("overal median="+me+"\n")

  val a=scala.io.Source.fromFile("./data/100ordered.ints").getLines().map(x=>x.toInt)
  def em(v:Vector[Int],n:Int):Unit={
  }
  def pri(v:Vector[Int],n:Int):Unit={
    println("the vector:")
    println(v)
    println("the int:")
    println(n)
  }
  def ave(v:Vector[Int],n:Int):Unit={
    val pr=v.map(x=>x.toLong)
    val re=pr.sum*1.0/n
    print("ave="+re+"\n")
  }
  def med(v:Vector[Int],n:Int):Unit={
    val sortV=v.sorted
    val med={if(n%2!=0)sortV((n*1.0/2).ceil.toInt-1) else (sortV(n/2-1)+sortV(n/2))*1.0/2}
    print("median="+med+"\n")
  }
  reservoirSample.process(a,9,scala.util.Random,List(pri,ave,med))






}
