package com.kam
import grails.converters.XML
import groovy.xml.MarkupBuilder
import java.text.DateFormat
import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.springframework.security.authentication.dao.SaltSource;
class MasterService {
	def springSecurityService
	def saltSource
	def masterStatusCheckService
	def getMaster(def object) {
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		def addBrandStatus
		def removeBrandStatus
		def updateBrandStatus
		def addArmatuurStatus
		def removeArmatuurStatus
		def updateArmatuurStatus
		def addNoodUnitStatus
		def removeNoodUnitStatus
		def updateNoodUnitStatus
		def addLightStatus
		def removeLightStatus
		def updateLightStatus
		def addBatteryStatus
		def removeBatteryStatus
		def updateBatteryStatus
		def formattedLastsynctime
		def addGroupNumberStatus
		def removeGroupNumberStatus
		def updateGroupNumberStatus
		def addKastStatus
		def removeKastStatus
		def updateKastStatus
		def addCheckpointStatus
		def removeCheckpointStatus
		def updateCheckpointStatus
		def lasttime
		def requestType = object.requesttype
		log.debug "request type is=="+requestType
		def test = object.loginvalidation.password
		log.debug "password in master service is==" +test
		def paramLoginName = object.loginvalidation.loginname
		log.debug "username in master service is==" + paramLoginName
		String salt = saltSource instanceof NullSaltSource ? object.loginvalidation.loginname:null
		log.debug "salt in Kam service is"+ salt
		def brandadded = Brand.findAllWhere(deleted:false)
		log.debug "All available brands=="+brandadded
		def brandremoved = Brand.findAllWhere(deleted:true)
		log.debug "All deleted brands=="+brandremoved
		log.debug "last synctime received is=="+object.lastsynctime
		if(object.lastsynctime.toString()==""){
			lasttime= convertToDate("Sun Jan 1 00:00:01 IST 2012")
		}
		else{
			lasttime = convertToDate(object.lastsynctime.toString().replace("=",":"))
		}
		log.debug "lasttime is=="+lasttime
		def brandupdated = Brand.findAllByLastUpdatedGreaterThan(lasttime)
		log.debug "updated brands are"+brandupdated
		def armatuuradded = Armatuur.findAllWhere(deleted:false)
		log.debug "armatuur available are =="+armatuuradded
		def armatuurremoved = Armatuur.findAllWhere(deleted:true)
		log.debug "armatuurremoved are=="+armatuurremoved
		def armatuurupdated = Armatuur.findAllByLastUpdatedGreaterThan(lasttime)
		log.debug "armatuurupdated are ==="+armatuurupdated
		def noodunitadded = EmergencyUnitOfPrint.findAllWhere(deleted:false)
		log.debug "noodunit available are=="+noodunitadded
		def noodunitremoved = EmergencyUnitOfPrint.findAllWhere(deleted:true)
		log.debug "noodunitremoved are==="+noodunitremoved
		def noodunitupdated = EmergencyUnitOfPrint.findAllByLastUpdatedGreaterThan(lasttime)
		log.debug "noodunit updated are =="+noodunitupdated
		def lightadded = Light.findAllWhere(deleted:false)
		log.debug "light available are=="+lightadded
		def lightremoved = Light.findAllWhere(deleted:true)
		log.debug "light removed are=="+lightremoved
		def lightupdated = Light.findAllByLastUpdatedGreaterThan(lasttime)
		log.debug "lights updated are=="+lightupdated

		def batteryadded = Battery.findAllWhere(deleted:false)
		log.debug "battery available are=="+batteryadded
		def batteryremoved = Battery.findAllWhere(deleted:true)
		log.debug "battery removed are =="+batteryremoved
		def batteryupdated = Battery.findAllByLastUpdatedGreaterThan(lasttime)
		log.debug "batttery updated is=="+batteryupdated
		def groupAdded = GroupNr.findAllWhere(deleted:false)
		log.debug "group added are=="+groupAdded
		def groupRemoved = GroupNr.findAllWhere(deleted:true)
		log.debug "group removed are=="+groupRemoved
		def groupUpdated = GroupNr.findAllByLastUpdatedGreaterThan(lasttime)
		log.debug "group updated are=="+groupUpdated
		def kastAdded = Kast.findAllWhere(deleted:false)
		log.debug "kast added is=="+kastAdded
		def kastRemoved = Kast.findAllWhere(deleted:true)
		log.debug "kast removed is=="+kastRemoved
		def kastUpdated = Kast.findAllByLastUpdatedGreaterThan(lasttime)
		log.debug "kast updated are=="+kastUpdated
		String encodedPassword = springSecurityService.encodePassword(object.loginvalidation.password.toString())
		log.debug encodedPassword
		def user = User.findByUsernameAndPassword(salt,encodedPassword)
		log.debug user
		def role= UserRole.findAllWhere(user:user,role:Role.findWhere(authority:'ROLE_USER'))
		log.debug "role for this user is==="+role

		if(user){
			if(user.enabled==true) {
				def lastSyncTime //= object.lastsynctime//"Sun Jan 1 00:00:01 IST 2012"
				if (object.lastsynctime.toString() == "") {
					lastSyncTime = "Sun Jan 1 00:00:01 IST 2012"
					formattedLastsynctime = convertToDate(lastSyncTime)
					log.debug "Updated lastSyncTime :" + lastSyncTime
				}
				else{
					lastSyncTime = object.lastsynctime.toString()
					formattedLastsynctime = convertToDate(lastSyncTime)
				}
				def customer = Customer.findById(User.get(user.id).customer.id)
				log.debug "dbCustomer in kam service is =="+customer
				def checkpointAdded = CheckpointsEquipment.findAllWhere(deleted:false,customerId:customer.id.toString()).sort(){[it.id]}
				log.debug "checkpoint added are=="+checkpointAdded
				def checkpointRemoved = CheckpointsEquipment.findAllWhere(deleted:true,customerId:customer.id.toString())
				log.debug "checkpoint removed are=="+checkpointRemoved
				def checkpointUpdated = CheckpointsEquipment.findAllByLastUpdatedGreaterThanAndCustomerId(lasttime,customer.id.toString())
				log.debug "checkpoint updated are=="+checkpointUpdated
				log.debug "lastsynctime Date==" + lastSyncTime
				log.debug "new formatted time is==" + formattedLastsynctime
				addBrandStatus = masterStatusCheckService.checkAddBrandStatus(brandadded,formattedLastsynctime)
				log.debug "add brand status is=="+addBrandStatus
				removeBrandStatus = masterStatusCheckService.checkRemoveBrandStatus(brandremoved,formattedLastsynctime)
				log.debug "remove brand status is=="+removeBrandStatus
				updateBrandStatus = masterStatusCheckService.checkUpdateBrandStatus(brandupdated,formattedLastsynctime)
				log.debug "update brand status is==="+updateBrandStatus

				addArmatuurStatus = masterStatusCheckService.checkAddArmatureStatus(armatuuradded,formattedLastsynctime)
				log.debug "add armature status is=="+addArmatuurStatus
				removeArmatuurStatus = masterStatusCheckService.checkRemoveArmatureStatus(armatuurremoved,formattedLastsynctime)
				log.debug "remove armature status is=="+removeArmatuurStatus
				updateArmatuurStatus = masterStatusCheckService.checkUpdateArmatureStatus(armatuurupdated,formattedLastsynctime)
				log.debug "udate armature status is==="+updateArmatuurStatus


				addNoodUnitStatus = masterStatusCheckService.checkAddNoodStatus(noodunitadded,formattedLastsynctime)
				log.debug "add nood status is==="+addNoodUnitStatus
				removeNoodUnitStatus = masterStatusCheckService.checkRemoveNoodStatus(noodunitremoved,formattedLastsynctime)
				log.debug "remove nood status is==="+addNoodUnitStatus
				updateNoodUnitStatus = masterStatusCheckService.checkUpdateNoodStatus(noodunitupdated,formattedLastsynctime)
				log.debug "update nood status =="+updateNoodUnitStatus

				addLightStatus = masterStatusCheckService.checkAddLightStatus(lightadded,formattedLastsynctime)
				log.debug "add light status =="+addLightStatus
				removeLightStatus = masterStatusCheckService.checkRemoveLightStatus(lightremoved,formattedLastsynctime)
				log.debug "remove light status =="+removeLightStatus
				updateLightStatus = masterStatusCheckService.checkUpdateLightStatus(lightupdated,formattedLastsynctime)
				log.debug "update light status =="+updateLightStatus


				addBatteryStatus = masterStatusCheckService.checkAddBatteryStatus(batteryadded,formattedLastsynctime)
				log.debug "add battery status is=="+addBatteryStatus
				removeBatteryStatus = masterStatusCheckService.checkRemoveBatteryStatus(batteryremoved,formattedLastsynctime)
				log.debug "remove battery status=="+removeBatteryStatus
				updateBatteryStatus = masterStatusCheckService.checkUpdateBatteryStatus(batteryupdated,formattedLastsynctime)
				log.debug "update battery status =="+updateBatteryStatus


				addGroupNumberStatus = masterStatusCheckService.checkAddGroupStatus(groupAdded,formattedLastsynctime)
				log.debug "group add status is=="+addGroupNumberStatus
				removeGroupNumberStatus = masterStatusCheckService.checkRemoveGroupStatus(groupRemoved,formattedLastsynctime)
				log.debug "group remove status is=="+removeGroupNumberStatus
				updateGroupNumberStatus = masterStatusCheckService.checkUpdateGroupStatus(groupUpdated,formattedLastsynctime)
				log.debug "group update status is=="+updateGroupNumberStatus

				addKastStatus = masterStatusCheckService.checkAddKastStatus(kastAdded,formattedLastsynctime)
				log.debug "kast add status is=="+addGroupNumberStatus
				removeKastStatus = masterStatusCheckService.checkRemoveKastStatus(kastRemoved,formattedLastsynctime)
				log.debug "kast remove status is=="+removeGroupNumberStatus
				updateKastStatus = masterStatusCheckService.checkUpdateKastStatus(kastUpdated,formattedLastsynctime)
				log.debug "kast update status is=="+updateGroupNumberStatus
				addCheckpointStatus = masterStatusCheckService.checkAddCheckpointStatus(checkpointAdded,formattedLastsynctime)
				log.debug "add checkpoint status=="+addCheckpointStatus
				removeCheckpointStatus = masterStatusCheckService.checkRemoveCheckpointStatus(checkpointRemoved,formattedLastsynctime)
				log.debug "remove checkpoint status=="+removeCheckpointStatus
				updateCheckpointStatus = masterStatusCheckService.checkUpdateCheckpointStatus(checkpointUpdated,formattedLastsynctime)
				log.debug "update checkpoint status=="+updateCheckpointStatus
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
					tables{
						brandtable{
							brandValues(xml,addBrandStatus,removeBrandStatus,updateBrandStatus,formattedLastsynctime,brandadded,brandremoved,brandupdated)
						}
						armatuurtable{
							armatuurValues(xml,addArmatuurStatus,removeArmatuurStatus,updateArmatuurStatus,formattedLastsynctime,armatuuradded,armatuurremoved,armatuurupdated)
						}
						noodunitofprinttable {
							noodUnitValues(xml,addNoodUnitStatus,removeNoodUnitStatus,updateNoodUnitStatus,formattedLastsynctime,noodunitadded,noodunitremoved,noodunitupdated)
						}
						lamptable{
							lampValues(xml,addLightStatus,removeLightStatus,updateLightStatus,formattedLastsynctime,lightadded,lightremoved,lightupdated)
						}
						accutable{
							accuValues(xml,addBatteryStatus,removeBatteryStatus,updateBatteryStatus,formattedLastsynctime,batteryadded,batteryremoved,batteryupdated)
						}
						grouptable{
							groupValues(xml,addGroupNumberStatus,removeGroupNumberStatus,updateGroupNumberStatus,formattedLastsynctime,groupAdded,groupRemoved,groupUpdated)
						}
						kasttable{
							log.debug "reached kast table tag====>>>>>"
							kastValues(xml,addKastStatus,removeKastStatus,updateKastStatus,formattedLastsynctime,kastAdded,kastRemoved,kastUpdated)
							log.debug "copleted kasttable tag====>>>>>"
						}
						checkpointtable{
							checkpointValues(xml,addCheckpointStatus,removeCheckpointStatus,updateCheckpointStatus,formattedLastsynctime,checkpointAdded,checkpointRemoved,checkpointUpdated)
						}
					}
				}
				}
				log.debug "response for getmaster generated is=="+writer.toString()
				return writer.toString()
			}
			if(user.enabled==false){
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
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
		Date date = (Date) dateFormat.parse(stringDate)
		return date
	}
	def addGroupList(def xml,def groupAdded,def formattedLastsynctime){
		xml.add{
			groups{
				for(groupItem in groupAdded){
					if(groupItem.dateCreated > formattedLastsynctime&&groupItem.deleted!=true) {
						group{
							id(groupItem.id)
							value(groupItem.groupNumber)
						}
					}
				}
			}
		}
		return xml
	}
	def removeGroupList(def xml,def groupRemoved,def formattedLastsynctime){
		xml.remove{
			groups{
				for(groupItem in groupRemoved){
					if(groupItem.lastUpdated > formattedLastsynctime&&groupItem.deleted==true) {
						group{
							id(groupItem.id)
							value(groupItem.groupNumber)
						}
					}
				}
			}
		}
		return xml
	}
	def updateGroupList(def xml,def groupUpdated,def formattedLastsynctime){
		xml.update{
			groups{
				for(groupItem in groupUpdated){
					if(groupItem.lastUpdated > formattedLastsynctime&&groupItem.deleted!=true&&groupItem.dateCreated < groupItem.lastUpdated) {
						group{
							id(groupItem.id)
							value(groupItem.groupNumber)
						}
					}
				}
			}
		}
		return xml
	}
	def addKastList(def xml,def kastAdded,def formattedLastsynctime){
		xml.add{
			kasts{
				for(kastItem in kastAdded){
					if(kastItem.dateCreated > formattedLastsynctime&&kastItem.deleted!=true) {
						kast{
							id(kastItem.id)
							value(kastItem.kastName)
						}
					}
				}
			}
		}
		return xml
	}
	def removeKastList(def xml,def kastRemoved,def formattedLastsynctime){
		xml.remove{
			kasts{
				for(kastItem in kastRemoved){
					if(kastItem.lastUpdated > formattedLastsynctime&&kastItem.deleted==true) {
						kast{
							id(kastItem.id)
							value(kastItem.kastName)
						}
					}
				}
			}
		}
		return xml
	}
	def updateKastList(def xml,def kastUpdated,def formattedLastsynctime){
		xml.update{
			kasts{
				for(kastItem in kastUpdated){
					if(kastItem.lastUpdated > formattedLastsynctime&&kastItem.deleted!=true&&kastItem.dateCreated < kastItem.lastUpdated) {
						kast{
							id(kastItem.id)
							value(kastItem.kastName)
						}
					}
				}
			}
		}
		return xml
	}
	def addCheckpointList(def xml,def checkpointAdded,def formattedLastsynctime){
		xml.add{
			checkpoints{
				for(checkpointItem in checkpointAdded){
					if(checkpointItem.dateCreated > formattedLastsynctime&&checkpointItem.deleted!=true) {
						checkpoint{
							id(checkpointItem.id)
							description(checkpointItem.value)
							type("checkpoint")
							options(" ")

						}
					}
				}
			}
		}
		//return xml
	}
	def removeCheckpointList(def xml,def checkpointRemoved,def formattedLastsynctime){
		xml.remove{
			checkpoints{
				for(checkpointItem in checkpointRemoved){
					if(checkpointItem.lastUpdated > formattedLastsynctime&&checkpointItem.deleted==true) {
						checkpoint{
							id(checkpointItem.id)
							description(checkpointItem.value)
							type("checkpoint")
							options(" ")
						}
					}
				}
			}
		}
		return xml
	}
	def updateCheckpointList(def xml,def checkpointUpdated,def formattedLastsynctime){
		xml.update{
			checkpoints{
				for(checkpointItem in checkpointUpdated){
					if(checkpointItem.lastUpdated > formattedLastsynctime&&checkpointItem.deleted!=true&&checkpointItem.dateCreated < checkpointItem.lastUpdated) {
						checkpoint{
							id(checkpointItem.id)
							description(checkpointItem.value)
							type("checkpoint")
							options("")
						}
					}
				}
			}
		}
		return xml
	}
	def addBrandList(def xml,def formattedLastsynctime,def brandadded){
		xml.add{
			brands{
				for(branditem in brandadded){
					if(branditem.dateCreated > formattedLastsynctime&&branditem.deleted!=true) {
						brand{
							id(branditem.id)
							value(branditem.brandName)
						}
					}
				}
			}
		}
		return xml
	}
	def removeBrandList(def xml,def formattedLastsynctime,def brandremoved){
		xml.remove{
			brands{
				for(branditem in brandremoved){
					if(branditem.lastUpdated > formattedLastsynctime&&branditem.deleted==true) {
						brand{
							id(branditem.id)
							value(branditem.brandName)
						}
					}
				}
			}
		}
		return xml
	}
	def updateBrandList(def xml,def formattedLastsynctime,def brandadded){
		xml.update{
			brands{
				for(branditem in brandadded){
					if(branditem.lastUpdated > formattedLastsynctime&&branditem.deleted!=true&&branditem.dateCreated<branditem.lastUpdated) {
						brand{
							id(branditem.id)
							value(branditem.brandName)
						}
					}
				}
			}
		}
		return xml
	}

