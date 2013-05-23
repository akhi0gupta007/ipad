package com.kam
import grails.converters.XML
import groovy.xml.MarkupBuilder
import java.text.DateFormat
import java.text.SimpleDateFormat

import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.springframework.security.authentication.dao.SaltSource;
class BuildingService {
	def springSecurityService
	def saltSource
	def grailsApplication
	def applicationContext
	def buildingStatusAndDataService
	def getBuilding(def object, def path,def serverName){
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		def addequipmentstatus
		def removeequipmentstatus
		def updateEquipmentStatus
		def addfloorstatus
		def removefloorstatus
		def updateFloorStatus

		def requestType = object.requesttype
		log.debug "request type is=="+requestType
		def paramLoginPassword = object.loginvalidation.password
		log.debug "password in Kam service is==" +paramLoginPassword
		def paramLoginName = object.loginvalidation.loginname
		log.debug "username in Kam service is==" + paramLoginName
		String salt = saltSource instanceof NullSaltSource ? object.loginvalidation.loginname:null
		log.debug "salt in Kam service is"+ salt
		def equipmentadded = Equipment.findAllWhere(isDeleted:false)
		log.debug "equipmentadded is=="+equipmentadded
		def equipmentremoved = Equipment.findAllWhere(isDeleted:true)
		log.debug "equipment deleted is=="+equipmentremoved
		String encodedPassword = springSecurityService.encodePassword(object.loginvalidation.password.toString())
		log.debug encodedPassword
		def xmlBuilding = object.building.id
		def user = User.findByUsernameAndPassword(salt,encodedPassword)
		log.debug "user is =="+user
		if(user){
			if(user.enabled==true) {
				def customer = Customer.findById(User.get(user.id).customer.id)
				def checkpointEquipment = CheckpointsEquipment.findAllByCustomerId(customer.id.toString())
				def emailGroupActionUser = EmailGroup.findAllWhere(customer:customer,groupType:"ActionUser")
				log.debug "email groups are =="+emailGroupActionUser
				def emailGroupNotifyUser = EmailGroup.findAllWhere(customer:customer,groupType:"NotifyUser")
				log.debug "email groups are =="+emailGroupNotifyUser
				def building1 = Building.findWhere(id:xmlBuilding.toLong())
				log.debug "building queried is=="+building1
				def flooradded = Floor.findAllByBuildingAndIsDeleted(building1,false)
				log.debug "floors added are="+flooradded
				def floorremoved = Floor.findAllByBuildingAndIsDeleted(building1,true)
				def lastSyncTime //= object.lastsynctime//"Sun Jan 1 00:00:01 IST 2012"
				if (object.lastsynctime.toString() == "") {
					lastSyncTime = "Sun Jan 1 00:00:01 IST 2012"
					log.debug "Updated lastSyncTime :" + lastSyncTime
				}
				else{
					/*lastSyncTime = object.lastsynctime.toString().replace('=',':')*/
					lastSyncTime = object.lastsynctime.toString()
				}
				log.debug "lastsynctime Date==" + lastSyncTime
				def formattedLastsynctime = convertToDate(lastSyncTime)
				addequipmentstatus = buildingStatusAndDataService.checkAddEquipmentStatus(equipmentadded,formattedLastsynctime)
				log.debug "add equipment status=="+addequipmentstatus
				removeequipmentstatus = buildingStatusAndDataService.checkRemoveEquipmentStatus(equipmentremoved,formattedLastsynctime)
				log.debug "remove equipment status=="+removeequipmentstatus
				updateEquipmentStatus = buildingStatusAndDataService.checkUpdateEquipmentStatus(equipmentadded,formattedLastsynctime)
				log.debug "update equipment status==="+updateEquipmentStatus
				addfloorstatus = buildingStatusAndDataService.checkAddFloorStatus(flooradded,formattedLastsynctime)
				log.debug "add floor status=="+addfloorstatus
				removefloorstatus = buildingStatusAndDataService.checkRemoveFloorStatus(floorremoved,formattedLastsynctime)
				def updateFloors = Floor.createCriteria().list{
					and{
						eq('building',building1)
						eq('isDeleted',false)
						gt('lastUpdated',formattedLastsynctime)
					}
				}
				log.debug "update floors are=="+updateFloors
				if(updateFloors.size()!=0){
					updateFloorStatus = true
				}
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
					building{
						for(checkbuilding in building1){
							id(checkbuilding.id)
							name(checkbuilding.buildingName)
							if(emailGroupNotifyUser.size()!=0){
								notifyusers{
									for(useritem in emailGroupNotifyUser){
										notifyuser(useritem.groupName)
									}
								}
							}
							if(emailGroupActionUser.size()!=0){
								actionusers{
									for(useritem in emailGroupActionUser){
										actionuser(useritem.groupName)
									}
								}
							}
							if(checkbuilding.floors.size()!=0){
								log.debug "in checkbuilding floors tag================="
								floorsTag(xml,checkbuilding,addfloorstatus,formattedLastsynctime,addequipmentstatus,removeequipmentstatus,serverName,path,removefloorstatus,checkpointEquipment,updateFloorStatus,updateEquipmentStatus)
							}
						}
					}
				}
				}
				log.debug "xml for get building is=="+writer.toString()
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
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
		Date date = (Date) dateFormat.parse(stringDate)
		return date
	}
	def convertToDateEquipment(def stringDate) {
		
		log.debug " n convertToDateEquipment-----date received==="+stringDate+"and its class===>>>>>"+stringDate.getClass()
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy")
		Date date = (Date) dateFormat.parse(stringDate)
		log.debug "date is now in convertToDateEquipment==="+date
		return date
	}
	def getBuildingTickets(def object){
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		def requestType = object.requesttype
		log.debug "request type in Building ticket Service is=="+requestType
		def test = object.loginvalidation.password
		log.debug "password in Building ticket service service is==" +test
		def paramLoginName = object.loginvalidation.loginname
		log.debug "username in Building ticket service is==" + paramLoginName
		String salt = saltSource instanceof NullSaltSource ? object.loginvalidation.loginname:null
		log.debug "salt in Building ticket service is"+ salt
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
				log.debug "dbCustomer in  Building ticket service getTickets is ==" + customer

				def projectInfo = Project.findByProjectName(projectName.toString())
				log.debug "Project in Building ticket service getTickets is=="+projectInfo
				def findBuilding = Building.findByBuildingNumber(projectBuilding.toString())
				log.debug "Project's building id is="+findBuilding
				def emailGroupNotifyUser = EmailGroup.findAllWhere(customer:customer,groupType:"NotifyUser")
				log.debug "email groups are =="+emailGroupNotifyUser
				def dbSavedEquipment = EquipmentCheckpoint.list()
				log.debug "dbSavedDocument size is=="+dbSavedEquipment
				if(dbSavedEquipment.size()==0){
					return noBuildingTicket()
				}

				else{
					def documentTemplateList = dbSavedDocument.document.documentTemplate
					log.debug "documentTemplate found is"+documentTemplateList
					def documentItemList = DocumentItem.findAllByDocumentTemplateInList(documentTemplateList)
					log.debug "documentItem found for corresponding template is= "+documentItemList
					def question = Question.findAllByDocumentItemInList(documentItemList)
					log.debug "question related to the documentItem is="+question
					xml.digikam{
						requesttype(requestType)
						loginvalidation{
							loginname(paramLoginName)
							password(test)
							response("OK")
							message("Login Validated")
						}
						building{ id(projectBuilding) }
						equipments{
							for(equip in dbSavedEquipment){
								equipment{
									id(equip.id)
									name(equip.equipment.name)
									version(equip.version)
									building{ id(projectName) }
									checkpoints{
										for(equipmentCheckpoint in dbSavedEquipment){
											checkpoint{
												type("checkpoint")
												description(equipmentCheckpoint.checkpointDescription)
											}
										}
									}
									attachments{
										for(images in dbSavedEquipment.equipmentImages)
											attachment{ id(images.imageName)  }
									}
									notifyusers{
										for(useritem in emailGroupNotifyUser){
											notifyuser{
												user(useritem.groupName)
												email(useritem.groupName)
											}
										}
									}
								}
							}
						}
					}
					log.debug "get tickets response is=="+writer.toString()
					return writer.toString()
				}
			}
			if(dbUser.enabled==false){
				xml.digikam{
					requesttype(requestType)
					loginvalidation{
						loginname(paramLoginName)
						password(test)
						response("DENIED")
						message("User not allowed to use iPad App")
					}
				}
				return writer.toString()
			}
		}
		else{
			xml.digikam{
				requesttype(requestType)
				loginvalidation{
					loginname(paramLoginName)
					password(test)
					response("ERROR")
					message("Wrong Username/Password")
				}
			}
			return writer.toString()
		}
	}
	def noBuildingTicket(){

	}
	def floorsTag(xml,checkbuilding,addfloorstatus,formattedLastsynctime,addequipmentstatus,removeequipmentstatus,serverName,path,removefloorstatus,checkpointEquipment,updateFloorStatus,updateEquipmentStatus)throws NullPointerException{
		xml.floors{
			try{
			if(addfloorstatus==true){
				add{
					def floorsFound = Floor.findAllWhere(building:checkbuilding).sort(){[it.id]}
					log.debug "floors are====="+floorsFound
					for(floorinbuilding in floorsFound){
						if(floorinbuilding.dateCreated > formattedLastsynctime&&floorinbuilding.isDeleted!=true){
							floor{
								id(floorinbuilding.id)
								name(floorinbuilding.floorNumber)
								type(checkbuilding.flow)
								url('http://'+serverName+':8080'+path+'/FloorMap/'+floorinbuilding.floorMap)
								if(checkbuilding.flow=="equipment"){
									equipments{
										for(floorequipment in floorinbuilding.equipments.sort(){[it.id]} ){
											if(addequipmentstatus==true){
												if(floorequipment.dateCreated > formattedLastsynctime&&floorequipment.isDeleted!=true){
													addEquipment(xml,floorequipment,checkpointEquipment)
													log.debug "cleared add equipment"
												}
											}
											if(removeequipmentstatus==true){
												if(floorequipment.lastUpdated > formattedLastsynctime&&floorequipment.isDeleted==true){
													removeEquipment(xml,floorequipment,checkpointEquipment)
													log.debug "cleared remove equipment"
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
		}catch(NullPointerException npe){
		npe.printStackTrace()
		}
			if(removefloorstatus==true){
				removeFloor(xml,formattedLastsynctime,checkbuilding)
			}
			if(updateFloorStatus==true){
				updateFloor(xml,formattedLastsynctime,checkbuilding,checkpointEquipment,serverName,path,addequipmentstatus,removeequipmentstatus,updateEquipmentStatus)
			}
		}
	}
	def addEquipment(xml,floorequipment,checkpointEquipment){
		xml.add{
			equipment{
				arm(floorequipment.id)
				name(floorequipment.name)
				if(floorequipment.equipmentType=="Inbuilt"||floorequipment.equipmentType=="Inbouw")
				{
					type("Inbouw")
				}
				if(floorequipment.equipmentType=="External"||floorequipment.equipmentType=="Opbouw")
				{
					type("Opbouw")
				}
				if(floorequipment.equipmentType2=="Indication"||floorequipment.equipmentType2=="Aanduiding"){
					vluchtweg("Aanduiding")
				}
				if(floorequipment.equipmentType2=="Lighting"||floorequipment.equipmentType2=="Verlichting"){
					vluchtweg("Verlichting")
				}
				//type(floorequipment.equipmentType)
				//vluchtweg(floorequipment.equipmentType2)
				typeOptions(EquipmentConstants.EQUIPMENT_TYPE_INTERNAL+","+EquipmentConstants.EQUIPMENT_TYPE_EXTERNAL)
				vluchtwegOptions(EquipmentConstants.EQUIPMENT_TYPE_LIGHTING+","+EquipmentConstants.EQUIPMENT_TYPE_INDICATION)
				checkpoints{
					for(checkpointItems in checkpointEquipment){
						checkpoint{
							type("checkpoint")
							description(checkpointItems.value)
						}
					}
				}
				if(floorequipment.brand!=null){
				merk(floorequipment.brand.id)
				log.debug "brand cleared"
				}
				if(floorequipment.armatuur!=null){
				typearmatuur(floorequipment.armatuur.id)//all should be id from master table
				}
				if(floorequipment.buildYearOfArmature!=null){
				log.debug "date of armature for this equipment is===="+floorequipment.buildYearOfArmature
				Date armatureBuildDate = convertToDateEquipment(floorequipment.buildYearOfArmature.toString())
				log.debug "after converting date------------<<<<<<<<<<<<"
				bouwjarm(armatureBuildDate.format("yyyy-MM-dd"))
				}
				if(floorequipment.emergencyUnitOfPrint!=null){
				noodunitofprint(floorequipment.emergencyUnitOfPrint.id)//all should be id from master table
				}
				if(floorequipment.buildYearOfEmergencyUnit!=null){
				log.debug "emergency unit cleared"
				Date noodBuildDate = convertToDateEquipment(floorequipment.buildYearOfEmergencyUnit.toString())
				bouwjnood(noodBuildDate.format("yyyy-MM-dd"))
				}
				if(floorequipment.light!=null){
				lamp(floorequipment.light.id)//all should be id from master table
				log.debug "lamp cleared"
				}
				if(floorequipment.battery!=null){
				accu(floorequipment.battery.id)//all should be id from master table
				}
				if(floorequipment.buildYearOfBattery!=null){
				Date batteryBuildDate = convertToDateEquipment(floorequipment.buildYearOfBattery)
				bouwjaccu(batteryBuildDate.format("yyyy-MM-dd"))
				log.debug "cleared battery"
				}
				if(floorequipment.groupNo!=null){
				group(floorequipment.groupNo.id)
				log.debug "cleared group number"
				}
				if(floorequipment.kast!=null){
				kast(floorequipment.kast.id)
				log.debug "cleared kast"
				}
			}
		}
	}
	def removeEquipment(xml,floorequipment,checkpointEquipment){
		xml.remove{
			equipment{
				log.debug "in remove tag of equipment ========"+floorequipment.id
				arm(floorequipment.id)
				checkpoints{
					checkpoints{
						for(checkpointItems in CheckpointsEquipment.list()){
							checkpoint{
								type("checkpoint")
								description(checkpointItems.value)
							}
						}
					}
				}
			}
		}
	}
	def updateEquipment(xml,floorequipment,checkpointEquipment){
		xml.update{
			log.debug "reached update equipment tag>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
			equipment{
				arm(floorequipment.id)
				name(floorequipment.name)
				if(floorequipment.equipmentType=="Inbuilt"||floorequipment.equipmentType=="Inbouw")
				{
					type("Inbouw")
				}
				if(floorequipment.equipmentType=="External"||floorequipment.equipmentType=="Opbouw")
				{
					type("Opbouw")
				}
				if(floorequipment.equipmentType2=="Indication"||floorequipment.equipmentType2=="Aanduiding"){
					vluchtweg("Aanduiding")
				}
				if(floorequipment.equipmentType2=="Lighting"||floorequipment.equipmentType2=="Verlichting"){
					vluchtweg("Verlichting")
				}
				//type(floorequipment.equipmentType)
				//vluchtweg(floorequipment.equipmentType2)
				typeOptions(EquipmentConstants.EQUIPMENT_TYPE_INTERNAL+","+EquipmentConstants.EQUIPMENT_TYPE_EXTERNAL)
				vluchtwegOptions(EquipmentConstants.EQUIPMENT_TYPE_LIGHTING+","+EquipmentConstants.EQUIPMENT_TYPE_INDICATION)
				checkpoints{
					for(checkpointItems in checkpointEquipment){
						checkpoint{
							type("checkpoint")
							description(checkpointItems.value)
						}
					}
				}
				log.debug "in update equipment tag=================="
				if(floorequipment.brand!=null){
				merk(floorequipment.brand.id)
				log.debug "after brand tag in update equipment"
				}
				if(floorequipment.armatuur!=null){
				typearmatuur(floorequipment.armatuur.id)//all should be id from master table
				}
				if(floorequipment.buildYearOfArmature!=null){
				Date armatureBuildDate = convertToDateEquipment(floorequipment.buildYearOfArmature)
				bouwjarm(armatureBuildDate.format("yyyy-MM-dd"))
				}
				if(floorequipment.emergencyUnitOfPrint!=null){
				noodunitofprint(floorequipment.emergencyUnitOfPrint.id)//all should be id from master table
				}
				if(floorequipment.buildYearOfEmergencyUnit!=null){
				Date noodBuildDate = convertToDateEquipment(floorequipment.buildYearOfEmergencyUnit)
				bouwjnood(noodBuildDate.format("yyyy-MM-dd"))
				}
				if(floorequipment.light!=null){
				lamp(floorequipment.light.id)//all should be id from master table
				}
				if(floorequipment.battery!=null){
				accu(floorequipment.battery.id)//all should be id from master table
				}
				if(floorequipment.buildYearOfBattery!=null){
				Date batteryBuildDate = convertToDateEquipment(floorequipment.buildYearOfBattery)
				bouwjaccu(batteryBuildDate.format("yyyy-MM-dd"))
				}
				if(floorequipment.groupNo!=null){
				group(floorequipment.groupNo.id)
				}
				if(floorequipment.kast!=null){
				kast(floorequipment.kast.id)
				}
			}
		}

	}
	def removeFloor(xml,formattedLastsynctime,checkbuilding){
		xml.remove{
			for(floorinbuilding in checkbuilding.floors){
				if(floorinbuilding.lastUpdated > formattedLastsynctime&&floorinbuilding.isDeleted==true){
					floor {
						id(floorinbuilding.id)
						name(floorinbuilding.floorNumber)
						type(checkbuilding.flow)
					}
				}
			}
		}
	}
	def updateFloor(xml,formattedLastsynctime,checkbuilding,checkpointEquipment,serverName,path,addequipmentstatus,removeequipmentstatus,updateEquipmentStatus){
		xml.update{
			log.debug "reached add tag of floors>>>>>>>>>>>>>>>>>>>>>>>>>>>>>and checbuilding is=="+checkbuilding
			def floorFound = Floor.findAllWhere(building:checkbuilding).sort(){[it.id]}
			log.debug "floors found are===="+floorFound
			for(floorinbuilding in floorFound){
				log.debug "in floorinbuiding"
				if(floorinbuilding.lastUpdated > formattedLastsynctime&&floorinbuilding.isDeleted!=true){
					floor{
						id(floorinbuilding.id)
						name(floorinbuilding.floorNumber)
						type(checkbuilding.flow)
						url('http://'+serverName+':8080'+path+'/FloorMap/'+floorinbuilding.floorMap)
						if(checkbuilding.flow=="equipment"){
							equipments{
								log.debug "reached equipments tag<<<<<<<<<<<<<<<<<<<"
								for(floorequipment in floorinbuilding.equipments.sort(){[it.id]} ){
									if(addequipmentstatus==true){
										if(floorequipment.dateCreated > formattedLastsynctime&&floorequipment.isDeleted!=true){
											log.debug "calling add equipment tag"
											addEquipment(xml,floorequipment,checkpointEquipment)
										}
									}
									if(removeequipmentstatus==true){
										if(floorequipment.lastUpdated > formattedLastsynctime&&floorequipment.isDeleted==true){
											log.debug "in remove equipment tag------"
											removeEquipment(xml,floorequipment,checkpointEquipment)
										}
									}
									if(updateEquipmentStatus==true){
										if(floorequipment.lastUpdated > formattedLastsynctime&&floorequipment.isDeleted==false){
											updateEquipment(xml,floorequipment,checkpointEquipment)
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