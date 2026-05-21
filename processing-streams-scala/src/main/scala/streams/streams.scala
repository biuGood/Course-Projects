import scala.collection.mutable.BitSet

package streams {

  import scala.collection.mutable

  class Bloom_Filter (fileName: String,
    falsePositiveRate: Double,
    hashes: List[Hash_Function]) {

    // leave this unchanged
    val bloomFilter : BitSet = BitSet()

    def parameters(): Filter_Parms = {
      // your code goes here
      val lines=scala.io.Source.fromFile(fileName).getLines()
      val lines_1=lines.map(l=>l.split(",").foldLeft(List.empty[String]){(x,y)=>x:+y})
      val m=lines_1.foldLeft(0){case (x,y)=>x+1}
      val n_1=(-1)*(m*math.log(falsePositiveRate))/(math.log(2)*math.log(2))
      val n_2=utils.truncate(n_1,7)
      val n=n_2.ceil.toInt
      val k=(n.toDouble/m*math.log(2)).ceil.toInt
      val hashes_k=hashes.take(k)
//      println(m)
//      println(n)
//      println(k)

      val l1=scala.io.Source.fromFile(fileName).getLines()
      def hash_n(hF:List[Hash_Function],st: String,n:Int,bl:mutable.BitSet):mutable.BitSet={
      val s=hF.foreach(x=>bl.update(x(st)%n,true))//
        return bl
      }
      val hsB=l1.foldLeft(bloomFilter){ (x, y)=>hash_n(hashes_k,y,n,x)}
      return Filter_Parms(m,n,k,hsB)


    }


    def in(v:String):Boolean = {
      val getRe=parameters()
      val h=hashes.take(getRe.nHashes)
      val theN=getRe.nBits
      val b=mutable.BitSet()
      h.foreach(x=>b(x(v)%theN)=true)
      val x=b.foldLeft(0){(a,b)=>a+(if(getRe.filter(b)==true)1 else 0)}
      return x==b.size

    }

  }
 
  class Flajolet_Martin(
    s : Iterator[String],
    bits: Int,
    hashes: List[Hash_Function]) {
    
    // your code goes here

    val hashCounts: List[Int] = {
      def getNum(theS:String,hF:List[Hash_Function],hC:List[Int],max_n:Int):List[Int]={
//after mod
        def f1(x:Int,n:Int):Int={
          if(x%2==0&&x!=0){
            return f1(x/2,n+1)
          }
          else {
            return n
          }
        }
//no mode
        def f2(x:Int,nu:Int,n:Int):Int={
          if(x%2==0&&x!=0&&n>0){
            return f2(x/2,nu+1,n-1)
          }
          else {
            return nu
          }
        }

        val r3=hF.map(x=>f1(x(theS)%max_n,0))
//        val r3=hF.map(x=>f2(x(theS),0,bits))
        val fin=(r3.zip(hC)).map(x=>(if(x._1>=x._2)x._1 else x._2))
        return fin
      }
      val max_num=(math.pow(2,bits)+1).toInt
      val fin_r=s.foldLeft(List.fill(hashes.size)(0)){(x,y)=>getNum(y,hashes,x,max_num)}
//      println(fin_r)
      fin_r.map(x=>(math.pow(2,x)).toInt)




//
    }



    def summarize(groupSize:Int):Double = {
      def getMe(theL:List[Int]):Double={
        val sor_L=theL.sorted
//        println("theL:"+theL)
//        println("sort_L:"+sor_L)
        val num=theL.size
        val med={if(num%2!=0)sor_L((num*1.0/2).ceil.toInt-1) else (sor_L(num/2-1)+sor_L(num/2))*1.0/2}
        return med
      }
      val re1=hashCounts.grouped(groupSize)
      val re2=re1.map(x=>getMe(x)).toList
//      println(re2)
      val ave=(re2.sum)*1.0/(re2.size)
      print("ave is:" +ave)
      return ave
    }


  }

  object reservoirSample {
  
    def process(
//      s : Iterator[Int],
      s : Iterator[Int],
      sizeSample: Int,
      r: scala.util.Random,
      queries: List[Standing_Query]
    )  = {
//      val (st1,st2)=s.zipWithIndex.splitAt(sizeSample)//split the st
//      val st1_toV=st1.map(x=>x._1).toVector
//      val st2_f=st2.foldLeft(st1_toV){(x,y)=>if(r.nextDouble()<(sizeSample*1.0/y._2))x.updated(r.nextInt(sizeSample),y._1) else x}
//      queries.map(x=>x(st2_f,sizeSample))

      val (st1,st2)=s.zipWithIndex.splitAt(sizeSample)//split the st
      val st1_toV=st1.map(x=>x._1).toVector
      queries.map(m=>m(st1_toV,sizeSample))
      val st2_f=st2.foldLeft(st1_toV){case(x,y)=>
        if(r.nextDouble()<(sizeSample*1.0/y._2))
        {
          val tr=x.updated(r.nextInt(sizeSample),y._1)
          queries.map(m=>m(tr,sizeSample))
          tr
        }
        else {
          x
        }
       }

    }
    
    // your code goes here

      // remember that the algorithm assumes
      // that the position of the element in the stream
      // starts at zero (not 1)
      // thus is the stream is 10,20,30
      // element 1 is 10, element 2 is 20 and element 3 is 30

      // use r.nextDouble to generate a random number between [0,1)
      // use r.nextInt(n) to generate a number between 0 and n-1

      
  }

}

