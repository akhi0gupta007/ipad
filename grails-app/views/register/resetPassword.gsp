<html>

<head>
<meta name='layout' content='main' />
<title><g:message code='spring.security.ui.resetPassword.title' /></title>
<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'kam.css')}" />
<g:javascript library="jquery" plugin="jquery"></g:javascript>
</head>

<body>
<div class="login_main">
	<div class="login_form formHeadingBg">
		<h2><g:message code='spring.security.ui.resetPassword.title' /></h2>
		<g:form action='resetPassword' name='resetPasswordForm' autocomplete='off'>
			<g:hiddenField name='t' value='${token}' />
			<div class="formInput">
				<label class="fl loginHeading"><g:message code='spring.security.ui.resetPassword.password' /></label>
				<input type="password" class="field" placeholder="<g:message code="spring.security.ui.resetPassword.password" />" name='password' id='username' />
			</div>
			<div class="formInput">
				<label class="fl loginHeading"><g:message code='spring.security.ui.resetPassword.password.again' /></label>
				<input type="password" class="field" placeholder="<g:message code="spring.security.ui.resetPassword.password.again" />" name='password2' id='username' />
			</div>
			<div class="submitCommonBg fr">
				<input type="submit" class="inputButton fl" value="<g:message code='spring.security.ui.forgotPassword.submit' />" />
			</div>
		</g:form>
	</div>
</div>
	
	<script>
		$(document).ready(function() {
			$('#password').focus();
		});
	</script>

</body>
</html>
