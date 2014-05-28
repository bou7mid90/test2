$(document).ready(function () {
	
	var nbmois = $('#nbmois').val();
	var promocodes = $('#promocodes').val();

	
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
	
	
	
	$("#qt").change(function () {
        
		  var qte = $("#qt").val();
		  var v = $('#vl1').val();
		  
		  
		  if (v<0){
			  
			  $("#tot ").html("<br /><strong>Total des codes d'essais a consommer: "+qte+"</strong>");
			  		  	  
			  
		  }
		  
		  else {
		  
		  var total = v*qte;
		  
			if (total>nbmois){	
          $("#oups ").css("display", "block");
			}
			else {
				$("#oups ").css("display", "none");
			}
		  
		 $("#total ").val(total);
		 $("#tot ").html("<br /><strong>Total des mois a consommer: "+total+"</strong>");
		  }
	});
	
	$("#vl1").change(function () {
        
		  var qte = $("#qt").val();
		  var v = $('#vl1').val();
		  
		  if (v<0){
			  
			  $("#tot ").html("<br /><strong>Total des codes d'essais a consommer: "+qte+"</strong>");
			  
			  
		  }
		  
		  else {
			  
		  var total = v*qte;
		  
			if (total>nbmois){	
         $("#oups ").css("display", "block");
			}
			else {
				$("#oups ").css("display", "none");
			}
		  
		 $("#total ").val(total);
		 $("#tot ").html("<br /><strong>Total des mois a consommer: "+total+"</strong>");
		  }
	});

    

});