	def addArmatuurList(def xml,def formattedLastsynctime,def armatuuradded){
		xml.add{
			armatuurs{
				for(armatuuritem in armatuuradded){
					if(armatuuritem.dateCreated > formattedLastsynctime&&armatuuritem.deleted!=true) {
						armatuur{
							id(armatuuritem.id)
							value(armatuuritem.armatuurType)
						}
					}
				}
			}
		}
		return xml
	}
	def removeArmatuurList(def xml,def formattedLastsynctime,def armatuurremoved){
		xml.remove{
			armatuurs{
				for(armatuuritem in armatuurremoved){
					if(armatuuritem.lastUpdated > formattedLastsynctime&&armatuuritem.deleted==true) {
						armatuur{
							id(armatuuritem.id)
							value(armatuuritem.armatuurType)
						}
					}
				}
			}
		}
		return xml
	}
	def updateArmatuurList(def xml,def formattedLastsynctime,def armatuurupdated){
		xml.update{
			armatuurs{
				for(armatuuritem in armatuurupdated){
					if(armatuuritem.lastUpdated > formattedLastsynctime&&armatuuritem.deleted!=true&&armatuuritem.dateCreated<armatuuritem.lastUpdated) {
						armatuur{
							id(armatuuritem.id)
							value(armatuuritem.armatuurType)
						}
					}
				}
			}
		}
		return xml
	}
	def addNoodUnitList(def xml,def formattedLastsynctime,def noodunitadded){
		xml.add {
			noodunitofprints{
				for(noodunitofprintlist in noodunitadded)
					if(noodunitofprintlist.dateCreated > formattedLastsynctime&&noodunitofprintlist.deleted!=true) {
						noodunitofprint{
							id(noodunitofprintlist.id)
							value(noodunitofprintlist.unitName)
						}
					}
			}
		}
		return xml
	}
	def removeNoodUnitList(def xml,def formattedLastsynctime,def noodunitremoved){
		xml.remove{
			noodunitofprints{
				for(noodunitofprintlist in noodunitremoved)
					if(noodunitofprintlist.lastUpdated > formattedLastsynctime&&noodunitofprintlist.deleted==true) {
						noodunitofprint{
							id(noodunitofprintlist.id)
							value(noodunitofprintlist.unitName)
						}
					}
			}
		}
		return xml
	}
	def updateNoodUnitList(def xml,def formattedLastsynctime,def noodunitupdated){
		xml.update{
			noodunitofprints{
				for(noodunitofprintlist in noodunitupdated)
					if(noodunitofprintlist.lastUpdated > formattedLastsynctime&&noodunitofprintlist.deleted!=true&&noodunitofprintlist.dateCreated<noodunitofprintlist.lastUpdated) {
						noodunitofprint{
							id(noodunitofprintlist.id)
							value(noodunitofprintlist.unitName)
						}
					}
			}
		}
	}
	def addLightList(def xml,def formattedLastsynctime,def lightadded){
		xml.add{
			lamps{
				for(lamplist in lightadded)
					if(lamplist.dateCreated > formattedLastsynctime&&lamplist.deleted!=true) {
						lamp{
							id(lamplist.id)
							value(lamplist.name)
						}
					}
			}
		}
		return xml
	}
	def removeLightList(def xml,def formattedLastsynctime,def lightremoved){
		xml.remove{
			lamps{
				for(lamplist in lightremoved)
					if(lamplist.lastUpdated > formattedLastsynctime&&lamplist.deleted==true) {
						lamp{
							id(lamplist.id)
							value(lamplist.name)
						}
					}
			}
		}
		return xml
	}
	def updateLightList(def xml,def formattedLastsynctime,def lightupdated){
		xml.update{
			lamps{
				for(lamplist in lightupdated)
					if(lamplist.lastUpdated > formattedLastsynctime&&lamplist.deleted!=true&&lamplist.dateCreated<lamplist.lastUpdated) {
						lamp{
							id(lamplist.id)
							value(lamplist.name)
						}
					}
			}
		}
		return xml
	}
	def addBatteryList(def xml,def formattedLastsynctime,def batteryadded){
		xml.add{
			accus{
				for(accuList in batteryadded)
					if(accuList.dateCreated > formattedLastsynctime&&accuList.deleted!=true) {
						accu{
							id(accuList.id)
							value(accuList.batteryType)
						}
					}
			}
		}
		return xml
	}

