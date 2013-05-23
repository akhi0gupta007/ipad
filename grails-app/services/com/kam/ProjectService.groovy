package com.kam
import grails.converters.XML
import groovy.xml.MarkupBuilder
import java.text.DateFormat
import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.springframework.security.authentication.dao.SaltSource;

class ProjectService {

	def springSecurityService
	def saltSource
	def projectDataService
	def getProjects(def object){
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		def addprojectstatus
		def removeprojectstatus
		def addbuildingstatus
		def removebuildingstatus
		def data
		def updatebuildingstatus = false
		boolean projectUpdated = false
		log.debug "springsecurityservice is "+springSecurityService
		log.debug "saltsource is=" +saltSource
		log.debug "xml in Kam Generic Service is"+object
		def requestType = object.requesttype
		log.debug "request type is=="+requestType
		def paramLoginPassword = object.loginvalidation.password
		log.debug "password in Kam service is==" +paramLoginPassword
		def paramLoginName = object.loginvalidation.loginname
		log.debug "username in Kam service is==" + paramLoginName
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
				
				
				def lastSyncTime //= object.lastsynctime//"Sun Jan 1 00:00:01 IST 2012"
				if (object.lastsynctime.toString() == "") {
					lastSyncTime = "Sun Jan 1 00:00:01 IST 2012"
					log.debug "Updated lastSyncTime :" + lastSyncTime
				}
				else{
					lastSyncTime = object.lastsynctime.toString()
				}
				log.debug "lastsynctime Date==" + lastSyncTime
				def formattedLastsynctime = convertToDate(lastSyncTime)
				log.debug "new formatted time is==" + formattedLastsynctime
				def projectadded = Project.findAllWhere(customer:customer,isDeleted:false).sort(){[it.id]}
				log.debug "project added are =="+projectadded
				def projectUpdatedAfterLastSyncTime = Project.findAllByCustomerAndLastUpdatedGreaterThanAndIsDeleted(customer,formattedLastsynctime,false)
				log.debug "project updated after last sync time=="+projectUpdatedAfterLastSyncTime
				if(projectUpdatedAfterLastSyncTime.size()!=0){
					projectUpdated = true
				}
				def projectremoved = Project.findAllWhere(customer:customer,isDeleted:true)
				log.debug "project deleted are =="+projectremoved

				def buildingList = Building.findAllByProjectInList(projectList)
				log.debug "buildings related to this project are"+buildingList
				def buildingadded = Building.findAllByProjectInListAndIsDeleted(projectList,false).sort(){[it.id]}
				log.debug "building added for project is=="+buildingadded
				def buildingremoved = Building.findAllByProjectInListAndIsDeleted(projectList,true)
				log.debug "building removed for project is=="+buildingremoved
				def buildingupdated = Building.findAllByProjectInListAndDateCreatedGreaterThan(projectadded,formattedLastsynctime).sort(){[it.buildingName]}
				log.debug "new buildings added are"+buildingupdated
				
				if(buildingupdated.size()!=0){
					updatebuildingstatus = true
				}
				addprojectstatus = projectDataService.projectAddStatus(projectadded,formattedLastsynctime)
				log.debug "addproject status is=="+addprojectstatus
				
				removeprojectstatus = projectDataService.projectRemoveStatus(projectremoved, formattedLastsynctime)
				log.debug "remove project status is=="+removeprojectstatus
				
				addbuildingstatus = projectDataService.buildingAddStatus(buildingadded,formattedLastsynctime)
				log.debug "add building status is--"+addbuildingstatus
				
				removebuildingstatus = projectDataService.buildingRemoveStatus(buildingremoved, formattedLastsynctime)
				log.debug "removebuildingstatus status is--"+removebuildingstatus
				xml.'?xml version="1.0" encoding="UTF-8"?'{
					digikam{
					requesttype(requestType)
					lastsynctime(new Date().toString())
					loginvalidation{
						loginname(paramLoginName)
						password(paramLoginPassword)
						response("OK")
						message("Login Validated")
					}
					projects{
						if(addprojectstatus==true){			 
							for(project_item in projectadded.sort(){[it.id]}){
								if(project_item.dateCreated > formattedLastsynctime&&project_item.isDeleted!=true){
									add{
										project{
											id(project_item.id)
											name(project_item.projectName)
											title(project_item.projectTitle)
											address(project_item.address)
											city(project_item.city)
											log.debug "building size for project"+project_item.id+"is=="+project_item.buildings.size()
											if(project_item.buildings.size()!=0){
												buildings{
													if(addbuildingstatus==true){
														add{
															for(build in project_item.buildings.sort(){[it.id]}){

																if(build.dateCreated > formattedLastsynctime&&build.isDeleted!=true){
																	building{
																		id(build.id)
																		name(build.buildingName)
																		type(build.flow)
																	}
																}
																
															}
														}
													}
													if(removebuildingstatus==true){
														remove{
															for(build in project_item.buildings){
																if(build.dateCreated > formattedLastsynctime&&build.isDeleted==true){

																	building{
																		id(build.id)
																		name(build.buildingName)
																		flow(build.flow)
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
						if(removeprojectstatus==true){
							remove{
								for(project_item in projectremoved){
									if(project_item.lastUpdated > formattedLastsynctime&&project_item.isDeleted==true){

										project{ id(project_item.id) }
									}
								}
							}
						}
				
					 if(projectUpdated == true)
					 {
						 
					 log.debug "i am under project updated tag>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
					 update{
					for(projectItem in projectUpdatedAfterLastSyncTime){
						project{
							id(projectItem.id)
							name(projectItem.projectName)
							title(projectItem.projectTitle)
							address(projectItem.address)
							city(projectItem.city)
							buildings{
								if(addbuildingstatus==true){
									add{
										for(build in projectItem.buildings){

											if(build.lastUpdated > formattedLastsynctime&&build.isDeleted!=true){
												building{
													id(build.id)
													name(build.buildingName)
													type(build.flow)
												}
											}
											
										}
									}
								}
								if(removebuildingstatus==true){
									remove{
										for(build in projectItem.buildings){
											if(build.lastUpdated > formattedLastsynctime&&build.isDeleted==true){

												building{
													id(build.id)
													name(build.buildingName)
													flow(build.flow)
												}
											}
										}
									}
								}
							}
						}
					 }
					 }
					 }
					}
				}
				}
				log.debug "output for project is=="+writer.toString()
				return writer.toString()
			}
			if(user.enabled==false){
				xml.'?xml version="1.0" encoding="UTF-8"?'{
					digikam{
					requesttype(requestType)
					loginvalidation{
						loginname(paramLoginName)
						password(paramLoginPassword)
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
					password(paramLoginPassword)
					response("ERROR")
					message("Wrong Username/Password")
				}
			}
			}
			return writer.toString()
		}
	}
	def convertToDate(def stringDate) {
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
		Date date = (Date) dateFormat.parse(stringDate)
		return date
	}
}
