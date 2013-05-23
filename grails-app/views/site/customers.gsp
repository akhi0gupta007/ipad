<!doctype html>
<%@page import="com.kam.User"%>
<%@page import="com.kam.UserRole"%>
<%@page import="com.kam.Role"%>
<%@page import="com.kam.Customer"%>
<html>
<head>
<meta name='layout' content='main' />
<title><g:message code="title.message"></g:message></title>

<script type="text/javascript">
 function customersForm(data,formName,param) {
  document.getElementById(param).value=data;
  document.forms[formName].submit();
 }
 </script>
<g:javascript library="jquery" plugin="jquery" />
</head>
<body>
	<g:render template="/layouts/navigation" model="[loggedInUserRole:role,loggedinUser:loggedinUser]"></g:render>

  		<ul class="backUl">
   			<li class="fl upperDownPadding"><g:message code="login.customer"/> </li>
 		</ul>
   
   
   
	<div class="customerList">
   		<div class="customerCompany gridColor">
    		<ul class="adminHead fullWidth">
     			<li class="company paddingTop fl"><g:message code="login.customer"/> </li>
     			<li class="enable paddingTop fl"><g:message code="users.user.status"/> </li>
     			<li class="paddingTop enable fl"><g:message code="users.user.edit"/> </li>
     			<li class="paddingTop fr role textRight"><g:message code="account.delete" /></li>
     		</ul>
     	</div>
    <g:if test="${customerlist!=null}">
    	<g:if test="${customerlist.size()>0}">
    		<div id="customersList">
    			<g:render template="customerUserList" model="[customerlist:customerlist]"></g:render>
    		</div>		
    	</g:if>
    	<g:else>
    		<div class="customerCompany thirdGridColor ">
    			<ul class="adminHead fullWidth ">
     				<li class="company paddingTop fl"><g:message code="customer.no.user"/> </li>     
     			</ul>
     		</div>
    	</g:else>
    </g:if>
    <g:else>
    	<div class="customerCompany thirdGridColor ">
    		<ul class="adminHead fullWidth ">
     			<li class="company paddingTop fl"><g:message code="customer.no.user"/> </li>
     		</ul>
	    </div>
    </g:else>
    <g:form controller="site" action="createAdmin" id="createAdminData" name="createAdminData">
    <input type="hidden" name="customer" id="customerAdminName" /> 
    </g:form>
    
    <g:form controller="site" action="admin" id="viewCustomerUsers" name="viewCustomerUsers">
    <input type="hidden" name="customer" id="customerUserName" /> 
    </g:form>
    
    <g:form controller="site" action="editCustomer" id="editCustomers" name="editCustomers">
    <input type="hidden" name="customer" id="customerName" /> 
    </g:form> 
 </div>
 


<script type="text/javascript">
$(document).ready(function(){
	$('a.EnableDisableButton').live('click', function(){
		var username = $(this).parents('ul.adminHead').find('a.changePointer').html();
		var elementId = $(this);
		var url='';
		if(elementId.hasClass('activeButton')){
			url = "${createLink(controller:'site',action:'disableAccount')}";
		}
		else if(elementId.hasClass('disableButton')){
			url = "${createLink(controller:'site',action:'enableAccount')}";
		}		
		if(elementId.parents('div.customerEnableDisable').find('a.enableDisableCustomer').hasClass('activeButton')){ 
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
		}
	});	
	
		
	
	$('a.enableDisableCustomer').live('click', function(){
		var customer = $(this).parents('ul.companyName').find('li.name').html();
		var elementId = $(this);
		var url='';
		if(elementId.hasClass('activeButton')){
			url = "${createLink(controller:'site',action:'disableCustomer')}";
		}
		else if(elementId.hasClass('disableButton')){
			url = "${createLink(controller:'site',action:'enableCustomer')}";
		}		 
		
		jQuery.ajax({
	         type: 'POST',
	         url: url,
	         data:'customer='+customer,
	         //alert('email');
	         success: function(response,textStatus){
	          if(response.sucess==true){
	        	  if(elementId.hasClass('activeButton')){
		          elementId.parents('div.customerEnableDisable').find('a.activeButton').each(function(){ 
	        	  		$(this).removeClass('activeButton').addClass('disableButton');
	        	  });
		          elementId.parents('div.customerEnableDisable').find('a.editButton').each(function(){ 
	        	  		$(this).attr('onclick','');
	        	  });
	        	  }
	        	  else{
		          	elementId.removeClass('disableButton').addClass('activeButton');
		          	var companyname = elementId.parents('ul.adminHead').find('input#userNameForList').val();
		          	elementId.parents('ul.companyName').find('a.editButton').attr('onclick','customersForm(\''+companyname+'\',\'editCustomers\',\'customerName\');');
	        	  } 
	          }
	          else{
	        	 
	           }
	          
	          },
	         error:function(XMLHttpRequest,textStatus,errorThrown){}
	     });
	});
});

function deleteCustomer(data,id){
	if(data.sucess){
		$('#'+id).parents('div.customerEnableDisable').remove();
		if($('#customersList').find('div.customerCompany').size()==0){
			$('#customersList').html('<div class="customerCompany thirdGridColor "><ul class="adminHead fullWidth "><li class="company paddingTop fl"><g:message code="customer.no.user"/> </li></ul></div>')
		}
	}
}

function deleteUser(data,id){
	if(data.sucess){
		$('#'+id).parents('div.customerCompany').remove();
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