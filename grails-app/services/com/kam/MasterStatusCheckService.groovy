package com.kam

class MasterStatusCheckService {

	def checkAddBrandStatus(def brandadded,def formattedLastsynctime){
		for(branditem in brandadded){
			if(branditem.dateCreated > formattedLastsynctime){
				log.debug "brand item added is=="+branditem
				if(branditem.list().size!=0){
					return true
				}
			}
		}
	}
	def checkRemoveBrandStatus(def brandremoved,def formattedLastsynctime){
		for(branditem in brandremoved){
			if(branditem.lastUpdated > formattedLastsynctime){
				log.debug "brand removed is="+branditem
				if(branditem.list().size!=0){
					return true
				}
			}
		}
	}
	def checkUpdateBrandStatus(def brandupdated,def formattedLastsynctime){
		for(branditem in brandupdated){
			if(branditem.lastUpdated > formattedLastsynctime&&branditem.dateCreated<branditem.lastUpdated){
				if(branditem.list().size!=0){
					log.debug "brand item updated is=="+branditem
					return true
				}
			}
		}
	}
	def checkAddArmatureStatus(def armatuuradded,def formattedLastsynctime){
		for(armatuuritem in armatuuradded){
					if(armatuuritem.dateCreated > formattedLastsynctime){
						if(armatuuritem.list().size!=0){
							log.debug "armatuuritem added is=="+armatuuritem
							return true
						}
					}
				}
			}
	
	def checkRemoveArmatureStatus(def armatuurremoved,def formattedLastsynctime){
		for(armatuuritem in armatuurremoved){
			if(armatuuritem.lastUpdated > formattedLastsynctime){
				if(armatuuritem.list().size!=0){
					log.debug "armatuuritem removed is="+armatuuritem
					return true
				}
			}
		}
	}
	
