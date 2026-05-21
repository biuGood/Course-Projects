package streams

object FilterMain extends App {

  println("Test your Bloom Filter implementation here")
//  val a=new Bloom_Filter("./data/test-movies.txt",0.0001,utils.hashes)
//  val try1=scala.io.Source.fromFile("./data/test10.txt").getLines()
//  a.parameters()
//  val r=try1.map(x=>a.in(x))
//  r.foreach(println)

//  val a=new Bloom_Filter("./data/test10.txt",0.0001,utils.hashes)
////  a.parameters()
//  println(a.in("2"))


//  val a=new Bloom_Filter("./data/who-movies.txt",0.0001,utils.hashes)
//  val try1=scala.io.Source.fromFile("./data/movies.ints").getLines()
//  a.parameters()
//  try1.map(x=>a.in(x)).filter(x=>x==true)
////  val r2=try1.filter(x=>a.in(x))
//  println("done")
//  println(r)
//  r.foreach(println)

//  val a=new Bloom_Filter("./data/who-movies.txt",0.0001,utils.hashes)
//  val try1=scala.io.Source.fromFile("./data/test-movies.txt").getLines()
//  a.parameters()
//  val r=try1.map(x=>a.in(x))
//  r.filter(x=>x==false).foreach(println)

//
//  val test = scala.io.Source.fromFile("./data/4096.ints").getLines().toList
//  test.foreach(str => {
//    utils.hashes.take(filterparams.nHashes).map(f => f(str))
//      .foreach(num => bloomfilter.bloomFilter.add(num))
//  })
//
//
//  val count = test.foldLeft(0) {
//    case (acc, str) => {
//      if (bloomfilter.in(str)) acc + 1
//      else {
////        println("not in: " + str)
//        acc
//      }
//    }
//  }
//
//  println("accuracy = " + count)


//Gr's test code:
  val filterFileName = "./data/test10.txt"
  val streamFileName1 = "./data/test10stream.txt"
  val streamFileName2 = "./data/petnames_1.txt"
  val streamFileName3 = "./data/petnames_2.txt"
  val fpRate = 0.25
  val tfilter = new Bloom_Filter(filterFileName, fpRate, utils.hashes)
  val test1 = scala.io.Source.fromFile(streamFileName1).getLines().filter(x=>tfilter.in(x)).toSet
  val test2 = scala.io.Source.fromFile(streamFileName2).getLines().filter(x=>tfilter.in(x)).toSet
  val test3 = scala.io.Source.fromFile(streamFileName3).getLines().filter(x=>tfilter.in(x)).toSet
  val size1:Int = scala.io.Source.fromFile(streamFileName1).getLines().size - 1 //know one will be legit so eliminate
  val size2:Int = scala.io.Source.fromFile(streamFileName2).getLines().size
  val size3:Int = scala.io.Source.fromFile(streamFileName3).getLines().size
  val fp1 = (test1.size -1).toDouble/size1.toDouble
  val fp2 = (test2.size -1).toDouble/size2.toDouble
  val fp3 = (test3.size -1).toDouble/size3.toDouble
  println(s"\nActual false positives 1: ${utils.truncate(fp1, 5)}")
  println(s"\nActual false positives 2: ${utils.truncate(fp2, 5)}")
  println(s"\nActual false positives 3: ${utils.truncate(fp3, 5)}")
  println(s"\nAverage false positives: ${utils.truncate((fp1+fp2+fp3).toDouble/3.0, 5)} versus set False Positive Rate of ${fpRate}\n")

}
