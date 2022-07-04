const usernameInput = document.querySelector(".username-input");
const usernameCheckMsg = document.querySelector(".username-check-msg");

let signupFlag = false; // 가입하기 버튼을 눌렀을 때 이미 있는 아이디면 가입하며 안되기 때문에 false를 줌

usernameInput.onblur = () => {
	let username = usernameInput.value;
	$.ajax({
		type: "get",
		url: `/check/username?username=${username}`,
		dataType: "text", // 응답받을 때의 데이터 형태
		success: (response) => {
			if(response == "true"){ // dataType에 text형태로 받기때문에 true가 텍스트로 들어감
				signupFlag = false;
				usernameCheckMsg.innerHTML = `<td colspan="2">${username}은(는) 이미 존재하는 사용자이름 입니다.</td>`;
			}else{
				signupFlag = true;
				usernameCheckMsg.innerHTML = `<td colspan="2">${username}은(는) 가입 할 수 있는 사용자이름 입니다.</td>`;
			}
		},
		error: (request, status, error) => { // status는 자리채우는 용도 status는 request안에 있는것을 사용
			alert("요청 실패");
			console.log(request.status);
			console.log(request.responseText);
			console.log(error);
		}
	});
}