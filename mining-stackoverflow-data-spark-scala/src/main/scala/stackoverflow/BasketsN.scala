package stackoverflow

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.fpm.FPGrowth
import org.apache.spark.{SparkConf, SparkContext}

import java.io.{File, PrintWriter}


object Baskets_4 extends App {

  val rootLogger = Logger.getRootLogger()
  rootLogger.setLevel(Level.ERROR)
  Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
  Logger.getLogger("org.spark-project").setLevel(Level.ERROR)

  val conf: SparkConf = new SparkConf().setMaster("local").setAppName("MinHash")
  val sc: SparkContext = new SparkContext(conf)
  sc.setLogLevel("ERROR") // avoid all those messages going on

  val pathRoot="./data/"
  val languagesFilename = pathRoot+args(1)
  val filename = pathRoot+args(0)

  //read languages from file
  val languagesMap=sc.textFile(languagesFilename).filter(x=>x!="index,language").map(line=>{
    val languageParams=line.split(",",-1)
    (languageParams(0).toInt,languageParams(1))
  }).collect().toMap
  //broadcast the languages map
  val bcLanguagesMap=sc.broadcast(languagesMap)


  //read document file,get language map
  val documentMap=sc.textFile(filename).map(line=>{
    val documentMap=line.split(" ",-1)
    val languagesNo = documentMap.takeRight(documentMap.size - 1).take(documentMap.size-2)
   //bcLanguagesMap.value.getOrElse(x.take(x.length-2).toInt,"")
    languagesNo.map(line=>{
      bcLanguagesMap.value.getOrElse(line.take(line.length-2).toInt,"")
    })
  })

  // run fp-growth
  val minSupport = 0.02
  val numPartitions = 3
  val confidence = 0.5
  val model = new FPGrowth().
    setMinSupport(minSupport).
    setNumPartitions(numPartitions).run(documentMap)

  //get pair - confidence >0.5
  val confidenceMap=model.generateAssociationRules(confidence).map(line=>{
    (line.antecedent++line.consequent).sorted.mkString(",")
  }).collect()
  //confidenceMap.foreach(l=>{println(l)})

  //get freq
  val result=model.freqItemsets.filter(line=>line.items.length>1).map { itemset =>
    (itemset.freq,itemset.items.sorted.mkString(","))
  }
    //filter pair - confidence >0.5
    //.filter(x=>confidenceMap.contains(x._2))
    //collect,sort the result
    .collect().toList.sortWith((left, right) => left._1 > right._1)


  //print to file
  val file=new File("./data/part2.txt")
  if(!file.exists())
    file.createNewFile()
  val writer = new PrintWriter(file)
  result.foreach(line=>{
    writer.println(line._1+" "+line._2)
  })
  writer.close()
  sc.stop()
}
