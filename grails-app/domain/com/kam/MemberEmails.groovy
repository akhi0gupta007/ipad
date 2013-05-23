package com.kam

class MemberEmails {

	String email
    static constraints = {
		email (blank:false,email:true)
    }
}
