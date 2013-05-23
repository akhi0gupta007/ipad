package com.kam

class QuestionValue {

	String questionValue
	String checkpointType
	static belongsTo=[question:Question,document:Document,documentItem:DocumentItem,documentItemValues:DocumentItemValue]
    static constraints = {
    }
}
