$(document).ready(function(){
	getequipmentComponentList();

	});
function getequipmentComponentList(){
    jQuery.ajax({
         type: 'POST',
         url: equipmentComponentUrl,
         success: function(response,textStatus){  		
     		
        	 $('#brand').html('');
        	 $('#battery').html('');
        	 $('#armature').html('');
        	 $('#light').html('');
        	 $('#emergencyUnitOfPrint').html('');
        	 $('#group').html('');
        	 $('#kast').html('');
        	 $.each(response.brands, function() {
    			 	$('#brand').append('<option value="'+this+'">'+this+'</option>');
    		 	});
        	 
        	 $.each(response.batteries, function() {
    			 	$('#battery').append('<option value="'+this+'">'+this+'</option>');
    		 	});
        	 
        	 $.each(response.armatuurs, function() {
    			 	$('#armature').append('<option value="'+this+'">'+this+'</option>');
    		 	});
        	 
        	 $.each(response.lights, function() {
    			 	$('#light').append('<option value="'+this+'">'+this+'</option>');
    		 	});
        	 
        	 $.each(response.emergencyUnitsOfPrint, function() {
    			 	$('#emergencyUnitOfPrint').append('<option value="'+this+'">'+this+'</option>');
    		 	});
        	 $.each(response.groupNr, function() {
 			 		$('#group').append('<option value="'+this+'">'+this+'</option>');
 		 		});
     	 
        	 $.each(response.kast, function() {
        		 	$('#kast').append('<option value="'+this+'">'+this+'</option>');
 		 		});
        	 $('#brand').append('<option value="Other">Other</option>');
        	 $('#battery').append('<option value="Other">Other</option>');
        	 $('#armature').append('<option value="Other">Other</option>');
        	 $('#light').append('<option value="Other">Other</option>');
        	 $('#emergencyUnitOfPrint').append('<option value="Other">Other</option>');
        	 $('#group').append('<option value="Other">Other</option>');
        	 $('#kast').append('<option value="Other">Other</option>');
        	 
        	 $('#brandName').val(response.brands[0]);
        	 $('#batteryName').val(response.batteries[0]);
        	 $('#armatureName').val(response.armatuurs[0]);
        	 $('#lightName').val(response.lights[0]);
        	 $('#emergencyUnitOfPrintName').val(response.emergencyUnitsOfPrint[0]);
        	 $('#groupName').val(response.groupNr[0]);
        	 $('#kastName').val(response.kast[0]);
        	 $('#oldBrandName').val(response.brands[0]);
        	 $('#oldBatteryName').val(response.batteries[0]);
        	 $('#oldArmatureName').val(response.armatuurs[0]);
        	 $('#oldLightName').val(response.lights[0]);
        	 $('#oldEmergencyUnitOfPrintName').val(response.emergencyUnitsOfPrint[0]);
        	 $('#oldGroupName').val(response.groupNr[0]);
        	 $('#oldKastName').val(response.kast[0]);
          },
         error:function(XMLHttpRequest,textStatus,errorThrown){}
     })
 }

