
//function to initialize the page
var init = function(){
	$("#station_form").hide();

	$.ajax({
		url : "/stationform/init",
		type : "POST",
		dataType:"json",
		success : function(resp) {
			var settings = resp.data;
			var redirect = settings["redirect"];
			if(redirect=="yes"){
				var url = settings["url"];
				window.location.replace(url);
				return;
			}else{
				if(objSize(settings)==1){
					settings = {};
				}
			}
			
			$("#station_form").show();

		}
	});
 
   



