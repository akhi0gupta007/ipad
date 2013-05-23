<html ><!--<![endif]-->
<head>
<meta name='layout' content='main' />
<title><g:message code="login.title" /></title>

</head>

<body>

<div class="login_main">
	 <div class="login_form formHeadingBg">
				<h2><g:message code="login.header" /></h2>
                    <form action='${postUrl}' method='POST' id='loginForm'class='cssform' >
                    	<g:if test='${flash.message}'>
							<div class='login_message'>
								${flash.message}
							</div>
						</g:if>
							<div class="formInput">
								<label class="fl loginHeading"><g:message code="login.username.label" /></label>
								<input type="text" class="field fl" placeholder="<g:message code="login.username.label" />" name='j_username' id='username'/>
                        	</div>
                        	<div class="formInput">
                           		<label class="fl loginHeading"><g:message code="login.password.label" /></label>
                            	<input type="password" class="field fl" placeholder="<g:message code="login.password.label" />" name='j_password' id='password'  />
                           </div>
                             <div class="formInput"> 
                            <div class="rememberCheckBox">
                  				<input type="checkbox" name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if> />
                                <label><g:message code="login.remember.me.label" /></label>
                           </div>
                      		
                      			<div class="submitCommonBg fr">
                          			<input type="submit" class="inputButton fl" value="<g:message code="superuser.login" />"/> 
                          		 </div>
                          </div>	 
                   </form>
                       <p class="LostPassword fr"> <g:link controller="register" action="forgotPassword"><g:message code="login.password.forgot.link"></g:message></g:link></p>
             </div>
     </div>
  
  
	<script type='text/javascript'>
	<!--
		(function() {
			document.forms['loginForm'].elements['j_username'].focus();
		})();
	// -->
	</script>
</body>
</html>
