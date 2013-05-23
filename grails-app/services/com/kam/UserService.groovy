package com.kam
import grails.converters.XML
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.codehaus.groovy.grails.web.context.ServletContextHolder;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.web.multipart.MultipartFile

class UserService {

	def springSecurityService
	def saltSource

	def loginCheck(def object){
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		def customer
		log.debug springSecurityService
		log.debug saltSource
		def requestType = object.requesttype
		log.debug "request type in user service is=="+requestType
		def test = object.loginvalidation.password
		log.debug "password in user service is==" +test
		def loginname1 = object.loginvalidation.loginname
		log.debug "username in user service is==" + loginname1
		String salt = saltSource instanceof NullSaltSource ? object.loginvalidation.loginname:null
		log.debug "salt in user service is"+ salt
		String encodedPassword = springSecurityService.encodePassword(object.loginvalidation.password.toString())
		log.debug encodedPassword
		def user = User.findByUsernameAndPassword(salt,encodedPassword)
		log.debug user
		if(user){

			customer = Customer.findById(User.get(user.id).customer.id)
			log.debug "dbCustomer in kam service is =="+customer

			def role = UserRole.findByUser(user)
			log.debug("Role is ....."+role.role.authority.toString().equals("ROLE_USER"))


			if(user.enabled==true) {
				if((role.role.authority.toString().equals("ROLE_USER"))) {
					xml.'?xml version="1.0" encoding="UTF-8"?'{
					digikam{
						requesttype(requestType)
						mode(customer.flow)
						loginvalidation{
							loginname(loginname1)
							password(test)
							response("OK")
							message("Login Validated")
						}
					}
					}
					log.debug "xml generated======"+writer.toString()
					return writer.toString()
				}else{
					xml.'?xml version="1.0" encoding="UTF-8"?'{
						digikam{
						requesttype(requestType)
						mode(customer.flow)
						loginvalidation{
							loginname(loginname1)
							password(test)
							response("REVOKED")
							message("User not allowed to use iPad App")
						}
					}
					log.debug "xml generated "+writer.toString()
				}
				return writer.toString()
			}
			}
			if(user.enabled==false){
				xml.'?xml version="1.0" encoding="UTF-8"?'{
					digikam{
					requesttype(requestType)
					mode(customer.flow)
					loginvalidation{
						loginname(loginname1)
						password(test)
						response("REVOKED")
						message("User not allowed to use iPad App")
					}
				}
				}
				return writer.toString()
			}
		}
		else{
			xml.'?xml version="1.0" encoding="UTF-8"?'{
				digikam{
				requesttype(requestType)
				loginvalidation{
					loginname(loginname1)
					password(test)
					response("ERROR")
					message("Wrong Username/Password")
				}
			}
			}
			return writer.toString()
		}
	}
	def methodNotFound(def object){
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		def requestType = object.requesttype

		xml.'?xml version="1.0" encoding="UTF-8"?'{
		digikam{
			requesttype(requestType)
			response("ERROR")
			message("Method Not Found")
		}
		}
		return writer.toString()
	}
}
