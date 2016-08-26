package groovyInAction

class CollectionsTest {
	//range
	static void rangeTest(){
		println ((0..10).contains(0))
		println ((0..10).contains(-1))
		println ((0..<10).contains(10))
		
		def a=1..10
		println a==new IntRange(1,10)
		
		a=(0.0..1.0)
		println a.contains(0.5)
		
		def today=new Date()
		def yesterday=today-1
		def dateRange=(yesterday..today)
		println dateRange
		
		println ('a'..'c')
		
		def log=''
		(9..5).each{
			log+=it+' '
		}
		println log
		
		println ((0..10).isCase(5))
		
		def age=35
		def insuranceRate
		switch(age)
		{
			case 15..30:insuranceRate=0.04;break
			case 31..45:insuranceRate=0.05;break
			case 46..60:insuranceRate=0.07;break
			default: throw new IllegalArgumentException()
		}
		println insuranceRate
		
		def ages=[20,35,47,56,60]
		def midage=21..100
		println ages.grep(midage)
		
		def mon=new WeekyDay('mon')
		def fri=new WeekyDay('fri')
		
		def weekLog=''
		for(day in mon..fri)
			weekLog+=day.toString()+' '
		println weekLog
		
		println (fri..mon)
	}
	
	/**
	 * 
	 */
	static void listTest(){
		def list=[1,2,3]
		assert list.size()==3
		assert list[1]==2
		
		assert list instanceof ArrayList
		
		def longList=(0..1000).toList()
		assert longList[555]==555
		
		def explicitList=new ArrayList()
		explicitList.addAll(list)
		explicitList[0]=10
		assert (list[0]==explicitList[0])==false
		
		explicitList=new LinkedList(list)
		assert explicitList[0]==1
		
		assert [1,[2,3]].flatten()==[1,2,3]
		
		assert[1,2,3,4].intersect([3,4,5,6])==[3,4]
		println ([1,2,3].disjoint([4,5,6]))
		
		//stack

		def poped=list.pop()
		assert poped==3
		
		assert list.reverse()==[2,1]
		
		assert list.sort()==[1,2]
		
		def list2=[[1,0],[0,1,2]]
		println list2.sort{a,b->a[0]<=>b[0]}
		
		assert (list2.remove(1)!=[[1,0]])
		
		list=['a','b','c','d']
		list.removeAll(['a','c'])
		println list
		assert list==['b','d']
		
		def adsa=[1,2,3].collect {
			it->it*2
		}
		println adsa
		
		adsa=[1,2,3].findAll {
			it->it%2==0
		}
		
		println adsa
		
		adsa=[1,2,3,1].unique()
		println adsa
		
		println adsa.count(2)
		println adsa.max()
		println adsa.min()
		
		println adsa.every {
			it<5
		}
		println adsa.any {
			it>2
		}
			
		println adsa.join('-')
		
		println adsa.inject(10){
			fir,sec->fir+=sec
		}
		
		println adsa.inject(10){
			fir,sec->
			sec*=10
			fir*=sec
			
		}
	
		adsa=[3,10,4,3,13,5,2,21,34,2,34,54,3,6,3,1,34,23]
		def a=QuickSort.quickSort(adsa)
		println a
		
		println QuickSort.quickSort(' ds fdAd dAF')
		
		
				
	}
	
	static void mapTest(){
		def map=[:]
		println map.getClass()
		
		assert map instanceof HashMap
		
		map=[s:3,b:2,a:2]
		assert map.size()==3
		
		assert map.get('a')==map['a']
		
		def treeMap=new TreeMap();
		treeMap.putAll(map)
		println treeMap
		
		assert map.a==map['a']
		assert map.get('a',0)==map.a
		assert map.get('d')==null
		assert map.get('d',0)==0
		assert map.get('d')==map.d
		
		map['a.b']==1
		assert map['a.b']==map.'a.b'
		
		println map.keySet()
		println map
		
		assert map.keySet()==toSet(['a','b','d','s'])
		
		println map.every {
			it.value>2
		}
		println map.any {
			it.value<2
		}
		
		
		map.clear()
		assert map.size()==0
		assert map.isEmpty()
		
		map=[a:1,b:2,c:3]
		map.remove('a')
		println map
		
		def subMap=map.subMap(['a','b'])
		println subMap
		
		def doubled=map.collect {
			it.value*=2
		}
		println map
		
		def a="""
            i am a child
		    u are a child too
		    i love u 
		    but i cannot see u
		    so i want u
		"""
		CountingWords.counting(a)
	}
	
	static void main(args){
		//range
		rangeTest()
		
		//list
		listTest()
		
		//map
		mapTest()
	}
	
	static def toSet(list){
		new HashSet(list)
	}
}

class WeekyDay implements Comparable{

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return this.index<=>o.index;
	}
	
	static final DAYS=['sun','mon','tue','wed','thu','fri','sat']
	
	private int index=0
	
	WeekyDay(String day){
		index=DAYS.indexOf(day)
	}
	WeekyDay next(){
		return new WeekyDay(DAYS[(index+1)%DAYS.size()])
	}
	WeekyDay previous(){
		return new WeekyDay(DAYS[index-1])
	}
	String toString(){
		return DAYS[index]
	}
}

class QuickSort{
	static def quickSort(list){
		if(list.size()<2)return list
		def pivot=list[list.size().intdiv(2)]
		def left=list.findAll {it<pivot}
		def middle=list.findAll {it==pivot}
		def right=list.findAll{it>pivot}
		return (quickSort(left)+middle+quickSort(right))
	}
	
}
class CountingWords{
	
	static def counting(var){
		def words=var.tokenize()
		println words
		
		def wordFrenquency=[:]
		
		words.each {
			wordFrenquency[it]=wordFrenquency.get(it,0)+1
		}
		def wordList=wordFrenquency.keySet().toList()
		wordList.sort{wordFrenquency[it]}
		def statistic="\n"
		wordList[-1..-(wordList.size())].each {
			statistic+=it.padLeft(12)+":"
			statistic+=wordFrenquency[it]+"\n"
		}
		println statistic
		
	}
	
}
