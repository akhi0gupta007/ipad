package com.kam

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.commons.ConfigurationHolder


import org.springframework.web.multipart.MultipartHttpServletRequest


@Secured(['IS_AUTHENTICATED_REMEMBERED'])

class ExcelController {

    def springSecurityService
    def xlsService
    ConfigObject holder = ConfigurationHolder.getConfig();
    def systemPath = holder.getProperty("systemPath")
    def webPath = holder.getProperty("webPath")

    def index() {
        def user=springSecurityService.currentUser
        render view:'createUsers',model:[loggedinUser:user,role:UserRole.findWhere(user:user).role.authority]
    }

    def handle(){
        def user=springSecurityService.currentUser

        if(request instanceof MultipartHttpServletRequest && user){

            def mhsr = request.getFile('xls')
            String date = new Date().toString().replaceAll(" ","_")
            date = date.replaceAll(":","_")
            String message = ""
            try{
                File file = new File(webPath)
                if (file.exists() && file.isDirectory()){
                    log.debug("Directory Already Exist")
                }else{
                    log.debug(file.mkdirs())
                }
                def fileName = "doc_${user.username}_${date}.xls"

                def dir = webPath + fileName
                def sys = systemPath + fileName


                if (!mhsr?.empty && mhsr.size) {
                    log.debug("Saving in File ................"+dir)
                    log.warn("Saving in file................"+dir)
                    mhsr.transferTo(
                            new File(
                                    dir
                            )
                    )
                }

                List data = xlsService.fetchMe(sys)
                int b,a,br,l,e = 0,g=0,k=0
                String eol = System.getProperty("line.separator");
                //  flash.message = "Master Table Updated:\n"+eol+"${b} Battery Added,${a} Battery Added,${l} Light Added,${br} Brands Added,${e} Emergency Unit of Print is added"

                try{
                    if (data){
                        for (x in data){

                            List real = x
                            for(int i=0;i<real.size();i++){

                                if (real[i].toString().length() <= 0){
                                    log.warn("B L A N K Data .........................!!!!!!!")
                                } else{
                                    switch(i){
                                        case 0:
                                            log.debug("Updating Battery")
                                            def old = Battery.findAllByBatteryTypeLike(real[i].toString())
                                            if (!old)     {
                                                new Battery(batteryType:real[i].toString()).save(flush: true)
                                                ++b
                                            }
                                            break
                                        case 1:
                                            log.debug("Updating Armatuur")
                                            def old = Armatuur.findAllByArmatuurTypeLike(real[i].toString())
                                            if (!old)    {
                                                new Armatuur(armatuurType:real[i].toString()).save(flush: true)
                                                ++a
                                            }
                                            break
                                        case 2:
                                            log.debug("Updating Brand")
                                            def old = Brand.findAllByBrandNameLike(real[i].toString())
                                            if (!old)                                                   {
                                                new Brand(brandName:real[i].toString()).save(flush: true)
                                                ++br
                                            }
                                            break
                                        case 3:
                                            log.debug("Upating Emergencyunitofprint")
                                            def old = EmergencyUnitOfPrint.findAllByUnitName(real[i].toString())
                                            if (!old) {
                                                new EmergencyUnitOfPrint(unitName:real[i].toString()).save(flush: true)
                                                ++e
                                            }
                                            break
                                        case 4:
                                            log.debug("LIght....")
                                            def old = Light.findAllByName(real[i].toString())
                                            if (!old) {
                                                new Light(name:real[i].toString()).save(flush: true)
                                                ++l
                                            }
										case 5:
											log.debug("Updating Group nr ....")
											def old = GroupNr.findAllByGroupNumber(real[i].toString())
											if (!old) {
												new GroupNr(groupNumber:real[i].toString()).save(flush: true)
												++g
											}
										case 6:
											log.debug("Updating Kast....")
											def old = Kast.findAllByKastName(real[i].toString())
											if (!old) {
												new Kast(kastName:real[i].toString()).save(flush: true)
												++k
											}


                                    }

                                }
                            }

                        }
                        message+=eol
                        if (b>0)
                            message+="${b} Battery Added,"
                        if (a>0)
                            message+="${a} Armatuur Added,"
                        if (l>0)
                            message+="${l} Light Added,"
                        if (br>0)
                            message+="${br} Brands Added,"
                        if (e>0)
                            message+="${e} Emergency Unit of Print is added."
						if (g>0)
							message+="${e} Emergency Unit of Print is added."
						if (k>0)
							message+="${e} Emergency Unit of Print is added."
                        if (b==0 & l==0 & br==0 & a==0 & e ==0  & g ==0  & k ==0)
                        {
                            message+="Nothing To Update"
                        }


                        def excel = new Excel(user: user.username,file:fileName)
                        if (excel.validate()){
                            excel.save(flush: true)
                        }else{
                            log.error("Could Not Save File...")
                        }
                    }
                }catch(Exception exp){
                     log.error("COuld Not Process")
                     message+="Wrong File Given"
                }




               // render "True: ${params}"

            }catch(Exception ex){
                log.error("Unexpected Error While Saving")
                message+="Wrong File Given"
            }
            redirect(action: "index")
            flash.message = message

        }
    }
}
