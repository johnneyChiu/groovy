package gitStack

import groovy.xml.StreamingMarkupBuilder

class XmlExpandBuilder {
	
	static main(args){
		useStreamingMarkupBuilder()
		println 'xmlparser--'
		XMLParserTest()
		println 'xmlslurper--'
		xmlslurperTest()
		xmlslurperTest2()
	}
	
	static def useStreamingMarkupBuilder(){
		def comment="<![CDATA[<!-- address is new to this release -->]]>"
		def build=new StreamingMarkupBuilder();
		build.encoding='utf8'
		def person={
			mkp.xmlDeclaration()
			mkp.pi("xml-stylesheet": "type='text/xsl' href='myfile.xslt'" )
		    mkp.declareNamespace('':'http://myDefaultNamespace')
		    mkp.declareNamespace('location':'http://someOtherNamespace')
			person1(id:100){
				firstname("Jane")
				lastname("Doe")
				mkp.yieldUnescaped(comment)
				location.address("123 Main")
			}
		}
		def writer=new FileWriter("person.xml")
		writer<<build.bind(person)
	}
	
	static def XMLParserTest(){
		def xml=
			"""
				<langs type='current' count='3' mainstream='true'>
				  <language flavor='static' version='1.5'>Java</language>
				  <language flavor='dynamic' version='1.6.0'>Groovy</language>
				  <language flavor='dynamic' version='1.9'>JavaScript</language>
				</langs>
			"""
		def langs=new XmlParser().parseText(xml)
		println langs.getClass()
		println langs
		langs.attributes().each {
			k,v->
			println "key is $k and value is $v"
		}
		
		langs.language.each {
			println it.text()
		}
		def results = []
		langs.language.each{
		  results << it.text()
		}
		println results
		// the short way using the spread-dot operator
		def values = langs.language*.text()
		println values
		
		def versions = langs.language*.attribute("version")
		println versions
	}
	
	static def xmlslurperTest(){
		def xml=
		"""
				<langs type='current' count='3' mainstream='true'>
				  <language flavor='static' version='1.5'>Java</language>
				  <language flavor='dynamic' version='1.6.0'>Groovy</language>
				  <language flavor='dynamic' version='1.9'>JavaScript</language>
				</langs>
		"""
		def langs=new XmlSlurper().parseText(xml)
		println langs.@type
		langs.language.each{
		  println it
		  println it.@version
		}
	}
	
	static def xmlslurperTest2(){
		def xml=
		"""
		<rss version="2.0"
		     xmlns:yweather="http://xml.weather.yahoo.com/ns/rss/1.0"
		     xmlns:geo="http://www.w3.org/2003/01/geo/wgs84_pos#">
		  <channel>
		    <title>Yahoo! Weather - Broomfield, CO</title>
		    <yweather:location city="Broomfield" region="CO"   country="US"/>
		    <yweather:astronomy sunrise="6:36 am"   sunset="5:50 pm"/>
		
		    <item>
		      <title>Conditions for Broomfield, CO at 7:47 am MST</title>
		      <pubDate>Fri, 27 Feb 2009 7:47 am MST</pubDate>
		      <yweather:condition text="Partly Cloudy"
		                          code="30"  temp="25"
		                          date="Fri, 27 Feb 2009 7:47 am MST" />
		    </item>
		  </channel>
		</rss>
		"""
		def rss=new XmlSlurper().parseText(xml)
		println "Sunrise: ${rss.channel.astronomy.@sunrise}"
		println "Sunset: ${rss.channel.astronomy.@sunset}"
		println "Currently:"
		println "\t" + rss.channel.item.condition.@date
		println "\t" + rss.channel.item.condition.@temp
		println "\t" + rss.channel.item.condition.@text
		 
	}
}
