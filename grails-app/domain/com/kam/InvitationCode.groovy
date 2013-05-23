package com.kam

import java.util.Date;

/**
 * Invitation code to be used in invitation link map to link name
 *
 */
class InvitationCode {


	//Invitation can be sent by company
	String superUser
	//Or Invitation can be sent by staff
	String adminName
	String username
	String email
	//TODO: It may not be unique after truncating to 6 chars need to find a better strategy will do a db check for now
	String token = UUID.randomUUID().toString().replaceAll('-', '').substring(0,6)
	Date dateCreated
	
	static constraints = {
		superUser(nullable: true)
		adminName(nullable: true)
	}
	static mapping = { version false }
}
