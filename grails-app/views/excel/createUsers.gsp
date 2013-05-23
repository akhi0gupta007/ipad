<!doctype html>
<%@page import="com.kam.User"%>
<html>
<head>
<meta name='layout' content='main' />
<title><g:message code="title.message"></g:message></title>

</head>

<body>

	<g:render template="/layouts/navigation" model="[loggedInUserRole:role,loggedinUser:loggedinUser]"></g:render>

	<!-- top header end -->
	<ul class="backUl">
			<li class="fr paddingRight"><g:link controller="site" class="back"><span><g:message code="login.customer.back"/></span></g:link></li>
	</ul>
<br>   <br>
<h3 style="color: #000000;"><g:message code="projects.masterMessage"></g:message> </h3>
<g:if test="${flash.message}">
    <div class="message" style="background: #0080FF; color: white;font-size: 1.3em;"><em>${flash.message}</em></div>
</g:if>
<form action="${request.contextPath}/excel/handle" enctype="multipart/form-data" method="post" id="xlsf">
    <p style="color: #000000;"><g:message code="projects.excelMessage"></g:message> :<input type="file" name="xls"/></p>
    <p><input type="submit" value="Upload"/></p>
</form>


	
</body>
</html>
