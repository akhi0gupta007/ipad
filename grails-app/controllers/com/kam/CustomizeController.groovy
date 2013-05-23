package com.kam

import org.apache.commons.io.IOUtils
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
/**
* @author Shridhar
*
*/
class CustomizeController {

    def index() { }
	
	def springSecurityService
	def imageService
	
	@Secured(['ROLE_ADMIN'])
	def customize = {
		def user=springSecurityService.currentUser
		def logo='' ,theme='', navigation='', links='', actionToSend='',backGround=''
		def customizeSettings = CustomizeSettings.findWhere(customer:user.customer)
		if(customizeSettings==null)
			actionToSend='saveCustomerSettings'    
		else{
			actionToSend='editCustomerSettings'    
			logo = customizeSettings.logo    
			theme = customizeSettings.theme    
			navigation = customizeSettings.navigation    
			links = customizeSettings.links
			backGround=customizeSettings.backGround
		}   
		[loggedinUser:user,role:UserRole.findWhere(user:user).role.authority,logo:logo,links:links,navigation:navigation,theme:theme,actionToSend:actionToSend,backGround:backGround]
	}
	
	@Secured(['ROLE_ADMIN'])
	def saveCustomerSettings = {
		def user=springSecurityService.currentUser
		def logo ,theme, navigation, links
		theme=params['customerTheme']
		logo=params['customerLogo']
		navigation=params['headerinput']
		links=params['textinput']
		def backGround = ''
		if(theme=='Image')
			backGround=params['themeImage'].toString().substring(params['themeImage'].toString().lastIndexOf('/')+1,params['themeImage'].toString().length())
		else if(theme=='Color')
			backGround=params['background']
		def customizeSettings = new CustomizeSettings(customer:user.customer,logo:logo,navigation:navigation,links:links,theme:theme,backGround:backGround)
		if(customizeSettings.save()){
			imageService.changeImageLocation(logo, grailsApplication.config.upload.image.path)
			
			if(theme=='Image')
			imageService.changeImageLocation(backGround, grailsApplication.config.upload.image.theme.path )
			redirect controller:'site', action:'index'
		}
		
	}
	
	def getCustomSettings = {
		def returnObject = [:]
		def user=springSecurityService.currentUser
		if(user!=null){
			def customizeSettings = CustomizeSettings.findWhere(customer:user.customer)
			if(customizeSettings!=null){
				if(customizeSettings.theme != null)
					returnObject.customerTheme=customizeSettings.theme
				if(customizeSettings.logo != null)
					returnObject.customerLogo=customizeSettings.logo
				if(customizeSettings.navigation != null)
					returnObject.customerNavigation=customizeSettings.navigation
				if(customizeSettings.links != null)
					returnObject.customerLinks=customizeSettings.links
				if(customizeSettings.backGround != null)
					returnObject.customerBackGround=customizeSettings.backGround
				render returnObject as JSON
			}
		}
		render returnObject as JSON
	}
	
	
	@Secured(['ROLE_ADMIN'])
	def editCustomerSettings = {
		def user=springSecurityService.currentUser
		def customizeSettings = CustomizeSettings.findWhere(customer:user.customer)
		if(customizeSettings!=null){
			customizeSettings.theme=params['customerTheme']
			customizeSettings.logo=params['customerLogo']
			customizeSettings.navigation=params['headerinput']
			customizeSettings.links=params['textinput']
			if(params['customerTheme']=='Image')
				customizeSettings.backGround=params['themeImage'].toString().substring(params['themeImage'].toString().lastIndexOf('/')+1,params['themeImage'].toString().length())
			else if(params['customerTheme']=='Color')
				customizeSettings.backGround=params['background']
			
			if(customizeSettings.save()){
				imageService.changeImageLocation(params['customerLogo'], grailsApplication.config.upload.image.path)
				
				if(params['customerTheme']=='Image')
				imageService.changeImageLocation(params['themeImage'], grailsApplication.config.upload.image.theme.path )
				redirect controller:'site', action:'index'
			}
		}
	}
	
	def imageThemeUploader={
		render template:'uploadThemeImage',model:[elementName:params.elementName]
	}
	
	def saveThemeImage={
		InputStream inputStream = null
		FileOutputStream fileOutputStream = null
		try {
		 def realPath = servletContext.getContext('/')
		 def filename = request.getHeader("X-File-Name")
		 if(filename!=null)
		 filename = filename.replaceAll('%20', '_')
		 if (filename == null){
		  filename = params['qqfile'].getFileItem().getName()
		  inputStream = params['qqfile'].getInputStream()
		 }else{
		  inputStream = request.getInputStream()
		 }
		 fileOutputStream = new FileOutputStream(new File(filename))
		 IOUtils.copy(inputStream, fileOutputStream)
		 imageService.changeImageLocation(filename, grailsApplication.config.upload.image.theme.path)
		 session.setAttribute("logoName",filename)
		 render "success"
		} catch (FileNotFoundException fileNotFoundException) {
		 response.setStatus(response.SC_INTERNAL_SERVER_ERROR)
		 render "failure"
		 
		} catch (IOException iOException) {
		 response.setStatus(response.SC_INTERNAL_SERVER_ERROR)
		 render "failure"
		 
		} finally {
		 try {
		  fileOutputStream.close()
		  inputStream.close()
		 } catch (IOException iOException) {
		 }
			  }

	}
	
	
}
