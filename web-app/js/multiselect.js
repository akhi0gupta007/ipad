$(document).ready(function(){
	
	$("#suggest3").bind("keypress", function(e) {
		
		if (e.keyCode == 13 ||e.keyCode == 32 ||e.keyCode == 44 ) {
			var externalEmail=$("#suggest3").val();
			if(externalEmail.replace(" ","")=='')
				return
			externalEmail = externalEmail.toString().replace(/,/g, '').replace(/ /g, '').replace(/;/g, '').replace(/"/g, '');
			e.preventDefault(); 
			if(checkEmailValid(e,externalEmail)){
				$(".available ul").prepend('<li class="ui-state-default ui-element ui-draggable">'+externalEmail+'<input type="hidden" name="emailGroupList" value="'+externalEmail+'" /><a href="#" class="action"><span class="ui-corner-all ui-icon fr ui-icon-plus"></span><span class=" activeEditBtn  fl"></span></a></li>');
			}
       	 
       	$("#suggest3").val('');     
        }
		$(function(){
	    $(".available ul li").toggle(function(){
	        $(this).prependTo(".selected ul ");
	        $(this).find("a.action span.ui-icon").removeClass('ui-icon-plus').addClass('ui-icon-minus');
	    },
	    function(){
	        $(this).prependTo($(".available ul"));
	        $(this).find("a.action span.ui-icon").removeClass('ui-icon-minus').addClass('ui-icon-plus');
	    });
	    
	});
 });
			

$('.add-all').click(function(){
$(".available ul li").appendTo(".selected ul ");
$('.selected ul li').find("a.action span.ui-icon").removeClass('ui-icon-plus').addClass('ui-icon-minus');
}),
$('.remove-all').click(function(){
$(".selected ul li").appendTo(".available ul ");
$('.available ul li').find("a.action span.ui-icon").removeClass('ui-icon-minus').addClass('ui-icon-plus');
}), 
	
		$('#createEmailGroup').submit(function(){
			if($('#groupName').val()==''){
				$('.errorMessage').html(emailGroupValidation);
				return false;
			}
			if($('div.selected').find('ul.selected > li').html()==null){
				$('#emailGroupMessage').html(groupMembersRequired);
				return false;
			}
			else
				$('#emailGroupMessage').html('');
			if($('.errorMessage').html()!='')
				return false;
			else{
				$(".available ul li").find('input[type=hidden]').remove();
				return true;
			}

		});				

	});

