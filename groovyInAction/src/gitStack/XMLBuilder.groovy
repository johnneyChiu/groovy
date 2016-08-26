package gitStack

import groovy.sql.Sql
import groovy.swing.SwingBuilder
import groovy.xml.MarkupBuilder

import java.awt.FlowLayout

class XMLBuilder {
	static main(args){
		def writer=new StringWriter()
		def builder=new MarkupBuilder(writer)
		
		builder.person(){
			name(first:'josh',second:'foo'){
				age('33')
				gender('female')
			}
			friends(){
				['julie','malian','hahah'].each{
					friend(it)
				}
			}
		}
		/*print writer.toString()
		*/
		//swingBuilder()
		markupBuilderTest()
	}
	
	static void swingBuilder(){
		def swingBuilder=new SwingBuilder()
		def langs=["groovy","ruby","scala","Python","java"]
		def gui=swingBuilder.frame(title:'swingBuilder with groovy',size:[300,200]){
			panel(layout:new FlowLayout()){
				langs.each{
					checkBox(text:it)
				}
			}
			button(text:'Groovy button',actionPerformed:{
				swinger.optionPane(message:'Indubitably Groovy!')
				.createDialog(null, 'Zen Message').show()
			})
			button(text:'Groovy Quit', actionPerformed:{ System.exit(0)})
		}
		
		gui.show();
		
	}
	
	//other
	static void markupBuilderTest(){
		def sql=Sql.newInstance("jdbc:mysql://localhost:3306/xiaoq","root","root","com.mysql.jdbc.Driver");
		def writer=new StringWriter()
		def builder=new MarkupBuilder(writer)
		builder.words(){
			sql.eachRow('select* from word'){row->
				builder.word(spelling:row.spelling,partofspeech:row.part_of_speech){
					builder.definitions(){
						sql.eachRow('select * from definition where word_id=?',[row.word_id]){
							builder.definition(it.definition)
						}
					}
					builder.synonyms(){
						sql.eachRow('select * from synonym where word_id=?',[row.word_id]){
							builder.synonym(it.spelling)
						}
					}
				}
			}
		}
		print writer.toString();
		
		new File('testWord.xml').withPrintWriter {
			it.println writer.toString()
		}
	}
}
