package com.kam

class ActionUser {
	String actionGroupName
	SendSynchronisationMail sendmail
    static constraints = {
		sendmail(nullable:true)
    }
}
