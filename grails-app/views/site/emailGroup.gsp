<!doctype html>
<%@page import="com.kam.User"%>
<%@page import="com.kam.UserRole"%>


<html>
<head>
<meta name='layout' content='main' />
<title><g:message code="title.message"></g:message></title>

</head>
<body>
	<g:render template="/layouts/navigation" model="[loggedInUserRole:role,loggedinUser:loggedinUser]"></g:render>
		<ul class="backUl">
			<li class="fr paddingRight"><a href="javascript:history.go(-1)" class="back"><span><g:message code="login.customer.back"/></span></a></li>
   		</ul>
	<div class="customerList"><!--main_box-->
		<div class="customerCompany gridColor">
			<g:form controller="site" action="saveEmailGroup" name="createEmailGroup" id="createEmailGroup" >
     	  		<h1 class="userHeading paddingTop"><g:message code="email.group.create"/> </h1>
     	  		<div class="fl paddingTop fullWidth">
					<label class="fl userHeading paddingTop marginRight"><g:message code="email.group.list"/> :</label>
					<input type="text" name="groupName" id="groupName" onblur="validateGroupExists();" class="emailGroupInput fl fourSideBorder"
						placeholder="<g:message code="group.list.email.name"/>" />	
					<div class="errorMessage errorPadding" id="emailGroupMessage" ><g:if test="${flash.error }">${flash.error }</g:if></div>
				</div>
				<div class="fl paddingTop fullWidth">
						<label class="fl userHeading paddingTop marginRight"><g:message code="messages.email.group.type"/></label>
						<div class="paddingTop marginRight fl">
 							<g:radio name="groupType" id="groupType1"  value="ActionUser" checked="true"/><g:message code="messages.email.group.type1"/>
 						</div>
 						<div class="paddingTop marginRight fl">
  							<g:radio name="groupType" id="groupType2" value="NotifyUser" /><g:message code="messages.email.group.type2"/>
  						</div>
  					</div>
				<div class="fl paddingTop fullWidth">
					<p class="fl userHeading paddingTop marginRight"><g:message code="email.group.new.users"/> :</p>
					<div class="fullWidth">
					<input type="text" id="suggest3" class="emailGroupInput widthHalf fl fourSideBorder" placeholder="<g:message  code="messages.group.newemail" />" />
						<div class="" id="recepientsMessage" ></div>
						
					</div>
				</div>
         		
      			
      			<div class="ui-multiselect ui-helper-clearfix ui-widget">
      			<div class="available">
      				<div class="actions ui-widget-header ui-helper-clearfix">
      				<div class="statusSpan">
      					<span class="fl"><g:message code="email.group.users.list"/></span>
      					<span class="fr"><g:message code="users.user.status"/> </span>
      					</div>
      					<a href="#" class="add-all click"><g:message code="email.group.add.all"/>
      					</a>
      					<ul class="available fl connected-list">
      					
      					</ul>
      				</div>
      			</div>
      			<div class="selected">
      				<div class="actions ui-widget-header ui-helper-clearfix">
      					<div class="statusSpan">
      					<span class="fr"><g:message code="users.user.status"/></span>
      					</div>
      					<a href="#" class="remove-all click"><g:message code="email.group.remove.all"/>
      					</a>
      					<ul class="selected fl connected-list ui-sortable">
      					
      					</ul>
      				</div>
      			</div>
         </div>
      			<div class="submitCommonBg fl docInputSubmit">
 					<input type="submit" class="inputButton fl" value="<g:message code="users.submit"/>"/>
 				</div>
      		</g:form>
      		
          </div> 
          
 </div>      

   <script type="text/javascript">
	var userListUrl='${createLink(controller:'site',action:'userListJson')}';
	var usersList=[];
	var validGroupUrl="${createLink(controller:'register',action:'checkIfGroupExists')}";
</script>
<script src="${resource(dir:'js',file:'multiselect.js') }"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#createEmailGroup').find('input[type=text]').val('');
});
	getUserList();
	function getUserList(){
	    jQuery.ajax({
	         type: 'POST',
	         url: userListUrl,
	         success: function(response,textStatus){
	         usersList=response.users;
	         $.each(usersList,function(){
        		$(".available ul").append('<li class="ui-state-default ui-element ui-draggable">'+this.username+'<input type="hidden" name="emailGroupList" value="'+this.email+'" /><a href="#" class="action"><span class="ui-corner-all ui-icon fr ui-icon-plus"></span><span class="activeButton activeEditBtn  fl"></span></a></li>');
        		   $(".available ul li").toggle(function(){
        		       $(this).appendTo(".selected ul ");
        		       $(this).find("a.action span.ui-icon").removeClass('ui-icon-plus').addClass('ui-icon-minus')
        		    },
        		    function(){
        		       $(this).appendTo($(".available ul"));
        		       $(this).find("a.action span.ui-icon").removeClass('ui-icon-minus').addClass('ui-icon-plus')
        		    });
	         });
	          },
	         error:function(XMLHttpRequest,textStatus,errorThrown){}
	     })
	 }

	function validateGroupExists(){ 
	    
	    var emailGroup=document.getElementById("groupName").value;
	    if(emailGroup=='') return;
	    jQuery.ajax({
	         type: 'POST',
	         url: validGroupUrl,
	         data:'emailGroup='+emailGroup,
	         success: function(response,textStatus){
	          if(response.sucess!=true){
	              $('.errorMessage').html(response.message)
	          }
	          else{
	              $('.errorMessage').html('')
	          }
	          },
	         error:function(XMLHttpRequest,textStatus,errorThrown){}
	     })
	 }
</script>
</body>


</html>