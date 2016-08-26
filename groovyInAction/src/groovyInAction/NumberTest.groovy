package groovyInAction

class NumberTest {
	static void main(args){
		def store=''
		10.times{
			store+='x'
		}
		println store
		
		store=''
		1.upto(5){
			store+=it
		}
		
		println store
		
		store=''
		2.downto(-2){
			store+= it +' '
		}
		println store
		
		store=''
		0.step(0.5,0.1){
			store+= it +' '
		}
		println store
	}
}
