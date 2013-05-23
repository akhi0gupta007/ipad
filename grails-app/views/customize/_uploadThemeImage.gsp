<div id="file-uploader-demo1">		
	<noscript>			
		<p>Please enable JavaScript to use file uploader.</p>
		<!-- or put a simple form for upload here -->
	</noscript>         
</div>
<input type="hidden" value="${elementName }" id="ElementName" />
<script type="text/javascript">
var uploadAFile='<g:message code="messages.upload.file" />';
</script>
<script src="${resource(dir: 'js', file: 'fileuploader.js') }"></script>
<script>        
        function createUploader(){   
            var uploader = new qq.FileUploader({
                element: document.getElementById('file-uploader-demo1'),
                action: '../customize/saveThemeImage',
                debug: true,
                allowedExtensions:['jpg','png','jpeg','gif'],
                onComplete :function (id, fileName, responseJSON){
                	parent.document.getElementById(elementName).value = fileName.replace(/ /g,'_');
					
                	if(elementName=='themeImage'){
                        var imageThemeUrl=themeImagesPath+fileName.replace(/ /g,'_');
                        parent.document.getElementById('customerTheme').value = 'Image';
                    	parent.document.getElementById('imgHolderThemeImage').src=imageThemeUrl;
                    	window.parent.$('body').css("background", "url('"+imageThemeUrl + "')");
                	}

                	if(elementName=='customerLogo'){
                		var imageThemeUrl=themeImagesPath+fileName.replace(/ /g,'_');
                    	parent.document.getElementById('customerLogoImage').src=imageThemeUrl;
                    }
                    
                }
            });           
        } 
        window.onload = createUploader;
</script>

<script type="text/javascript">
	var themeImagesPath='../ThemeImages/';
    var elementName=document.getElementById('ElementName').value;
</script>
   