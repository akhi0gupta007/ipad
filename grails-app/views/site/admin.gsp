<!doctype html>
<%@page import="com.kam.User"%>
<%@page import="com.kam.UserRole"%>

<html>
<head>
<meta name='layout' content='main' />
<title><g:message code="title.message"></g:message></title>
<g:javascript library="jquery" plugin="jquery" />
</head>

<body>	
	<g:render template="/layouts/navigation" model="[loggedInUserRole:role]"></g:render>
	
	<!-- back Navigation start from here -->
	
		<ul class="backUl">
			<li class="fl upperDownPadding"><g:message code="users.of"/> ${customername }</li>
			<li class="fr paddingRight"><g:link controller="site" class="back"><span><g:message code="login.customer.back"/></span></g:link></li>
		</ul>
		
	<!-- back Navigation end here -->
	
			<div class="customerList">
				<div class="customerCompany gridColor">
					<ul class="adminHead fullWidth">
						<li class="paddingTop userName fl"><g:message code="users.user.name"/></li>
						<li class="userName paddingTop fl"><g:message code="new.customer.name"/></li>
						<li class="width25percent paddingTop fl"><g:message code="users.insert.email"/></li>
						<li class="userName paddingTop fl"><g:message code="users.Contact.Number"/></li>
						<li class="enable paddingTop fl"><g:message code="users.user.role"/></li>
						<li class="enable paddingTop fl"><g:message code="users.user.status"/></li>
						<li class="enable paddingTop fl"><g:message code="users.user.edit"/></li>
					 	<li class="paddingTop delete textRight fr"><g:message code="account.delete" /></li>
					</ul>
				</div>
			<!-- second ul start -->
			<g:if test="${userlist == null  || userlist.size()==0}">
				<div class=" paddingTop customerCompany thirdGridColor">
					<g:message code="user.not.available"/>
				</div>
			</g:if>
			
			<g:else>
				<div id="UsersList">
					<g:render template="userList" model="[userlist:userlist,actionToSend:'admin']"></g:render>
				</div>		
			</g:else>	
		</div>
			
		<!-- second ul end -->			
				
<script type="text/javascript">
$(document).ready(function(){
	$('a.EnableDisableButton').live('click', function(){
		var username = $(this).parents('ul.userListHead').find('li.userName').html();
		var elementId = $(this);
		var url='';
		if(elementId.hasClass('activeButton')){
			url = "${createLink(controller:'site',action:'disableAccount')}";
		}
		else if(elementId.hasClass('disableButton')){
			url = "${createLink(controller:'site',action:'enableAccount')}";
		}		 
		jQuery.ajax({
	         type: 'POST',
	         url: url,
	         data:'username='+username,
	         //alert('email');
	         success: function(response,textStatus){
	          if(response.sucess==true){
	        	  if(elementId.hasClass('activeButton')){
	        		  elementId.removeClass('activeButton').addClass('disableButton');
	        		  elementId.parents('ul.adminHead').find('li.edit > a.editButton').attr('onclick','');
		        	}
	        	  else{
	        		  elementId.addClass('activeButton').removeClass('disableButton');
	        		  var username = elementId.parents('ul.adminHead').find('input#userNameForList').val();
	        		  elementId.parents('ul.adminHead').find('li.edit > a.editButton').attr('onclick','usersForm(\''+username+'\',\'editCustomerUsers\',\'cusUserEditName\');');
		        	 }
	          }
	          else{
	        	 
	           }
	          
	          },
	         error:function(XMLHttpRequest,textStatus,errorThrown){}
	     });
	});	
});

function deleteUser(data,id){
	if(data.sucess){
		$('#'+id).parents('div.customerCompany').remove();
		if($('#UsersList').find('div.customerCompany ').size()==0)
		$('#UsersList').html('<div class=" paddingTop customerCompany thirdGridColor"><g:message code="user.not.available"/></div>');
	}
}

function comfirmUserDelete(name){
	var selection=confirm(deletetionConfirm+" "+name+"?");
	if(selection==false)
		return false;
	else
		return true;
}
</script>
		
</body>
</html>