	def checkUpdateArmatureStatus(def armatuurupdated,def formattedLastsynctime){
		for(armatuuritem in armatuurupdated){
			if(armatuuritem.lastUpdated > formattedLastsynctime&&armatuuritem.dateCreated<armatuuritem.lastUpdated){
				if(armatuuritem.list().size!=0){
					log.debug "armatuur item updated is"+armatuuritem
					return true
				}
			}
		}
	}
	def checkAddNoodStatus(def noodunitadded,def formattedLastsynctime){
		for(noodunititem in noodunitadded){
			if(noodunititem.dateCreated > formattedLastsynctime){
				log.debug "nood unit added is=="+noodunititem
				if(noodunititem.list().size!=0){
					return true
				}
			}
		}
	}
	def checkRemoveNoodStatus(def noodunitremoved,def formattedLastsynctime){
		for(noodunititem in noodunitremoved){
					if(noodunititem.lastUpdated > formattedLastsynctime){
						log.debug "nood unit added is=="+noodunititem
						if(noodunititem.list().size!=0){
							return true
						}
					}
				}
	}
	def checkUpdateNoodStatus(def noodunitupdated,def formattedLastsynctime){
		for(noodunititem in noodunitupdated){
					if(noodunititem.lastUpdated > formattedLastsynctime&&noodunititem.dateCreated<noodunititem.lastUpdated){
						if(noodunititem.list().size!=0){
							log.debug "nood unit updated is=="+noodunititem
							return true
						}
					}
				}
				}
	def checkAddLightStatus(def lightadded,def formattedLastsynctime){
		for(lightitem in lightadded){
			if(lightitem.dateCreated > formattedLastsynctime){
				log.debug "light item added is="+lightitem
				if(lightitem.list().size!=0){
					return true
				}
			}
		}
	}
	def checkRemoveLightStatus(def lightremoved,def formattedLastsynctime){
		for(lightitem in lightremoved){
			if(lightitem.lastUpdated > formattedLastsynctime){
				log.debug "light item removed is="+lightitem
				if(lightitem.list().size!=0){
					return true
				}
			}
		}
	}
	def checkUpdateLightStatus(def lightupdated,def formattedLastsynctime){
		for(lightitem in lightupdated){
			if(lightitem.lastUpdated > formattedLastsynctime&&lightitem.dateCreated<lightitem.lastUpdated){
				log.debug "light item updated is="+lightitem
				if(lightitem.list().size!=0){
					return true
				}
			}
		}
	}
	def  checkAddBatteryStatus(def batteryadded,def formattedLastsynctime){
		for(batteryitem in batteryadded){
			if(batteryitem.dateCreated > formattedLastsynctime){
				log.debug "battery item added is"+batteryitem
				if(batteryitem.list().size!=0){
					return true
				}
			}
		}
	}
	def checkRemoveBatteryStatus(def batteryremoved,def formattedLastsynctime){
		for(batteryitem in batteryremoved){
			if(batteryitem.lastUpdated > formattedLastsynctime){
				log.debug "battery item removed is"+batteryitem
				if(batteryitem.list().size!=0){
					return true//print writer.toString()
				}
			}
		}
	}
	def checkUpdateBatteryStatus(def batteryupdated,def formattedLastsynctime){
		for(batteryitem in batteryupdated){
			if(batteryitem.lastUpdated > formattedLastsynctime&&batteryitem.dateCreated<batteryitem.lastUpdated){
				log.debug "battery item updated is"+batteryitem
				if(batteryitem.list().size!=0){
					return true//print writer.toString()
				}
			}
		}
	}
	def checkAddGroupStatus(def groupAdded,def formattedLastsynctime){
		for(groupItem in groupAdded){
			if(groupItem.dateCreated > formattedLastsynctime){
				log.debug "group item added is"+groupItem
				if(groupItem.list().size!=0){
					return true
				}
			}
		}
	}
	def checkRemoveGroupStatus(def groupRemoved,def formattedLastsynctime){
		for(groupItem in groupRemoved){
			if(groupItem.lastUpdated > formattedLastsynctime){
				log.debug "group item added is"+groupItem
				if(groupItem.list().size!=0){
					return true
				}
			}
		}
	}
	def checkUpdateGroupStatus(def groupUpdated,def formattedLastsynctime){
		for(groupItem in groupUpdated){
			if(groupItem.lastUpdated > formattedLastsynctime&&groupItem.dateCreated<groupItem.lastUpdated){
				log.debug "group item updated is=="+groupItem
				if(groupItem.list().size!=0){
					return true//print writer.toString()
				}
			}
		}
	}
	def checkAddKastStatus(def kastAdded,def formattedLastsynctime){
		for(kastItem in kastAdded){
			if(kastItem.dateCreated > formattedLastsynctime){
				log.debug "kast item added is"+kastItem
				if(kastItem.list().size!=0){
					return true
				}
			}
		}
	}
	def checkRemoveKastStatus(def kastRemoved,def formattedLastsynctime){
		for(kastItem in kastRemoved){
			if(kastItem.lastUpdated > formattedLastsynctime){
				log.debug "kast item removed is"+kastItem
				if(kastItem.list().size!=0){
					return true
				}
			}
		}
	}
	def checkUpdateKastStatus(def kastUpdated,def formattedLastsynctime){
		for(kastItem in kastUpdated){
			if(kastItem.lastUpdated > formattedLastsynctime&&kastItem.dateCreated<kastItem.lastUpdated){
				log.debug "kast item updated is=="+kastItem
				if(kastItem.list().size!=0){
					return true//print writer.toString()
				}
			}
		}
	}
	def checkAddCheckpointStatus(def checkpointAdded,def formattedLastsynctime){
		log.debug "in master status service=====checkpoints added are==="+checkpointAdded
		for(checkpointItem in checkpointAdded){
			if(checkpointItem.dateCreated > formattedLastsynctime){
				log.debug "checkpoint item added is"+checkpointItem
				if(checkpointItem.list().size!=0){
					return true
				}
			}
		}
	}
	def checkRemoveCheckpointStatus(def checkpointRemoved,def formattedLastsynctime){
		for(checkpointItem in checkpointRemoved){
			if(checkpointItem.lastUpdated > formattedLastsynctime){
				log.debug "checkpoint item added is"+checkpointItem
				if(checkpointItem.list().size!=0){
					return true
				}
			}
		}
	}
	def checkUpdateCheckpointStatus(def checkpointUpdated,def formattedLastsynctime){
		for(checkpointItem in checkpointUpdated){
			if(checkpointItem.lastUpdated > formattedLastsynctime&&checkpointItem.dateCreated<checkpointItem.lastUpdated){
				log.debug "checkpoint item updated is=="+checkpointItem
				if(checkpointItem.list().size!=0){
					return true//print writer.toString()
				}
			}
		}
	}
}

