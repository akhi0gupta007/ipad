<div id="header" class="fl">
	<div id="Wrapper">
		<div class="fullWidth">
			<h1>
				<g:link controller="site" class="  fl" title="DIGIKAM">
				<img src="${resource(dir:'images',file:'logo.png') }" height="180px" width="139px" /></g:link>
			</h1>
		 	<ul class="lanNav"> 
	 			<li class="fl"><g:link url="${request.contextPath}"  class="homeButton fl "><span class="fl"><g:message code="header.home"/></span></g:link></li>
				<li class="fr languageLi"><a href="#" class="english hidden fl language" onclick="changeToDutch();">Dutch</a></li>
				<li class="fr languageLi"><a href="#" class="dutch hidden fl language" onclick="changeToEnglish();">English</a></li>
			</ul> 
 		</div>
 	</div>
</div>
 <script type="text/javascript">
function changeToEnglish()
{
	var location=window.location.href.replace('?lang=nl','');
	location=location.replace('?lang=en','');
	location=location.replace('&lang=en','');
	location=location.replace('?lang=nl','');
	location=location.replace('&lang=nl','');
	location=location.replace('#','');
	if(location.indexOf('?')==-1)
	window.location=location+"?lang=en";
	else
	window.location=location+"&lang=en";

	}
function changeToDutch()
{
	var location=window.location.href.replace('?lang=nl','');
	location=location.replace('?lang=en','');
	location=location.replace('&lang=en','');
	location=location.replace('?lang=nl','');
	location=location.replace('&lang=nl','');
	location=location.replace('#','');
	if(location.indexOf('?')==-1)
		window.location=location+"?lang=nl";
		else
		window.location=location+"&lang=nl";
	
	}
 </script>
 <script type="text/javascript">
	var groupListUrl='${createLink(controller:'site',action:'groupListJson')}';
	var groupData=[];
</script>