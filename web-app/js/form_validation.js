$(document).ready(function() {
 $.validator.addMethod('pass',function (value,element) {
  if (value != element.defaultValue){
   return this.optional(element) || /(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z]).{6,}/i.test(value); 
    }
   return true;
    },formPasswordMessage);
 

 $.validator.addMethod("meets", function(value,element) {
  if(element.selectedIndex <= 0) return element.selectedIndex;
        else return value;
}, " You must select any Option to continue.");
 

 // validate the comment form when it is submitted
 // validate signup form on keyup and submit
 $("#CustomerForm").validate({
  rules: {
   name: {
    required: true,
    minlength: 3,
    },
   
   
   },
  messages: {
   name: {
    required: formError1,
    minlength: formError2
    },
    
     
   }
  });
 $("#DocumentInputForm").validate({
		rules: {
			DocumentName: {
					    required: true,
					    minlength: 6,
					    },
					   password2: {
					    required: true,
					    minlength: 6,
					    equalTo: "#password"
					    },
			   },
			   messages: {
				   DocumentName: {
					    required: formError5,
					     },
					   password2: {
					    required: formError6,
					    equalTo: formError7
					     },
				   }
				  });
	 $("#TemplateForm").validate({
			rules: {
				name: {
						    required: true,
						    minlength: 4,
						    },
						   password2: {
						    required: true,
						    minlength: 6,
						    equalTo: "#password"
						    },
				   },
				   messages: {
					   name: {
						    required: formError8,
						     },
						   password2: {
						    required: formError9,
						    equalTo: formError10
						     },
					   }
					  });
 
 $("#adminForm").validate({
	  rules: {
		  username: {
	    required: true,
	    minlength: 3,
	    },
	    firstName: {
		    required: true,
		    minlength: 3,
		    },
		lastName: {
		   required: true,
		   minlength: 3,
			    },
		email: {
	    required: true,
	    email: true,
	    },
	    
	    
	   },
	  messages: {
	   username: {
	    required: formError11,
	    minlength: formError12
	    },
	   firstName: {
	    required: formError13,
	    minlength: formError14
	    },
	   lastName: {
	    required: formError15,
	    minlength: formError16
	    },
	    email: {
	    required: formError17,
	     },
	    email: formError18,
	   },
	  
	  });
 
 
 
 
 $("#createUser").validate({
	  rules: {
		  firstName: {
	    required: true,
	    minlength: 3,
	    },
	    username: {
	    required: true,
	    },
	    lastName: {
	    required: true,
	    minlength: 3,
	    },
	    email: {
		    required: true,
		    email: true,
		    },
		    
		    
	   },
	   messages: {
		   username: {
		    required: formError19,
		    minlength: formError20
		    },
		   firstName: {
		    required: formError21,
		    minlength: formError22
		    },
		   lastName: {
		    required: formError23,
		    minlength: formError24
		    },
		    email: {
		    required: formError25,
		     },
		    email: formError26,
		    
		   }
		  });
	 
 
 $("#adminRegisterForm").validate({
	  rules: {
		  password: {
			    required: true,
			    minlength: 6,
			    },
			   password2: {
			    required: true,
			    minlength: 6,
			    equalTo: "#password"
			    },
	   },
	   messages: {
		   password: {
			    required: formError27,
			     },
			   password2: {
			    required: formError28,
			    equalTo: formError29
			     },
		   }
		  });
 
 $("#userRegisterForm").validate({
	  rules: {
		  password: {
			    required: true,
			    minlength: 6,
			    },
			   password2: {
			    required: true,
			    minlength: 6,
			    equalTo: "#password"
			    },
	   },
	   messages: {
		   password: {
			    required: formError30,
			     },
			   password2: {
			    required: formError31,
			    equalTo: formError32
			     },
		   }
		  });
 
 
 
 $("#updateUserForm").validate({
	  rules: {
		  username: {
	    required: true,
	    minlength: 3,
	    },
	    firstName: {
		    required: true,
		    minlength: 3,
		    },
		lastName: {
		   required: true,
		   minlength: 3,
			    },
		email: {
	    required: true,
	    email: true,
	    },
	    password: {
		    minlength: 6,
		    },
		   password2: {
		    minlength: 6,
		    equalTo: "#password"
		    },
		    
		    
	   },
	  messages: {
	   username: {
	    required: formError33,
	    minlength: formError34
	    },
	   firstName: {
	    required: formError35,
	    minlength: formError36
	    },
	   lastName: {
	    required: formError37,
	    minlength: formError36
	    },
	    email: {
	    required: formError38,
	     },
	    email: formError38,
	    
	   },
	   password: {
		    required: formError27,
		     },
		   password2: {
		    required: formError27,
		    equalTo: formError29
		     },
	  });
 // validate Login form on keyup and submit
 
 
 
});
