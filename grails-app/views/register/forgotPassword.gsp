<html>

<head>
<meta name='layout' content='main' />
<title><g:message code='spring.security.ui.forgotPassword.title' /></title>
<link rel="stylesheet" media="screen"
	href="${resource(dir:'css',file:'kam.css')}" />
<g:javascript library="jquery" plugin="jquery"></g:javascript>
</head>

<body>
	
				<div class="login_main">
					<div class="login_form formHeadingBg">
						<h2><g:message code="spring.security.ui.forgotPassword.title" /></h2>
							<g:form action='forgotPassword' name="forgotPasswordForm" autocomplete='off'>
								
								<g:if test='${emailSent}'>
								
									<g:message code='spring.security.ui.forgotPassword.sent' />
								</g:if>
								<g:else>
								<g:message code='spring.security.ui.forgotPassword.description' />
								<div class="formInput">
								<label class="fl loginHeading"><g:message code='spring.security.ui.forgotPassword.username' /></label>
								<input type="text" class="field"  placeholder="<g:message code="login.username.label" />" name='username' id='username' />
								<g:if test="${flash.error }">
									<div class="login_message">${flash.error }</div>
								</g:if>
								</div>
								<div class="submitCommonBg fr">
									<input type="submit" class="inputButton fl" value="<g:message code='spring.security.ui.forgotPassword.submit' />" />
								</div>
						</g:else>
					</g:form>


				</div>
			</div>
	<script>
		$(document).ready(function() {
			$('#username').focus();
		});
	</script>

</body>
</html>