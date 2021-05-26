$(function() {
  $("form[name='addComputerForm']").validate({
    rules: {
      computerName: "required",
	  introduced: {
		date : true, 
	  },
	  discontinued : {
		date: true,
		greaterThan:  "#introduced",
	  },
    },
    messages: {
      computerName: "Name is required !",
	  introduced: "Please enter a valid date",
	  discontinued : {
		date: "Please enter a valid date",
		greaterThan: "Discontinued date must be greater than introduced date"
		}
    },
    submitHandler: function(form) {
      form.submit();
    }
  });
});


jQuery.validator.addMethod("greaterThan", 
function(value, element, params) {
    if (!/Invalid|NaN/.test(new Date(value)) && !/Invalid|NaN/.test(new Date($(params).val()))){
        return new Date(value) > new Date($(params).val());
    }
	else if (!/Invalid|NaN/.test(new Date(value)) && /Invalid|NaN/.test(new Date($(params).val()))){
		return false;
	}
    return true; 
});



