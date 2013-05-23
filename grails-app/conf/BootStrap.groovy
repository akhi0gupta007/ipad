import java.util.jar.Attributes.Name;

import com.kam.*

class BootStrap {

    def init = { servletContext ->
        if (Role.list().size() == 0) {
            //init script
            new Role(authority:"ROLE_CUSTOMER").save();
            new Role(authority:"ROLE_ADMIN").save();
            new Role(authority:"ROLE_USER").save();
            new Role(authority:"ROLE_SUPERUSER").save();
        }
        if (User.list().size() == 0) {
            def superuser=new User(firstName:'superuser',lastName:'superuser',email:'email',username:'superuser',password:'marcel123',accountLocked: false,enabled: true,accountExpired:false,passwordExpired:false,createdBy:'');
            superuser.save()
            def role=new UserRole(user:superuser,role:Role.findWhere(authority:'ROLE_SUPERUSER')).save()
        }
		
		if(Brand.list().size()==0){
			def brand = new Brand(brandName:"NVT")
			brand.save()
			def battery =new Battery(batteryType:"NVT")
			battery.save()
			def light = new Light(name:"NVT")
			light.save()
			def emergencyUnitofPrint = new EmergencyUnitOfPrint(unitName:"NVT")
			emergencyUnitofPrint.save()
			def armatuur =  new Armatuur(armatuurType:"NVT")
			armatuur.save()
			def groupNr =  new GroupNr(groupNumber:"NVT")
			groupNr.save()
			def Kast =  new Kast(kastName:"NVT")
			Kast.save()
		}
		
		if(Disciplines.list().size()==0){
			def discipline1 = new Disciplines(disciplineName:'Electrotechniek').save()
			def discipline2 = new Disciplines(disciplineName:'Regeltechniek').save()
			def discipline3 = new Disciplines(disciplineName:'Bouwkundig').save()
			def discipline4 = new Disciplines(disciplineName:'Werktuigbouw').save()
		}
		
        def destroy = {
        }
    }
}