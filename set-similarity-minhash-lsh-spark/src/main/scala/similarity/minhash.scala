package similarity

/*
Your student name
*/

import org.apache.spark.rdd.RDD
import org.spark_project.jetty.util.HttpCookieStore.Empty
import org.apache.spark

object minhash {


  def shingle_line(stList: List[String], shingleSize:Int):Shingled_Record = {
    //    println("iamhereshingle")
    class MyCustomException(s: String) extends Exception(s) {}
    val myid=stList(0)
    val stL_0=stList.tail //remove the id
    val num1=stL_0.size
    val shingleSet ={
      if (num1<shingleSize){Set()}
      else{val slice=stL_0.sliding(shingleSize,1);slice.toSet
      }}
    if (shingleSet.isEmpty){throw new MyCustomException("the record size is less than the shinglesize.")}
    val shingles:Option[Set[Int]]={
      if(shingleSet!=None){Some(shingleSet.foldLeft(Set.empty[Int]){(a,b)=>a+utils.hash_string_lst(b)})}
      else None
    }
    return (Shingled_Record(myid,num1,shingles))

  }

  def minhash_record(r: Shingled_Record, hashFuncs: List[Hash_Func]): Min_Hash_Record = {
    val myid=r.id
    val thesets=r.shingles.get
    def minget(hashF:Hash_Func,theset:Set[Int]):Int={
      val res=theset.foldLeft(Set.empty[Int]){(ab,cd)=>ab+hashF(cd)}
      val resu=res.min
      return resu
    }
    val re1=hashFuncs.foldLeft(Vector.empty[Int]){(a,b)=>a:+(minget(b,thesets))}
    return (Min_Hash_Record(myid,re1))

  }

  def compute_jaccard_pair(a:Shingled_Record, b: Shingled_Record): Similarity = {
    //    println("iamherejacp")
    val id_A=a.id
    val id_B=b.id
    val inter=a.shingles.get.intersect(b.shingles.get)
    val uni=a.shingles.get++b.shingles.get
    val simi=inter.size.toDouble/uni.size.toDouble
    return (Similarity(id_A,id_B,simi))
  }

  def find_jaccard_matches(records: RDD[Shingled_Record], minSimilarity:Double): Matches = {
//    val a=records.cartesian(records).filter(x=>x._1.id<x._2.id ).persist
//    val a1=a.map(y=>Similarity(y._1.id,y._2.id,compute_jaccard_pair(y._1,y._2).sim)).persist
//    a.unpersist()
//    val a2=a1.filter(x=>x.sim>=minSimilarity)
//    a1.unpersist()
//    val re=a2.collect()
//    return(re)

//    val a=records.cartesian(records).filter(x=>x._1.id<x._2.id ).map(y=>Similarity(y._1.id,y._2.id,compute_jaccard_pair(y._1,y._2).sim)).filter(x=>x.sim>=minSimilarity).persist
//    val re=a.collect()
//    a.unpersist()
//    return(re)

    val a=records.cartesian(records).filter(x=>x._1.id<x._2.id ).map(y=>Similarity(y._1.id,y._2.id,compute_jaccard_pair(y._1,y._2).sim)).filter(x=>x.sim>=minSimilarity)
    val re=a.collect()
    return(re)
  }

  def find_minhash_matches(records:RDD[Min_Hash_Record], minSimilarity:Double): Matches = {
    println("iamhereminma")
    def calcuSi(a:Min_Hash_Record,b:Min_Hash_Record):Double={
      val c=a.minHashes.toList
      val c2=b.minHashes.toList
      val zip=c.zip(c2)
      val inter=zip.foldLeft(0){(m,n)=>m+(if(n._1==n._2)1 else 0)}
      val siz=c.size     //c.size
      val sim=inter.toDouble/(siz.toDouble)
      return (sim)

    }
//    val b=records.cartesian(records).filter(x=>x._1.id<x._2.id).persist
//    val b1=b.map(y=>Similarity(y._1.id,y._2.id,calcuSi(y._1,y._2))).filter(x=>x.sim>=minSimilarity)
//    val re2=b1.collect
//    b.unpersist()
//    return (re2)

//    val b=records.cartesian(records).filter(x=>x._1.id<x._2.id).map(y=>Similarity(y._1.id,y._2.id,calcuSi(y._1,y._2))).filter(x=>x.sim>=minSimilarity)
//    val re=b.collect()
//    return re

    val b=records.cartesian(records).filter(x=>x._1.id<x._2.id).map(y=>Similarity(y._1.id,y._2.id,calcuSi(y._1,y._2))).filter(x=>x.sim>=minSimilarity).persist()
    val re=b.collect()
    b.unpersist()
    return re

//    val b=records.cartesian(records).filter(x=>x._1.id<x._2.id).map(y=>Similarity(y._1.id,y._2.id,calcuSi(y._1,y._2))).filter(x=>x.sim>=minSimilarity)
//    return (b.collect())

  }

  def find_lsh_matches(minHashes: RDD[Min_Hash_Record], minSimilarity: Double, bandSize: Int): Matches = {
    println("iamherelsh")
    def calcus(l1:Min_Hash_Record,l2:Min_Hash_Record,ban:Int):Double={
      val li1=l1.minHashes.toList
      val li2=l2.minHashes.toList
      val cen1=li1.zip(li2)
      val inter=cen1.foldLeft(0){(m,n)=>m+(if(n._1==n._2)1 else 0)}
      val fenm=cen1.size
      val sim=inter.toDouble/fenm
      return sim
    }
    val a=minHashes.map(x=>Min_Hash_Record(x.id,x.minHashes.toList.sliding(bandSize,bandSize).toList.foldLeft(Vector.empty[Int]){(a,b)=>a:+utils.hash_int_lst(b)})).persist()
    val a1=a.cartesian(a).filter(x=>x._1.id<x._2.id).map(x=>Similarity(x._1.id,x._2.id,calcus(x._1,x._2,bandSize))).filter(x=>x.sim>=minSimilarity)
    val res=a1.collect()
    a.unpersist()
    return res

  }

}


