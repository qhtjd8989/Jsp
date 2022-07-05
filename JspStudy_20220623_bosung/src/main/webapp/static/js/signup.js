const usernameInput = document.querySelector(".username-input");
const usernameCheckMsg = document.querySelector(".username-check-msg");
const inputItems = document.querySelectorAll("table input");
const submitButton = document.querySelector(".submit-button");

let signupFlag = [false, false, false, false, false]; // 가입하기 버튼을 눌렀을 때 이미 있는 아이디면 가입하며 안되기 때문에 false를 줌

submitButton.onclick = () => {
	for(let i = 0; i < inputItems.length; i++){
		if(isEmpty(inputItems[i].value)){
			alert((i == 0 ? "이름을" 
				: i == 1 ? "이메일을"
				: i == 2 ? "사용자 이름을"
				: "비밀번호를")
				+ " 입력해 주세요.");
				
			singsignupFlag[i] = false; 
			
			return;
		}
		signupFlag[i] = true;
	}
	
	if(signupFlag[4] == false){
		alert("사용자이름 중복확인이 필요합니다.");
		return;
	}
	
	if(!signupFlag.includes(false)){
		submit();
	}
	
}

usernameInput.onblur = () => {
	let username = usernameInput.value;
	$.ajax({
		type: "get",
		url: `/check/username?username=${username}`,
		dataType: "text", // 응답받을 때의 데이터 형태
		success: (response) => {
			if(response == "true"){ // dataType에 text형태로 받기때문에 true가 텍스트로 들어감
				signupFlag[4] = false;
				usernameCheckMsg.innerHTML = `<td colspan="2">${username}은(는) 이미 존재하는 사용자이름 입니다.</td>`;
			}else{
				signupFlag[4] = true;
				usernameCheckMsg.innerHTML = `<td colspan="2">${username}은(는) 가입 할 수 있는 사용자이름 입니다.</td>`;
			}
		},
		error: (request, status, error) => { // status는 자리채우는 용도, status는 request안에 있는것을 사용
			alert("요청 실패");
			console.log(request.status);
			console.log(request.responseText);
			console.log(error);
		}
	});
}

function submit() {
	$.ajax({
		type:"post",
		url: `/signup`,
		data: { //post 요청일때에는 {}로 묶어서 객체로 요청을 보내야됨
			name: inputItems[0].value,
			email: inputItems[1].value,
			username: inputItems[2].value,
			password: inputItems[3].value,
		},
		dataType: "text",
		success: (response) => {
			if(response == "true"){
				alert("축하합니다!\n회원가입에 성공하였습니다.");
				location.replace("/signin");
			}else{
				alert("회원가입에 실패하였습니다.\n다시 시도해 주세요.");
				usernameCheckMsg.innerHTML = ``;
				document.querySelector("form").reset(); // 리셋버튼과 동일한 동작
			}
		},
		error: errorMessage
	})
}

function errorMessage(request, status, error) {
	alert("요청 실패");
	console.log(request.status);
	console.log(request.responseText);
	console.log(error);
}

function isEmpty(str) {
	return str == "" || str == null || typeof str == undefined;
}






