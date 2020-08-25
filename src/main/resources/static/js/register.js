var bPWCheck = "";

$(document).ready(function(){
    $('#eyecon').on('hover',function(){
        if($('#eyestat').hasClass('active')){
        	$('#eyestat').toggleClass('dis-active');
            $('#eyecon').attr('class',"fas fa-eye-slash");
            $('#password').attr('type',"text");
        }else{
        	$('#eyestat').toggleClass('active');
        	$('#eyecon').attr('class',"fas fa-eye");
            $('#password').attr('type',"password");
        }
    });
});

function validate() {

	var id = document.getElementById("id");
	var password = document.getElementById("password");
	var password2 = document.getElementById("password2");
	var name = document.getElementById("name");

	var phone_str = "";
	var phone = document.getElementById("phone");
	var phone1 = document.getElementById("phone1");
	var phone2 = document.getElementById("phone2");
	var phone3 = document.getElementById("phone3");
	
	var address_str = "";
	var address = document.getElementById("address");
	var address1 = document.getElementById("address1");
	var address2 = document.getElementById("address2");

	if(bPWCheck == "true"){
		if (phone2.value == "" || phone3.value == "") {
			alert("휴대폰번호가 입력되지 않았습니다.");
			if (phone2.value == "") phone2.focus();
			else if (phone3.value == "") phone3.focus();
			return false;
		}

		if (name.value == "") {
			alert("이름이 입력되지 않았습니다.");
			name.focus();
			return false;
		}

		if (address2.value == "") {
			alert("주소가 입력되지 않았습니다.");
			address2.focus();
			return false;
		}

		if (id.value == "") {
			alert("아이디가 입력되지 않았습니다.");
			id.focus();
			return false;
		}

		if (password.value == "") {
			alert("비밀번호가 입력되지 않았습니다.");
			password.focus();
			return false;
		}

		if (password.value != password2.value) {
			alert("비밀번호 확인에 실패하였습니다.");
			password2.focus();
			return false;
		}

		phone_str = phone1.value+"-"+phone2.value+"-"+phone3.value;
		phone.value = phone_str;
		address_str = address1.value+" "+address2.value;
		address.value = address_str;
		
		alert("회원가입에 성공하였습니다.");
	}
	else{
		alert("비밀번호가 입력되지 않았거나 규격에 맞지 않습니다.");
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
			}
			else if (user_pw1 == user_pw2) {
				console.log("비밀번호가 일치함");
				bPWCheck = "true";
			}
		}
		else{
			console.log("비밀번호 규격에 맞지않음");
		}
}