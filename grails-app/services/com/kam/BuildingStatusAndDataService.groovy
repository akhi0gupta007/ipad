package com.kam

class BuildingStatusAndDataService {
def checkAddEquipmentStatus(def equipmentadded,def formattedLastsynctime){
	for(equipmentitem in equipmentadded){
		if(equipmentitem.dateCreated > formattedLastsynctime){
			if(equipmentitem.list().size!=0){
				log.debug "that added equipemnt is=="+equipmentitem
				return true
			}
		}
	}
}

def checkRemoveEquipmentStatus(def equipmentremoved,def formattedLastsynctime){
	for(equipmentitem in equipmentremoved){
		if(equipmentitem.lastUpdated > formattedLastsynctime){
			if(equipmentitem.list().size!=0){
				log.debug "that  equipemnt deleted is=="+equipmentitem
				return true
			}
		}
	}
	
}
def checkAddFloorStatus(def flooradded,def formattedLastsynctime){
	for(flooritem in flooradded){
		if(flooritem.dateCreated > formattedLastsynctime){
			if(flooritem.list().size!=0){
				log.debug "floor item removed is=="+flooritem
				return true
			}
		}
	}
}
def checkRemoveFloorStatus(def floorremoved,def formattedLastsynctime){
	log.debug "reached remove floor tag in building status and data service=========="
	for(flooritem in floorremoved){
		log.debug "under for loopp of floorremoved tag"
		if(flooritem.lastUpdated > formattedLastsynctime){
			if(flooritem.list().size!=0){
				log.debug "floor item removed is=="+flooritem
				return true
			}
		}
	}
}
def checkUpdateEquipmentStatus(equipmentadded,formattedLastsynctime){
	for(equipmentitem in equipmentadded){
		if(equipmentitem.lastUpdated > formattedLastsynctime){
			if(equipmentitem.list().size!=0){
				log.debug "that updated equipemnt is=="+equipmentitem
				return true
			}
		}
	}
}
}
