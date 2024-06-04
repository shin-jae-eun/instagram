/**
  1. 유저 프로파일 페이지
  (1) 유저 프로파일 페이지 구독하기, 구독취소
  (2) 구독자 정보 모달 보기
  (3) 구독자 정보 모달에서 구독하기, 구독취소
  (4) 유저 프로필 사진 변경
  (5) 사용자 정보 메뉴 열기 닫기
  (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달 
  (8) 구독자 정보 모달 닫기
 */

// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(toUserId, obj) {
	if ($(obj).text() === "구독취소") {
	    $.ajax({
            type: "delete",
            url: "/api/subscribe/" + toUserId,
            dataType : "json"
	    }).done(res=>{
            $(obj).text("구독하기");
            $(obj).toggleClass("blue");
	    }).fail(error=>{
            console.log("구독 취소하기 실패", error);
	    });

	} else {
	    $.ajax({
           type: "post",
            url: "/api/subscribe/" + toUserId,
           dataType : "json"
        }).done(res=>{
           $(obj).text("구독취소");
           $(obj).toggleClass("blue");
        }).fail(error=>{
           console.log("구독하기 실패", error);
        });
	}
}

// (2) 구독자 정보  모달 보기
function subscribeInfoModalOpen(pageUserId) {
	$(".modal-subscribe").css("display", "flex");

    $.ajax({
        url: `/api/user/${pageUserId}/subscribe`,
        dataType: "json"
    }).done(res=>{
        console.log(res.data);
        res.data.forEach((u)=>{
            let item = getSubscribeModalItem(u);
            $("#subscribeModalList").append(item);
        })
    }).fail(error=>{
        console.log("구독정보 불러오기 오류", error);
    });
}

function getSubscribeModalItem() {
    let item = `<div class="subscribe__item" id="subscribeModalItem-${u.id}">
                				<div class="subscribe__img">
                					<img src="/upload/${u.profileImageUrl}" onerror="this.src='/images/person.jpeg'"/>
                				</div>
                				<div class="subscribe__text">
                					<h2>${u.username}</h2>
                				</div>
                				<div class="subscribe__btn">`;

                                if(u.equalUserState){ //동일 유저가 아닐 대 버튼이 만들어져야함
                                    if(u.subscribeState){ //구독한 상태
                                      item += `<button class="cta blue" onclick="toggleSubscribe(${u.id}this)">구독취소</button>`;
                                    }
                                    else{ //구독안한 상태
                                      item += `<button class="cta blue" onclick="toggleSubscribe(${u.id}this)">구독하기</button>`;
                                    }
                                }
                				Item +=`
                				</div>
                			</div>`;

    return item;
}

// (3) 유저 프로파일 사진 변경 (완)
function profileImageUpload(pageUserId, principalId) {

	if (pageUserId != principalId) {
		alert("프로필 사진 수정할 권한이 없습니다");
		return;
	}

	$("#userProfileImageInput").click();

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}

		//서버에 이미지 전송
		  let profileImageForm = $("#userProfileImageForm")[0];
        console.log(profileImageForm);

		//formData객체를 이용하면 form 태그의 필드와 그 값을 나타내는 일련의 key/value 쌍을 담을 수 있다.
	let formData = new FormData(profileImageForm);

        $.ajax({
            type: "put",
            url: `/api/user/${principalId}/profileImageUrl`,
            data: formData,
            contentType: false,
            processData: false,
            enctype: "multipart/form-data",
            dataType: "json"
        }).done(res => {
            // 사진 전송 성공시 이미지 변경
            let reader = new FileReader();
            reader.onload = (e) => {
                $("#userProfileImage").attr("src", e.target.result);
            }
            reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
        }).fail(error => {
            console.log("오류", error);
		});
	});
}


// (4) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (5) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (6) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (7) 구독자 정보 모달 닫기
function modalClose() {
	$(".modal-subscribe").css("display", "none");
	location.reload();
}

// +(8) 회원탈퇴
function deleteUser(){
    if(confirm("정말로 회원탈퇴를 하시겠습니까?")){
    $.ajax({
        type: "delete",
        url: "/api/user/delete",
        dataType: "json"
    }).done(res=>{
        alert(res.message);
        location.href= "/logout";
    }).fail(error=>{
        console.log("회원탈퇴 실패", error);
        alert("회원탈퇴 중 문제가 발생했습니다. 다시 시도해주세요.");
    })};
}





