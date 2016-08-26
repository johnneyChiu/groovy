package groovyInAction

import org.codehaus.groovy.runtime.InvokerHelper
import org.codehaus.groovy.runtime.StringBufferWriter

class HiLevel {
	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	static void main(args){
		/*def button=new JButton("push me");
		button.actionPerformed={
			event->println button.text;
		}*/
	
		Expando n=new Expando();
		
		assert n.takeThis==null;
		
		n.takeThis='xiaoq';
		
		assert n.takeThis=='xiaoq'
		
		n.tripleTime={times->return takeThis*times}	
		
		println n.tripleTime(3);
		
		
		//
		println this.class.methods.name.grep(~/get.*/).sort()
		
		///
		
		def date=new Date(107,1,28)
		def ulc=new Product(name:'ULC',dollar:1499)
		def ve=new Product(name:'visual Editor',dollar:499)
		def invoices=[
			new Invoice(date:date,items:[
					new LineItem(product:ulc,count:3),
					new LineItem(product:ve,count:10)
				]),
			new Invoice(date:new Date(106,3,22),items:[
				new LineItem(product:new Product(name:'haha',dollar:200),count:30)
				])
			]
		invoices.items.each{
			item->println item.each{
				print it.total()
			}
		}
		//print invoices.items.grep{it.total()>10000}.product.name
		
		//meta test
		
		def log=new StringBuffer()
		def traceMe=new Wether(writer:new StringBufferWriter(log))
		traceMe.outer()
		print log
		
		//other intercept
		def log2=new StringBuffer("\n")
		def tracer=new TracingInterceptor()
		tracer.writer=new StringBufferWriter(log2)
		def proxy=ProxyMetaClass.getInstance(OtherWether.class)
		proxy.interceptor=tracer
		proxy.use{
			new OtherWether().outer()
		}
		print log.toString()
		
	}
	def name
	def age
	def stu
	
}



class Invoice{
	List items
	Date date
}
class LineItem{
	Product product
	int count
	int total(){
		return product.dollar*count
	}
}
class Product{
	String name
	int dollar
}


class TraceAble implements GroovyInterceptable{
	Writer writer=new PrintWriter(System.out)
	int indent=0
	
	Object invokeMethod(String name,Object args)
	{
		writer.write("\n"+' '*indent+"before method $name")
		writer.flush();
		indent++
		def metaClass=InvokerHelper.getMetaClass(this)
		def res=metaClass.invokeMethod(this,name,args)
		indent--
		writer.write("\n"+' '*indent+"after method $name")
		writer.flush()
		return res
		
	}
}

class Wether extends TraceAble{
	int outer(){
		return inner();
	}
	
	int inner(){
		return 1;
	}
	
}
class OtherWether{
	int outer(){
		return inner()
	}
	int inner(){
		return 1
	}
}