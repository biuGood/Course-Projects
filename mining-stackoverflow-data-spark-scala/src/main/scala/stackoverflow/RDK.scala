package stackoverflow

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import java.io.{File, PrintWriter}


object RDK extends App {
  val rootLogger = Logger.getRootLogger()
  rootLogger.setLevel(Level.ERROR)
  Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
  Logger.getLogger("org.spark-project").setLevel(Level.ERROR)

  val conf: SparkConf = new SparkConf().setMaster("local").setAppName("MinHash")
  val sc: SparkContext = new SparkContext(conf)
  sc.setLogLevel("ERROR") // avoid all those messages going on

  def isIntByRegex(s : String) = {
    val pattern = """^(\d+)$""".r
    s match {
      case pattern(_*) => true
      case _ => false
    }
  }


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


  //read post from file
  val postsMap=sc.textFile("./data/data/posts.csv")
    .filter(line=>line.split(",").length>8)//filter the record size<8
    .filter(line=>{
      val l=line.split(",")
      isIntByRegex(l(8))&&isIntByRegex(l(1))&&(if(l(1).toInt==1)isIntByRegex(l(0)) else if(l(1).toInt==2)isIntByRegex(l(2)) else false)
    })//fiter when userid and postid(parentid) arent integers
    .map(line=>{
      val l=line.split(",")
      if(l(1).toInt==2)(l(2),l(8))
      else (l(0),l(8))
    })//(postid,userid)    //(postid,language)

    // join
//    .join(tagsCountMap)
//    .map(line=>{
//      (line._1,line._2._1,line._2._2)
//    })//(postid,userid,language)
//    .groupBy(_._2)//(string,iter(s,s,s))
//    .filter(l=>l._2.toSet.size>1)

    .join(tagsCountMap)//(postid,(userid,language))
    .groupBy(_._2)
    // ignore languages that they have used only once
    .filter(x=>x._2.size>1)
    .map(line=>{
      line._1
    })//(userid,language)
println("2")




  //ignore users who have only used one programming language
  val result= postsMap
    .groupByKey().filter(x=>isIntByRegex(x._1)).filter(x=>x._2.toSet.size>1)
    .map(line=>{
    //sort
    (line._1,line._2.toSeq.sorted)
  }).collect().toList
println("3")
  //print to file
    val file=new File("./data/mytry5.txt")
    if(!file.exists())
      file.createNewFile();
    val writer = new PrintWriter(file)
    result.foreach(line=>{
      writer.println(line._1+","+line._2.mkString(","))
    })

    writer.close()
  sc.stop()



}
