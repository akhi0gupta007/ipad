 package com.kam

import java.util.Date
import grails.converters.JSON
import grails.plugin.asyncmail.AsynchronousMailService
import grails.plugins.springsecurity.Secured
import org.apache.commons.io.IOUtils;
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JFrame;
import com.sun.imageio.plugins.png.*
/**
 * @author Shridhar
 *
 */
@Secured(['IS_AUTHENTICATED_REMEMBERED'])
class SiteController {

	def springSecurityService
	def mailService
	
	AsynchronousMailService asynchronousMailService

	def index() {
		if(SpringSecurityUtils.ifAnyGranted("ROLE_SUPERUSER"))
			redirect action:"customers"
		else if(SpringSecurityUtils.ifAnyGranted("ROLE_ADMIN"))
			redirect action:"users"
		else if(SpringSecurityUtils.ifAnyGranted("ROLE_USER")){
			if(springSecurityService.currentUser.customer.flow=='document')
				redirect controller:'document',action:'documentList'
			if(springSecurityService.currentUser.customer.flow=='equipment')
				redirect controller:'equipment',action:'equipmentList'
		}
	}

	/**
	 * Displays the list of customers to superuser
	 */
	def customers={
		if(SpringSecurityUtils.ifAnyGranted("ROLE_SUPERUSER")){
			def startIndex=params["offset"]
			def customerlist=Customer.list([offset:startIndex?:0,max:10])
			def totalSize=Customer.list().size()
			def user=springSecurityService.currentUser
			if(params.id=="paginate")
			render template:'customerUserList',model:[customerlist:customerlist,loggedinUser:user,role:UserRole.findWhere(user:user).role.authority,totalSize:totalSize]
			else
			render view:'customers',model:[customerlist:customerlist,loggedinUser:user,role:UserRole.findWhere(user:user).role.authority,totalSize:totalSize]
		}
		else if(SpringSecurityUtils.ifAnyGranted("ROLE_ADMIN"))
			redirect action: "users"
		else if(SpringSecurityUtils.ifAnyGranted("ROLE_USER")){
			if(springSecurityService.currentUser.customer.flow=='document')
				redirect controller:'document',action:'documentList'
			if(springSecurityService.currentUser.customer.flow=='equipment')
				redirect controller:'equipment',action:'equipmentList'
		}
	}
	def createCustomer={
		def user=springSecurityService.currentUser
		render view:'createCustomer',model:[loggedinUser:user,role:UserRole.findWhere(user:user).role.authority]
		
	}
	def createAdmin={
		if(params.customer != null)
		session.setAttribute("customer", params.customer)
		def user=springSecurityService.currentUser
		[customername:session.getAttribute("customer"),loggedinUser:user,role:UserRole.findWhere(user:user).role.authority]
	}
	
	def createUser={
		def user=springSecurityService.currentUser
		render view:'createUsers',model:[loggedinUser:user,role:UserRole.findWhere(user:user).role.authority]
	}

