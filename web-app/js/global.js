$(document).ready(function(){
	$("removeButton").click(function () {
		$(this).parents('div.formInput').html('');
	    });	
	$('.click').click(function(e) {
	     // do something fancy
	     return false; // prevent default click action from happening!
	     e.preventDefault(); // same thing as above
	});
});




// header dropdown code start from here
$(function(){

    $(" li.subMenu").hover(function(){
    
        $(this).addClass("dropUp");
        $('ul:first',this).css('visibility', 'visible');
    
    }, function(){
    
        $(this).removeClass("dropUp");
        $('ul:first',this).css('visibility', 'hidden');
    
    });

});
//header dropdown code end from here

function addComponents(id){
	$('#'+id).slideToggle();
}
function openOverlay(id,parentValue,inputId){
	$('#'+inputId).val(parentValue);
	
	$('#'+id).fadeIn(250);
	if(id=='floorOverlay')
		$('#floorOverlay').find('iframe').contents().find('ul.qq-upload-list').html('');
	
	if(id=='equipmentOverlay'){
		  $('#'+id).find('select').each(function(){
		   if($(this).val()=='Other'){
		    $('#'+this.id+'Name').val('');
		    //$('#'+this.id+'Name').removeAttr('readonly');  
		   }
		   else{
		    //$('#'+this.id+'Name').attr('readonly','readonly');
		    $('#'+this.id+'Name').val($(this).val());
		    $('#old'+this.id.charAt(0).toUpperCase() + this.id.slice(1)+'Name').val($(this).val());
		   }
		   
		  });
		 }
}

function closeOverlay(id){
	$('#message').html('');
	if(id!='emailOverlay'){
		$('#'+id).find('input[type=text]').val('');
		$('#'+id).find('input[type=hidden]').val('');
		$('#'+id).find('div.formInput').removeClass('displayNone');
	}
	$('#'+id).find('textarea').val('');
	$('#'+id).fadeOut(250);
	$('#'+id).find('input[type=text]').each(function(){
			$('#'+this.id+'Error').html('');
	});
	}

$.validator.addMethod("validName", function(value, element) {
	 return this.optional(element) || value == value.match(/^[a-zA-Z]+$/);
	 },validName);


$.validator.addMethod("validCity", function(value, element) {
	 return this.optional(element) || value == value.match(/^[a-zA-Z /']+$/);
	 },validCity);
	 
$.validator.addMethod("validCustomerName", function(value, element) {
	return this.optional(element) || value == value.match(/^[a-zA-Z /'/.]+$/);
	 },validCustomerName);
	 
function phone_validate(userUrl)
{
	 	var regUrl = /(^\+[0-9]{2}|^\+[0-9]{2}\(0\)|^\(\+[0-9]{2}\)\(0\)|^00[0-9]{2}|^0)([0-9]{9}$)/;
	 		if(userUrl=='' ||regUrl.test(userUrl) == true)
	 		{
	 			$('#validPhoneMessage').removeClass('error');
	 			$('#validPhoneMessage').html('');
	 		}
	 		else
	 		{
	 			$('#validPhoneMessage').addClass('error');
	 			$('#validPhoneMessage').html(phoneValidate);
	 		}
}

function city_Validate(city,id){
	var regCity=/^[a-zA-Z /']+$/;
	if(city!=''){
		if(regCity.test(city) == true)
	 	{
	 		//$('#'+id).removeClass('error');
	 		$('#'+id).html('');
	 	}
	 	else
	 	{
	 		//$('#'+id).addClass('error');
	 		$('#'+id).html(validCity);
	 	}
	}
}

function url_validate(userUrl)
{
	userUrl = userUrl.replace('http://www.','http://');
	var regUrl = /^(((ht|f){1}(tp:[/][/]){1})|((www.){1}))[-a-zA-Z0-9@:%_\+~#?&=]+\.[-a-zA-Z0-9@:%_\+.~#?&=]+$/;
	if(userUrl=='' || regUrl.test(userUrl) == true)
	{
		$('#validWebsiteMessage').removeClass('error');
		$('#validWebsiteMessage').html('');
	}
	else
	{

		$('#validWebsiteMessage').addClass('error');
		$('#validWebsiteMessage').html(urlValidate);
	}
}




function checkmailInput(){	
		if($('#suggest3').val()=='' || $('#message').val()==''){
			if($('#suggest3').val()=='')
				$('#recepientsMessage').addClass('errorMessage').html(receipientValidate);
			else
				$('#mailMessage').addClass('errorMessage').html(messageValidate);
			return false;
		}
		else if($('#suggest3').val().indexOf(',')==-1){
			return checkEmailValid($('#suggest3').val());
		}
		
		else{
			return true;
		}
}

function removeErrorClass(id1,id2){
	if($('#'+id1).val()!='')
		$('#'+id2).removeClass('ErrorMessage').html('');
}

function checkEnter(e){
    //e = e || event;
    //return (e.keyCode || event.which || event.charCode || 0) !== 13;
}

function checkEmailValid(e,emailToCheck){
	e = e || event;
	
	email=emailToCheck.substring(emailToCheck.lastIndexOf(',')+1,emailToCheck.length);
	if(email.replace(/ /g,'')==''){
		if(e.keyCode == 13 || e.keyCode == 188)
		e.preventDefault();
		return true;
	}
	var validEmail = /^[a-z0-9_\+-]+(\.[a-z0-9_\+-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*\.([a-z]{2,4})$/;
	email=email.replace('\n','');
	if(email.replace(/ /g,'')!='' && jQuery.inArray(email.replace(/ /g,''), groupData)==-1){
		if(validEmail.test(email.replace(/ /g,''))==false){
			//$('#recepientsMessage').addClass('errorMessage').html('Please enter a valid mail address');
			email='failed'
			}
		else
			$('#recepientsMessage').removeClass('errorMessage').html('');
	}
	if((jQuery.inArray(email.replace(/ /g,''), groupData)==-1 && email=='failed') || jQuery.inArray(email.replace(/ /g,''), groupData)!=-1){
		if($('#suggest3').val().lastIndexOf(', ')!=-1)
			$('#suggest3').val($('#suggest3').val().substring(0,$('#suggest3').val().lastIndexOf(', ')+2));
		else
			$('#suggest3').val('');
		
		
	}
	else if(email!='failed'){
		$('#suggest3').val($('#suggest3').val().replace('\n','')+', ');
		 e.preventDefault();
	}
	if(e.keyCode == 13 || e.keyCode==188 || e.keyCode==186 || e.keyCode==32){
		 e.preventDefault();
	}
	
	if(email=='failed')
		return false;
	$('#recepientsMessage').removeClass('errorMessage').html('');

	return true;
}

$(document).ready(function() {
	  $('#confirm').click(function() {
		  $("#confirmOverlay").show();
	  });
	});

function confirmImagePdf(confirm_box){
	closeOverlay('confirmOverlay');
	 if (confirm_box) {
	       window.location.href = applicationContext+"/pdf/pdfLink?pdfController=document&pdfAction=showDocument&pdfId=pdfImageYes"
	       //uncomment below and remove above if you want the link to open in a new window
	       //window.open(url,'_blank');
	    }
	    else{
	    	 window.location.href=applicationContext+"/pdf/pdfLink?pdfController=document&pdfAction=showDocument&pdfId=pdfImageNo";
	    	
	    }
}


function formatDate(myDate){
	var myNewDate=''
	if(myDate!=undefined){
	var dateObject = myDate.substring(0,10).split('-');
	myNewDate = dateObject[2]+'/'+dateObject[1]+'/'+dateObject[0];
	}
	return myNewDate;
}