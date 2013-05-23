package com.kam

import grails.converters.JSON
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource;
import org.codehaus.groovy.grails.plugins.springsecurity.ui.RegistrationCode
import org.codehaus.groovy.grails.web.json.JSONObject;
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils


class RegisterController extends grails.plugins.springsecurity.ui.RegisterController {
	def springSecurityService
	def asynchronousMailService
	def adminRegister={AdminRegisterCommand command ->
		String token = params.id
		def invitationCode = token ? InvitationCode.findWhere(token:token) : null
		if (invitationCode==null) {
			render view: 'adminRegister', model: [code:message(code: 'security.invitation.badCode')]
			return
		}
		def user = User.findWhere(username:invitationCode.username)
		if(user!=null){
			command.name=user.firstName
			if(user.middleName!=null && user.middleName!='')
			command.name+=' '+user.middleName
			command.name+=' '+user.lastName
			command.username=invitationCode.username
			command.customer=user.customer.name
			command.email=invitationCode.email
		}
		else{
			render view: 'adminRegister', model: [code:message(code: 'security.invitation.badCode')]
			return
		}
		render view: 'adminRegister', model: [admin: command]
	}
	
	def userRegister={UserRegisterCommand command ->
		String token = params.id
		def invitationCode = token ? InvitationCode.findByToken(token) : null
		if (!invitationCode) {
			render view: 'userRegister', model: [code:message(code: 'security.invitation.badCode')]
			return
		}
		def user = User.findWhere(username:invitationCode.username)
		if(user!=null){
			command.name=user.firstName
			if(user.middleName!=null && user.middleName!='')
			command.name+=' '+user.middleName
			command.name+=' '+user.lastName
			command.username=invitationCode.username
			command.customer=user.customer.name
			command.email=invitationCode.email
		}
		else{
			render view: 'userRegister', model: [code:message(code: 'security.invitation.badCode')]
			return
		}
		render view: 'userRegister', model: [user: command]
	}
	
	
	def update={
		if (params.password!=params.password2 ) {
			render message(code: 'kam.security.password.match.false')
			return
		}
		else if(params.password=='' || params.password2=='')
		{
			render message(code: 'kam.security.password.blank')
			return
		}
		
			def user = User.findWhere(username:params.user)
			if(user==null)
			{
				render message(code: 'kam.security.user.exists.false',args:[params.user])
				return
			}
			def code=InvitationCode.findWhere(username:params.user)
			if(code==null)
			{
				render message(code: 'security.invitation.badCode')
				return
			}
			code.delete()
			user.password = params.password
			user.save()
			//render message(code: 'account.updatePassword.success')
			redirect controller:'login'
		
	}
	
	def forgotPassword = {
		
				if (!request.post) {
					return
				}
				String username = params.username
				if (!username) {
					flash.error = message(code: 'spring.security.ui.forgotPassword.username.missing')
					redirect action: 'forgotPassword'
					return
				}
				def user = User.findWhere(username: username)
				if (!user) {
					flash.error = message(code: 'spring.security.ui.forgotPassword.user.notFound')
					redirect action: 'forgotPassword'
					return
				}
				def registrationCode = new RegistrationCode(username: user.username)
				registrationCode.save(flush: true)
				String url = generateLink('resetPassword', [t: registrationCode.token])
				String name=user.firstName
				if(user.middleName!=null)
				name+=user.middleName
				name+=user.lastName
				def body = message(code:'login.forgot.password.email.body',args:[user.firstName+' '+user.middleName+' '+user.lastName,url])
				asynchronousMailService.sendMail {
					to user.email
					from grailsApplication.config.register.invitation.emailFrom
					subject message(code:'login.forgot.password.email.subject')
					html body.toString()
				}
				[emailSent: true]
			}
	
