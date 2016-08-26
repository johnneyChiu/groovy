package groovyInAction

class ClousureTest {
	static void main(args){
		//define
		
		def printer={line->print line}
		print printer.getClass()
		
		
		//nomal
		MethodClosureSample mcs1=new MethodClosureSample(5);
		MethodClosureSample mcs2=new MethodClosureSample(6);
		
		//引用方法作为闭包
		Closure mcs1Closure=mcs1.&validate
		def words=['sdsadsd dsadsa','dsaad']
		println words.find(mcs1Closure)
		
		
		Closure c=new MutipleClosureSample().&mysteryMethod
		
		assert c("abcd",4)
		assert 3==c(["a","b","d"])
		assert 14==c(5,9)
		
		def slow=benchmark(10000){
			(int)it/2
		}
		println slow
		def quick=benchmark(10000){
			it.intdiv(2)
		}
		println quick
		
		def ac={it+=it}
		def en=benchmark(10000,ac)
		println en
		
		curryTest()
		
		Mother mother=new Mother()
		def closure=mother.birth(4)
		
		def context=closure.call(mother)
		println context
		context=closure.call(this)
		println context
		
		def firstClosure=mother.birth(5)
		def seccondClosure=mother.birth(5)
		assert false==firstClosure.is(seccondClosure)
		
		
		
		//visitor test
		def picture=new Drawing(shapes:[new Square(width:10),new Circle(radius:2)])
		def total=0
		picture.accept{total+=it.area()}
		print "all area is $total"
		picture.accept{println it.class.name+":"+it.area()}
		
	}
	
	static def curryTest(){
		def configurator={format,filter,line->filter(line)?format(line):null}
		def appender={config,append,line->def out=config(line)
			if(out) append(out)
		}
		
		def dateFormatter={line->"${new Date()}: $line"}
		def debugFilter={line->line.contains('debug')}
		def consoleAppender={line->println line}
		
		def myConf=configurator.curry(dateFormatter,debugFilter);
		def myLog=appender.curry(myConf,consoleAppender)
		
		myLog("here is some debug message")
		myLog("this will not be printed")
		
		
	}
	
	//define closure with function
	def Closure printer2(){
		return {line->print line}
	}
	
	static def benchmark(repeat,Closure closure){
		def start=System.currentTimeMillis()
		repeat.times {
			closure.call(it)
		}
		def stop=System.currentTimeMillis()
		return stop-start
	}
}

class MethodClosureSample{
	int limit
	MethodClosureSample(int limit){
		this.limit=limit
	}
	boolean validate(String value){
		return value.length()<=limit
	}
}
class MutipleClosureSample{
	boolean mysteryMethod(String value,int size){
		return value.length()==size
	}
	
	int mysteryMethod(List list){
		return list.size()
	}
	
	int mysteryMethod(int x,int y){
		return x+y
	}
}

class Mother{
	int field=1
	def foo(){
		return 2
	}
	def Closure birth(param){
		def local=3
		return {
			caller->[this,field,foo(),local,param,caller]
		}
	}


}


//visitor
class Drawing{
	List shapes
	def accept(Closure closure){
		shapes.each {
			it.accept(closure);
		}
	}
}
class Shape{
	def accept(Closure closure){
		closure.call(this)
	}
}
	
class Square extends Shape{
	def width
	def area(){
		width*2
	}
}

class Circle extends Shape{
	def radius
	def area(){
		Math.PI*radius*2
	}
}