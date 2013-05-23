	
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
                action: '../site/uploadImage',
                debug: true,
                allowedExtensions:['jpg','png','jpeg','gif','bmp'],
                onComplete :function (id, fileName, responseJSON){
                	parent.document.getElementById(elementName).value = fileName.replace(/ /g,'_').replace(/%/g,'_');
                }
            });           
        } 
        window.onload = createUploader;
</script>

<script type="text/javascript">

    var elementName=document.getElementById('ElementName').value;
</script>
   