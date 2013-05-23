package com.kam

import static org.junit.Assert.*
import org.junit.*

class EquipmentTestTests {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testSomething() {
        
    }
	void addEquipment(){
		def customer = new Customer(name:"Samsung India",address:"850 gurgaon",city:"gurgaon",website:"www.nokia.com",contact:"0990541157",activatedby:"superuser",enabled:true)
		assertNotNull customer.save()
		def project = new Project(projectNumber:"KAMSamsung",projectName:'KAM007',projectTitle:'ipad app project',address:'22,faridabad',city:'faridabad',customer:customer)
		assertNotNull project.save()
		def building = new Building(buildingName:"ER",buildingNumber:'12',address:'fridabad',zipCode:'121005',city:'fariddabad',type:'floormap',project:project)
		assertNotNull building.save()
		def floor = new Floor(floorNumber:"11",floorDescription:'for floormap',floorMap:'/home/oodles/abc.jpg',building:building)
		assertNotNull floor.save()
		def equipment = new Equipment(name:'light',description:'bulb',floor:floor)
		assertNotNull equipment.save()
		def armatuur = new Armatuur(armatuurType:'7403N',equipment:equipment)
		assertNotNull armatuur.save()
		/*def light = new Light(name:'1xFL8W/830')
		assertNotNull light.save()
		def emergencyUnitofPrint = new EmergencyUnitOfPrint(unitName:'350715V')
		assertNotNull emergencyUnitofPrint.save()
		def battery = new Battery(batteryType:'van lien 119017V')
		assertNotNull battery.save()*/
		
	}
	
	
}
