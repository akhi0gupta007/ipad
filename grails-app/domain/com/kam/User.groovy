package com.kam

import java.util.Date;

class User {

	transient springSecurityService

	String firstName
	String middleName
	String lastName
	String username
	String email
	String mobile
	String password
	String createdBy
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	Customer customer
	boolean isDeleted
	Date dateCreated=new Date()
	Date lastUpdated=new Date()
	static hasMany = [document:Document]
	static constraints = {
		password(blank: false)
		customer(nullable:true)
		firstName(blank:false)
		middleName(nullable:true)
		mobile(nullable:true)
		lastName(blank:false)
		username(blank:false,unique:true)
		email(blank:false,unique:true)
		isDeleted(nullable:true)
	}

	static mapping = { password column: '`password`' }

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}
