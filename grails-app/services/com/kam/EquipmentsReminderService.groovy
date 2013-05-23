package com.kam

import java.text.DateFormat
import java.text.SimpleDateFormat

import org.apache.commons.lang.time.DateUtils;

class EquipmentsReminderService {

	def grailsApplication
	def asynchronousMailService
	def serviceMethod() {
	}

	/*def equipmentReminders(){
		for(int i=0;i<4;i++){
			Calendar currentDate1=Calendar.getInstance()
			if(i==0)
				currentDate1.add(Calendar.DAY_OF_MONTH, +1)
			else if(i==1)
				currentDate1.add(Calendar.DAY_OF_MONTH, +2)
			else if(i==2)
				currentDate1.add(Calendar.DAY_OF_MONTH, +7)
			else if(i==3)
				currentDate1.add(Calendar.DAY_OF_MONTH, +30)
			DateFormat formatter=new SimpleDateFormat('EEE MMM dd HH:mm:ss z yyyy')
			def dayOfReminder = formatter.format(currentDate1.getTime())
			def buildingList = Building.findAllWhere(isDeleted:false)
			log.debug "building list"+buildingList
			def equipmentCheckpoints = EquipmentCheckpoint.findAllByPriorityDateAndPriority(dayOfReminder,i+1)
			for(equipments in equipmentCheckpoints){
				def actionUsers=ActionUser.findAllWhere(type:'Building',parentId:equipments.id.toString())
				for(def actionUser in actionUsers){
					if(actionUser!=null){
						def groupName = EmailGroup.findWhere(groupName:actionUser.actionGroupName)
						for(def member in groupName.members){
							asynchronousMailService.sendMail {
								to member.email
								from grailsApplication.config.grails.plugins.springsecurity.ui.forgotPassword.emailFrom
								subject 'Projectnummer '+equipments.equipment.floor.building.project.projectNumber+' '+equipments.equipment.floor.building.project.projectName+' afgekeurde armaturen'
								html equipments.equipment.floor.building.buildingName+', '+equipments.equipment.floor.floorNumber+'<br/><br/>'+equipments.equipment.name+'<br/>'+equipments.equipment.spec
							}
						}
					}
				}
				def notifyUsers=NotifyUser.findAllWhere(type:'EquipmentCheckpoint',parentId:equipments.id.toString())
				for(def notifyUser in notifyUsers){
					if(notifyUser!=null){
						def groupName = EmailGroup.findWhere(groupName:notifyUser.notifyGroupName)
						for(def member in groupName.members){
							asynchronousMailService.sendMail {
								to member.email
								from grailsApplication.config.grails.plugins.springsecurity.ui.forgotPassword.emailFrom
								subject 'Projectnummer '+equipments.equipment.floor.building.project.projectNumber+' '+equipments.equipment.floor.building.project.projectName+' afgekeurde armaturen'
								html equipments.equipment.floor.building.buildingName+', '+equipments.equipment.floor.floorNumber+'<br/><br/>'+equipments.equipment.name+'<br/>'+equipments.equipment.spec
							}
						}
					}
				}
			}
		}
	}*/
	def equipmentReminders() {
		def equipmentCheckpoint
		
		def equipmentList = Equipment.findAllWhere(isDeleted:false)
		log.debug "equipment list received is====="+equipmentList
		Date today = new Date()
		Date nextday = DateUtils.addDays(today,2);
		log.debug "nextday received is===="+nextday
		for(mailingEquipments in equipmentList){
			def equipmentCheckpointData = ''
			def latestReport = findLatestReport(mailingEquipments.floor.building)
			log.debug "latestReportis======="+latestReport 
			def equipmentCustomer = mailingEquipments.floor.building.project.customer
			log.debug "customer found is====="+equipmentCustomer
			def emailGroupNotifyUser = EmailGroup.findAllWhere(customer:equipmentCustomer,groupType:'NotifyUser')
			log.debug "email group found is==="+emailGroupNotifyUser
			if(mailingEquipments.deadLine!=null){
				if(mailingEquipments.deadLine <= nextday && mailingEquipments.deadLine >today){
					equipmentCheckpoint = EquipmentCheckpoint.findAllWhere(equipment:mailingEquipments,value:'3',reportId:latestReport.id.toString())
					for(checkpoints in equipmentCheckpoint){
						equipmentCheckpointData = equipmentCheckpointData+checkpoints.checkpointDescription+"   :"+checkpoints.priorityDate+"         :"+checkpoints.comment+"<br/>"
					}
					log.debug "total checkpoints to send are===="+equipmentCheckpointData
				}
				if(mailingEquipments.deadLine <= nextday && mailingEquipments.deadLine >today){
					for(notifyusers in emailGroupNotifyUser){
						def groupName = EmailGroup.findWhere(groupName:notifyusers.groupName)
						log.debug "group name found notify users are===="+groupName
						for(def member in groupName.members){
							asynchronousMailService.sendMail {
								to member.email
								from equipmentCustomer.website.replace("www.","noreply@")
								subject 'Herinnering afgekeurd armatuur   '+mailingEquipments.floor.building.project.projectName
								html "<body>"+"Let op de overall deadline voor reparatie van onderstaande armatuur is bijna bereikt<br/><br/>"+"Project:"+mailingEquipments.floor.building.project.projectName+"<br/>Gebouw:"+mailingEquipments.floor.building.buildingName+"<br/>Verdieping:"+mailingEquipments.floor.floorNumber+"<br/>Armatuur nummer:"+mailingEquipments.name+"<br/><br/>"+equipmentCheckpointData+"</body>"
							}
						}
					}
				}
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
}
