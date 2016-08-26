package groovyInAction

class RegularTest {
	static void main(args){
		def myFairStringy="The rain in Spain stays mainly in the plain"
		def BOUNDS=/\b/
		def rhyme=/$BOUNDS\w*ain$BOUNDS/
		def found=''
		myFairStringy.eachMatch(rhyme){
			match->found+=match+' '
		}
		println found
		
		found=''
		(myFairStringy=~rhyme).each {
			match->found+=match+' '
		}
		println found
		
		def cloze=myFairStringy.replaceAll(rhyme){
			it-'ain'+'___'
		}
		print cloze
		
		//group
		def matcher='a:1 b:2 c:3'=~/(\S+):(\S+)/
		println matcher.hasGroup()
		println matcher[2]
		
		
		('xy'=~/(.)(.)/).each{
			all,x,y->
			println all
			println x
			println y
		}
		
		//
		def twister='she sells sea shells at the sea shore of seychelles'
		def regex=/\b(\w)\w*\b/
		println regex.class.getName()
		
		def start=System.currentTimeMillis()
		10000.times {
			twister=~regex;
		}
		println System.currentTimeMillis()-start
		
		/*def pattern= ~regex
		start =System.currentTimeMillis()
		10000.times {
			pattern.matches(twister)
		}
		println System.currentTimeMillis()-start*/
		
	}
}
