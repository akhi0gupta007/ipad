package com.kam

class SendSynchronisationMail {
	boolean isSent = false
	Building building
	Report report
	Customer customer
    static constraints = {
		customer(nullable:true)
		report(nullable:true)
		building(nullable:true)
    }
	static hasMany = [actionUsers:ActionUser,notifyUsers:NotifyUser]
}
