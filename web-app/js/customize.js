
function getCustomSettings(){
    jQuery.ajax({
         type: 'POST',
         url: customSettingsUrl,
         success: function(response,textStatus){
        	 applySettings(response);
          },
         error:function(XMLHttpRequest,textStatus,errorThrown){}
     })
 }


function applySettings(response){
	if(response.customerTheme!='' && response.customerTheme!=null){
		if(response.customerTheme=='Color')
			$('body').css("background-color", "#"+response.customerBackGround );
		else
			$('body').css("background", "url('" +themeImagesPath + response.customerBackGround + "')");
	}
	if(response.customerNavigation!='' && response.customerNavigation!=null)
	$(".topHeader").css("background-color", "#"+response.customerNavigation);
	
	if(response.customerLinks!='' && response.customerLinks!=null){
		$('.topHeader, ul.dropdown > li').css("color", "#"+response.customerLinks);
		$('.topHeader, ul.dropdown > li > a.alink').css("color", "#"+response.customerLinks);
	}
	
	if(response.customerLogo!='' && response.customerLogo!=null){
		var logoUrl=themeImagesPath+response.customerLogo;
		$('#header').find('img').attr('src',logoUrl);
	}
}