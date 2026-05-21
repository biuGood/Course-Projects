package streams

object CountingMain extends App {

  println("Test your Flajolet_Martin implementation here")
  val theIt=scala.io.Source.fromFile("./data/4096.ints").getLines()
  println(theIt.size)
  val a=new  Flajolet_Martin(scala.io.Source.fromFile("./data/4096.ints").getLines(),14,utils.hashes)//
//  a.hashCounts
  a.summarize(12)

}
