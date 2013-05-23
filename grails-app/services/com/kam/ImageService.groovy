package com.kam

import org.apache.commons.io.FileUtils;
import org.codehaus.groovy.grails.commons.GrailsApplication;

class ImageService {

    def serviceMethod() {

    }
	
	def changeImageLocation(def fileName,path){
		try{
			def logo=new File(fileName)
			def logoLocation=new File(path+fileName)
			FileUtils f=new FileUtils();
			f.copyFile(logo,logoLocation );
			logo.delete()
			}
			catch(Exception ex){}
	}
}
