<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@page import="javax.servlet.http.Cookie" %>
<script type="text/javascript">
function usersForm(data,formName,param) {
	document.getElementById(param).value=data;
	document.forms[formName].submit();
}
</script>
<g:if test="${loggedInUserRole=='ROLE_SUPERUSER' }">
<div class="topHeader">
   <!-- top header start -->
   <ul class="fr dropdown">
    <li class=" fl subMenu">
   		<span id="dropIcon"><g:message code="create.admin.welcome.message"/> ${loggedinUser.firstName }</span>
    	<ul class="headerDrop fr" id="userLink">
    		<li ><g:link controller="logout" class="fontColor"> <g:message code="kam.admin.logout"/> </g:link></li>
    	</ul>
    </li>
    <li class=" fl noDrop"><g:link controller="site" action="createCustomer" class="alink"><g:message code="customer.create.customer"/> </g:link></li>
    
   </ul>
</div>
  <!-- top header end -->
</g:if>
<g:elseif test="${loggedInUserRole=='ROLE_ADMIN' }">
<div class="topHeader">
			<!-- top header start -->
			<div class="companyLogo "><g:message code="login.customer"/> : ${loggedinUser.customer.name }</div>
			<ul class="fr dropdown">
				 <li class=" fl subMenu">
   					<span class="fl"  ><g:message code="create.admin.welcome.message"/> ${loggedinUser.firstName }</span>
    				<ul class="headerDrop fr" id="userLink">
    					
    					<li ><g:link controller="customize" action="customize" class="fontColor"> 
							<g:message code="menu.settings"/> </g:link></li>
    					<li><a href="#" class="fontColor" onclick="usersForm('${loggedinUser.username}','editUsersPassword','cusUserEditPassword');"> <g:message code="customer.update.details"/> </a></li>
    				<li ><g:link controller="logout" class="fontColor"><g:message code="kam.admin.logout"/> </g:link></li>
    				</ul>
   				 </li>
				
				<li class="fl subMenu"> 
					<span class="fl" id="dropIcon"><g:message code="list.project"/></span>
					<ul class="headerDrop fr" id="userLink">
						<g:if  test="${loggedinUser.customer.flow=='equipment' }">
							<li class="fl"><g:link controller="equipment" action="createCheckpoints" class="fontColor"><g:message code="template.add.checkpoint"/> </g:link></li>
						</g:if>
						<li class="fl" id="createProject"><a href="#"  onclick="openOverlay('projectOverlay','','')"><span class="fontColor"><g:message code="create.project"/></span></a></li>
						<li class=" fl"><g:link controller="component" action="projectList" class="fontColor"><g:message code="projects.label"/> </g:link></li>
						<g:if  test="${loggedinUser.customer.flow=='equipment' }">
                        	<li class=" fl"> <g:link controller="excel" action="index" class="fontColor"><g:message code="projects.master"/> </g:link></li>
                        </g:if>
					</ul>
				</li>
				<g:if test="${loggedinUser.customer.flow=='document' }">
					<li class="fl subMenu"> 
						<span class="fl" id="dropIcon"><g:message code="navigation.template"/></span>
						<ul class="headerDrop fr" id="userLink">
							<li><g:link controller="document" action="showTemplate" class="fontColor"><g:message code="customer.create.template"/> </g:link></li>
							<g:if test="${page!='templateList' }">
								<li> <g:link controller="document" action="templateList" class="fontColor"><g:message code="template.list"/> </g:link></li>
							</g:if>
						</ul>
					</li>
					<g:if test="${page!='documentList' }">
						<li class="fl noDrop"><g:link controller="document" action="documentList" class="alink"><g:message code="view.document.document.list"/> </g:link></li>
					</g:if>	
				</g:if>
				<g:elseif test="${loggedinUser.customer.flow=='equipment' }">
					<g:if test="${page!='equipmentList' }">
						<li class="fl noDrop"><g:link controller="equipment" action="equipmentList" class="alink"><g:message code="messages.equipment.list"/> </g:link></li>
					</g:if>
				</g:elseif>
				<li class="fl noDrop"><g:link controller="site" action="createUser" class="alink"><span ><g:message code="user.createUsers.message"></g:message></span></g:link></li>
			</ul>
		</div>
		<!-- top header end -->
</g:elseif>
<g:elseif test="${loggedInUserRole=='ROLE_USER' }">
<div class="topHeader">
			<!-- top header start -->
			<div class="companyLogo "><g:message code="login.customer"/> : ${loggedinUser.customer.name }</div>
			<ul class="fr dropdown">
				 <li class=" fl subMenu">
   					<span class="fl" id="dropIcon"><g:message code="create.admin.welcome.message"/> ${loggedinUser.firstName }</span>
    				<ul class="headerDrop fr" id="userLink">
    					<li><a href="#" class="fontColor" onclick="usersForm('${loggedinUser.username}','editUsersPassword','cusUserEditPassword');"> <g:message code="customer.update.details"/> </a></li>
    					<li><g:link controller="logout" class="fontColor"><g:message code="kam.admin.logout"/></g:link></li>
    				</ul>
   				 </li>
   			<g:if test="${loggedinUser.customer.flow=='document' }">
				<li class="noDrop fl"> <g:link controller="document" action="templateList" class="alink"><g:message code="template.list"/> </g:link></li>
			</g:if>
			</ul>
		</div>
		<!-- top header end -->
</g:elseif>
<g:form controller="site" action="editUsers" id="editCustomerUsers" name="editCustomerUsers">
	<input type="hidden" name="username" id="cusUserEditName" /> 
</g:form>
<g:form controller="site" action="editUserPassword" id="editUsersPassword" name="editUsersPassword">
	<input type="hidden" name="username" id="cusUserEditPassword" />
</g:form>
<g:render template="/component/projectOverlay"></g:render>
<%
Cookie[] cookies = request.getCookies();
boolean foundCookie = false;
def locale = RequestContextUtils.getLocale(request)
if(cookies!=null){
for(int i = 0; i < cookies.length; i++) {
	Cookie cookie1 = cookies[i];
	if (cookie1.getName().equals('myLocaleCookie')) {
		cookie1.setValue(locale.toString())
		cookie1.setPath('/')
		response.addCookie(cookie1)
		foundCookie = true
	}
}
}

if (!foundCookie) {
	Cookie cookie1 = new Cookie('myLocaleCookie', locale.toString());
	cookie1.setMaxAge(24*60*60);
	cookie1.setPath('/')
	response.addCookie(cookie1);
}
%>