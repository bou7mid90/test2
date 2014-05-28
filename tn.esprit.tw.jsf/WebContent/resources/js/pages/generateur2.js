$(document).ready(function () {
	
	$('#gener').click(function(){
		var qt = $('.genform [name=qt]').val();
		var ln = $('.genform [name=ln]').val();
		var vl = $('#vl1').val();
		var us = $('#listcodes').val();
		
		$('.list option').remove();
		for(i=0;i<qt;i++){
			var user = "";
			for(j=0;j<ln;j++){
				user += parseInt(Math.random()*10);
			}
			if(us.indexOf(user+",")==-1)
				$('<option value="'+user+'">'+user+'</option>').appendTo('.list');
			else
				i--;		
		}
		$('#vl2').val(vl);
		
		$('[name=codes] option').each(function(i,e){
			$('[name=list]').val($('[name=list]').val() + $(e).val() + ",");
		});
		return false;
	});
	

});
