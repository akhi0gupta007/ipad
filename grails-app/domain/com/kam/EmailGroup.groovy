package com.kam

import java.util.Date;

class EmailGroup {

	
	String groupName
	String activatedBy
	String groupType
	Date dateCreated=new Date()
	static hasMany = [members:MemberEmails]
	static belongsTo = [customer:Customer]
    static constraints = {
    }
}
