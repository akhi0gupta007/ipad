<!doctype html>
<html>
<head>
<meta name='layout' content='main' />
<title><g:message code="title.message"></g:message></title>
<g:javascript library="jquery" plugin="jquery" />
</head>

<body>
		<g:render template="/layouts/navigation" model="[loggedInUserRole:role]"></g:render>

	
			<ul class="backUl">
			
				<li class="fl upperDownPadding"><g:message code="customer.user.of"/> ${loggedinUser.customer.name }</li>
				 <li class="fl paddingRight"><g:link controller="site" action="groupList" class="syn"><span><g:message code="email.group.list" /></span></g:link></li>      
			</ul>
		<div class="customerList">	
				<div class="customerCompany gridColor">
				<ul class="adminHead fullWidth fl ">
					<li class="paddingTop userName fl"><g:message code="users.user.name"/> </li>
						<li class="userName paddingTop fl"> <g:message code="new.customer.name"/> </li>
							<li class="width25percent paddingTop fl"><g:message code="users.insert.email"/></li>
							<li class="userName paddingTop fl"><g:message code="users.Contact.Number"/></li>
							<li class="enable paddingTop fl"><g:message code="users.user.role"/></li>
					<li class="enable paddingTop fl"><g:message code="users.user.status"/> </li>
					<li class="enable paddingTop fl"><g:message code="users.user.edit"/></li>
					<li class="paddingTop fr delete textRight"><g:message code="account.delete" /></li>
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
					<g:render template="userList" model="[userlist:userlist,actionToSend:'users',totalSize:totalSize]"></g:render>
				</div>
			</g:else>
				
				
			</div>

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
		$('#'+id).parents('div.customerCompany').html('');
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
