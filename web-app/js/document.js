function sortDocumentList(field,spanId){ 
		
	    var order='';
	    if($('#'+spanId).hasClass('downArrow'))
		    order='asc';
		else
			order='desc';
	    jQuery.ajax({
	         type: 'POST',
	         url: sortUrl,
	         data:'name='+field+'&order='+order+'&query='+$('#documentsSearchQuery').val(),
	         success: function(response,textStatus){
	         if(response.newList==null){
		        
	        	 $('#documentsList').html(response);
	        	 $('#pagination').remove();
	        	 $('#documentsList').easyPaginate({
	       		  });
	         }
	         else{
	        	 updateDocumentList(response);
	         }
	         if(order=='asc')
		        	$('#'+spanId).addClass('upArrow').removeClass('downArrow');
		        else
		        	$('#'+spanId).addClass('downArrow').removeClass('upArrow');
	        	 
	          },
	         error:function(XMLHttpRequest,textStatus,errorThrown){}
	     });
	 }

	 function documentDelete(id,name){
		 
		 if(comfirmDocumentDelete(name)){
		    jQuery.ajax({
		         type: 'POST',
		         url: deleteDocumentUrl,
		         data:'id='+id,
		         success: function(response,textStatus){
			        if(response.success==true){
			        	deleteDocument(response,id);
				        }
		          },
		         error:function(XMLHttpRequest,textStatus,errorThrown){}
		     });
		 }
		 }

	 
	 function updateDocumentList(data){
		 var status='';
		$('#documentsList').html('');
		 $.each(data.newList, function() {
			 if(!this[9]){
				 if(this[3]=='Not Ready')
					 status='<span class="statusIcon notreadyIcon">'+notReadyStatus+'</span>';
				 else if(this[3]=='Afgekeurd')
					 status='<span class="statusIcon notreadyIcon">'+afgekueredStatus+'</span>';
				 else if(this[3]=='Goedgekeurd')
					 status='<span class="statusIcon nvtIcon">'+goedgekeuredStatus+'</span>';
				 else if(this[3]=='Akkoord na herkeuring')
					 status='<span class="statusIcon intreatmentIcon">'+akkoordnaherkeuringStatus+'</span>';
				 else if(this[3]=='NVT')
					 status='<span class="statusIcon readyIcon ">'+nvtStatus+'</span>';
				 var myDate=formatDate(this[4]);
				 $('#documentsList').append('<div class="fullWidth  thirdGridColor"><ul class="adminHead list "><li class="width10 docList fl">'+this[0]+'</li><li  class="userName docList  fl"><a href="'+link+'/'+this[7]+'" >'+this[1]+'</a></li><li  class="userName docList  fl">'+this[2]+'</li><li class="width10 docList fl">'+this[8]+'</li><li class="width12Half textCenter  fl ">'+status+'</li><li class="userName docList fl">'+myDate+'</li><li class="width10 docList fl">'+formatDate(this[10])+'</li><li class=" width9Half textCenter fl "><a href="#" onclick="documentDelete(\''+this[7]+'\',\''+this[1]+'\')"><span class="marginLeft disableButton activeEditBtn fl" ><g:message code="list.synchronize" /></span></a></li></ul></div>');
			 }
        	 	 
		 });
		 $('#pagination').remove();
		 $('#documentsList').easyPaginate({
  		  });
		 if(status=='')
			 $('#documentsList').append('<div class="fullWidth paddingTop  thirdGridColor">'+documentMessage1+'</div>');
	}

	 
	 function deleteDocument(data,id){
			if(data.success){
				$('#'+id).remove();
				$('#pagination').remove();
				$('#documentsList').easyPaginate({
		  		  });
				if($('#documentsList').find('ul.adminHead').size()==0)
					$('#documentsList').html('<div class=" paddingTop fullWidth thirdGridColor" >'+documentsListingEmpty+'</div>');
			}
		}

		function comfirmDocumentDelete(name){
			var selection=confirm(deletetionConfirm+" "+name+"?");
			if(selection==false)
				return false;
			else
				return true;
		}