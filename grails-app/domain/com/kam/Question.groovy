package com.kam

class Question {

	String question
	boolean isHeader
	String serialNo
	
	static belongsTo=[documentItem:DocumentItem]
	static hasMany=[questionValues:QuestionValue]
    static constraints = {
		serialNo(nullable:true)
    }
	static mapping = {
		questionValues cascade: 'all-delete-orphan'
	}
}
