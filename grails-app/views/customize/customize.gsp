<!doctype html>
<html>
<head>
<meta name='layout' content='main' />
<title><g:message code="title.message"></g:message></title>
<g:javascript library="jquery" plugin="jquery" />

</head>

<body>
		<g:render template="/layouts/navigation" model="[loggedInUserRole:role]"></g:render>
		<!-- back ul start -->
			<ul class="backUl">
				<li class="fl upperDownPadding"><g:message code="customer.user.of"/> ${loggedinUser.customer.name }</li>
				<li class="fl paddingRight"><g:link controller="site" action="groupList" class="syn"><span><g:message code="email.group.list" /></span></g:link></li>
				<li class="fr paddingRight"><a href="javascript:history.go(-1)" class="back"><span><g:message code="login.customer.back"/></span></a></li>      
			</ul>
		<!-- back ul end -->
		
		<!-- main content start -->
			<div class="customerList">	
				<div class="marginTopBottom fullWidth">
					<div class="formBox">
						<div class="createUser formHeadingBg">
							<h2><g:message code='customize.design'/></h2>
								<h2 class="loginDetails"><g:message code='customize.choose.background'/></h2>
								<div class="fl fullWidth">
									<ul class="adminHead fullWidth fl " id="customize">
									</ul>
								</div>
								<div class="fl fullWidth">
									<h2 class="loginDetails"><g:message code='customize.upload.own.theme'/></h2>
									<g:form controller="customize" action="${actionToSend }">
									
							<!-- upload background theme code -->
									<div class="formInput borderBottom paddingTop">
									<div class="fl ">
										<label class="fl inputHeading"><g:message code='customize.upload.image'/> :</label>
										</div>
										<div class="fl ">
											<span class="customize" id="imgHolder">
												<g:if test="${theme=='Image' }">
													<img id="imgHolderThemeImage" src="${resource(dir:'ThemeImages',file:backGround) }" width="88px" height="75px"/>
												</g:if>
												<g:else>
													<img id="imgHolderThemeImage" src="" width="88px" height="75px"/>
												</g:else>
											</span>
										<!--  <input type="button" class="fl marginTop" id="removeImage" value="<g:message code='users.cancel'/>" />-->
											<iframe src="${createLink(controller:'customize',action:'imageThemeUploader') }?elementName=themeImage" class="fl marginTop iframeCss" > </iframe>
							
							<g:if test="${theme=='Image' }">
													<input type="hidden"  id="themeImage" value="${backGround }" name="themeImage"/>
												</g:if>
												<g:else>
													<input type="hidden"  id="themeImage" value="${backGround }" name="themeImage"/>
												</g:else>
										</div>
									</div>	
							<!-- upload background theme code end -->	
							
							<!-- upload log theme code -->	
									<div class="formInput borderBottom paddingTop">
										<label class="fl inputHeading"><g:message code='customize.upload.logo'/> :</label>
										<div class="fl ">
											<span class="customize">
												<img id="customerLogoImage" src="${resource(dir:'ThemeImages',file:logo) }" width="88px" height="75px"/>
											</span>
										
											<iframe src="${createLink(controller:'customize',action:'imageThemeUploader') }?elementName=customerLogo" class="fl marginTop iframeCss" > </iframe>
							<input type="hidden"  id="customerLogo" value="${logo }" name="customerLogo"/>
			
     										
										</div>
									</div>
							<!-- upload log theme code end-->		
							<input type="hidden" name="customerTheme" value="${theme }" id="customerTheme" />
									<div class="formInput">
										<label class="fl inputHeading"><g:message code='customize.background.color'/> :</label>
										<g:if test="${theme=='Color' }">
											<input type="text" name="background" id="background" class="color field" placeholder='<g:message code="messages.click.here" />' value="${backGround }"  readonly="readonly"/>
										</g:if>
										<g:else>
											<input type="text" name="background" id="background" class="color field" placeholder='<g:message code="messages.click.here" />' readonly="readonly"/>
										</g:else>
									</div>
									
									<div class="formInput">
										<label class="fl inputHeading"><g:message code='customize.navigation.color'/> :</label>
										<input type="text" name="headerinput" class="color field" id="headerinput" placeholder='<g:message code="messages.click.here" />' value="${navigation }"  readonly="readonly" />
									</div>
									<div class="formInput">
										<label class="fl inputHeading"><g:message code='customize.navigation.text.color'/> :</label>
										<input type="text" name="textinput" class="color field" id="textinput" placeholder='<g:message code="messages.click.here" />' value="${links }"  readonly="readonly"/>
									</div>
									<div class="submitCommonBg fl">
										<input type="submit" class="inputButton fl" value="<g:message code='users.submit'/>"/>
									</div>
									</g:form>
								</div>
						</div>
					</div>
				</div>
			</div>
			<script src="${resource(dir:'js',file:'colorChange.js') }"></script>	
			<script type="text/javascript">

			
			
			
			
$(document).ready(function()
{
	var images=["redTheme.jpg", "pinkTheme.jpg", "purpleTheme.jpg", "blueTheme.jpg", "greyTheme.png"];
	var i=0;
	var classToImage='rightMargin';
	for(i=0;i<images.length;i++){
		if(i==(images.length-1))
			classToImage='rightNone';
		$('#customize').append('<li class="fl '+classToImage+' customize" ><img src="'+themeImagesPath+images[i]+'" width="88px" height="75px" /></li>')
		}
	
	$('.color').ColorPicker({
	onSubmit: function(hsb, hex, rgb, el) {
		$(el).val(hex);
		$(el).ColorPickerHide();
	},
	onBeforeShow: function () {
		$(this).ColorPickerSetColor(this.value);
	}
})
.bind('keyup', function(){
	$(this).ColorPickerSetColor(this.value);
});

$(".colorpicker_submit").click(function() 
{
	var B = $("#background").val();
	var header = $("#headerinput").val();
	var T = $("#textinput").val();

	$(".topHeader").css("background-color", "#"+header);
	$('body').css("background", "#"+B);
	$('.topHeader, ul.dropdown > li').css("color", "#"+T);
	$('.topHeader, ul.dropdown > li > a.alink').css("color", "#"+T);
	if(B!='')
	{
		$('#customerTheme').val('Color');
	}
});

$('#customize > li').click(function(){
	var liElement=$(this);
	
	
	
	$('#customize').find('li').each(function(){
		$(this).removeClass('showBorder');
		
	});
	
	//$('#imgHolder ').append('<img src=".." width="88px" height="75px"/>');
	$('#imgHolder').find('img').attr('src',liElement.find('img').attr('src'));
	liElement.addClass('showBorder');
	
	var bodyBg=$('#imgHolder img').attr('src');
	//$('body').css("background", "url('" + bodyBg + "')");
	$('body').css("background", "url('" + bodyBg + "')");
	if(bodyBg!='')
	{
		$('#themeImage').val(bodyBg);
		$('#customerTheme').val('Image');
	}
	
});
$('#background').click(function(){
	$('body').css("background", "url('')");
	$("#imgHolder").find('img').attr('src','');
});


});
</script>
				
</body>
</html>