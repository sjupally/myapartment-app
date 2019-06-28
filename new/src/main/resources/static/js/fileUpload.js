function bs_input_file(multipleFiles) {
	console.log("called::::::::::::::::::::::::");
	$(".input-file").before(
		function() {
			if ( ! $(this).prev().hasClass('input-ghost') ) {
				var element = $("<input type='file' class='input-ghost' style='visibility:hidden; height:0' accept='application/pdf'>");
				if(multipleFiles === 'multiple'){
					var element = $("<input type='file' class='input-ghost' style='visibility:hidden; height:0' accept='application/pdf' multiple>");
				}
				element.attr("name",$(this).attr("name"));
				element.change(function(){
					element.next(element).find('input').val((element.val()).split('\\').pop());
				});
				$(this).find("button.btn-choose").click(function(){
					element.click();
				});
				$(this).find("button.btn-reset").click(function(){
					element.val(null);
					$(this).parents(".input-file").find('input').val('');
				});
				$(this).find('input').css("cursor","pointer");
				$(this).find('input').mousedown(function() {
					$(this).parents('.input-file').prev().click();
					return false;
				});
				return element;
			}
		}
	);
	$('#storeinshare').click(cleanFolderName);
}
/*$(function() {
	bs_input_file();
});*/

function changeCheckBoxValue(obj){
	var objChecked = $(obj).is(':checked');
	if(objChecked){
		$(obj).val('true');
	}else{
		$(obj).val('false');
	}
}
function resetCheckBoxValues(){
	changeCheckBoxValue($('#matchCase'));
	changeCheckBoxValue($('#compareTextStyles'));
	changeCheckBoxValue($('#compareDiffOnly'));
	changeCheckBoxValue($('#storeinshare'));
	cleanFolderName();
};
function cleanFolderName(){
	if($('#storeinshare').is(':checked')){
		$('#shareFolder').removeAttr('disabled');
		$("#sharedFolderSpan").show();
		$("#shareDriveLocationSpan").show();
	}else{
		$('#shareFolder').val('');
		$('#shareFolder').attr('disabled','disabled');
		$("#sharedFolderSpan").hide();
		$("#shareDriveLocationSpan").hide();
	}
	appendText();
}

function generatePDF(){
	$("#successmsg").hide();
	$("#errormsg").hide();
	$('#errormsgsamecontent').hide();
	$("#successmsgsamecontent").hide();
	$("#submitBtn").attr('disabled','disabled');
	var data = new FormData();
	data.append('baseFile', document.getElementsByName('baseFile')[0].files[0]);
	data.append('compareFile', document.getElementsByName('compareFile')[0].files[0]);
	data.append('matchCase', $('#matchCase').val());
	data.append('compareTextStyles', $('#compareTextStyles').val());
	data.append('compareDiffOnly', $('#compareDiffOnly').val());
	data.append('storeinshare', $('#storeinshare').val());
	data.append('shareFolder', $('#shareFolder').val());
	$.ajax({
		url: 'compareFile',
	    data: data,
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: 'POST',
        success: function (data) {
        	if(data == 'BOTHFILESHASSAMEDATA'){
        		$("#successmsgsamecontent").show();
        	}else{
        		downloadFile(data);
        		$("#successmsg").show();
        	}
        	$("#submitBtn").removeAttr('disabled');
        	$("#comparePDFForm")[0].reset();
        	resetCheckBoxValues();
        },
        error: function (e) {
        	debugger;
        	if(e.responseText == 'Problem while generating file from desknet.'){
        		$('#errormsgsamecontent').html(e.responseText);
        		$('#errormsgsamecontent').show();
        	}else{
            	$("#errormsg").show();
        	}
        	$("#submitBtn").removeAttr('disabled');
        	$("#comparePDFForm")[0].reset();
        	resetCheckBoxValues();
        }
    });
}

function downloadFile(data){
	var downloadLink      = document.createElement('a');
	downloadLink.target   = '_blank';
	downloadLink.href = "downloadFile?fileName="+data;
	document.body.appendChild(downloadLink);
	downloadLink.click();
	document.body.removeChild(downloadLink);
}

function appendText(){
	$("#sharedFolderSpan").text($('#shareFolder').val());
}
function appendTextMulti(){
	$("#sharedFolderSpan").text($('#folderName').val());
}