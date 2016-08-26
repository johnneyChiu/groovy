package gitStack

import groovy.text.SimpleTemplateEngine

class GroovyMvc {
	static main(args){
		SimpleTemplate.testTemplate()
	}

}

class SimpleTemplate{
	static void testTemplate(){
		def fle=new File('simpleTemp.tmpl')
		def binding=['tVar':'hahaha']
		def engine=new SimpleTemplateEngine()
		def template=engine.createTemplate(fle).make(binding)
		println template.toString()
	}
}
