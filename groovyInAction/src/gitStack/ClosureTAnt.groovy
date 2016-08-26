package gitStack

class ClosureTAnt {
	
	static main(args){
		filterTest()
	}
	
	static filterTest(){
		def simpleFilter={
				str->
			if(str.indexOf('java.')>0)
				return true
				else 
				return false
		}
		
		def filter=new Filter(strategy:simpleFilter)
		println  filter.call("xiaoq")
		println filter.call('1111java.1343')
		
		def regxFilter={
			istr->if(istr=~ "com.hhaah.*")
			return true
			else return false
		}
		filter=new Filter(strategy:regxFilter)
		println filter.call('com.hhaah.sdsadsa')
	}

}

class Filter{
	Closure strategy
	boolean call(str){
		strategy.call(str)
	}
}
