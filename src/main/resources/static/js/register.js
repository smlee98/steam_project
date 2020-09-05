var bPWCheck = "";
var bIDCheck = "";

function onoffPassword(){
	$('#eyestat').toggleClass("active");
	if($('#eyestat').hasClass("active")){
		$('#eyecon').attr('class',"fas fa-eye-slash");
		$('#password').attr('type',"text");
		$('#password2').attr('type',"text");

	}else{
		$('#eyecon').attr('class',"fas fa-eye");
		$('#password').attr('type',"password");
		$('#password2').attr('type',"password");
	}
}

function validate() {

	var id = document.getElementById("id");
	var password = document.getElementById("password");
	var password2 = document.getElementById("password2");
	var name = document.getElementById("name");
	var chkbox = document.getElementsByName("favorite");

	var phone_str = "";
	var phone = document.getElementById("phone");
	var phone1 = document.getElementById("phone1");
	var phone2 = document.getElementById("phone2");
	var phone3 = document.getElementById("phone3");

	var address_str = "";
	var address = document.getElementById("address");
	var address1 = document.getElementById("address1");
	var address2 = document.getElementById("address2");

	if(bIDCheck == "true" && bPWCheck == "true"){
		if (phone2.value == "" || phone3.value == "") {
			if (phone2.value == "") phone2.focus();
			else if (phone3.value == "") phone3.focus();
			$('.numberWarn').css('display', '');
			return false;
		}

		if (name.value == "") {
			name.focus();
			$('.nameWarn').css('display', '');
			return false;
		}

		if (address2.value == "") {
			address2.focus();
			$('.addressWarn').css('display', '');
			return false;
		}

		if (id.value == "") {
			id.focus();
			$('.idWarn').css('display', '');
			return false;
		}

		if (password.value == "") {
			password.focus();
			$('.pwWarn').css('display', '');
			return false;
		}

		if (password.value != password2.value) {
			password2.focus();
			$('.pwWarn').css('display', '');
			return false;
		}

		var checked = 0;
		var chkcount = chkbox.length;
		for(var i=0; i < chkcount; i++ ){
			if( chkbox[i].checked == true ){
				checked += 1;
			}
		}

		if(checked == 0){
			$('.favWarn').css('display', '');
			return false;
		}

		phone_str = phone1.value+"-"+phone2.value+"-"+phone3.value;
		phone.value = phone_str;
		address_str = address1.value+" "+address2.value;
		address.value = address_str;

		alert("회원가입에 성공하였습니다.");
	}
	else{
		alert("아이디 또는 비밀번호를 확인해주세요.");
		return false;
	}
}

function pw_check(){
	var user_pw1 = $('#password').val();
	var user_pw2 = $('#password2').val();
	var userPwCheck = RegExp(/^[!@#$%^*()\-_=+\\\|\[\]{};:\'",.<>\/?](?=.*[a-zA-Z])(?=.*[0-9]).{8,12}$/);

	if(userPwCheck.test(user_pw1)){
		if (user_pw1 != user_pw2) {
			console.log("비밀번호가 일치하지 않음");
			$('.pwCheckOn').css('display', 'none');
			$('.pwCheckOff').css('display', '');
		}
		else if (user_pw1 == user_pw2) {
			console.log("비밀번호가 일치함");
			bPWCheck = "true";
			$('.pwCheckOn').css('display', '');
			$('.pwCheckOff').css('display', 'none');
		}
	}
	else{
		console.log("비밀번호 규격에 맞지않음");
	}
}

function id_check(){
	var user_id = $('#id').val();
	var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

	if(regExp.test(user_id)){
		$.ajax({
			url : '/check?id=' + user_id,
			type : 'get',
			success : function(data) {								
				if (data == 1) {
					// 1 : 아이디가 중복되는 문구
					console.log("아이디가 중복됨");
					document.getElementById("toggleId").className = "badge badge-danger"
					$("#id_check").text("사용중인 아이디입니다.");
					$("#id_check").css("color", "red");
					$('.idExist').css('display', '');
				}
				else if (data == 0) {
					// 1 : 아이디가 중복되는 문구
					console.log("아이디가 중복되지 않음");
					bIDCheck = "true";
					document.getElementById("toggleId").className = "badge badge-success"
					$("#id_check").text("사용가능한 아이디입니다.");
					$("#id_check").css("color", "green");
					$('.idExist').css('display', '');
				}
			},
			error : function() {
				console.log("실패");
			}
		});
	}else{
		console.log("아이디 규격에 맞지않음");
		document.getElementById("toggleId").className = "badge badge-danger"
		$("#id_check").text("아이디가 이메일 형식에 맞지 않습니다.");
		$("#id_check").css("color", "red");
		$('.idExist').css('display', '');
	}		
}