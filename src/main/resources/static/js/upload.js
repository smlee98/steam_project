$(document).ready(function() {

	$('#gamefile_file').on('change', function(){
		if(window.FileReader){
			var filename = $(this)[0].files[0].name;
		} else {
			var filename = $(this).val().split('/').pop().split('\\').pop();
		}
		$('#orgfile').val(filename);
	});

	$('#thumbfile_file').on('change', function(){
		if(window.FileReader){
			var filename = $(this)[0].files[0].name;
		} else {
			var filename = $(this).val().split('/').pop().split('\\').pop();
		}
		$('#thumbnail').val(filename);
	});
});

function chkword(obj, maxByte){
	var strValue = obj.value;
	var strLen = strValue.length;
	var totalByte = 0;
	var len = 0;
	var oneChar = "";
	var str2 = "";

	for (var i = 0; i < strLen; i++){
		oneChar = strValue.charAt(i);
		if (escape(oneChar).length > 4){
			totalByte += 2;
		}
		else {
			totalByte++;
		}

		if (totalByte <= maxByte) {
			len = i + 1;
		}
	}

	if (totalByte > maxByte) {
		alert("게임 설명은 "+ maxByte + "자 이상 입력 할 수 없습니다.");
		str2 = strValue.substr(0, len);
		obj.value = str2;
		chkword(obj, 4000);
	}
	$("#countWord").text(totalByte);
}

function validate() {

	var file = document.getElementById("orgfile");
	var thumbnail = document.getElementById("thumbnail");
	var name = document.getElementById("name");
	var version = document.getElementById("version");
	var amount = document.getElementById("amount");
	var explain = document.getElementById("explain");
	
	if (file.value == "") {
		file.focus();
		init();
		$('.fileWarn').css('display', '');
		return false;
	}
	
	if (thumbnail.value == "") {
		thumbnail.focus();
		init();
		$('.thumbnailWarn').css('display', '');
		return false;
	}

	if (name.value == "") {
		name.focus();
		init();
		$('.nameWarn').css('display', '');
		return false;
	}
	
	if (version.value == "") {
		version.focus();
		init();
		$('.versionWarn').css('display', '');
		return false;
	}
	
	if (amount.value == "") {
		amount.focus();
		init();
		$('.amountWarn').css('display', '');
		return false;
	}
	
	if (explain.value == "") {
		explain.focus();
		init();
		$('.explainWarn').css('display', '');
		return false;
	}

	alert("업로드에 성공하였습니다.");
}

function init(){	
	$('.fileWarn').css('display', 'none');
	$('.thumbnailWarn').css('display', 'none');
	$('.nameWarn').css('display', 'none');
	$('.versionWarn').css('display', 'none');
	$('.amountWarn').css('display', 'none');
	$('.explainWarn').css('display', 'none');
}