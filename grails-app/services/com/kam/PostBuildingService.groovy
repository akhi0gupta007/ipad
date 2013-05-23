package com.kam
import java.util.Formatter.DateTime
import groovy.xml.MarkupBuilder
import java.text.DateFormat
import java.text.SimpleDateFormat

import org.apache.commons.lang.time.DateUtils;
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.json.simple.JSONArray
import org.json.simple.JSONObject

class PostBuildingService{
	def springSecurityService
	def saltSource
	def pdfService
	def grailsApplication
	def asynchronousMailService
	
	
	def postBuilding(def object,def requiredXml) {
		def reader = new StringReader(requiredXml)
		def xmlRead = new XmlParser().parse(reader)
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		def reportId=''
		def equipmentCheckpointStatus,buildingObject,buildingReport
		JSONArray jsonArrayEquipment = new JSONArray()
		JSONArray jsonArrayAddEquipment = new JSONArray()
		JSONArray jsonArrayImageAdd = new JSONArray()
		JSONObject jsonObject
		JSONObject jsonObjectAdd
		JSONObject jsonObjectImageAdd
		def buildingReportId
		log.debug "xml in postmaster Service is"+object
		log.debug "documents received are"+object.documents.document
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
		def reportCheckpoints
		reportCheckpoints=CheckpointsEquipment.list()
		if(dbUser){
			if(dbUser.enabled==true){
				def customer = Customer.findById(User.get(dbUser.id).customer.id)
				log.debug "dbCustomer in kam service is ==" + customer
				xmlRead.buildings.each{buildings->
					def currentActionUsers = []
					def currentNotifyUsers = []
					buildings.building.each{building->
						log.debug "building is=="+building.id.text()
						boolean buildingSaved=false
						buildingObject=Building.get(building.id.text().toLong())
						buildingReport=new Report(reportName:'Report_'+dbUser.username+"_"+convertToDate(new Date()),building:buildingObject,projectName:buildingObject.project.projectName,projectNumber:buildingObject.project.projectNumber,reviewedBy:dbUser.username)
						buildingReport.save(flush:true)
						log.debug "i have saved report ========="+buildingReport
						if(buildingReport.save()){
							buildingReportId = buildingReport.id
							setMailToBeSent(buildingObject,buildingReport,building,customer)
							log.debug "report id received from database is===="+buildingReportId
							def reportDB = Report.findById(buildingReportId.toLong())
							log.debug "report ID from  db ===="+reportDB
							def reportInput1=new ReportValue(type:'aduies',value:building.report.aduies.text(),report:buildingReport).save()
							def reportInput3=new ReportValue(type:'conclusion',value:building.report.conlusion.text(),report:buildingReport).save()
							building.report.attachments.attachment.each{attachment->
								if(attachment.id.text().indexOf('customer')!=-1){
									def customerSignature=new ReportValue(type:'CustomerSignature',value:attachment.id.text(),report:buildingReport).save()
								}
								else
									def userSignature=new ReportValue(type:'UserSignature',value:attachment.id.text(),report:buildingReport).save()
							}
							buildingSaved=true
							building.report.each{report->
								
								report.actionusers.actionuser.each{actionuser->
									currentActionUsers.add(actionuser.name.text().toString())
								}
								report.notifyusers.notifyuser.each{notifyuser->
									currentNotifyUsers.add(notifyuser.name.text().toString())
								}
							}
						}
						building.floors.update.floor.each{floor->
							def floorFound = Floor.findById(floor.id.text())
							log.debug "floor is===="+floorFound
							if(floorFound!=null){
							floorFound.floorComment = floor.text.text()
							}
							if(floor.type.text()=="equipment"){
								floor.equipments.update.equipment.each{equipment->
									DateFormat getDate=new SimpleDateFormat('dd/MM/yyyy')
									DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd")
									Date dateReceived
									def findEquipment = Equipment.findById(equipment.arm.text())
									log.debug "equippment found is=="+findEquipment
									findEquipment.name = equipment.name.text()
									/*findEquipment.equipmentType = equipment.type.text()
									findEquipment.equipmentType2 = equipment.vluchtweg.text()*/
									String equipmentTypeOne
									String equipmentTypeTwo
									if(equipment.type.text().toString()=="Inbouw"){
										equipmentTypeOne = "Inbuilt"
									}
									else{
										equipmentTypeOne = "External"
									}
									if(equipment.vluchtweg.text().toString()=="Aanduiding"){
										equipmentTypeTwo = "Indication"
									}
									else{
										equipmentTypeTwo = "Lighting"
									}
									findEquipment.equipmentType = equipmentTypeOne
									findEquipment.equipmentType2 = equipmentTypeTwo
									def equipmentOverallStatus
									//def reportId=''
									equipment.checkpoints.checkpoint.each{checkpoint->
										if(checkpoint.value.text()=="3"){
											equipmentCheckpointStatus = 'Afgekeurd'
											equipmentOverallStatus='Afgekeurd'
											findEquipment.spec = checkpoint.specs.text()
										}
										if(checkpoint.value.text()=="2"){
											equipmentCheckpointStatus = 'NVT'
										}
										if(checkpoint.value.text()=="1"){
											equipmentCheckpointStatus = 'Goedgekeurd'
										}
										if(checkpoint.value.text()=="4"){
											equipmentCheckpointStatus = 'Akkoord na herkeuring'
										}
										if(checkpoint.value.text()==""){
											equipmentCheckpointStatus = ''
										}
										def equipmentCheckpointType='Functional'

										for(def reportCheckpoint in reportCheckpoints){
											if(reportCheckpoint.value == checkpoint.description.text()){
												equipmentCheckpointType=reportCheckpoint.category
											}
										}
										if(buildingSaved)
											reportId = buildingReport.id
										def equipmentCheckpoint = new EquipmentCheckpoint(equipment:findEquipment,comment:checkpoint.comment.text(),checkpointDescription:checkpoint.description.text(),status:equipmentCheckpointStatus,value:checkpoint.value.text(),priority:checkpoint.priority.text(),priorityDate:checkpoint.prioritydate.text(),floorId:floor.id.text(),checkpointType:equipmentCheckpointType,reportId:reportId)
										equipmentCheckpoint.save()
										checkpoint.attachments.attachment.each{attachment->
											def equipmentImage = new EquipmentImage(imageName:attachment.id.text(),equipmentCheckpoint:equipmentCheckpoint)
											equipmentImage.save()
											jsonObjectImageAdd = new JSONObject()
											jsonObjectImageAdd.put("id",equipmentImage.id)
											jsonObjectImageAdd.put("imageNameReceived",attachment.id.text())
											jsonArrayImageAdd.add(jsonObjectImageAdd)
										}
										if(equipmentCheckpoint.hasErrors()){
											equipmentCheckpoint.errors.each{ println it }
										}
									}
									log.debug "report is now====="+reportId
									if(equipment.merk.text()!=""){
										log.debug "brand received is=="+equipment.merk.text()
										def brandfound = Brand.findById(equipment.merk.text().toLong())
										findEquipment.brand = brandfound
										log.debug "updated the brand"
									}
									if(equipment.typearmatuur.text()!=""){
										def armatuurfound = Armatuur.findById(equipment.typearmatuur.text().toLong())
										findEquipment.armatuur = armatuurfound
										log.debug "date received is====="+equipment.bouwjarm.text()
										//findEquipment.buildYearOfArmature = dateFormat.parse(equipment.bouwjarm.text().toString()).format("dd/MM/yyyy")
										log.debug "updated armatuur"
									}
									if(equipment.noodunitofprint.text()!=""){
										def emergencyUnitOfPrintfound = EmergencyUnitOfPrint.findById(equipment.noodunitofprint.text().toLong())
										findEquipment.emergencyUnitOfPrint = emergencyUnitOfPrintfound
										//findEquipment.buildYearOfEmergencyUnit =  dateFormat.parse(equipment.bouwjnood.text().toString()).format("dd/MM/yyyy")
									}
									if(equipment.lamp.text()!=""){
										log.debug "light received is=="+equipment.lamp.text()
										def lightfound = Light.findById(equipment.lamp.text().toLong())
										findEquipment.light = lightfound
									}
									if(equipment.accu.text()!=""){
										def batteryfound = Battery.findById(equipment.accu.text().toLong())
										findEquipment.battery = batteryfound
										//findEquipment.buildYearOfBattery =  dateFormat.parse(equipment.bouwjaccu.text().toString()).format("dd/MM/yyyy")
										log.debug "battery found is=="+batteryfound
									}
									if(equipment.group.text()!=""){
										log.debug "group recceived is====="+equipment.group.text()
										def groupFound = GroupNr.findById(equipment.group.text().toLong())
										log.debug "group found is======"+groupFound
										findEquipment.groupNo = groupFound
									}
									if(equipment.kast.text()!=""){
										def kastFound = Kast.findById(equipment.kast.text().toLong())
										findEquipment.kast = kastFound
									}
									if(equipment.bouwjaccu.text()!="")
									findEquipment.buildYearOfBattery = getDate.format(convertToDateEquipment(equipment.bouwjaccu.text().toString()))
									else
									findEquipment.buildYearOfBattery = getDate.format(new Date())
									
									if(equipment.bouwjarm.text()!="")
									findEquipment.buildYearOfArmature = getDate.format(convertToDateEquipment(equipment.bouwjarm.text().toString()))
									else
									findEquipment.buildYearOfArmature =  getDate.format(new Date())
									
									if(equipment.bouwjnood.text()!="")
									findEquipment.buildYearOfEmergencyUnit = getDate.format(convertToDateEquipment(equipment.bouwjnood.text().toString()))
									else
									findEquipment.buildYearOfEmergencyUnit = getDate.format(new Date())
									
									setEquipmentStatus(findEquipment,reportId,currentNotifyUsers,customer)
									jsonObject = new JSONObject()
									jsonObject.put("arm",findEquipment.id)
									jsonArrayEquipment.add(jsonObject);
								}
								floor.equipments.add.equipment.each{equipment->
									def ipadEquipment = addEquipmentFromIpad(equipment,floor,customer,dbUser)
										jsonObjectAdd = new JSONObject()
										jsonObjectAdd.put("arm",ipadEquipment.id )
										jsonObjectAdd.put("name", ipadEquipment.name)
										jsonObjectAdd.put("oldarm", equipment.arm.text())
										jsonArrayAddEquipment.add(jsonObjectAdd);
									ipadEquipmentCheckpoints(equipment,ipadEquipment,reportCheckpoints,floor,reportId)
									setEquipmentStatus(ipadEquipment,reportId,currentNotifyUsers,customer)
								}
								floor.equipments.remove.equipment.each{equipment->
									log.debug "equipment received is=="+equipment.arm.text()
									def removeEquipment = Equipment.findById(equipment.arm.text())
									log.debug "equipment to be removed is=="+removeEquipment
									removeEquipment.isDeleted = true
								}
								if(buildingSaved){
									try{
										def currentFloor = Floor.get(floor.id.text().toString().toLong())
										def reportInput4=new ReportValue(type:'FloorComment',value:floor.text.text(),report:buildingReport,floor:currentFloor)
										reportInput4.save()
									}catch(Exception e){
										e.printStackTrace()
									}
								}
							}
						}
						log.debug "at the end of receiving starting xml tags "
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
					buildings{
						for(int k=0;k<xmlRead.buildings.building.size();k++){
							building{
								id(xmlRead.buildings.building.id[k].text())
								report{
									attachments{
										attachment{
											log.debug "under attachment tag for building"
											if(xmlRead.buildings.building.report[k].attachments.attachment.size()!=0){
												log.debug "building report is========="+buildingReport.id
											id(buildingReport.id)
											name(xmlRead.buildings.building.report[k].attachments.attachment[0].id.text())
											log.debug "attachment 1 completed"
											}
										}
										attachment{
											if(xmlRead.buildings.building.report[k].attachments.attachment.size()!=0){
												log.debug "inside attachment==="
												id(buildingReport.id)
											name(xmlRead.buildings.building.report[k].attachments.attachment[1].id.text())
											log.debug "attachment 2 completed"
											}
										}
									}
								}
								floors{
									log.debug "inside floors>>>==="
									updateTagFloor(xml,xmlRead,jsonArrayAddEquipment,jsonArrayEquipment,buildingReportId)
									log.debug "after updatetagfloors>>>====="
								}
							}
						}
					}
				}
				}
				log.debug "response for PostBuilding is=="+writer.toString()
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
		log.debug "at the end of method postbuildingservice"
	}
	def convertToDate(def stringDate) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy")
		def date = dateFormat.format(stringDate)
		return date
	}
	def convertToDateEquipment(def stringDate) {	
		log.debug " n convertToDateEquipment-----date received==="+stringDate+"and its class===>>>>>"+stringDate.getClass()
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd")
		Date date = (Date) dateFormat.parse(stringDate)
		log.debug "date is now in convertToDateEquipment==="+date
		return date
	}
	def updateTagFloor(xml,xmlRead,jsonArrayAddEquipment,jsonArrayEquipment,buildingReportId){
		xml.update{
			for(int i=0;i<xmlRead.buildings.building.floors.update.floor.size();i++){
				floor{
					id(xmlRead.buildings.building.floors.update.floor.id[i].text())
					type(xmlRead.buildings.building.floors.update.floor.type[i].text())
					equipments{
						addEquipmentsTag(xml,xmlRead,jsonArrayAddEquipment,buildingReportId,i)
						updateEquipmentsTag(xml,xmlRead,jsonArrayEquipment,buildingReportId,i)
					}
				}
			}
		}
	}
	def addEquipmentsTag(xml,xmlRead,jsonArrayAddEquipment,buildingReportId,i){
		xml.add{
			for(int l = 0; l <jsonArrayAddEquipment.size(); l++){
				def equip = Equipment.createCriteria().list{
					and{
						eq('floor',Floor.get(xmlRead.buildings.building.floors.update.floor.id[i].text()))
					}
					and {
						eq('id',jsonArrayAddEquipment[l].get('arm'))
					}
				}
				log.debug("Equipment=="+equip+" for  floor......"+xmlRead.buildings.building.floors.update.floor.id[i].text()+"...............")

				if(equip){
					equipment{
						log.debug "equipment received"+equip
						def equipmentAdd = Equipment.findById(equip[0].id)
						log.debug "equipent add has value-------"+equipmentAdd
						arm(equip[0].id)
						oldarm(jsonArrayAddEquipment[l].get("oldarm").toString())
						if(equipmentAdd.equipmentType=="Inbuilt"||equipmentAdd.equipmentType=="Inbouw")
						{
							type("Inbouw")
						}
						if(equipmentAdd.equipmentType=="External"||equipmentAdd.equipmentType=="Opbouw")
						{
							type("Opbouw")
						}
						if(equipmentAdd.equipmentType2=="Indication"||equipmentAdd.equipmentType2=="Aanduiding"){
							vluchtweg("Aanduiding")
						}
						if(equipmentAdd.equipmentType2=="Lighting"||equipmentAdd.equipmentType2=="Verlichting"){
							vluchtweg("Verlichting")
						}
						checkpoints{
							log.debug "equipmentAdd has checkpoints===="+equipmentAdd.checkpoints
							def findcheckpoints = EquipmentCheckpoint.findAllWhere(equipment:equipmentAdd,reportId:buildingReportId.toString())
							log.debug "findCheckpoints has value====="+findcheckpoints
							for(equipmentCheckpointValue in findcheckpoints){
								if(equipmentCheckpointValue.reportId.toString().equals(buildingReportId.toString())){
									checkpoint{
										id(equipmentCheckpointValue.id)
										description(equipmentCheckpointValue.checkpointDescription)
										attachments{
											def newEquipmentImage = EquipmentImage.findAllWhere(equipmentCheckpoint:equipmentCheckpointValue)
											for(equipmentCheckpointImages in newEquipmentImage){
												log.debug "equipmentCheckpointValue.equipmentImages=="+equipmentCheckpointImages.imageName
												attachment{
													id(equipmentCheckpointImages.id)
													name(equipmentCheckpointImages.imageName)
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
	def updateEquipmentsTag(xml,xmlRead,jsonArrayEquipment,buildingReportId,i){
		xml.update{
			for(int l = 0; l <jsonArrayEquipment.size(); l++){

				def equipmentList = Equipment.createCriteria().list{
					and{
						eq('floor',Floor.get(xmlRead.buildings.building.floors.update.floor.id[i].text()))
					}
					and {
						eq('id',jsonArrayEquipment[l].get('arm'))
					}
				}
				equipmentsTag(xml,equipmentList,buildingReportId)
			}
		}
	}
	def equipmentsTag(xml,equipmentList,buildingReportId){
		for (equipmentValue in equipmentList){
			xml.equipment{
				arm(equipmentValue.id)
				if(equipmentValue.equipmentType=="Inbuilt"||equipmentValue.equipmentType=="Inbouw")
				{
					type("Inbouw")
				}
				if(equipmentValue.equipmentType=="External"||equipmentValue.equipmentType=="Opbouw")
				{
					type("Opbouw")
				}
				if(equipmentValue.equipmentType2=="Indication"||equipmentValue.equipmentType2=="Aanduiding"){
					vluchtweg("Aanduiding")
				}
				if(equipmentValue.equipmentType2=="Lighting"||equipmentValue.equipmentType2=="Verlichting"){
					vluchtweg("Verlichting")
				}
				/*type(equipmentValue.equipmentType)
				vluchtweg(equipmentValue.equipmentType2)*/
				checkpoints{
					for(equipmentCheckpointValue in equipmentValue.checkpoints){
						if(equipmentCheckpointValue.reportId.toString().equals(buildingReportId.toString())){
							checkpoint{
								id(equipmentCheckpointValue.id)
								description(equipmentCheckpointValue.checkpointDescription)
								attachments{
									def newEquipmentImage = EquipmentImage.findAllWhere(equipmentCheckpoint:equipmentCheckpointValue)
									for(equipmentCheckpointImages in newEquipmentImage){
										log.debug "equipmentCheckpointValue.equipmentImages=="+equipmentCheckpointImages.imageName
										attachment{
											id(equipmentCheckpointImages.id)
											name(equipmentCheckpointImages.imageName)
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
	def setEquipmentStatus(findEquipment,reportId,currentNotifyUsers,customer){
		def checkpointItem = []
		Calendar currentDate=Calendar.getInstance()
		List<Date> checkpointItemDate = new ArrayList<Date>();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		log.debug "report id received is====="+reportId+"and it has class=="+reportId.getClass()
		def checkpoint = EquipmentCheckpoint.findAllWhere(equipment:findEquipment,reportId:reportId.toString())
		log.debug "checkpoints for this equipment is==="+checkpoint
		for(int i=0;i<checkpoint.size();i++){
			checkpointItem.add(checkpoint[i].status)
			if(checkpoint[i].priorityDate==null&&checkpoint[i].priority=="4"){
				Date newDate = new Date()
				newDate = DateUtils.addDays(newDate,30);
				log.debug "deadline date for month is now set as=====>>>>>"+newDate
				checkpoint[i].priorityDate = newDate.format("dd/MM/yyyy").toString()
				checkpointItemDate.add(newDate)
			}
			else if(checkpoint[i].priorityDate==null&&checkpoint[i].priority=="3"){
				Date newDate = new Date()
				newDate = DateUtils.addDays(newDate,7);
				log.debug "deadline date for week is now set as=====>>>>>"+newDate
				checkpoint[i].priorityDate = newDate.format("dd/MM/yyyy").toString()
				checkpointItemDate.add(newDate)
			}
			else if(checkpoint[i].priorityDate==null&&checkpoint[i].priority=="2"){
				Date newDate = new Date()
				newDate = DateUtils.addDays(newDate,2);
				log.debug "deadline date for hour 48 is now set as=====>>>>>"+newDate
				checkpoint[i].priorityDate = newDate.format("dd/MM/yyyy").toString()
				checkpointItemDate.add(newDate)
			}
			else if(checkpoint[i].priorityDate==null&&checkpoint[i].priority=="1"){
				Date newDate = new Date()
				newDate = DateUtils.addDays(newDate,1)
				log.debug "deadline date for hour 24 is now set as=====>>>>>"+newDate
				checkpoint[i].priorityDate = newDate.format("dd/MM/yyyy").toString()
				checkpointItemDate.add(newDate)
			}
			else if(checkpoint[i].priorityDate!=null){
				log.debug "prioritydate recieevd----------"+checkpoint[i].priorityDate
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd")
				Date date = (Date)dateFormat.parse(checkpoint[i].priorityDate)
				log.debug "date is now===="+date+"and its classs==="+date.getClass()
				log.debug "date formattted is now ====="+date.format("dd/MM/yyyy")
				checkpoint[i].priorityDate = date.format("dd/MM/yyyy")
				checkpointItemDate.add(date)
			}
		}
		int afgekerudOccurrences = Collections.frequency(checkpointItem, "Afgekeurd");
		int goedgekeurdOccurences = Collections.frequency(checkpointItem, "Goedgekeurd");
		int akkordNaHarkeuringOccurences = Collections.frequency(checkpointItem, "Akkoord na herkeuring");
		int nvtOccurences = Collections.frequency(checkpointItem, "NVT");
		log.debug "it has afkgeurd occurences===="+afgekerudOccurrences
		if(afgekerudOccurrences!=0){
			findEquipment.status = "Afgekeurd"
		}
		else if(afgekerudOccurrences==0&&goedgekeurdOccurences!=0){
			findEquipment.status = "Goedgekeurd"
		}
		else if(afgekerudOccurrences==0&&goedgekeurdOccurences==0&&akkordNaHarkeuringOccurences!=0){
			findEquipment.status = "Akkoord na herkeuring"
		}
		else if(afgekerudOccurrences==0&&goedgekeurdOccurences==0&&akkordNaHarkeuringOccurences==0&&nvtOccurences!=0){
			findEquipment.status = "NVT"
		}
		setEquipmentDeadLine(checkpointItemDate,findEquipment)
		if(afgekerudOccurrences!=0){
		sendEquipmentMailAfterSync(findEquipment,reportId,currentNotifyUsers,customer)
		}
	}
	def setEquipmentDeadLine(checkpointItemDate,findEquipment){
		Collections.sort(checkpointItemDate)
		Collections.reverse(checkpointItemDate)
		findEquipment.deadLine = checkpointItemDate.getAt(0)
		log.debug "current equipment has now deadline"+findEquipment.deadLine
	}
	
	def sendEquipmentMailAfterSync(findEquipment,reportId,currentNotifyUsers,customer){
		log.debug "equipment received is======"+findEquipment+"and its deadline is===="+findEquipment.deadLine
		String equipmentCheckpointData = ''
		def equipmentCheckpoint
		Date today = new Date()
		Date nextday = DateUtils.addDays(today,2);
		log.debug "nextday is===="+nextday
		if(findEquipment.deadLine!=null){
			if(findEquipment.deadLine <= nextday){
				equipmentCheckpoint = EquipmentCheckpoint.findAllWhere(equipment:findEquipment,reportId:reportId.toString(),value:'3')
			}
			for(checkpoints in equipmentCheckpoint){
				equipmentCheckpointData = equipmentCheckpointData+checkpoints.checkpointDescription+"   :"+checkpoints.priorityDate+"         :"+checkpoints.comment+"<br/>"
			}
			log.debug "total checkpoints to send are===="+equipmentCheckpointData
			if(findEquipment.deadLine <= nextday){
				for(notifyusers in currentNotifyUsers){
					def groupName = EmailGroup.findWhere(groupName:notifyusers)
					log.debug "group name found notify users are===="+groupName
					for(def member in groupName.members){
						asynchronousMailService.sendMail {
							to member.email
							from customer.website.replace("www.","noreply@")
							subject 'Herinnering afgekeurd armatuur   '+findEquipment.floor.building.project.projectName
							html "<body>"+"Let op de overall deadline voor reparatie van onderstaande armatuur is bijna bereikt<br/><br/>"+"Project:"+findEquipment.floor.building.project.projectName+"<br/>Gebouw:"+findEquipment.floor.building.buildingName+"<br/>Verdieping:"+findEquipment.floor.floorNumber+"<br/>Armatuur nummer:"+findEquipment.name+"<br/><br/>"+equipmentCheckpointData+"</body>"
						}
					}
				}
				findEquipment.isMailSent = true
			}
			else{
				findEquipment.isMailSent = false
			}
		}
	}
	def addEquipmentFromIpad(equipment,floor,customer,dbUser){
		log.debug "in add tag of equipment"
		String equipmentTypeOne
		String equipmentTypeTwo
		String buildYearOfBattery
		String buildYearOfArmature
		String buildYearOfEmergencyUnit
		DateFormat getDate=new SimpleDateFormat('dd/MM/yyyy')
		if(equipment.type.text().toString()=="Inbouw"){
			equipmentTypeOne = "Internal"
		}
		if(equipment.type.text().toString()=="Opbouw"){
			equipmentTypeOne = "External"
		}
		if(equipment.vluchtweg.text().toString()=="Aanduiding"){
			equipmentTypeTwo = "Indication"
		}
		if(equipment.vluchtweg.text().toString()=="Verlichting"){
			equipmentTypeTwo = "Lighting"
		}
		if(equipment.bouwjaccu.text()!="")
		buildYearOfBattery = getDate.format(convertToDateEquipment(equipment.bouwjaccu.text().toString()))
		else
		buildYearOfBattery = getDate.format(new Date())
		log.debug "buidl year of battery for newly added equipment==="+buildYearOfBattery
		if(equipment.bouwjarm.text()!="")
		buildYearOfArmature = getDate.format(convertToDateEquipment(equipment.bouwjarm.text().toString()))
		else
		buildYearOfArmature =  getDate.format(new Date())
		log.debug "buidl year of battery for newly added equipment==="+buildYearOfArmature
		if(equipment.bouwjnood.text()!="")
		buildYearOfEmergencyUnit = getDate.format(convertToDateEquipment(equipment.bouwjnood.text().toString()))
		else
		buildYearOfEmergencyUnit = getDate.format(new Date())
		log.debug "buidl year of battery for newly added equipment==="+buildYearOfEmergencyUnit
		def floorFound = Floor.findById(floor.id.text().toLong())
		def brandfound = Brand.findById(equipment.merk.text().toLong())
		log.debug "brand id is=="+brandfound
		def armatuurfound = Armatuur.findById(equipment.typearmatuur.text().toLong())
		log.debug "found  armatuur"+armatuurfound
		def emergencyUnitOfPrintfound = EmergencyUnitOfPrint.findById(equipment.noodunitofprint.text().toLong())
		def lightfound = Light.findById(equipment.lamp.text().toLong())
		log.debug "light found is== "+lightfound
		def batteryfound = Battery.findById(equipment.accu.text().toLong())
		log.debug "battery found is=="+batteryfound
		def groupNo = GroupNr.findById(equipment.group.text().toLong())
		def kast = Kast.findById(equipment.kast.text().toLong())
		def ipadEquipment = new Equipment(createdBy:dbUser.username,name:equipment.name.text(),brand:brandfound,floor:floorFound,light:lightfound,armatuur:armatuurfound,battery:batteryfound,emergencyUnitOfPrint:emergencyUnitOfPrintfound,description:' new ',equipmentType2:equipmentTypeTwo,equipmentType:equipmentTypeOne,customer:customer.id,kast:kast,groupNo:groupNo,buildYearOfBattery:buildYearOfBattery,buildYearOfEmergencyUnit:buildYearOfEmergencyUnit,buildYearOfArmature:buildYearOfArmature)
		ipadEquipment.save()
		if(ipadEquipment.hasErrors()){
			ipadEquipment.errors.each { println it }
		}
		return ipadEquipment
	}
	def ipadEquipmentCheckpoints(equipment,ipadEquipment,reportCheckpoints,floor,reportId){
		def equipmentCheckpointStatus
		equipment.checkpoints.checkpoint.each{checkpoint->
			if(checkpoint.value.text()=="3"){
				equipmentCheckpointStatus = 'Afgekeurd'
				def equipmentOverallStatus='Afgekeurd'
				ipadEquipment.spec = checkpoint.specs.text()
			}
			else if(checkpoint.value.text()=="2"){
				equipmentCheckpointStatus = 'NVT'
			}
			else if(checkpoint.value.text()=="1"){
				equipmentCheckpointStatus = 'Goedgekeurd'
			}
			else if(checkpoint.value.text()=="4"){
				equipmentCheckpointStatus = 'Akkoord na herkeuring'
			}
			else if(checkpoint.value.text()==""){
				equipmentCheckpointStatus = ''
			}
			def equipmentCheckpointType='Functional'

			for(def reportCheckpoint in reportCheckpoints){
				if(reportCheckpoint.value == checkpoint.description.text()){
					equipmentCheckpointType=reportCheckpoint.category
				}
			}
			def equipmentCheckpoint = new EquipmentCheckpoint(equipment:ipadEquipment,comment:checkpoint.comment.text(),checkpointDescription:checkpoint.description.text(),status:equipmentCheckpointStatus,value:checkpoint.value.text(),priority:checkpoint.priority.text(),priorityDate:checkpoint.prioritydate.text(),floorId:floor.id.text(),checkpointType:equipmentCheckpointType,reportId:reportId)
			equipmentCheckpoint.save()
			log.debug "saved ipad equipment checkpoints"+"for equipment======"+ipadEquipment
			checkpoint.attachments.attachment.each{attachment->
				def equipmentImage = new EquipmentImage(imageName:attachment.id.text(),equipmentCheckpoint:equipmentCheckpoint)
				equipmentImage.save(flush:true)
				/*jsonObjectImageAdd = new JSONObject()
				jsonObjectImageAdd.put("id",equipmentImage.id)
				jsonObjectImageAdd.put("imageNameReceived",attachment.id.text())
				jsonArrayImageAdd.add(jsonObjectImageAdd)*/
			}
			if(equipmentCheckpoint.hasErrors()){
				equipmentCheckpoint.errors.each{ println it }
			}
		}
	}
void setMailToBeSent(buildingObject,buildingReport,building,customer){
	def sendMail = new SendSynchronisationMail(isSent:false,building:buildingObject,report:buildingReport,customer:customer)
	sendMail.save(flush:true)
	log.debug "saved send mail has value===="+sendMail
	building.report.each{report->		
		report.actionusers.actionuser.each{actionuser->
			def actionUser = new ActionUser(actionGroupName:actionuser.name.text().toString(),sendmail:sendMail)
			actionUser.save()
			if(actionUser.hasErrors()){
				actionUser.errors.each {
					println "error saving action users====="+it
				}
			}
		}
		report.notifyusers.notifyuser.each{notifyuser->
			def notifyUser = new NotifyUser(notifyGroupName:notifyuser.name.text().toString(),sendmail:sendMail)
			notifyUser.save()
			if(notifyUser.hasErrors()){
				notifyUser.errors.each {
					println "error saving notify  users====="+it
				}
			}
		}
	}
}
}