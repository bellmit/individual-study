package cn.gyw.groovy.model

class Teller {
	
	def age
	def name
	def date
	
	@Override
	public String toString() {
		return "Teller:${name}/${age}/${date}"
	}
}
