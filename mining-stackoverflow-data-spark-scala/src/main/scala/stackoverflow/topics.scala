package stackoverflow

import scala.io.Source

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import org.apache.log4j.{Level, Logger}
import org.apache.hadoop
import org.apache.spark.sql.DataFrameReader
import org.apache.spark.sql.DataFrameWriter
import org.apache.spark.sql.SparkSession
import org.apache.spark
import org.apache.spark.sql.SQLContext
import org.apache.spark.ml.clustering.LDA
import org.apache.spark.sql
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.ml.feature
import java.util.Properties
import org.apache.spark.sql.SparkSession

//import org.apache.spark.mllib.clustering.{LDA, DistributedLDAModel}
//import org.apache.spark.mllib.linalg.Vectors




object Topics extends App {


  val rootLogger = Logger.getRootLogger()
  rootLogger.setLevel(Level.ERROR)
  Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
  Logger.getLogger("org.spark-project").setLevel(Level.ERROR)

  val conf: SparkConf = new SparkConf().setMaster("local").setAppName("MinHash")
  val sc: SparkContext = new SparkContext(conf)
  sc.setLogLevel("ERROR") // avoid all those messages going on


  val spark = SparkSession
    .builder()
    .appName("MinHash")
    .config("spark.some.config.option", "some-value")
    .getOrCreate()
//  println("Reading from file ", filename)


  val pathRoot="./data/"
  val dFilename = pathRoot+args(0)
  val Lfilename = pathRoot+args(1)


  val dataset =spark.read.format("libsvm").load(dFilename)
  val df=dataset
//  dataset.show(5)
//  df.collect.foreach(println)

  // Trains a LDA model.
  val lda = new LDA().setK(25).setMaxIter(20).setSeed(0)
  val model = lda.fit(dataset)

  // Describe topics.
  val topics = model.describeTopics()
//  println("The topics described by their top-weighted terms:")
//  topics.show(false)
  val lang=scala.io.Source.fromFile(Lfilename).getLines().toList.tail.
    map(l=>l.split(",")).foldLeft(Map.empty[Double,String]){
    (l,a)=>l+(a(0).toDouble->(a(1)))
  }
  def getS(s:Double):String={
    val a:Option[Any] = lang.get(s)
    val i = (a match {
      case Some(x:String) => x
      case _ => null
    })
    //        println(i)
    return i
  }
  val d=topics.collect()
  d.foreach(r=>
    {
//      print("cluster "+r.getInt(0)+"\n")
      val p1=r.getList(2)
      val p2=p1.toArray.filter(a=>a.toString.toFloat>=0.05)//.foreach(println)
      val p3=p2.size
//      println(p3)
      if (p3!=0) {
        val p4=r.getList(1).toArray.take(p3)
        val p5=p4.zip(p2)
        print("cluster " + r.getInt(0) + "\n")
        p5.foreach(x => print(getS(x._1.toString.toDouble + 1.0) + "," + x._2 + "\n"))
        print("\n")
      }
    }
  )









  sc.stop()


}
