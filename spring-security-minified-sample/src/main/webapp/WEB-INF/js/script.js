function sendAjaxPost(url) {
	alert("Url : " + url);
}

function sendAjaxPost(url, divToRender) {
	alert("Url : " + url);
	alert("divToRender : " + divToRender);
}

function sendAjaxPost(ajaxurl, divToRender, ajaxForm) {
	//alert ("Url : "+ajaxurl);
	//alert("divToRender : "+divToRender);
	//alert("ajaxForm : "+ajaxForm);
	//alert(divToRender.html());
	$.ajax({
		type : 'post',
		url : ajaxurl,
		data : ajaxForm.serialize(),
		success : function(msg) {
			//alert (msg);
			//divToRender.html("Hello");
			divToRender.html(msg);

			//$("#status").fadeIn(300).delay(800).fadeOut(300).ajaxComplete(function (event, request, settings) {
			//    divToRender.html(msg);
			//});
		}
	});
}

