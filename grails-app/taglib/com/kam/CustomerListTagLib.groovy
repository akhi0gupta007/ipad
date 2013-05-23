package com.kam

class CustomerListTagLib {
	
	static namespace='adminList'
	
	def adminList={attrs->
		def admin= attrs.admin
		def color=attrs.color
		if(color==0)
		session.setAttribute('color', '0')
		if(UserRole.findByUserAndRole(admin,Role.findByAuthority('ROLE_ADMIN'))) {
			if(!admin.isDeleted){
			def name=admin.firstName
			def enabled
			admin.middleName?name+=" "+admin.middleName:name
			name+=" "+admin.lastName
			def email=admin.email
			def mobile=admin.mobile
			
			if(admin.customer.enabled)
			enabled=admin.enabled
			else
			enabled=false
			def username=admin.username
			if(session.getAttribute('color')=='0')
			{
				color='0'
				session.setAttribute('color', '1')
				}
			else{
				color='1'
				session.setAttribute('color', '0')
			}
			out << render ( template:'customerList', model:[name:name,email:email,mobile:mobile,enabled:enabled,username:username,color:color,id:admin.id])
		}
		}
	}
}
