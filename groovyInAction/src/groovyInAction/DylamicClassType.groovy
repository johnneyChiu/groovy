package groovyInAction

class DylamicClassType {
	static void main(args){
		def d=new DylamicClassType();
		
		assert d.isNoField=='pretend Value'
		
		assert d.count==0
		
		d.isNoFieldEither='hahah '
		assert d.count==1
		
		//constructor test
		ConstructorTest a=new ConstructorTest();
		ConstructorTest b=new ConstructorTest(name:'xiaoq');
		ConstructorTest c=new ConstructorTest(age:'haha');
		ConstructorTest e=new ConstructorTest();
		
		a=['xiaoq','133','cc'] as ConstructorTest
		assert a.name=='xiaoq'
		a=['xiaoq','333','314']
		assert a.name
		
	}
	
	public count=0;
	
	Object get(String name){
		return "pretend Value"
	}
	
	void set(String name,Object value){
		count++
	}
}


class ConstructorTest{
	
	def name
	def age
	def cla 
	ConstructorTest(name,age,cla)
	{
		this.name=name
		this.age=age
		this.cla=cla	
	}
	ConstructorTest(){
		
	}
}
