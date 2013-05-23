<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->

 <html><!--<![endif]-->
 <head>
 	<meta http-equiv="Expires" content="-1"/>
  	<meta http-equiv="Cache-Control" content="no-cache"/>
  	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><g:message code="title.message"></g:message></title>
	<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:'kam.css')}" />
 	<g:javascript library="jquery" plugin="jquery"></g:javascript>
 	<g:layoutHead/>
    <r:layoutResources />
</head>
<body id="customizeBodyBackground">
 	<meta http-equiv="Expires" content="-1"/>
  	<meta http-equiv="Cache-Control" content="no-cache"/>
  	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 	<g:render template="/layouts/header"></g:render>
 	<g:render template="/layouts/myMessages"></g:render>
	<div class="mainBg">
	   	<div id="Wrapper">
 			<div class="kamMain fullWidth">
  				<g:layoutBody/>
  			</div>
 		</div>
  	</div>
  	<script type="text/javascript">
		var customSettingsUrl="${createLink(controller:'customize',action:'getCustomSettings')}";
		var applicationContext='${application.contextPath}';
		var themeImagesPath=applicationContext+'/ThemeImages/';
  	</script>
  	<script src="${resource(dir:'js',file:'customize.js') }"></script>
  	<script type="text/javascript">
  	$(document).ready(function(){
  		getCustomSettings();

  		});
  	</script>
	<g:render template="/layouts/footer"></g:render>
</body>
</html>