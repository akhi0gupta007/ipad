function sortEquipmentList(field,spanId){ 
	
    var order='';
    if($('#'+spanId).hasClass('downArrow'))
	    order='asc';
	else
		order='desc';
    jQuery.ajax({
         type: 'POST',
         url: sortUrl,
         data:'name='+field+'&order='+order+'&query='+$('#equipmentsSearchQuery').val(),
         success: function(response,textStatus){
         if(response.newList==null){
        	 $('#equipmentsList').html(response);
        	 $('#pagination').remove();
        	 $('#equipmentsList').easyPaginate({
       		  });
         }
         else{
        	 updateEquipmentList(response);
         }
         if(order=='asc')
	        	$('#'+spanId).addClass('upArrow').removeClass('downArrow');
	        else
	        	$('#'+spanId).addClass('downArrow').removeClass('upArrow');
        	 
          },
         error:function(XMLHttpRequest,textStatus,errorThrown){}
     });
 }
 
 function updateEquipmentList(data){
	 var status='<span class="statusIcon notreadyIcon">'+notReadyStatus+'</span>';
	$('#equipmentsList').html('');
	var equipmentCount = 0;
	$.each(data.newList, function() {
		 if(!this[7]){
			 equipmentCount=1;
			 if(this[4]=='Not Ready')
				 status='<span class="statusIcon notreadyIcon">'+notReadyStatus+'</span>';
			 else if(this[4]=='Afgekeurd')
				 status='<span class="statusIcon notreadyIcon">'+afgekueredStatus+'</span>';
			 else if(this[4]=='Goedgekeurd')
				 status='<span class="statusIcon nvtIcon">'+goedgekeuredStatus+'</span>';
			 else if(this[4]=='Akkoord na herkeuring')
				 status='<span class="statusIcon intreatmentIcon">'+akkoordnaherkeuringStatus+'</span>';
			 else if(this[4]=='NVT')
				 status='<span class="statusIcon readyIcon ">'+nvtStatus+'</span>';
			 var myDate=formatDate(this[5]);
			 $('#equipmentsList').append('<div class="fullWidth  thirdGridColor" id="equipmentNumber'+this[8]+'"><ul class="adminHead list "><li class="width10 docList fl"><a href="'+link+'/'+this[8]+'" > '+this[0]+'</a></li><li  class="userName docList  fl">'+this[1]+'</li><li  class="userName docList  fl">'+this[2]+'</li><li class="width10 docList fl">'+this[3]+'</li><li class="width12Half textCenter  fl ">'+status+'</li><li class="userName docList fl">'+myDate+'</li><li class="width10 docList fl">'+formatDate(this[9])+'</li><li class=" width9Half textCenter fl "><a href="#" onclick="deleteRequest(\''+this[8]+'\',\'Equipment\',\''+this[0]+'\')"><span class="marginLeft disableButton activeEditBtn fl" ><g:message code="list.synchronize" /></span></a></li></ul></div>');
		 }
   	 	 
	 });
	 $('#pagination').remove();
	 $('#equipmentsList').easyPaginate({
		  });
	 if(equipmentCount==0)
		 $('#equipmentsList').append('<div class="paddingTop fullWidth thirdGridColor ">'+equipmentMessage1+'</div>');
}
 
 function deleteRequest(id,type,name){
	 if(comfirmEquipmentDelete(name)){
	    jQuery.ajax({
	         type: 'POST',
	         url: deleteEquipmentUrl,
	         data:'id='+id+'&type='+type,
	         success: function(response,textStatus){
	        		 deleteComponent(response);
	          },
	         error:function(XMLHttpRequest,textStatus,errorThrown){}
	     });
	 }
	}

	function deleteComponent(data){
		if(data.deletedType=="Equipment")
			$('#equipmentNumber'+data.deletedId.id).remove();
		if($('#equipmentsList').find('div').size()==0){
			$('#equipmentsList').html('<div class="paddingTop fullWidth thirdGridColor" >'+equipmentsListingEmpty+'</div>');
		}
		
	}
	
	function comfirmEquipmentDelete(name){
		var selection=confirm(deletetionConfirm+" "+name+"?");
		if(selection==false)
			return false;
		else
			return true;
	}
