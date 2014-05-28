//alert($('body').attr('dir'));

var loc = $('#loc').val();



if(loc=="ar")
{
	$('body').css("direction", "rtl");
	
//	$('#foo').addClass("my_class");

//	$('#foo').addClass("my_class");

	$('#head1').removeClass().addClass('hide');
	
	$('#head3').removeClass('hide');
	
	
	$('#head2').removeClass().addClass('nav pull-left');

	$('#lang').removeClass().addClass('dispol');
	
		
}
else
{
	$('body').css("direction", "ltr");

	$('#head3').removeClass().addClass('hide');

	
    $('#head1').removeClass('hide');
	
	
	$('#head2').removeClass().addClass('nav pull-right');

	$('#lang').removeClass().addClass('dispor');
	
	
}