package com.kam
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.springframework.security.authentication.dao.SaltSource
import java.text.DateFormat
import java.text.SimpleDateFormat;

class DocumentServiceNewService {
	/**
	 *
	 * @param user
	 * @param object
	 * @param salt
	 * @param test
	 * @return
	 */
	def springSecurityService
	def saltSource
	def getDocuments(def object){
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		def latestDocuments = []
		def latestVersionDocuments = []
		def latestUpdatedDocuments = []
		def latestUpdatedVersionDocuments = []
		log.debug "springsecurityservice is "+springSecurityService
		log.debug "saltsource is=" +saltSource
		log.debug "xml in Document Service is"+object
		def requestType = object.requesttype
		log.debug "request type in document Service is=="+requestType
		def test = object.loginvalidation.password
		log.debug "password in document service service is==" +test
		def paramLoginName = object.loginvalidation.loginname
		log.debug "username in document service is==" + paramLoginName
		String saltUserName = saltSource instanceof NullSaltSource ? object.loginvalidation.loginname:null
		log.debug "salt in document service is"+ saltUserName
		String encodedPassword = springSecurityService.encodePassword(object.loginvalidation.password.toString())
		log.debug encodedPassword
		def dbUser = User.findByUsernameAndPassword(saltUserName,encodedPassword)
		log.debug dbUser
		if(dbUser){
			if(dbUser.enabled==true) {
				def customer = Customer.findById(User.get(dbUser.id).customer.id)
				log.debug "dbCustomer in document is ==" + customer
				def projectList = Project.findAllWhere(customer:customer)
				log.debug "Project in document service is=="+projectList
				def documentList = Document.findAllByProjectInList(projectList)
				log.debug "document found for user logged in is"+documentList
				def documentTemplatesList = documentList.documentTemplate
				log.debug "templates for documents in list ="+documentTemplatesList
				def documentItemsList = DocumentItem.findAllByDocumentTemplateInList(documentTemplatesList)
				log.debug "Items for that template are=="+documentItemsList
				def docList = documentList
				/*def emailGroup = EmailGroup.findAllWhere(customer:customer)
				 log.debug "email groups are =="+emailGroup*/
				def emailGroupNotifyUser = EmailGroup.findAllWhere(customer:customer,groupType:"NotifyUser")
				log.debug "email groups are =="+emailGroupNotifyUser
				def emailGroupActionUser = EmailGroup.findAllWhere(customer:customer,groupType:"ActionUser")
				log.debug "email groups are =="+emailGroupActionUser
				log.debug "last synctime received is==........................................"+object.lastsynctime
				def lasttime

				if(object.lastsynctime.toString()==""){
					log.debug("Date Not Found***************************")
					lasttime= convertToDate("Sun Jan 1 00:00:01 IST 2012")
				}
				else{
					lasttime = convertToDate(object.lastsynctime.toString())
					log.debug "lasttime is....................................."+lasttime
				}

				def docUpdated = Document.findAllByDateCreatedGreaterThanAndIsDeletedAndCustomer(lasttime,false,customer)
				log.debug "docUpdated are===="+docUpdated
				def docDeleted = Document.findAllByLastUpdatedGreaterThanAndIsDeletedAndCustomer(lasttime,true,customer)
				def docEdited = Document.findAllByLastUpdatedGreaterThanAndIsDeletedAndCustomer(lasttime,false,customer)
				//
				def reverseDocumentList = docUpdated.sort{it.documentVersion}.reverse()
				def reverseUpdatedDocumentList = docEdited.sort{it.documentVersion}.reverse()
				String Docitem
				reverseDocumentList.each{
					log.debug "lastest documet name is =="+it.name
					log.debug "does it match documents in list"+latestDocuments.contains(it.name)
					if(!latestDocuments.contains(it.name)){
						println "name received==="+it.name
						Docitem=it.name
						latestDocuments.add(it.name)
						latestVersionDocuments.add(it)
					}
				}
				reverseUpdatedDocumentList.each{
					log.debug "lastest documet name is =="+it.name
					log.debug "does it match documents in list"+latestUpdatedDocuments.contains(it.name)
					if(!latestUpdatedDocuments.contains(it.name)){
						println "name received==="+it.name
						Docitem=it.name
						latestUpdatedDocuments.add(it.name)
						latestUpdatedVersionDocuments.add(it)
					}
				}
				log.debug "reverse list of documents accrding to their version is=="+reverseDocumentList
				log.debug "latest document list is==="+latestDocuments
				log.debug "latest unique added list is==="+latestVersionDocuments
				log.debug "latest unique updated list is==="+latestUpdatedVersionDocuments
				log.debug("To be Added Docs...****************"+docUpdated)
				log.debug("To be deleted Docs...&&&&&&&&&&&"+docDeleted)
				log.debug("Edited Docs,,,^^^^^^^^^^^^^^^^^^^^"+docEdited)
				xml.'?xml version="1.0" encoding="UTF-8"?'{
					digikam{
					requesttype(requestType)
					lastsynctime(new Date().toString())
					loginvalidation{
						loginname(paramLoginName)
						password(test)
						response("OK")
						message("Login Validated")
					}
					documents{
						addDocuments(xml,latestVersionDocuments,emailGroupNotifyUser,emailGroupActionUser)
						removeDocuments(xml,docDeleted,emailGroupNotifyUser,emailGroupActionUser)
						updateDocuments(xml,latestUpdatedVersionDocuments,emailGroupNotifyUser,emailGroupActionUser)
					}
				}
				}
				log.debug "document service response=="+writer.toString()
				return writer.toString()
			}
			if(dbUser.enabled==false){
				xml.'?xml version="1.0" encoding="UTF-8"?'{
					digikam{
					requesttype(requestType)
					loginvalidation{
						loginname(paramLoginName)
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
					loginname(paramLoginName)
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
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		//  log.debug("Entring to CoverDate..........................****************************************")
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
		Date date = (Date) dateFormat.parse(stringDate)
		//log.debug("Date is.............................."+date)
		return date
	}
	def addDocuments(xml,docUpdated,emailGroupNotifyUser,emailGroupActionUser){
		xml.add{
			for(doc in docUpdated){
				document{
					id(doc.id)
					title(doc.name)
					discipline(doc.documentTemplate.discipline)
					addDocumentCheckpoints(xml,doc,emailGroupNotifyUser,emailGroupActionUser)
					notifyusers(xml,emailGroupNotifyUser)
				}
			}

		}
	}
	def actionusers(xml,emailGroupActionUser){
		xml.actionusers{
			for(useritem in emailGroupActionUser){
				actionuser{
					name(useritem.groupName)
					email(useritem.groupName)
				}
			}
		}
	}
	def notifyusers(xml,emailGroupNotifyUser){
		xml.notifyusers{
			for(useritem in emailGroupNotifyUser){
				actionuser{
					name(useritem.groupName)
					email(useritem.groupName)
				}
			}
		}
	}
	def removeDocuments(xml,docDeleted,emailGroupNotifyUser,emailGroupActionUser){
		xml.remove{
			for(doc in docDeleted){
				document{
					id(doc.id)
					title(doc.name)
					discipline(doc.documentTemplate.discipline)
					removeDocumentCheckpoints(xml,doc,emailGroupNotifyUser,emailGroupActionUser)
					notifyusers(xml,emailGroupNotifyUser)
				}
			}
		}
	}
	def updateDocuments(xml,docEdited,emailGroupNotifyUser,emailGroupActionUser){
		xml.update{
			for(doc in docEdited){
				if(doc.lastUpdated > doc.dateCreated)
				{
					document{
						id(doc.id)
						title(doc.name)
						discipline(doc.documentTemplate.discipline)
						updateDocumentCheckpoints(xml,doc,emailGroupNotifyUser,emailGroupActionUser)
						notifyusers(xml,emailGroupNotifyUser)
					}
				}
			}

		}
	}
	def addDocumentCheckpoints(xml,doc,emailGroupNotifyUser,emailGroupActionUser){
		xml.checkpoints{
			for(templates in doc.documentTemplate){
				for(items in templates.documentItems){
					checkpoint{
						if(items.type=="Text") {
							type("text")
							description(items.formElementName)
						}// ipad app currently can not handle checkpoint of type table and image so it will be rendered as text
						if(items.type=="Checkpoint"){
							type("checkpoint")
							description(items.formElementName)
							actionusers(xml,emailGroupActionUser)
						}
					}
					if(items.type=="Table"){
						log.debug "reached tables tag====================================="
						def questions = Question.findAllWhere(documentItem:items)
						log.debug "questions are====="+questions
						tableCheckpoints(xml,questions,doc,items)
					}
				}
			}
		}
	}
	
	def removeDocumentCheckpoints(xml,doc,emailGroupNotifyUser,emailGroupActionUser){
		xml.checkpoints{
			for(templates in doc.documentTemplate){
				for(items in templates.documentItems){
					checkpoint{
						if(items.type=="Text") {
							type("text")
							description(items.formElementName)
						}// ipad app currently can not handle checkpoint of type table and image so it will be rendered as text
						if(items.type=="Checkpoint"){
							type("checkpoint")
							description(items.formElementName)
							actionusers(xml,emailGroupActionUser)
						}
					}
				}
			}
		}
	}
	def updateDocumentCheckpoints(xml,doc,emailGroupNotifyUser,emailGroupActionUser){
		xml.checkpoints{
			for(templates in doc.documentTemplate){
				for(items in templates.documentItems){
					checkpoint{
						if(items.type=="Text") {
							type("text")
							description(items.formElementName)
						}// ipad app currently can not handle checkpoint of type table and image so it will be rendered as text
						if(items.type=="checkpoint"){
							actionusers(xml,emailGroupActionUser)
						}
					}
				}
			}
		}
	}
	def tableCheckpoints(xml,questions,doc,items){
		log.debug "in table checkpoints function"
		log.debug "questions received are==="+questions
		def docItemValue = DocumentItemValue.findByDocumentAndDocumentItem(doc,items)
		log.debug "document item value is==="+docItemValue
		for(quest in questions){
			def questionValue = QuestionValue.findAllWhere(question:quest,document:doc,documentItem:items,documentItemValues:docItemValue)
			log.debug "question value is===="+questionValue
			for(newquest in questionValue){
			xml.checkpoint{
			id(newquest.id)
			if(newquest.checkpointType=="TextBox"){
				type("tabletextarea")
			}
			if(newquest.checkpointType=="RadioButton"){
			def headerList = Header.findWhere(documentItem:items, checkpointType:'RadioButton')
			log.debug "headerList received is==="+headerList
				type("tableradiobutton")
				options(headerList.name)
			}
			if(newquest.checkpointType=="Checkpoint"){
				type("tablecheckpoint")
			}
			description(newquest.question.question)
			
			}
			}
		}
	}
	
}
