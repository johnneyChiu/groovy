#!/groovyAction/Qizi
//author xiaoq
package groovyInAction

class Qizi {
	/**
	 * @param args
	 */
	static void main(args){
		//file test
		def number=0
		println System.getProperty("user.dir")
		
		new File("newFileTest").eachLine {
			line->
			number++
			println "$number:$line"
		}
		
		
		//list test
		
		println ([File,List,String]."package".name)
		
		//test xml
		def customers=new XmlSlurper().parse(new File("NewFile.xml"));
		/*for(customer in customers.corporate.customer)
			println "${customer.@name} works for ${customer.@company}"*/
		println customers
		
		/*customers.each {
			customer->def corporate=customer.consumer
			println corporate
			corporate.each{
				println "${it.@name}"
			}
		}*/
		
		//fab
		def current=0
		def next=1
		10.times {
			print current+' '
			def nextCurrent=next
			next=next+current
			current=nextCurrent
		}
		println ""
		
		def a=[1,2,3]
		a.each {
			println it
		}
		
		//使用闭包计算所有在会议室的客人之间的碰杯数
		def totalClicks=0
		def partyPeople=100
		def clinksWithGuest=0
		1.upto(partyPeople) { guestNumber ->
			clinksWithGuest = guestNumber-1
			totalClicks += clinksWithGuest
		}
		assert totalClicks==(partyPeople*(partyPeople-1))/2
		
		def me='xiaoq'
		def you ='xiaomei'
		def line="me $me - you $you"
		println line
		assert line=='me xiaoq - you xiaomei'
		
		def date=new Date(0)
		print Date."package".name
		def out="year $date.year month $date.month Day $date.date"
		
		println out
		out="Date is ${date.toGMTString()} or ${date.toLocaleString()}"
		println out 
		
		def greeting="Hello"
		greeting <<= 'Groovy'
		println greeting instanceof java.lang.StringBuffer
		assert greeting=="Hello Groovy"
		
		greeting[1..4]='i'
		assert greeting=="Hi Groovy"
		
	}
}
