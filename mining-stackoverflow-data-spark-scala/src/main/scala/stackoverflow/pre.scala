package stackoverflow
import scala.io.Source
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import java.io.PrintWriter
import java.io.File
object pre {
    def main(args: Array[String]): Unit = {
//      print("yea")
      //get the language map
      val lang=scala.io.Source.fromFile("./data/data/languages.csv").getLines().toList.tail
      val langM=lang.map(l=>l.split(",")).foldLeft(Map.empty[String,Int]){
        (l,a)=>l+(a(1)->(a(0).toInt))
      }
//      print(langM)
//      writer.flush()

      def getIndex(s:String):Int={
  val a:Option[Any] = langM.get(s)
  val i = (a match {
    case Some(x:Int) => x
    case _ => -1
  })
//        println(i)
  return i
}

      val basket=scala.io.Source.fromFile("./data/baskets.txt").getLines()
      val baskets=basket.map(b=>b.split(","))
    val writer = new PrintWriter(new File("./data/documents.txt"))
//      writer.flush()
    def write(a:Array[String]):Unit={
        val tem=a.tail
        val uid=a(0)
        writer.print(uid+" ")
        val tem1=tem.map(x=>getIndex(x)).sorted
//        tem.foreach(x=>writer.print(getIndex(x)+":"+1+" "))
        tem1.foreach(x=>writer.print(x+":"+1+" "))
        writer.print("\n")
//        writer.close()

      }

    val b2l=baskets.foreach(x=>write(x))
      writer.close()





    }
}
