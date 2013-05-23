package com.kam

import java.text.DateFormat
import java.text.SimpleDateFormat

class DocumentsReminderService {

	def grailsApplication
	def asynchronousMailService
	
    def serviceMethod() {

    }
	
	def documentReminders(){
		Calendar currentDate1=Calendar.getInstance()
		
		DateFormat formatter=new SimpleDateFormat('yyyy-MM-dd hh:mm:ss')
		currentDate1.add(Calendar.DAY_OF_MONTH, +2)
		def dayOfReminder1 = formatter.format(currentDate1.getTime())
		def date1 = (Date)formatter.parse(dayOfReminder1)
		
		Calendar currentDate2=Calendar.getInstance()
		currentDate2.add(Calendar.DAY_OF_MONTH, +3)
		def dayOfReminder2 = formatter.format(currentDate2.getTime())
		def date2 = (Date)formatter.parse(dayOfReminder2)
		def documents = Document.findAllByDeadLineGreaterThanAndDeadLineLessThan(date1,date2)
			for(document in documents){
				def actionUsers=ActionUser.findAllWhere(type:'Document',parentId:document.id.toString())
				for(def actionUser in actionUsers){
					if(actionUser!=null){
						def groupName = EmailGroup.findWhere(groupName:actionUser.actionGroupName)   
						for(def member in groupName.members){
							asynchronousMailService.sendMail {         
								to member.email             
								from grailsApplication.config.grails.plugins.springsecurity.ui.forgotPassword.emailFrom
								subject 'Reminder for completion of documents'
								html 'Hello, <br><br>This is the reminder mail for the deadLine of Completing the document.<br><br>Thanks,<br>Team AppUBuild'
							}
						}
					}
				}
				def notifyUsers=NotifyUser.findAllWhere(type:'Document',parentId:document.id.toString())
				for(def notifyUser in notifyUsers){
					if(notifyUser!=null){
						def groupName = EmailGroup.findWhere(groupName:notifyUser.notifyGroupName)
						for(def member in groupName.members){
							asynchronousMailService.sendMail {
								to member.email
								from grailsApplication.config.grails.plugins.springsecurity.ui.forgotPassword.emailFrom
								subject 'Reminder for completion of documents'
								html 'Hello, <br><br>This is the reminder mail for the deadLine of Completing the document.<br><br>Thanks,<br>Team AppUBuild'
							}
						}
					}
				}
			}
	}
}
