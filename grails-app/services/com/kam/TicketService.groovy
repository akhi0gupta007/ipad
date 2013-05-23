package com.kam
import org.springframework.transaction.annotation.Transactional
import grails.converters.XML
import groovy.xml.MarkupBuilder
import java.text.DateFormat
import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.springframework.security.authentication.dao.SaltSource;
class TicketService {
	def springSecurityService
	def saltSource


	def getTickets(def object){

		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)

		log.debug "springsecurityservice is "+springSecurityService
		log.debug "saltsource is=" +saltSource
		log.debug "xml in Ticket Service is"+object
		def requestType = object.requesttype
		log.debug "request type in ticket Service is=="+requestType
		def test = object.loginvalidation.password
		log.debug "password in ticket service service is==" +test
		def paramLoginName = object.loginvalidation.loginname
		log.debug "username in ticket service is==" + paramLoginName
		String salt = saltSource instanceof NullSaltSource ? object.loginvalidation.loginname:null
		log.debug "salt in ticket service is"+ salt
		String encodedPassword = springSecurityService.encodePassword(object.loginvalidation.password.toString())
		log.debug encodedPassword
		def projectName = object.project.id
		def projectBuilding = object.project.building
		log.debug "Project name received is=="+projectName+" and project building is="+projectBuilding
		def dbUser = User.findByUsernameAndPassword(salt,encodedPassword)
		log.debug dbUser
		if(dbUser){
			if(dbUser.enabled==true) {
				def customer = Customer.findById(User.get(dbUser.id).customer.id)
				log.debug "dbCustomer in ticket service getTickets is ==" + customer

				def projectInfo = Project.findByProjectName(projectName.toString())
				log.debug "Project in ticket service getTickets is=="+projectInfo
				def findBuilding = Building.findById(projectBuilding.toString())
				log.debug "Project's building id is="+findBuilding
				def emailGroup = EmailGroup.findAllWhere(customer:customer)
				log.debug "email groups are =="+emailGroup
				def emailGroupNotifyUser = EmailGroup.findAllWhere(customer:customer,groupType:"NotifyUser")
				log.debug "email groups are =="+emailGroupNotifyUser
				def emailGroupActionUser = EmailGroup.findAllWhere(customer:customer,groupType:"ActionUser")
				log.debug "email groups are =="+emailGroupActionUser
				def dbSavedDocument = SavedDocument.findAllWhere(isNowaTicket:true)
				log.debug "dbSavedDocument size is=="+dbSavedDocument
				def getProjectName = projectInfo.projectName;
				if(dbSavedDocument.size()==0){
					return noTicket(object,projectInfo,findBuilding)
				}
				else{
					xml.'?xml version="1.0" encoding="UTF-8"?'{
						digikam{
						requesttype(requestType)
						loginvalidation{
							loginname(paramLoginName)
							password(test)
							response("OK")
							message("Login Validated")
						}
						project{
							id(projectInfo.projectName)
							building(projectBuilding)
						}
						ticketDocumentXml(xml,dbSavedDocument,emailGroupActionUser,getProjectName,findBuilding,emailGroupNotifyUser)
					}
					}
					log.debug "get ticckets response is=="+writer.toString()
					return writer.toString()
				}
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

	def tableCheckpoints(xml,questions,doc,items){
		log.debug "in table checkpoints function"
		log.debug "questions received are==="+questions
		//def docItemValue = DocumentItemValue.findByDocumentAndDocumentItem(doc,items)
		def docItemValue = DocumentItemValue.findAllWhere(document:doc,documentItem:items)
		log.debug "document item value is==="+docItemValue
		for(quest in questions){
			def questionValue = QuestionValue.findAllWhere(question:quest,document:doc,documentItem:items,documentItemValues:docItemValue.getAt(0))
			log.debug "question value is===="+questionValue
			for(newquest in questionValue){
				xml.checkpoint{
					id(newquest.id)
					if(newquest.checkpointType=="TextBox"){
						type("tabletextarea")
					}
					if(newquest.checkpointType=="RadioButton"){
						def headerList = Header.findAllWhere(documentItem:items)
						log.debug "headerList received is==="+headerList
						type("tableradiobutton")
						//options("option-1;option-2")
					}
					if(newquest.checkpointType=="Checkpoint"){
						type("tablecheckpoint")
					}
					description(newquest.question.question)
				}
			}
		}
	}
	def postTickets(def object,def requiredXml){
		def checkpointStatus
		def lastVersion
		def globalTicket
		def savedDocument
		def checkpointComment
		def checkpointPriority
		def checkpointPriorityDate
		def checkpointArray = []
		def reader = new StringReader(requiredXml)
		def xmlRead = new XmlParser().parse(reader)
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		def xmlContent =  new ArrayList()
		JSONArray jsonArray = new JSONArray()
		JSONObject jsonObject
		log.debug "springsecurityservice is "+springSecurityService
		log.debug "saltsource is=" +saltSource
		log.debug "xml in Post-Ticket Service is"+object
		log.debug "documents received are"+object.documents.document
		def requestType = object.requesttype
		log.debug "request type in document Service is=="+requestType
		def test = object.loginvalidation.password
		log.debug "password in ticket service service is==" +test
		def paramLoginName = object.loginvalidation.loginname
		log.debug "username in ticket service is==" + paramLoginName
		String salt = saltSource instanceof NullSaltSource ? object.loginvalidation.loginname:null
		log.debug "salt in ticket service is"+ salt
		String encodedPassword = springSecurityService.encodePassword(object.loginvalidation.password.toString())
		log.debug encodedPassword
		def dbUser = User.findByUsernameAndPassword(salt,encodedPassword)
		log.debug "DB user in posttickets is"+dbUser
		if(dbUser){
			if(dbUser.enabled==true){

				log.debug xmlRead.getClass()
				log.debug xmlRead.name()
				xmlRead.documents.each{documents->
					documents.document.each {document->
						log.debug document.id.text()
						def project = Project.findByProjectName(document.project.id.text())
						log.debug "project found is=="+project
						def ticketDoc = Document.findById(document.id.text())
						def ticketFloor = Floor.findByFloorNumber(document.project.floor.text())
						def ticketRoom = Room.findByRoomId(document.project.room.text())
						def ticketBuilding = Building.findById(document.project.building.text())
						def ticketTemplate = ticketDoc.documentTemplate
						log.debug "document's template is="+ticketTemplate
						def ticketDocumentItem = DocumentItem.findAllWhere(documentTemplate:ticketTemplate)
						log.debug "Document Item Id is"+ticketDocumentItem
						document.checkpoints.each{checkpoints->
							try{
								checkpoints.checkpoint.each{checkpoint->
									if(checkpoint.type.text()=='text') {
										def docItem = DocumentItem.findByFormElementNameAndDocumentTemplate(checkpoint.description.text().toString(),ticketTemplate)
										log.debug "ticcket document found is=="+ticketDoc
										def documentItemValue = DocumentItemValue.findByDocumentItemAndDocument(docItem,ticketDoc)
										log.debug "1 earlier value for docitem--"+docItem.formElementName+"--is =="+documentItemValue.formElementValue
										documentItemValue.formElementValue = checkpoint.value.text().toString()
										log.debug "2 new value entered is=="+documentItemValue.formElementValue
									}
									if (checkpoint.type.text()=='checkpoint'&& checkpoint.value.text()=="3") {
										def questValue = QuestionValue.findById(checkpoint.id.text().toLong())
										def question1 = questValue.question
										log.debug "question for tis is ====="+question1
										def reqqvalue = QuestionValue.findAllWhere(question:question1,documentItem:questValue.question.documentItem)
										log.debug "totaol reqqvalue found are====="+reqqvalue
										reqqvalue.getAt(2).questionValue = "3"
										checkpointStatus = "3"
										checkpointArray.add("3")
									}
									if(checkpoint.type.text()=="checkpoint" && checkpoint.value.text()=="1"){
										def questValue = QuestionValue.findById(checkpoint.id.text().toLong())
										def question2 = questValue.question
										log.debug "question for tis is ====="+question2
										def reqqvalue = QuestionValue.findAllWhere(question:question2,documentItem:questValue.question.documentItem)
										log.debug "totaol reqqvalue found are====="+reqqvalue
										reqqvalue.getAt(1).questionValue = "1"
										checkpointStatus = "1"
										checkpointArray.add("1")
									}
									 if(checkpoint.type.text()=="checkpoint" && checkpoint.value.text()=="2"){
										def questValue = QuestionValue.findById(checkpoint.id.text().toLong())
										def question3 = questValue.question
										log.debug "question for tis is ====="+question3
										def reqqvalue = QuestionValue.findAllWhere(question:question3,documentItem:questValue.question.documentItem)
										log.debug "totaol reqqvalue found are====="+reqqvalue
										reqqvalue.getAt(3).questionValue = "2"
										checkpointStatus = "2"
										checkpointArray.add("2")
									}
									 if(checkpoint.type.text()=="checkpoint" && checkpoint.value.text()=="4"){
										def questValue = QuestionValue.findById(checkpoint.id.text().toLong())
										def question4 = questValue.question
										log.debug "question for tis is ====="+question4
										def reqqvalue = QuestionValue.findAllWhere(question:question4,documentItem:questValue.question.documentItem)
										log.debug "totaol reqqvalue found are====="+reqqvalue
										reqqvalue.getAt(4).questionValue = "4"
										checkpointStatus = "4"
										checkpointArray.add("4")
									}
								}
							}catch(Exception e){
								e.printStackTrace()
							}
							checkpoints.checkpoint.each{checkpoint->
								if(checkpoint.type.text()=="tabletextarea"){
									log.debug "i came inside tabletextarea======"+checkpoint.id.text()
									def questValue = QuestionValue.findByIdAndCheckpointType(checkpoint.id.text().toLong(),"TextBox")
									log.debug "question value found is==="+questValue
									questValue.questionValue = checkpoint.value.text().toString()
									log.debug "now question value is=="+questValue.questionValue
								}
								if(checkpoint.type.text()=="tableradiobutton"){
									def questValue = QuestionValue.findByIdAndCheckpointType(checkpoint.id.text().toLong(),"RadioButton")
									log.debug "question value found is==="+questValue
									questValue.questionValue = checkpoint.value.text().toString()
									log.debug "now question value is=="+questValue.questionValue
								}
							}
						}
						def ticket = new Ticket(document:ticketDoc,floor:ticketFloor,room:ticketRoom)
						ticket.save()
						globalTicket=ticket.id
						log.debug "checkpoint status before -.......>>>>>>is=="+checkpointStatus
						def latestCheckpointStatus = getcheckpointStatus(checkpointArray)
						log.debug "final document's status is===="+latestCheckpointStatus
						if(latestCheckpointStatus == "Afgekeurd"){
							//ticketDoc
							createNewDocumentVersion(project,ticketDoc,ticketTemplate,document,dbUser,checkpointPriorityDate,ticketFloor,ticketRoom)
							log.debug "checkpoint status after ...>>is=="+checkpointStatus
							def documentReport = new DocumentReport(document:ticketDoc,user:dbUser)
							documentReport.save()
							Date savedDocumentDeadLine
							if(checkpointPriorityDate!=null){
								savedDocumentDeadLine = convertToDate(checkpointPriorityDate.toString())
							}
							savedDocument = new SavedDocument(isNowaTicket:true,comments:checkpointComment,status:checkpointStatus.toString(),title:document.title.text(),priority:checkpointPriority,priorityDate:checkpointPriorityDate,document:ticketDoc,room:ticketRoom,building:ticketBuilding,user:dbUser,project:project,documentReport:documentReport)
							savedDocument.save()
							if(savedDocument.hasErrors()) {
								savedDocument.errors.each{ println it }
							}
							def list = SavedDocument.findAllWhere(document:ticketDoc).sort{it.documentVersion}
							log.debug "list is=="+list
							def lastSavedDocument=list.get(list.size()-1)
							savedDocument.documentVersion = lastSavedDocument.documentVersion+1
							log.debug "now document version is=="+savedDocument.documentVersion
						}
						else{
							ticketDoc.status = latestCheckpointStatus.toString()
							def oldSavedDocument = SavedDocument.findAllWhere(document:ticketDoc,user:dbUser)
							log.debug "saved documents for that document are=="+oldSavedDocument
							for(docs in oldSavedDocument){
								docs.isNowaTicket = false
								log.debug "now its status=="+docs.isNowaTicket
							}
						}
						document.attachments.attachment.each{attachment->
							log.debug "attachmentreceived is=="+attachment.id.text()
							def image = new Image(name:attachment.id.text(),document:ticketDoc,ticket:ticket)
							image.save()
							log.debug "image was saved"
						}
						String jsonstatus
						if(latestCheckpointStatus=="Afgekeurd"){
							jsonstatus = "3"
						}
						else if(latestCheckpointStatus=="NVT"){
							jsonstatus = "2"
						}
						else if(latestCheckpointStatus=="Goedgekeurd"){
							jsonstatus = "1"
						}
						else if(latestCheckpointStatus=="Akkoord na herkeuring"){
							jsonstatus = "4"
						}
						jsonObject = new JSONObject()
						jsonObject.put("documentId", ticketDoc.id)
						jsonObject.put("ticketId", globalTicket)
						jsonObject.put("checkpointStatus", jsonstatus)
						jsonArray.add(jsonObject)
						if(latestCheckpointStatus=="Goedgekeurd"||latestCheckpointStatus=="Akkoord na herkeuring"){
							
							
							}
					}
				}
				xml.'?xml version="1.0" encoding="UTF-8"?'{
					digikam{
					requesttype(requestType)
					loginvalidation{
						loginname(paramLoginName)
						password(test)
						response("OK")
						message("Login Validated")
					}
					documents{
						log.debug "json array size is==="+jsonArray.size()
						for(int i=0;i<jsonArray.size();i++){
							document{
								log.debug "document id is==="+xmlRead.documents.document.id[i].text()
								id(xmlRead.documents.document.id[i].text())
								def documetForTicket = Document.findById(xmlRead.documents.document.id[i].text())
								log.debug "document for the ticket found is=="+documetForTicket
								log.debug "document title value===="+xmlRead.documents.document.title[i].text()
								title(xmlRead.documents.document.title[i].text())
								log.debug "document title value===="+xmlRead.documents.document.title[i].text()
								discipline(documetForTicket.documentTemplate.discipline)
								log.debug " after document title value===="+xmlRead.documents.document.title[i].text()
								//log.debug "before document version===="+xmlRead.documents.document.version[i].text()
								//if(documents.document.version[i].text()==null)
								//version(xmlRead.documents.document.version[i].text())
								//log.debug "after document version===="+xmlRead.documents.document.version[i].text()
								project{
									id(xmlRead.documents.document.project.id[i].text())
									building(xmlRead.documents.document.project.building[i].text())
									floor(xmlRead.documents.document.project.floor[i].text())
									room(xmlRead.documents.document.project.room[i].text())
								}
								status(jsonArray[i].get("checkpointStatus"))
								ticketid(jsonArray[i].get("ticketId"))
								response("OK")
							}
						}
					}
				}
				}
				log.debug "response for POSTTICKETS is=="+writer.toString()
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

	def postAttachment(def username,def passwordReceived,def mode,def signatureId,def ticketid){
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		String saltUserName = saltSource instanceof NullSaltSource ? username:null
		log.debug "salt in document service is"+ saltUserName
		log.debug "username and password are"+username+"and pass word is"+passwordReceived
		String encodedPassword = springSecurityService.encodePassword(passwordReceived)
		log.debug encodedPassword
		def dbUser = User.findByUsernameAndPassword(username,encodedPassword)
		log.debug dbUser
		if(dbUser){
			if(dbUser.enabled==true){
				def ticket = SavedDocument.findById(ticketid)
				log.debug "saved document ID is =="+ticket
				xml.'?xml version="1.0" encoding="UTF-8"?'{
					digikam{
					requesttype("POSTATTACHMENT")
					loginvalidation{
						loginname(username)
						password(passwordReceived)
						response("OK")
						message("Login Validated")
					}
					attachment{
						id(signatureId)
						response("OK")
					}
				}
				}
				log.debug "before sending response for post attachment to controller"+writer.toString()
				return writer.toString()

			}
			if(dbUser.enabled==false){
				xml.'?xml version="1.0" encoding="UTF-8"?'{
					digikam{
					requesttype("POSTATTACHMENT")
					loginvalidation{
						loginname(username)
						password(passwordReceived)
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
				requesttype("POSTATTACHMENT")
				loginvalidation{
					loginname(username)
					password(passwordReceived)
					response("ERROR")
					message("Wrong Username/Password")
				}
			}
			}
			return writer.toString()
		}

	}
	def noTicket(def receivedObject,def receivedProject,def receivedBuilding){
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		log.debug "received project is=="+receivedProject
		log.debug "received Building is=="+receivedBuilding
		xml.'?xml version="1.0" encoding="UTF-8"?'{
			digikam{
			requesttype(receivedObject.requesttype)
			loginvalidation{
				loginname(receivedObject.loginvalidation.loginname)
				password(receivedObject.loginvalidation.password)
				response("OK")
				message("Login Validated")
			}
			project{
				id(receivedProject.projectName)
				building(receivedBuilding.buildingNumber)
			}
		}
		}
		log.debug "output for no tickets is=="+writer.toString()
		return writer.toString()
	}
	def postBuildingAttachment(def username,def passwordReceived,def mode,def attachmentId){
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		String saltUserName = saltSource instanceof NullSaltSource ? username:null
		log.debug "salt in document service is"+ saltUserName
		log.debug "username and password are"+username+"and pass word is"+passwordReceived
		String encodedPassword = springSecurityService.encodePassword(passwordReceived)
		log.debug encodedPassword
		def dbUser = User.findByUsernameAndPassword(username,encodedPassword)
		log.debug dbUser
		if(dbUser){
			if(dbUser.enabled==true){
				
					xml.digikam{
					requesttype("POSTBUILDINGATTACHMENT")
					loginvalidation{
						loginname(username)
						password(passwordReceived)
						response("OK")
						message("Login Validated")
					}
					attachment{
						id(attachmentId)
						response("OK")
					}
				}
				log.debug "before sending response for post attachment to controller"+writer.toString()
				return writer.toString()

			}
			if(dbUser.enabled==false){
				
					xml.digikam{
					requesttype("POSTBUILDINGATTACHMENT")
					loginvalidation{
						loginname(username)
						password(passwordReceived)
						response("DENIED")
						message("User not allowed to use iPad App")
					}
				}
				return writer.toString()
			}
		}
		else{
			
				xml.digikam{
				requesttype("POSTBUILDINGATTACHMENT")
				loginvalidation{
					loginname(username)
					password(passwordReceived)
					response("ERROR")
					message("Wrong Username/Password")
				}
			}
			return writer.toString()
		}

	}
	def convertToDate(def stringDate) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd")
		def date = dateFormat.parse(stringDate)
		return date
	}
	def createNewDocumentVersion(project,ticketDoc,ticketTemplate,document,dbUser,checkpointPriorityDate,ticketFloor,ticketRoom){
		log.debug "going to create new version"
		log.debug "checkpoint priority date has class==="+checkpointPriorityDate.getClass()+"and value==="+checkpointPriorityDate
		def customer = Customer.findById(User.get(dbUser.id).customer.id)
		Date receivedDate
		if(checkpointPriorityDate!=null){
			log.debug "inside checkpoint prioritydate"
			receivedDate = convertToDate(checkpointPriorityDate)
		}
		def newDocumentVersion = new Document(name:ticketDoc.name,status:"Afgekeurd",documentNumber:ticketDoc.documentNumber,updatedBy:dbUser.username,discipline:ticketTemplate.discipline,isDeleted:false,customer:customer,project:project,deadLine:receivedDate,documentTemplate:ticketTemplate)
		newDocumentVersion.save()
		if(newDocumentVersion.hasErrors()){
			newDocumentVersion.errors.each { println "error in saving a new document is===="+it }
		}
		log.debug "document was saved ant its is==="+newDocumentVersion.id
		def ticket = new Ticket(document:newDocumentVersion,floor:ticketFloor,room:ticketRoom)
		ticket.save()
			log.debug "now going to create its fields"
			//log.debug "values in document object are"+document
			def tableItem = DocumentItem.findByTypeAndDocumentTemplate("Table",ticketTemplate)
			log.debug "tableItem found is ====="+tableItem
			def tableDocumentItemValue = new DocumentItemValue(alignment:"Left",document:newDocumentVersion,documentItem:tableItem,formElementValue:"table",type:"Table",position:"Body")
			tableDocumentItemValue.save(flush:true)
			
		document.checkpoints.checkpoint.each{checkpoint->
			log.debug "checkpoint type is==="+checkpoint.type.text()
				if(checkpoint.type.text()=="text"){
					log.debug "form element value from post request is="+checkpoint.value.text()
					def docItem = DocumentItem.findByFormElementNameAndDocumentTemplate(checkpoint.description.text().toString(),ticketTemplate)
					log.debug "document item for creating new version is=="+docItem
					log.debug "new  document version is=="+newDocumentVersion
					def documentItemValue =new  DocumentItemValue(formElementValue:checkpoint.value.text().toString(),position:docItem.position,type:docItem.type,alignment:docItem.alignment,documentItem:docItem,document:newDocumentVersion)
					documentItemValue.save()
					log.debug "document item value for new version is=="+documentItemValue
				}
				else if(checkpoint.type.text()=="checkpoint"){
					createAndUpdateQuestionValue(checkpoint,newDocumentVersion,tableItem,tableDocumentItemValue)
					/*def questionValue = QuestionValue.findById(checkpoint.id.text().toLong())
					log.debug "question item found is==="+questionValue
					log.debug "tableDocumentItemValue"+tableDocumentItemValue
					log.debug "questionValue.question"+questionValue.question
					def newQuestionValue = new QuestionValue(questionValue:checkpoint.value.text(),checkpointType:"CheckBox",document:newDocumentVersion,documentItem:tableItem,documentItemValues:tableDocumentItemValue,question:questionValue.question)
					newQuestionValue.save()
					if(newQuestionValue.hasErrors()){
						newQuestionValue.errors.each{
						println "error in saving new questionvalue==="+it
						}
					}*/
				}
				else if(checkpoint.type.text()=="tabletextarea"){
					def questionItem = Question.findById(checkpoint.id.text().toLong())
					log.debug "question item found is==="+questionItem
				}
				else if(checkpoint.type.text()=="tableradiobutton"){
					def questionItem = Question.findById(checkpoint.id.text().toLong())
					log.debug "question item found is==="+questionItem
				}
		}
		document.attachments.attachment.each{attachment->
			log.debug "attachmentreceived is=="+attachment.id.text()
			def image = new Image(name:attachment.id.text(),document:newDocumentVersion,ticket:ticket)
			image.save()
			log.debug "image was saved"
		}
		def documentImageItems  = DocumentItem.createCriteria().list{
			and{
				eq("documentTemplate",ticketTemplate)
				eq("type","Image")
				eq("position","Title")
			}
			or{
				eq("alignment","Left")
				eq("alignment","Right")
				eq("alignment","Center")
			}
		}
		log.debug "DocumentImage item is===="+documentImageItems
		if(documentImageItems.size()!=0){
			def imageItemValueName = DocumentItemValue.findByDocumentAndDocumentItem(ticketDoc,documentImageItems.get(0))
			log.debug "document item value is=="+imageItemValueName+"and its form element value=="+imageItemValueName.formElementValue
			def imageItemValue = new DocumentItemValue(formElementValue:imageItemValueName.formElementValue,position:documentImageItems.position.get(0),type:documentImageItems.type.get(0),alignment:documentImageItems.alignment.get(0),documentItem:documentImageItems.get(0),document:newDocumentVersion)
			imageItemValue.save()
		}
		def list = Document.findAllWhere(name:newDocumentVersion.name).sort{it.documentVersion}
		log.debug "list is=="+list
		def lastDocument=list.get(list.size()-1)
		log.debug "got list of lastdocuments=="+lastDocument
		newDocumentVersion.documentVersion = lastDocument.documentVersion+1
		newDocumentVersion.save(flush:true)
		log.debug "assigned document version>>>>>>>>>>>>>>>>>>>>>>>>>>"
		log.debug "now document version is=="+newDocumentVersion.documentVersion
		
		/*if(checkpointPriorityDate.size()!=null){
		 newDocumentVersion.deadLine = convertToDate(checkpointPriorityDate.toString())
		 }*/
	}
	def ticketDocumentXml(xml,dbSavedDocument,emailGroupActionUser,getProjectName,findBuilding,emailGroupNotifyUser){
		xml.documents{
			for(doc in dbSavedDocument){
				document{
					id(doc.document.id)
					title(doc.document.name)
					discipline(doc.document.documentTemplate.discipline)
					version(doc.documentVersion)
					project{
						id(getProjectName)
						building(findBuilding)
						floor(doc.room.floor.floorNumber)
						room(doc.room.roomId)
					}
					ticketDocumentXmlCheckpoint(xml,doc,emailGroupActionUser)
					attachments{
						for(attachedimages in doc.document.images){
							attachment{ id(attachedimages.name) }
						}
					}
					notifyusers{
						for(useritem in emailGroupNotifyUser){
							notifyuser{
								user(useritem.groupName)
								email(useritem.groupName)
							}
						}
					}
					status(doc.status)
				}
			}
		}

	}
	def ticketDocumentXmlCheckpoint(xml,doc,emailGroupActionUser){
		xml.checkpoints{
			for(templates in doc.document.documentTemplate){
				for(items in templates.documentItems){
					checkpoint{
						if(items.type=="Text"){
							type("text")
							description(items.formElementName)
						}
						if(items.type=="Checkpoint"){
							type("checkpoint")
							description(items.formElementName)
							actionusers{
								for(useritem in emailGroupActionUser){
									actionuser{
										user(useritem.groupName)
										email(useritem.groupName)
									}
								}
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
	}
	def getcheckpointStatus(checkpointArray){
		int afgekerudOccurrences = Collections.frequency(checkpointArray, "3");
		int goedgekeurdOccurences = Collections.frequency(checkpointArray, "1");
		int akkordNaHarkeuringOccurences = Collections.frequency(checkpointArray, "4");
		int nvtOccurences = Collections.frequency(checkpointArray, "2");
		log.debug "it has afkgeurd occurences===="+afgekerudOccurrences
		if(afgekerudOccurrences!=0){
			return "Afgekeurd"
		}
		else if(afgekerudOccurrences==0&&goedgekeurdOccurences!=0){
			return "Goedgekeurd"
		}
		else if(afgekerudOccurrences==0&&goedgekeurdOccurences==0&&akkordNaHarkeuringOccurences!=0){
			return "Akkoord na herkeuring"
		}
		else if(afgekerudOccurrences==0&&goedgekeurdOccurences==0&&akkordNaHarkeuringOccurences==0&&nvtOccurences!=0){
			return "NVT"
		}
	}
	def createAndUpdateQuestionValue(checkpoint,newDocumentVersion,tableItem,tableDocumentItemValue){
		int indexToUpdate
		def newQuestionValue
		def header = Header.findAllWhere(documentItem:tableItem)
		log.debug "header size is===="+header.size()
		def questionValue = QuestionValue.findById(checkpoint.id.text().toLong())
		log.debug "question item found is==="+questionValue
	
		if(checkpoint.value.text()=="1"){
			indexToUpdate = 1
		}
		else if(checkpoint.value.text()=="2"){
			indexToUpdate = 3
		}
		else if(checkpoint.value.text()=="3"){
			indexToUpdate = 2
		}
		else if(checkpoint.value.text()=="4"){
			indexToUpdate = 4
		}
		for(int i=1;i<=header.size()-2;i++){
		log.debug "values for table innew versoin=="+checkpoint.value.text()
		if(i==indexToUpdate){
		 newQuestionValue = new QuestionValue(questionValue:checkpoint.value.text(),checkpointType:"CheckBox",document:newDocumentVersion,documentItem:tableItem,documentItemValues:tableDocumentItemValue,question:questionValue.question)
		newQuestionValue.save()
		}
		else{
			newQuestionValue = new QuestionValue(questionValue:"",checkpointType:"CheckBox",document:newDocumentVersion,documentItem:tableItem,documentItemValues:tableDocumentItemValue,question:questionValue.question)
			newQuestionValue.save()
		}
		if(newQuestionValue.hasErrors()){
			newQuestionValue.errors.each{
			println "error in saving new questionvalue==="+it
			}
		}
		}
	}
}