	def resetPassword = { ResetPasswordCommand command ->
		
				String token = params.t
		
				def registrationCode = token ? RegistrationCode.findByToken(token) : null
				if (!registrationCode) {
					flash.error = message(code: 'spring.security.ui.resetPassword.badCode')
					redirect uri: grailsApplication.config.password.reset.postResetUrl
					return
				}
		
				if (!request.post) {
					return [token: token, command: new ResetPasswordCommand()]
				}
				command.password=params.password
				command.password2=params.password2
				command.username = registrationCode.username
				command.validate()
				if (command.password!=command.password2) {
					return [token: token, command: command]
				}
					def user = User.findByUsername(registrationCode.username)
					user.password = command.password
					user.save()
					registrationCode.delete()
				flash.message = message(code: 'spring.security.ui.resetPassword.success')
				redirect uri: grailsApplication.config.password.reset.postResetUrl
			}
	
	def checkIfEmailRegistered={
		def returnObject = [ : ]
		def email=User.findWhere(email:params.email,isDeleted:false)
		if(email!=null)
		{
			returnObject.sucess=false
			returnObject.message=message(code:'user.email.unique')
			render returnObject as JSON
		}		  
		else
		{
			returnObject.sucess=true
			returnObject.message=""
			render returnObject as JSON
		}
	}
	
	def checkIfUsernameRegistered={
		def returnObject = [ : ]
		def email=User.findWhere(username:params.username,isDeleted:false)
		if(email!=null)
		{
			returnObject.sucess=false
			returnObject.message=message(code:'user.username.unique')
			render returnObject as JSON
		}
		else
		{
			returnObject.sucess=true
			returnObject.message=""
			render returnObject as JSON
		}
	}
	
	def checkIfGroupExists={
		def returnObject = [ : ]
		def user=springSecurityService.currentUser
		def emailGroup=EmailGroup.findByGroupNameAndCustomer(params.emailGroup,user.customer)      
		if(emailGroup!=null)       
		{     
			returnObject.sucess=false      
			returnObject.message=message(code:'email.group.unique')          
			render returnObject as JSON
		}
		else
		{
			returnObject.sucess=true
			returnObject.message=""
			render returnObject as JSON
		}
	}
	
	def checkIfProjectExists={
		def returnObject = [ : ]
		def user=springSecurityService.currentUser
		def project=Project.findByProjectNameAndCustomerAndIsDeleted(params.projectName,user.customer,false)
		if(project!=null)
		{
			returnObject.sucess=false
			returnObject.message=message(code:'project.name.unique')
			render returnObject as JSON
		}
		else
		{
			returnObject.sucess=true
			returnObject.message=""
			render returnObject as JSON
		}
	}
	
	def checkIfCustomerExists={
		def returnObject = [ : ]
		def customer=Customer.findByNameAndIsDeleted(params.customerName,false)
		if(customer!=null)
		{
			returnObject.sucess=false
			returnObject.message=message(code:'customer.name.unique')
			render returnObject as JSON
		}
		else
		{
			returnObject.sucess=true
			returnObject.message=""
			render returnObject as JSON
		}
	}
	
}

class AdminRegisterCommand {
	String name
	String username
	String customer
	String email
}

class UserRegisterCommand {
	String name
	String username
	String customer
	String email
}


class RegisterCommand {
	
		String username
		String email
		String password
		String password2
	
		def grailsApplication
	
		static constraints = {
			username blank: false, nullable: false, validator: { value, command ->
				if (value) {
					def User = command.grailsApplication.getDomainClass(
						SpringSecurityUtils.securityConfig.userLookup.userDomainClassName).clazz
					if (User.findByUsername(value)) {
						return 'registerCommand.username.unique'
					}
				}
			}
			email blank: false, nullable: false, email: true
			password blank: false, nullable: false, validator: RegisterController.passwordValidator
			password2 validator: RegisterController.password2Validator
		}
	}
	
	class ResetPasswordCommand {
		String username
		String password
		String password2
	
		static constraints = {
			username nullable: false
			password blank: false, nullable: false, validator: RegisterController.passwordValidator
			password2 validator: RegisterController.password2Validator
		}
	}
	