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

	
			<ul class="backUl">
			
			<li class="fr paddingRight"><a href="javascript:history.go(-1)" class="back"><span><g:message code="login.customer.back"/></span></a></li>
			<li class="fl"><g:link controller="site" action="emailGroup" class="syn"><span><g:message code="email.group.create" /></span></g:link></li>
			</ul>
		<div class="customerList">	
				<div class="customerCompany gridColor">
				<ul class="adminHead fullWidth fl ">
					<li class="paddingTop width25percent fl"><g:message code="group.list.email.name" /> </li>
					<li class="width25percent paddingTop fl"> <g:message code="group.list.member" /> </li>
					<li class="width25percent paddingTop fl"><g:message code="group.list.created.group" /></li>
					<li class="userName paddingTop fl"><g:message code="users.user.edit"/></li>
					<li class="paddingTop fr width10 textRight"><g:message code="account.delete" /></li>
				</ul>	
				</div>			
		
			
			<div class="customerCompany secondGridColor" id="emailGroupsList">
			<g:if test="${emailGroups!=null && emailGroups.size()>0 }">
			<g:each in="${emailGroups }" var="group">
				<ul class="adminHead fullWidth fl " id="${group.id }">
					<li class="paddingTop width25percent fl">${group.groupName }</li>
					<li class="width25percent paddingTop fl"><g:if test="${group.members!=null }">${group.members.size() }</g:if>
					<g:else>0</g:else>
					</li>
					<li class="width25percent paddingTop fl">${group.activatedBy }</li>
					<li class="userName fl"><g:link controller="site" action="editGroup" id="${group.id }"  class="editButton activeEditBtn fl" ><g:message code="users.user.edit"/></g:link></li>
					<li class=" fr width10 "><g:remoteLink controller="site" action="deleteGroup" id="${group.id }" beforeSend="comfirmGroupDelete('${group.groupName }')" before="if(comfirmGroupDelete('${group.groupName }')){" after="}"  onSuccess="deleteGroup(data,'${group.id  }');" class="activeEditBtn disableButton  fr marginRight">active button</g:remoteLink></li>
				</ul>	
			</g:each>
			</g:if>
			<g:else>
				<div class=" paddingTop customerCompany thirdGridColor">
					<g:message code="messages.groups.emptylist"/>
				</div>
			</g:else>
			</div>
			</div>
			
			
			



<script type='text/javascript'>

function comfirmGroupDelete(name){
	var selection=confirm(deletetionConfirm+" "+name+"?");
	if(selection==false)
		return false;
	else
		return true;
}

	function deleteGroup(data,id){
		if(data.success){
			$('#'+id).remove();
			if($('#emailGroupsList').find('ul.adminHead').size()==0)
			$('#emailGroupsList').html('<div class=" paddingTop customerCompany thirdGridColor"><g:message code="messages.groups.emptylist"/></div>')
		}
	}

	
		</script>
</body>
</html>
