$(document).ready(function(){
	getGroupList();

	});
function getGroupList(){
    jQuery.ajax({
         type: 'POST',
         url: groupListUrl,
         success: function(response,textStatus){
        	var count=0;
     		for(count=0;count<response.groups.length;count++){
     			groupData[count]=response.groups[count].groupName;
     		}
     		$("#suggest3").autocomplete(groupData, {
     	  		multiple: true,
     	 		mustMatch: false,
     	  		autoFill: true
     	 	});
          },
         error:function(XMLHttpRequest,textStatus,errorThrown){}
     })
 }