	def uploadImage={
		InputStream inputStream = null
		FileOutputStream fileOutputStream = null
		try {
		 def realPath = servletContext.getContext('/')
		 def filename = request.getHeader("X-File-Name")
		 if(filename!=null){
			 filename = filename.replaceAll('%20', '_')
			 filename = filename.replaceAll('%25', '_')
		 }
		 if (filename == null){
		  filename = params['qqfile'].getFileItem().getName()
		  inputStream = params['qqfile'].getInputStream()
		 }else{ 
		  inputStream = request.getInputStream()
		 }	 
		 BufferedImage i = ImageIO.read(inputStream)
		 compressAndShow(i, 0.5f,filename)
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
		  inputStream.close()
		 } catch (IOException iOException) {
		 }
			  }

	}
	
	def compressAndShow(def image, float quality, def filename){
		if(!filename.endsWith('.gif') && !filename.endsWith('.GIF') && !filename.endsWith('.png') && !filename.endsWith('.PNG')){
			Iterator<ImageWriter> writers
			writers = ImageIO.getImageWritersBySuffix("jpeg")
			if (!writers.hasNext()) throw new IllegalStateException("No writers found")
			ImageWriter writer = (ImageWriter) writers.next();
			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(quality);
			File file = new File(filename)
			FileImageOutputStream output = new FileImageOutputStream(file)
			writer.setOutput(output) 
			writer.write(null, new IIOImage(image, null,null), param)
			writer.dispose()
			output.flush()
			output.close()
		}
		else{
			File file = new File(filename)
			if(filename.endsWith('.gif') || !filename.endsWith('.GIF'))
				ImageIO.write(image, "gif", file)
			else if(filename.endsWith('.png') || filename.endsWith('.PNG'))
				ImageIO.write(image, "png", file)
		}			
	}
	/**
	 * Allows superuser to create new customer
	 */
	def saveCustomer={
		params.enabled=true;
		def loggedinUser=springSecurityService.currentUser
		def customer=new Customer(name:params.name, address:params.address, city:params.city, website:params.website, contact:params.contact,enabled:true,activatedby:springSecurityService.currentUser.firstName,isDeleted:false,flow:params.customerFlow)
		if(customer.save())
			redirect action:'customers', model:[customer:customer,from:'customers',loggedinUser:loggedinUser,role:UserRole.findWhere(user:loggedinUser).role.authority]
		else
			redirect action:'createCustomer', model:[domainError:customer,loggedinUser:loggedinUser,role:UserRole.findWhere(user:loggedinUser).role.authority]
	}

	/**
	 * Displays the list of admin users for a particular customer to superuser
	 */
	def admin={
		if(SpringSecurityUtils.ifAnyGranted("ROLE_SUPERUSER")){
			def loggedinUser=springSecurityService.currentUser
			def startIndex=params["offset"]
			if(params.customer != null)
				session.setAttribute("customer", params.customer)
			def userlist=User.findAllWhere(customer:Customer.findWhere(name:session.getAttribute("customer")),isDeleted:false,[offset:startIndex?:0,max:10])
			def totalSize=User.findAllWhere(customer:Customer.findWhere(name:session.getAttribute("customer")),isDeleted:false).size()
			if(params.id=="paginate")
			render template:'userList',model:[userlist:userlist,customername:session.getAttribute("customer"),loggedinUser:loggedinUser,role:UserRole.findWhere(user:loggedinUser).role.authority,totalSize:totalSize,actionToSend:'admin']
			else 
			[userlist:userlist,customername:session.getAttribute("customer"),loggedinUser:loggedinUser,role:UserRole.findWhere(user:loggedinUser).role.authority,totalSize:totalSize]
		}
		else
			redirect action: "users"
	}

	/**
	 * Super user can create admin users for a particular customer
	 */
	def saveAdmin={
		def loggedinUser=springSecurityService.currentUser
		def customer=Customer.findWhere(name:params.customername)
		def user=new User(firstName:params.firstName,middleName:params.middleName,lastName:params.lastName,mobile:params.mobile,email:params.email,username:params.username,password:UUID.randomUUID().toString().replaceAll('-', '').substring(0,8),enabled: true,customer:customer,createdBy:loggedinUser.username,isDeleted:false)
		if(user.save()) {
			def role=new UserRole(user:user,role:Role.findWhere(authority:'ROLE_'+params.role))
			if(role.save()) {
				sendInvitationMail(user, 'adminRegister')
				if(params.role=='ADMIN')
				redirect action:'customers'
				else{
					session.setAttribute("customer", params.customername)
					redirect action:'admin', model:[customer:params.customername]
				}
			}
		}
		else{
			session.setAttribute("customer", params.customername)
			redirect action:"createAdmin", model:[domainError:user,customername:params.customername,loggedinUser:loggedinUser]
		}
	}

	/**
	 * Displays a list of users for a customer to the admin user
	 */
	def users={
		if(SpringSecurityUtils.ifAnyGranted("ROLE_ADMIN")){
			def loggedinUser=springSecurityService.currentUser
			def startIndex=params["offset"]
			def customername=loggedinUser.customer.name
			def userlist=User.findAllByCustomerAndIsDeleted(Customer.findWhere(name:customername),false,[offset:startIndex?:0,max:10])
			def totalSize=User.findAllWhere(customer:Customer.findWhere(name:customername),isDeleted:false).size()
			if(params.id=="paginate")
			render template:'userList',model:[userlist:userlist,loggedinUser:loggedinUser,role:UserRole.findWhere(user:loggedinUser).role.authority,totalSize:totalSize,actionToSend:'users']
			else
			render view:'users',model:[userlist:userlist,loggedinUser:loggedinUser,role:UserRole.findWhere(user:loggedinUser).role.authority,totalSize:totalSize]
		}
		else if(SpringSecurityUtils.ifAnyGranted("ROLE_SUPERUSER"))
			redirect action:"customers"
	}

	/**
	 * Admin user of a particular customer can  create users for the customer
	 */
	def saveUser={
		if(SpringSecurityUtils.ifAnyGranted("ROLE_ADMIN")){
			def loggedinUser=springSecurityService.currentUser
			def customername=loggedinUser.customer.name
			
			def user=new User(firstName:params.firstName,middleName:params.middleName,lastName:params.lastName,mobile:params.mobile,email:params.email,username:params.username,password:UUID.randomUUID().toString().replaceAll('-', '').substring(0,8),accountLocked: false,enabled: true,accountExpired:false,passwordExpired:false,customer:Customer.findWhere(name:customername),createdBy:loggedinUser.username,isDeleted:false);
			if(user.save()) {
				def role=new UserRole(user:user,role:Role.findWhere(authority:'ROLE_'+params.role)).save()
				sendInvitationMail(user,'userRegister')
				redirect action:'users', model:[user:user,from:'users']
			}
			else
				redirect action:'createUsers', model:[domainError:user,loggedinUser:loggedinUser,role:UserRole.findWhere(user:loggedinUser).role.authority]
		}
	}
	
	def sendInvitationMail(def user,def actionToSend){
		def email=params.email
		def loggedinUser=springSecurityService.currentUser
		def memberEmail=new MemberEmails(email:email)
		memberEmail.save()
		def invitationCode = new InvitationCode(adminName:loggedinUser.username,username:params.username,email:email).save()
		
		def invitationUrl=createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",controller: 'register', action: actionToSend,id:invitationCode.token)
		def nameUser=params.firstName+' '
		if(params.middleName!=null)
		nameUser=nameUser+params.middleName+' '
		nameUser+=params.lastName
		def emailmessage=message(code:'superuser.createAdmin.email.message',args:[nameUser,params.username,invitationUrl,user.customer.name])
		try{
		asynchronousMailService.sendMail {
			to email
			from user.customer.website.replace("www.","noreply@")
			subject message(code:'email.subject',args:[user.customer.name])
			html emailmessage
		}
		}
		catch(Exception ex)
		{
			log.debug "Invitation URL is "+invitationUrl
		}		
	}

	/**
	 * Enable a particular account that is already disabled
	 */
	def enableAccount={
		def returnObject = [ : ]
		if(params.username != null)
		session.setAttribute("username", params.username)
		def user=User.findByUsername(session.getAttribute("username"))
		user.enabled=true
		if(user.save()){
			returnObject.sucess=true
			render returnObject as JSON
		}
		else{
			returnObject.sucess=false
			render returnObject as JSON
		}
	}

	/**
	 * Disable a particular account that is already enabled
	 */
	def disableAccount={
		def returnObject = [ : ]
		if(params.username != null)
		session.setAttribute("username", params.username)
		def user=User.findByUsername(session.getAttribute("username"))
		user.enabled=false
		if(user.save()){		
			returnObject.sucess=true
			render returnObject as JSON
		}
		else{
			returnObject.sucess=false
			render returnObject as JSON
		}
	}


	/**
	 * To Delete the entire customer and its users
	 */
	def deleteCustomer={
		def returnObject = [ : ]
		def customer=Customer.get(params.id)
		def documentList=Document.findAllWhere(customer:customer)
		//TODO: deletion should not be in loop
		for(def document in documentList){
			def project = document.project
			
			for (def building in project.buildings){
				for (def floor in building.floors){
					for (def room in floor.rooms){
						room.documents.remove(document)
					}
					floor.documents.remove(document)
				}
				building.documents.remove(document)
			}
			document.delete()
		} 
		def customerUsers=User.findAllWhere(customer:customer)  
		for(def user in customerUsers){
			def role=UserRole.findWhere(user:user)
			role.delete()
			user.delete()
		}
		def customerSettings = CustomizeSettings.findWhere(customer:customer)
		if(customerSettings!=null)
		customerSettings.delete()   
		customer.isDeleted=true
		
			returnObject.sucess=true
			render returnObject as JSON
	}
	
	
	
	def deleteUser={
		def returnObject = [ : ]
		def user=User.get(params.id)
		user.isDeleted=true
		UserRole.findWhere(user:user).delete()
		def groups = EmailGroup.findAllWhere(customer:user.customer)
		for(def group in groups){
			group.members.remove(user)
		}
		
		user.delete()
			returnObject.sucess=true
			render returnObject as JSON
	}
	
	/**
	 * To enable a customer account which is disabled
	 */
	def enableCustomer={
		def returnObject = [ : ]
		if(params.customer != null)
		session.setAttribute("customer", params.customer)
		def customer=Customer.findWhere(name:session.getAttribute("customer"))
		customer.enabled=true
		if(customer.save()){
			returnObject.sucess=true
			render returnObject as JSON
		}
		else{
			returnObject.sucess=false
			render returnObject as JSON
		}
	}
	
	/**
	 * To disable a customer and all its user account which is enabled
	 */
	def disableCustomer={
		def returnObject = [ : ]
		if(params.customer != null)
		session.setAttribute("customer", params.customer)
		def customer=Customer.findWhere(name:params.customer)
		customer.enabled=false
		User.executeUpdate("update User set enabled=false where customer_id='"+customer.id+"'")
		if(customer.save()){
			returnObject.sucess=true
			render returnObject as JSON
		}
		else{
			returnObject.sucess=false
			render returnObject as JSON
		}
	}
	
	
	def editUsers={
		if(params.username != null)
		session.setAttribute("username", params.username)
		def user=User.findWhere(username:session.getAttribute("username"))
		def loggedinUser=springSecurityService.currentUser
		render view:'editUsers',model:[loggedinUser:loggedinUser,role:UserRole.findWhere(user:loggedinUser).role.authority,firstName:user.firstName,middleName:user.middleName,lastName:user.lastName,username:user.username,email:user.email,mobile:user.mobile,customer:user.customer.name,forwardTo:params.forwardTo]
	}
	
	def editUserPassword={
		if(params.username != null)
		session.setAttribute("username", params.username)
		def user=User.findWhere(username:session.getAttribute("username"))
		def loggedinUser=springSecurityService.currentUser
		[loggedinUser:loggedinUser,role:UserRole.findWhere(user:loggedinUser).role.authority,firstName:user.firstName,middleName:user.middleName,lastName:user.lastName,username:user.username,email:user.email,mobile:user.mobile,customer:user.customer.name,forwardTo:params.forwardTo]
	}
	
	def updateUsers={
		def user=User.findWhere(username:params.oldUsername)
		user.firstName=params.firstName
		user.lastName=params.lastName
		user.middleName=params.middleName
		user.username=params.username
		user.email=params.email
		user.mobile=params.mobile
		if(params.password!=null && params.password!='')		
		user.password=params.password
		if(user.save())
			redirect controller:'site', action:''
		else
		{
			def forwardTo='editUsers'
			if(params.forwardTo!=null)
			forwardTo=params.forwardTo
			redirect controller:'site', action:forwardTo
		}
	}
	
	
	def editCustomer={
		def loggedinUser=springSecurityService.currentUser
		if(params.customer != null)
		session.setAttribute("customer", params.customer)
		def customer=Customer.findWhere(name:session.getAttribute("customer"))
		render view:'editCustomer',model:[loggedinUser:loggedinUser,role:UserRole.findWhere(user:loggedinUser).role.authority,name:customer.name,address:customer.address,city:customer.city,website:customer.website,contact:customer.contact,flow:customer.flow]
	}
	
	def updateCustomer={
		
		def customer=Customer.findWhere(name:params.oldCustomer)
		customer.name=params.name
		customer.address=params.address
		customer.city=params.city
		customer.website=params.website
		customer.contact=params.contact
		customer.flow=params.customerFlow
		if(customer.save())
		redirect controller:'site', action:'customers'
		else
		redirect controller:'site', action:'editUsers',params:[customer:params.oldCustomer]
	}
	
	def emailGroup={
		def user=springSecurityService.currentUser
		def userList
		userList=user.findAllWhere(customer:user.customer,isDeleted:false)
		render view:'emailGroup',model:[loggedinUser:user,role:UserRole.findWhere(user:user).role.authority,userList:userList]
		
		}
	def editGroup={
		def user=springSecurityService.currentUser
		def group, userList
		group=EmailGroup.get(params.id.toLong())
		userList=User.findAllWhere(customer:user.customer,isDeleted:false) 
		render view:'editGroup',model:[loggedinUser:user,role:UserRole.findWhere(user:user).role.authority,userList:userList,oldGroup:group]
	}
	
	def saveEmailGroup={
		def loggedInUser=springSecurityService.currentUser
		def newGroup
		def forwardAction
		String[] groupUsers=[]
		if(params['emailGroupList']!=null && params['emailGroupList']!='null'){
			newGroup= new EmailGroup(groupName:params['groupName'],activatedBy:loggedInUser.username,customer:loggedInUser.customer,groupType:params['groupType']).save()
			groupUsers=params['emailGroupList'].toString().replace(' ','').replace('[','').replace(']','').split(',')
			int startIndex=groupUsers.length
			forwardAction='groupList'
			for(int i=0;i<groupUsers.length;i++){    
				if(groupUsers[i]!='null' && groupUsers[i]!=''){ 
					def memberEmail=getEmailMember(groupUsers[i])
					newGroup.addToMembers(memberEmail)  
				}   
			}
		}      
		else{    
			redirect controller:'site', action:'emailGroup'
			flash.error=message(code:'messages.group.members.must')
			return
		}
		newGroup.save()
		redirect controller:'site', action:forwardAction
	}
 
	def editEmailGroup={
		def loggedInUser=springSecurityService.currentUser
		def group=EmailGroup.get(params.oldGroup)
		String[] groupUsers
		groupUsers=params['emailGroupList'].toString().replace(' ','').replace('[','').replace(']','').split(',')
		def userMembers=group.members
		def deletedMembers= new ArrayList()
		def newMembers= new ArrayList()
		int j=0
		if(params['emailGroupList']=='null' || params['emailGroupList']==null)
		group.members.removeAll(group.members)
		else{
			for(def member in userMembers){
				if(!groupUsers.contains(member.email)){
					deletedMembers[j]=member
					j++
				}
			}  
			group.members.removeAll(deletedMembers)
			for(int i=0;i<groupUsers.length;i++){
				if(groupUsers[i]!='' && !userMembers.email.contains(groupUsers[i]))
				{
					newMembers[j]=getEmailMember(groupUsers[i])
					j++ 
				}
			}
			for(int i=0;i<newMembers.size();i++){
				group.members.add(newMembers[i])
			}
		}
		group.groupName=params['groupName']
		group.groupType=params['groupType']
		group.save() 
		redirect controller:'site', action:'groupList'
	}
	
	def deleteGroup={
		def returnObject = [:]
		def group=EmailGroup.get(params.id)
		group.members.removeAll(group.members)
		group.delete()
		returnObject.success=true
		render returnObject as JSON
	}
	
	def userListJson={
		def userListObject = [:]
		def user=springSecurityService.currentUser
		def userList=user.findAllWhere(customer:user.customer,isDeleted:false)
		def oldEmailGroup
		if(params['emailGroupId']!=null){
				oldEmailGroup = EmailGroup.get(params['emailGroupId'])
		}
		userListObject.put("users", userList) 
		userListObject.put("userEmails",userList.email)
		if(oldEmailGroup!=null){
				userListObject.put("groupMembers",oldEmailGroup.members)
				userListObject.put("groupMembersEmail",oldEmailGroup.members.email)
		}
		render userListObject as JSON
	}
	def groupList={
		def loggedInUser=springSecurityService.currentUser
		def groups=EmailGroup.findAllWhere(customer:loggedInUser.customer)
		[loggedinUser:springSecurityService.currentUser,role:UserRole.findWhere(user:springSecurityService.currentUser).role.authority,emailGroups:groups]
	}
	
	def groupListJson={
		def groupListObject = [:]
		def loggedInUser=springSecurityService.currentUser
		def groups=EmailGroup.findAllWhere(customer:loggedInUser.customer)
		groupListObject.put("groups", groups)
		render groupListObject as JSON
	}
	
	def sendGroupEmail={
		def loggedInUser=springSecurityService.currentUser
		def receipients=params.recepients.toString().split(',')
		for(def receipient in receipients){
			def group=EmailGroup.findWhere(groupName:receipient.trim(),customer:loggedInUser.customer)
			if(group!=null){
			for(def member in group.members){
				asynchronousMailService.sendMail {
					to member.email
					from loggedInUser.email
					subject params['mailSubject']
					html params.message
				}
			}
			}
			else if(receipient.trim() != ''){
				asynchronousMailService.sendMail {
					to receipient.trim()
					from loggedInUser.email
					subject params['mailSubject']
					html params.message
				}
			}
		}
		render "Mail sent successfully"
	}
	
	def getEmailMember(def memberEmail){
		def emailMember=MemberEmails.findWhere(email:memberEmail)
		if(emailMember==null){
			emailMember=new MemberEmails(email:memberEmail)
			emailMember.save()
		}
		return emailMember
	}
}
