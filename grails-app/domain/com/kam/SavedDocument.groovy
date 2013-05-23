package com.kam

class SavedDocument {
	String comments
	String status
	String title
	String priority
	String priorityDate
	boolean isNowaTicket = false
	int documentVersion
	DocumentReport documentReport 
	static belongsTo = [document:Document,building:Building,room:Room,user:User,project:Project,user:User]
	static hasMany = [attachments:Image]
    static constraints = {
		priority(nullable:true)
		priorityDate(nullable:true)
		comments(nullable:true,blank:true)
    }
}
