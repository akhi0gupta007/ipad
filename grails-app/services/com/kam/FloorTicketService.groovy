package com.kam

import groovy.xml.MarkupBuilder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.List;

import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource

class FloorTicketService {
	def springSecurityService
	def saltSource
	def grailsApplication
	def applicationContext

	def getFloorTickets(def object){
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)

		log.debug "springsecurityservice is "+springSecurityService
		log.debug "saltsource is=" +saltSource
		log.debug "xml in floor Ticket Service is"+object
		def requestType = object.requesttype
		log.debug "request type in floor ticket Service is=="+requestType
		def test = object.loginvalidation.password
		log.debug "password in floor ticket service service is==" +test
		def paramLoginName = object.loginvalidation.loginname
		log.debug "username in floor ticket service is==" + paramLoginName
		def paramLoginPassword = object.loginvalidation.password
		log.debug "password in Kam service is==" +paramLoginPassword
		String salt = saltSource instanceof NullSaltSource ? object.loginvalidation.loginname:null
		log.debug "salt in floor ticket service is"+ salt
		String encodedPassword = springSecurityService.encodePassword(object.loginvalidation.password.toString())
		log.debug encodedPassword
		// def projectName = object.project.id
		//def projectBuilding = object.project.building
		//log.debug "Project name received is=="+projectName+" and project building is="+projectBuilding
		
		def xmlFloor = object.building.floor.id
		log.debug "floor received is =="+xmlFloor
		def dbUser = User.findByUsernameAndPassword(salt,encodedPassword)

		log.debug dbUser

		if(dbUser){
			if(dbUser.enabled==true) {
				def customer = Customer.findById(User.get(dbUser.id).customer.id)
				log.debug "dbCustomer in ticket service getTickets is ==" + customer
				def emailGroup = EmailGroup.findAllWhere(customer:customer)
				log.debug "email groups are =="+emailGroup
				//def notifyUser = emailGroup.members
				//log.debug "notify users are =="+notifyUser
				//log.debug "member emails size is="+notifyUser.size()
				def floor1 = Floor.get(Long.parseLong(xmlFloor.toString()))
				log.debug "floor queried is=="+floor1
				/*
				def floorAdded = Floor.findAllByBuildingInListAndIsDeleted(building1,false)
				log.debug "floors added are="+floorAdded

				def floorRemoved = Floor.findAllByBuildingInListAndIsDeleted(building1,true)
				log.debug "floors removed are="+floorRemoved
				 */


				//def floors1 = Floor.findAllByBuilding(building1)
				log.debug "it is above report"
				def report = getLatestReport(dbUser.username.toString(),floor1.building)
				if(report && report instanceof Report)
					log.debug("Latest Report Recieved is ..........................################"+report.id)
				def badEquip

				//log.debug("Floors for these building................................."+floors1)
				xml.'?xml version="1.0" encoding="UTF-8"?'{
					digikam{
					requesttype(requestType)

					loginvalidation{
						loginname(paramLoginName)
						password(paramLoginPassword)
						response("OK")
						message("Login Validated")
					}
					building{
						boolean checkBuilding

						if(report && report instanceof Report)
							checkBuilding = checkBuildings(report,floor1.building)
						else
							checkBuilding = false


						if(report && report instanceof Report &&  checkBuilding){

//
							
							log.debug("CheckBuilding//////////////////////////////////////////////////////////"+checkBuilding)
							id(floor1.building.id)
							def badFloor = false
										floor{
											id(floor1.id)
											def equips = Equipment.findAllByFloorAndIsDeleted(floor1,false)
											log.debug("Equipments are ......................"+equips)
											if(equips.size()!=0){
												equipments{
													def bad = false
													for(y in equips){
														def checks
														//def checks = y.checkpoints
														if(report && report instanceof Report)
															checks = getCheckPoints(report,y)
														else
															checks = y.checkpoints

														log.debug("Latest Checkpoints are???????????????????????l???"+checks)
														if(checks){
															equipment{
																id(y.id)
																if(checks.size()!=0){
																	log.debug("Checkpoints are ..."+checks)
																	bad = checkIfBad(checks)
																	log.debug("Bad is........................"+bad)
																	if(bad){
																		status("1")
																		 badFloor = true
																		checkpoints{
																			for(z in checks){
																			  //  if(z.value.toString().equals("3")){
																					checkpoint{
																						log.debug("Afgekeured.......................................:)")
																						value(z.value)
																						if(z.priorityDate!=null){
																						Date df = convertToDateCheckpoint(z.priorityDate)
																						priorityDate(df.format("yyyy-MM-dd"))
																						}
																						priority(z.priority)
																						comment(z.comment)
																						description(z.checkpointDescription)    //Description
																					}
																			   // }
																			}
																		}

																	}else{
																		status("3")
																	}
																}
																}
														}
													}
												}
											}
											//
											
											if(badFloor) {
												status("1")
												badFloor = false
											}
											else {
												status("3")
											}
										}
						}

					}
				}

			}
			}
			log.debug "tickets sent are====="+writer.toString()
			return writer.toString()
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
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
		Date date = (Date) dateFormat.parse(stringDate)
		return date
	}
	def convertToDateCheckpoint(def stringDate) {
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy")
		Date date = (Date) dateFormat.parse(stringDate)
		return date
	}

	public boolean checkIfBad(List<EquipmentCheckpoint> checks){

		for(x in checks){
			if(x.value.toString().equals("3")){
				return true
			}
		}
		return false
	}

	public Object getLatestReport(String user,Building building){

		//  Report report = Report.findAllByReviewedByAndBuilding(user,building).list(sort:"lastUpdated")
		def results = Report.createCriteria().list {
			and{
				eq("reviewedBy",user)
			}
			and{
				eq("building",building)
			}
			order("lastUpdated","desc")
		}
		//  log.debug("Report for this user and buildings are.....@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+results)
		def latest
		if(results.size()!=0){
			latest = results.get(0)
		}
		return latest
	}

	def getCheckPoints(def report,def equipment){
		//    log.debug("Inside getCheckPoint......................................................................................")
		log.debug("Equipment:..."+equipment)
		log.debug("ReportId...................."+report.id.toString())
		def checks = EquipmentCheckpoint.createCriteria().list {
			and{
				eq("equipment",equipment)
			}
			and{
				eq("reportId",report.id.toString())
			}
		}
		return checks
	}

	def checkBuildings(def report,def building){
		log.debug("Building check for building......>>>>>>"+building)
		if(building instanceof Building){
			for(floor in building.floors){
				log.debug("Floor:" +floor)
				for(equip in floor.equipments){
					def checks = EquipmentCheckpoint.createCriteria().list {
						and{
							eq("equipment",equip)
						}
						and{
							eq("reportId",report.id.toString())
						}
					}
					if(checks.size()!=0){
						log.debug("Badddd Building.............")
						for(x in checks){
							log.debug("Checkpoiint in checkBuildinf//////////////////////////////////////////"+x.value)
							if(x.value.toString().equals("3")){
								return true
							}
						}

					}
				}
			}
		}
		return false
	}

	
}