	def removeBatteryList(def xml,def formattedLastsynctime,def batteryremoved){
		xml.remove{
			accus{
				for(accuList in batteryremoved)
					if(accuList.lastUpdated > formattedLastsynctime&&accuList.deleted==true) {
						accu{
							id(accuList.id)
							value(accuList.batteryType)
						}
					}
			}
		}
		return xml
	}
	def updateBatteryList(def xml,def formattedLastsynctime,def batteryupdated){
		xml.update{
			accus{
				for(accuList in batteryupdated)
					if(accuList.lastUpdated > formattedLastsynctime&&accuList.deleted!=true&&accuList.dateCreated<accuList.lastUpdated) {
						accu{
							id(accuList.id)
							value(accuList.batteryType)
						}
					}
			}
		}
		return xml
	}
	def brandValues(xml,addBrandStatus,removeBrandStatus,updateBrandStatus,formattedLastsynctime,brandadded,brandremoved,brandupdated){
		if(addBrandStatus==true){
			addBrandList(xml,formattedLastsynctime,brandadded)
		}
		if(removeBrandStatus==true){
			removeBrandList(xml,formattedLastsynctime,brandremoved)
		}
		if(updateBrandStatus==true){
			updateBrandList(xml,formattedLastsynctime,brandupdated)
		}

	}
	def armatuurValues(xml,addArmatuurStatus,removeArmatuurStatus,updateArmatuurStatus,formattedLastsynctime,armatuuradded,armatuurremoved,armatuurupdated){
		if(addArmatuurStatus==true){
			addArmatuurList(xml,formattedLastsynctime,armatuuradded)
		}
		if(removeArmatuurStatus==true){
			removeArmatuurList(xml,formattedLastsynctime,armatuurremoved)
		}
		if(updateArmatuurStatus==true){
			updateArmatuurList(xml,formattedLastsynctime,armatuurupdated)
		}

	}
	def noodUnitValues(xml,addNoodUnitStatus,removeNoodUnitStatus,updateNoodUnitStatus,formattedLastsynctime,noodunitadded,noodunitremoved,noodunitupdated){
		if(addNoodUnitStatus==true){
			addNoodUnitList(xml,formattedLastsynctime,noodunitadded)
		}
		if(removeNoodUnitStatus==true){
			removeNoodUnitList(xml,formattedLastsynctime,noodunitremoved)
		}
		if(updateNoodUnitStatus==true){
			updateNoodUnitList(xml,formattedLastsynctime,noodunitupdated)
		}
	}
	def lampValues(xml,addLightStatus,removeLightStatus,updateLightStatus,formattedLastsynctime,lightadded,lightremoved,lightupdated){
		if(addLightStatus==true){
			addLightList(xml,formattedLastsynctime,lightadded)
		}
		if(removeLightStatus==true){
			removeLightList(xml,formattedLastsynctime,lightremoved)
		}
		if(updateLightStatus==true){
			updateLightList(xml,formattedLastsynctime,lightupdated)
		}
	}
	def accuValues(xml,addBatteryStatus,removeBatteryStatus,updateBatteryStatus,formattedLastsynctime,batteryadded,batteryremoved,batteryupdated){
		if(addBatteryStatus==true){
			addBatteryList(xml,formattedLastsynctime,batteryadded)
		}
		if(removeBatteryStatus==true){
			removeBatteryList(xml,formattedLastsynctime,batteryremoved)
		}
		if(updateBatteryStatus==true){
			updateBatteryList(xml,formattedLastsynctime,batteryupdated)
		}

	}
	def groupValues(xml,addGroupNumberStatus,removeGroupNumberStatus,updateGroupNumberStatus,formattedLastsynctime,groupAdded,groupRemoved,groupUpdated){
		if(addGroupNumberStatus==true){
			addGroupList(xml,groupAdded,formattedLastsynctime)
		}
		if(removeGroupNumberStatus==true){
			removeGroupList(xml,groupRemoved,formattedLastsynctime)
		}
		if(updateGroupNumberStatus==true){
			updateGroupList(xml,groupUpdated,formattedLastsynctime)
		}

	}
	def kastValues(xml,addKastStatus,removeKastStatus,updateKastStatus,formattedLastsynctime,kastAdded,kastRemoved,kastUpdated){
		if(addKastStatus==true){
			addKastList(xml,kastAdded,formattedLastsynctime)
			log.debug "completed add kast status tag========"
		}
		if(removeKastStatus==true){
			removeKastList(xml,kastRemoved,formattedLastsynctime)
		}
		if(updateKastStatus==true){
			updateKastList(xml,kastUpdated,formattedLastsynctime)
			log.debug "completed update kast status============"
		}
	}
	def checkpointValues(xml,addCheckpointStatus,removeCheckpointStatus,updateCheckpointStatus,formattedLastsynctime,checkpointAdded,checkpointRemoved,checkpointUpdated){
		if(addCheckpointStatus==true){
			addCheckpointList(xml,checkpointAdded,formattedLastsynctime)
		}
		if(removeCheckpointStatus==true){
			removeCheckpointList(xml,checkpointRemoved,formattedLastsynctime)
		}
		if(updateCheckpointStatus==true){
			updateCheckpointList(xml,checkpointUpdated,formattedLastsynctime)
		}
	}
}
