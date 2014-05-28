
    var url = $(location).attr('href');  
    if (url.indexOf("error=403") < 0){
    	$('#error_login').removeClass().addClass('hide');
    	
    }