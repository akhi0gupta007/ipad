package com.kam

class DocumentReport {

	static belongsTo = [document:Document,user:User]
	Date dateCreated = new Date()
    static constraints = {
    }
}
