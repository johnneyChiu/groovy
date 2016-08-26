package gitStack

import groovy.sql.Sql

class GroovyMysql {
	static void main(args)
	{
		def sql=Sql.newInstance("jdbc:mysql://localhost:3306/xiaoq","root","root","com.mysql.jdbc.Driver");
		sql.eachRow("select*from test"){
			println "ID:$it.id\tNAME:$it.name"
		}
		//sql.executeInsert('insert into test(id,name) values(?,?)',[3,'xiaomei'])
		sql.eachRow('select* from test'){
			sql.executeUpdate("update test set age=? where id=?", [Math.random()*100, it.id])
			println it
		}
		
		sql.eachRow('select* from test'){
		
			println it//sql.executeUpdate("update test set age=? where id=?", [Math.random()*100, it.id])
		}
		def test=sql.dataSet('test')
		test.each{
			print it
		}
		//test.add(id:4,name:'xiaoo',age:Math.round(Math.random()*100))
		
		sql.eachRow('show status'){
			println it
		}
		
	}
}
