<!doctype html>
<html>
<head>
<meta name='layout' content='main' />
<title><g:message code="title.message"></g:message></title>
<g:javascript library="jquery" plugin="jquery" />


</head>

<body>
<div class="topHeader"> <!-- top header start -->
	<ul>
  		<li class="upperDownPadding"><g:message code="create.admin.welcome.message"/></li>
   </ul>
  
</div> <!-- top header end -->
	<div class="customerList">
		
<div class="marginTopBottom fullWidth">
		<div class="formBox">
			<div class="createUser formHeadingBg">
			<g:if test="${admin=='' ||admin==null }">
				<h2><g:message code="register.invitation.expired"/></h2>
				<div id="updateMessage"></div>
				<g:message code='security.invitation.badCode'/>
			</g:if>
			<g:else>
				<h2><g:message code="register.update.password"/></h2>
				<div id="updateMessage"></div>
				<g:form id="adminRegisterForm" name="adminRegisterForm" url="[controller:'register',action:'update']">
					<h2 class="loginDetails"><g:message code="register.login.details"/></h2>
					<g:hasErrors bean="${domainError}">
					<g:renderErrors bean="${domainError}" as="list" />
					</g:hasErrors>
				
					<div class="formInput">
						<label class="fl inputHeading"><g:message code="new.customer.name"/> :</label>
						<input type="text" class="field" placeholder="<g:message code="new.customer.name"/>" 
							name='name' id='name' value='${admin.name }' disabled="disabled"/>
					</div>
				
					<div class="formInput">
						<label class="fl inputHeading"><g:message code="new.customer.email"/>  :</label>
						<input type="text" class="field" placeholder="<g:message code="new.customer.email"/>" name='email'
							id='email' value='${admin.email }' disabled="disabled"/>
					</div>
					<div class="formInput">
						<label class="fl inputHeading"><g:message code="users.user.name"/>  :</label>
						<input type="text" class="field" placeholder="<g:message code="users.user.name"/>"
						name='username' id='username' value='${admin.username }' disabled="disabled"/>
						<input type="hidden" name="user" value='${admin.username }'>
					</div>
					<div class="formInput">
						<label class="fl inputHeading"><g:message code="admin.register.password"/> :</label>
						<input type="password" class="field" placeholder="<g:message code="admin.register.password"/>"
							name='password' id='password' />
					</div>
					<div class="formInput">
						<label class="fl inputHeading"><g:message code="register.retype.password"/> :</label>
						<input type="password" class="field" placeholder="<g:message code="register.retype.password"/>"
							name='password2' id='password2' />
						<div class="fr width300">
							<div class="cancelCommonBg">
							<g:link controller="login">
								<span class="inputButton fl"><g:message code='users.cancel'/> </span>
								</g:link>
							</div>
							<div class="submitCommonBg fr">
								<input type="submit" class="inputButton fl" value="<g:message code='users.submit'/>"/>
							</div>
						</div>
					</div>
				</g:form>
			</g:else>
		</div>
	</div>
</div>
</div>	
	<script src="${resource(dir:'js',file:'jquery.validate.js') }"></script>
<script src="${resource(dir:'js',file:'form_validation.js') }"></script>
</body>
</html>