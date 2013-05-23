package com.kam

import org.apache.commons.lang.time.DateUtils;
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource

class PostBuildingMailService {
	def springSecurityService
	def saltSource
	def pdfService
	def grailsApplication
	def asynchronousMailService
    def serviceMethod() {

    }
	def sendBuildingSynchronisationMail(def object,def requiredXml){
		def reader = new StringReader(requiredXml)
		def xmlRead = new XmlParser().parse(reader)
		def requestType = object.requesttype
		def test = object.loginvalidation.password
		log.debug "password in postmaster service service is==" +test
		def paramLoginName = object.loginvalidation.loginname
		log.debug "username in postmaster service is==" + paramLoginName
		String salt = saltSource instanceof NullSaltSource ? object.loginvalidation.loginname:null
		log.debug "salt in postmaster service is"+ salt
		String encodedPassword = springSecurityService.encodePassword(object.loginvalidation.password.toString())
		log.debug encodedPassword
		def dbUser = User.findByUsernameAndPassword(salt,encodedPassword)
		log.debug "DB user in postMaster is"+dbUser
		def customer = Customer.findById(User.get(dbUser.id).customer.id)
		log.debug "dbCustomer in kam service is ==" + customer
		parseDataForMail(dbUser,xmlRead,customer)
	}
	def parseDataForMail(dbUser,xmlRead,customer){
		def buildingReportId
		xmlRead.buildings.each{buildings->
			def currentActionUsers = []
			def currentNotifyUsers = []
			buildings.building.each{building->
				log.debug "building is=="+building.id.text()
				def reportBuilding = Building.findById(building.id.text())
				log.debug "building found in database>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+reportBuilding
				buildingReportId = findLatestReport(reportBuilding)
				building.report.each{report->
					report.actionusers.actionuser.each{actionuser->
						currentActionUsers.add(actionuser.name.text().toString())
					}
					report.notifyusers.notifyuser.each{notifyuser->
						currentNotifyUsers.add(notifyuser.name.text().toString())
					}
				}
				//callequipmentPDF(buildingReportId,dbUser,customer)
				sendBuildingSynchronisationMail(reportBuilding,buildingReportId,currentActionUsers,currentNotifyUsers,customer,dbUser)
			}
		}
	}
	def findLatestReport(equipmentBuilding){
		def report = Report.findAllWhere(building:equipmentBuilding)
		log.debug "reports found ==="+report
		Collections.reverse(report)
		def latestReport = report.getAt(0)
		return latestReport
	}
	def callequipmentPDF(buildingReportId,customer){
		byte[] b
		def redirectUrl="/pdf/equipmentReportPdf/"+buildingReportId.id
		log.debug "log after redirect url"
		def pdfLink = grailsApplication.config.report.server.url+redirectUrl
		log.debug "pdfLink created is===="+pdfLink
		try{
		b = pdfService.buildPdf(pdfLink)
		log.debug "report link  is now====="+pdfLink
		log.debug "building report received ===="+buildingReportId
		OutputStream out = new FileOutputStream(grailsApplication.config.report.pdf.server.path+customer.name+"-Report-"+buildingReportId.reportName.replace("/","-")+buildingReportId.id+".pdf");
		out.write(b);
		out.close();
		}catch(FileNotFoundException fnfe){
		fnfe.printStackTrace()
		}
		catch(NullPointerException npe){
			npe.printStackTrace()
			}
	}
	def sendBuildingSynchronisationMail(buildingId,buildingReportId,currentActionUsers,currentNotifyUsers,customer,dbUser){
		log.debug "asynchronous mail service is===="+asynchronousMailService
		log.debug "customer found is==="+customer
		String equipmentData = ''
		//def building = Building.findById(buildingId.id)
		def building = buildingId
		log.debug "building found is==="+building
		String path = grailsApplication.config.report.pdf.server.path+customer.name+"-Report-"+buildingReportId.reportName.replace("/","-")+buildingReportId.id+".pdf"
			for(notifyusers in currentNotifyUsers){
				def groupName = EmailGroup.findWhere(groupName:notifyusers)
				log.debug "group name found for action users are===="+groupName
				for(def member in groupName.members){
					asynchronousMailService.sendMail {
						to member.email
						from customer.website.replace("www.","noreply@")
						subject 'ULC keuringsrapport  Noodverlichting Projectnaam '+' '+building.project.projectName
						html "Geachte klant"+'<br/><br/>'+"Hierbij ontvangt u het keuringsrapport  Noodverlichting van inspectiedatum :"+new Date().format("dd/MM/yyyy")+'<br/><br/>'+'Met vriendelijke groet ,'+'<br/><br/>'+customer.name+'<br/>'+customer.address+'<br/>'+customer.city+'<br/>'+customer.contact
						attachBytes customer.name+buildingReportId.reportName.replace("/","-")+buildingReportId.id+".pdf","application/pdf",new File(path).readBytes()
					}
				}
		}
		for(actionusers in currentActionUsers){
			String floorData
			String totalData= ' .'
			//StringBuilder sb = new StringBuilder()
			def groupName = EmailGroup.findWhere(groupName:actionusers)
			log.debug "group name found action users are===="+groupName
			def floors = Floor.findAllByBuildingAndIsDeleted(building,false)
			log.debug "floors are===="+floors+"and their size is===="+floors.size()
			for(def member in groupName.members){
					//def getBuilding = Building.findById(buildingId)
					for(floor in floors){
						StringBuilder sb = new StringBuilder()
						floorData = "Project: "+building.project.projectName+", "+"Building: "+building.buildingName+','+"Floor: "+floor.floorNumber+'<br/><br/>'
						log.debug "floor data is==="+floorData
						def equipments = Equipment.findAllWhere(floor:floor,isDeleted:false,status:'Afgekeurd')
						log.debug "equipments found are==="+equipments+"and their size is===="+equipments.size()
						if(equipments.size()!=0){
							for(int i=0;i<equipments.size();i++){
								sb.append("<br/><br/>"+equipments[i].name+"<br/>"+equipments[i].spec)
							}
						log.debug "equipment data received is======="+sb.toString()
						log.debug "floor data received ==="+floorData
						totalData = floorData+sb.toString()
				}
						else{
							totalData = floorData
						}
			}
				log.debug "total data to send ====="+totalData
				//String path = grailsApplication.config.report.pdf.server.path+dbUser.customer.name+"-Report-"+buildingReport.reportName.replace("/","-")+buildingReport.id+".pdf"
				asynchronousMailService.sendMail {
					multipart true
					to member.email
					from customer.website.replace("www.","noreply@")
					subject 'Afgekeurde armaturen '
					html totalData
				}
			}
		}
	}
	def sendMailWithSchedular(){
		def buildingList = []
		def sendMails = SendSynchronisationMail.findAllWhere(isSent:false)
		log.debug "sendMails value is======"+sendMails
		for(buildings in sendMails){
			buildingList.add(buildings.building)
		}
		for(building in buildingList){
		def latestBuildingReport = findLatestReport(building)
		callequipmentPDF(latestBuildingReport,building.project.customer)
		sendBuildingSyncMail(building,latestBuildingReport,building.project.customer)
		}
	}
	def sendBuildingSyncMail(building,latestBuildingReport,customer){
		String path = grailsApplication.config.report.pdf.server.path+customer.name+"-Report-"+latestBuildingReport.reportName.replace("/","-")+latestBuildingReport.id+".pdf"
		def sendMails = SendSynchronisationMail.findAllWhere(isSent:false,building:building,report:latestBuildingReport)
		log.debug "sendMails value is======"+sendMails
		for(mailItems in sendMails){
			def actionUsers = ActionUser.findAllWhere(sendmail:mailItems)
			log.debug "action users are==="+actionUsers
			def notifyUsers = NotifyUser.findAllWhere(sendmail:mailItems)
			log.debug "notify users are==="+notifyUsers
			sendMailToNotifyUsers(notifyUsers,customer,latestBuildingReport,path,building)
			sendMailToActionUsers(actionUsers,building,customer)
			mailItems.isSent = true
		}
	}
	def sendMailToNotifyUsers(notifyUsers,customer,latestBuildingReport,path,building){
		for(notifyuser in notifyUsers){
			def groupName = EmailGroup.findWhere(groupName:notifyuser.notifyGroupName)
			log.debug "group name found for action users are===="+groupName
			for(def member in groupName.members){
				asynchronousMailService.sendMail {
					to member.email
					from customer.website.replace("www.","noreply@")
					subject 'ULC keuringsrapport  Noodverlichting Projectnaam '+' '+building.project.projectName
					html "Geachte klant"+'<br/><br/>'+"Hierbij ontvangt u het keuringsrapport  Noodverlichting van inspectiedatum :"+new Date().format("dd/MM/yyyy")+'<br/><br/>'+'Met vriendelijke groet ,'+'<br/><br/>'+customer.name+'<br/>'+customer.address+'<br/>'+customer.city+'<br/>'+customer.contact
					attachBytes customer.name+latestBuildingReport.reportName.replace("/","-")+latestBuildingReport.id+".pdf","application/pdf",new File(path).readBytes()
				}
			}
			log.debug "mail is sent for notifyusers"
	}
	}
	def sendMailToActionUsers(actionUsers,building,customer){
		for(actionuser in actionUsers){
			String floorData
			String totalData= ' .'
			def equipments
			//StringBuilder sb = new StringBuilder()
			def groupName = EmailGroup.findWhere(groupName:actionuser.actionGroupName)
			log.debug "group name found notify users are===="+groupName
			def floors = Floor.findAllByBuildingAndIsDeleted(building,false)
			log.debug "floors are===="+floors+"and their size is===="+floors.size()
			for(def member in groupName.members){
					//def getBuilding = Building.findById(buildingId)
					for(floor in floors){
						StringBuilder sb = new StringBuilder()
						floorData = "Project: "+building.project.projectName+", "+"Building: "+building.buildingName+','+"Floor: "+floor.floorNumber+'<br/><br/>'
						log.debug "floor data is==="+floorData
						equipments = Equipment.findAllWhere(floor:floor,isDeleted:false,status:'Afgekeurd')
						log.debug "equipments found are==="+equipments+"and their size is===="+equipments.size()
						if(equipments.size()!=0){
							for(int i=0;i<equipments.size();i++){
								sb.append("<br/><br/>"+equipments[i].name+"<br/>"+equipments[i].spec)
							}
						log.debug "equipment data received is======="+sb.toString()
						log.debug "floor data received ==="+floorData
						totalData = floorData+sb.toString()
				}
						else{
							totalData = floorData
						}
			}
				log.debug "total data to send ====="+totalData
				//String path = grailsApplication.config.report.pdf.server.path+dbUser.customer.name+"-Report-"+buildingReport.reportName.replace("/","-")+buildingReport.id+".pdf"
				if(equipments.size()!=0){
				asynchronousMailService.sendMail {
					multipart true
					to member.email
					from customer.website.replace("www.","noreply@")
					subject 'Afgekeurde armaturen '
					html totalData
				}
				}
				log.debug "mail is sent for actionusers"
			}
		}
	}
	def sendEquipmentMailWithSchedular(){
		String equipmentCheckpointData = ''
		def equipmentCheckpoint
		Date today = new Date()
		Date nextday = DateUtils.addDays(today,2);
		log.debug "nextday is===="+nextday
		def equipmentList = Equipment.findAllWhere(isDeleted:false,isMailSent:false)
		log.debug "equipment are===="+equipmentList
		
		for(findEquipment in equipmentList){
			log.debug "customer is==="+findEquipment.floor.building.project.customer
			def customer = findEquipment.floor.building.project.customer
			def building = findEquipment.floor.building
			def latestReport = findLatestReport(building)
			log.debug "latest Report is==="+latestReport
			def emailGroup = EmailGroup.findAllWhere(customer:customer,groupType:'NotifyUser')
			log.debug "email group are===="+emailGroup
		    if(findEquipment.deadLine!=null){
			if(findEquipment.deadLine <= nextday){
				equipmentCheckpoint = EquipmentCheckpoint.findAllWhere(equipment:findEquipment,reportId:latestReport.id.toString(),value:'3')
			}
			for(checkpoints in equipmentCheckpoint){
				equipmentCheckpointData = equipmentCheckpointData+checkpoints.checkpointDescription+"   :"+checkpoints.priorityDate+"         :"+checkpoints.comment+"<br/>"
			}
			log.debug "total checkpoints to send are===="+equipmentCheckpointData
			if(findEquipment.deadLine <= nextday){
				if(findEquipment.status=="Afgekeurd"){
				for(notifyusers in emailGroup){
					for(def member in notifyusers.members){
						asynchronousMailService.sendMail {
							to member.email
							from customer.website.replace("www.","noreply@")
							subject 'Herinnering afgekeurd armatuur'+findEquipment.floor.building.project.projectName
							html "<body>"+"Let op de overall deadline voor reparatie van onderstaande armatuur is bijna bereikt<br/><br/>"+"Project:"+findEquipment.floor.building.project.projectName+"<br/>Gebouw:"+findEquipment.floor.building.buildingName+"<br/>Verdieping:"+findEquipment.floor.floorNumber+"<br/>Armatuur nummer:"+findEquipment.name+"<br/><br/>"+equipmentCheckpointData+"</body>"
						}
					}
				}
				findEquipment.isMailSent = true
			}
			}
		}
	}
	}
}
