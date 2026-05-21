package stackoverflow

import scala.io.Source
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import org.apache.log4j.{Level, Logger}
import org.apache.hadoop
import org.apache.spark.sql.{DataFrameReader, DataFrameWriter, Row, SQLContext, SparkSession}
import org.apache.spark
import org.apache.spark.ml.clustering.LDA
import org.apache.spark.sql
import org.apache.spark.sql.Row
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.ml.feature

import java.util.Properties
import org.apache.spark.sql.catalyst.plans._
import org.apache.spark.sql.catalyst.plans.{Inner, LeftOuter}

import java.io.{File, PrintWriter}

object basket {
  def main(args: Array[String]): Unit = {
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



    val languagesMap=sc.textFile("./data/data/languages.csv").map(line=>{
      val languageParams=line.split(",")
      (languageParams(1),languageParams(0))
    }).collect().toMap
    //  languagesMap.foreach(println)
    //broadcast the languages map
    val bcLanguagesMap=sc.broadcast(languagesMap)
    //  print(bcLanguagesMap.value)//fantom->index

    //read tags from file,filter no-language tag,and filter by languageTag count
    val tagsMap=sc.textFile("./data/data/poststags.csv").map(line=>{
      val tagsParams=line.split(",")
      (tagsParams(0),tagsParams(1))
    })//(postid,tag)
      //filter no-language tag
      .filter(x=>bcLanguagesMap.value.contains(x._2))//（postid,tag）

    //ignore any post that has been tagged with two or more programming languages.
    val tagsCountMap=tagsMap.groupByKey().filter(x=>x._2.toSet.size==1)
      .map(line=>{
        (line._1,line._2.toList(0))
      })//(postid,language)
    //val bcTagsMap=sc.broadcast(tagsCountMap.collect())//sc.broadcast(tagsCountMap.collect().toMap)
    println("1")


    def isIntByRegex(s : String) = {
      val pattern = """^(\d+)$""".r
      s match {
        case pattern(_*) => true
        case _ => false
      }
    }

//    val test=spark.read.text("./data/baskets.txt")
//    print("number: "+test.count())


    val df_posts = spark.read.options(Map("header"->"true")).format("csv").csv("./data/data/posts.csv")
    val d1=df_posts.withColumn("postid", col("postid").cast(IntegerType))
          .withColumn("userid", col("userid").cast(IntegerType))
          .withColumn("parentid", col("parentid").cast(IntegerType))
          .withColumn("posttypeid",col("posttypeid").cast(IntegerType))
    //    d1.show(false)//turn to integertype
    val d2=d1.select("userid","postid","parentid","posttypeid")
      .filter(col("userid").isNotNull
        && col("posttypeid").isNotNull)
    val d2_1=d2.filter(line=>{
      if(line.getInt(3)==1) isIntByRegex(line.getInt(0).toString)
      else if(line.getInt(3)==2) isIntByRegex(line.getInt(2).toString)
      else false
    })
//    println("the schema is:")
    //StructField(userid,IntegerType,true),StructField(postid,IntegerType,true),StructField(parentid,IntegerType,true), StructField(posttypeid,IntegerType,true))
//println(d2_1.schema)

   val d3=d2_1.rdd
     .map(line=>{
       if(line.getInt(3)==2) (line(2).toString,line(0).toString)
       else (line(1).toString,line(0).toString)
     })
     .join(tagsCountMap)//(postid,(userid,language))
     .groupBy(_._2)//((userid,language),iter(postid,(userid,language)))
     .filter(_._2.size>1)
     .map(line=>line._1)//(userid,language)
//    val dt=d3
//    dt.take(3).foreach(println)//(3829487,objective-c)
    println("2")

    //ignore users who have only used one programming language
    val result= d3
      .groupByKey()
      .filter(x=>x._2.toSet.size>1)
      .map(line=>{
        //sort
        (line._1.toInt,line._2.toSeq.sorted)
      }).collect().toList.sortBy(_._1)
    println("3")
    //print to file
    val file=new File("./data/baskets.txt")
    if(!file.exists())
      file.createNewFile();
    val writer = new PrintWriter(file)
    result.foreach(line=>{
      writer.println(line._1+","+line._2.mkString(","))
    })

    writer.close()
    sc.stop()









  }

}
