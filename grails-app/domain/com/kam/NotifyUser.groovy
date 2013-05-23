package com.kam

class NotifyUser {
	String notifyGroupName	
	SendSynchronisationMail sendmail
    static constraints = {
		sendmail(nullable:true)
    }
}
