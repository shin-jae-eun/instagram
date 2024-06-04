package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final SubscribeService subscribeService;

    @PutMapping("/api/user/{principalId}/profileImageUrl")
    public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId, MultipartFile profileImageFile,
                                                   @AuthenticationPrincipal PrincipalDetails principalDetails){
        User userEntity = userService.회원프로필사진변경(principalId, profileImageFile);
        principalDetails.setUser(userEntity); //세션변경
        return new ResponseEntity<>(new CMRespDto<>(1, "프로필사진 변경성공", null), HttpStatus.OK);

    }

    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@PathVariable Integer pageUserId,
                                           @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<SubscribeDto> subscribeDtos = subscribeService.구독리스트(principalDetails.getUser().getId(), pageUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "구독자 정보 리스트 들고오기 성공", subscribeDtos), HttpStatus.OK);
    }

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(
            @PathVariable int id,
            @Valid UserUpdateDto userUpdateDto,
            BindingResult bindingResult, //꼭 @Vaild 적혀있는 곳 다음에 적어줘야함.
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
            // 실제 업데이트 로직 구현
            User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
            principalDetails.setUser(userEntity); //세션 정보 변경
            return new CMRespDto<>(1, "회원수정완료", userEntity);
            //응답 시에 userEntity의 모든 getter함수가 호출되서 JSON으로 파싱하여 응답한다.
    }
    @DeleteMapping("/api/user/delete")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal PrincipalDetails principalDetails){
        userService.회원탈퇴(principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "회원탈퇴가 완료되었습니다.", null),HttpStatus.OK);
    }
}
