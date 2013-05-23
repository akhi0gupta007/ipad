package com.kam
import grails.converters.XML
import groovy.xml.MarkupBuilder
import java.text.DateFormat
import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.springframework.security.authentication.dao.SaltSource;
class KamGenericService {

	def springSecurityService
	def saltSource
	def projectDataService
	def getProjects(def object){
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		log.debug "springsecurityservice is "+springSecurityService
		log.debug "saltsource is=" +saltSource
		log.debug "xml in Kam Generic Service is"+object
		def requestType = object.requesttype
		log.debug "request type is=="+requestType
		def test = object.loginvalidation.password
		log.debug "password in Kam service is==" +test
		def loginNameReceived = object.loginvalidation.loginname
		log.debug "username in Kam service is==" + loginNameReceived
		String salt = saltSource instanceof NullSaltSource ? object.loginvalidation.loginname:null
		log.debug "salt in Kam service is"+ salt
		String encodedPassword = springSecurityService.encodePassword(object.loginvalidation.password.toString())
		log.debug encodedPassword
		def user = User.findByUsernameAndPassword(salt,encodedPassword)
		log.debug user
		if(user){
			if(user.enabled==true) {
				def customer = Customer.findById(User.get(user.id).customer.id)
				log.debug "dbCustomer in kam service is ==" + customer
				def projectList = Project.findAllWhere(customer:customer)
				log.debug "Project in kam service  is=="+projectList
				return projectDataService.projectData(requestType,projectList,loginNameReceived,test)//calculate xml data and return back
				
			}
			if(user.enabled==false){
				xml.'?xml version="1.0" encoding="UTF-8"?'{
					digikam{
					requesttype(requestType)
					loginvalidation{
						loginname(loginNameReceived)
						password(test)
						response("DENIED")
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
					loginname(loginNameReceived)
					password(test)
					response("ERROR")
					message("Wrong Username/Password")
				}
			}
			}
			return writer.toString()
		}
	}
	def convertToDate(def stringDate) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		Date date = (Date) dateFormat.parse(stringDate)
		return date
	}
}
