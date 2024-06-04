package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.domain.likes.LikesRepository;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SubscribeRepository subscribeRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;
    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void 회원탈퇴(int userId){
        // 구독 정보 삭제
        subscribeRepository.deleteByFromUserId(userId);
        subscribeRepository.deleteByToUserId(userId);

        // 좋아요 정보 삭제
        likesRepository.deleteByUserId(userId);

        // 댓글 정보 삭제
        commentRepository.deleteByUserId(userId);

        // 이미지 정보 삭제
        imageRepository.deleteByUserId(userId);

        // 사용자 삭제
        userRepository.deleteById(userId);
    }
    @Transactional
    public User 회원프로필사진변경(int principalId, MultipartFile profileImageFile){
        UUID uuid = UUID.randomUUID(); //uuid로 이미지 파일에 고유이름을 붙여줌.
        String imageFileName = uuid+ "_" + profileImageFile.getOriginalFilename(); //1.jpg같은 실제 파일 이름
        System.out.println("imageFileName = " + imageFileName);

        Path imageFilePath = Paths.get(uploadFolder+imageFileName);

        //통신, I/O가 일어날 때 예외가 발생할 수 있다.
        try {
            Files.write(imageFilePath, profileImageFile.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
        User userEntity = userRepository.findById(principalId).orElseThrow(()->{
            throw new CustomApiException("유저를 찾을 수 없습니다");
        });
        userEntity.setProfileImageUrl(imageFileName);

        return userEntity;
    }//더티체킹으로 업데이트 됨.

    @Transactional(readOnly = true)
    public UserProfileDto 회원프로필(int pageUserid, int principalId){
        UserProfileDto dto = new UserProfileDto();

        //SELECT * FROM image WHERE userId = :userId;
        User userEntity = userRepository.findById(pageUserid).orElseThrow(()->{
            throw new CustomException("해당 프로필은 없는 페이지입니다.");
        });
        dto.setUser(userEntity);
        dto.setPageOwnerState(pageUserid == principalId); // 1은 페이지 주인, -1은 주인이 아님
        dto.setImageCount(userEntity.getImages().size());

        int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserid);
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserid);

        dto.setSubscribeState(subscribeState == 1);
        dto.setSubscribeCount(subscribeCount);

        //좋아요 카운트 추가하기
        userEntity.getImages().forEach((image)->{
            image.setLikeCount(image.getLikes().size());
        });

        return dto;
    }
    @Transactional
    public User 회원수정(int id, User user){
        //1.영속화
        User userEntity = userRepository.findById(id).orElseThrow( ()->{return new CustomValidationApiException("찾을 수 없는 id입니다.");}); //1. 무조건 찾았따 걱정마 get(), 2.못찾았어 익섹션발동시킬게 orElseThrow()
        userEntity.setName(user.getName());

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setPassword(encPassword);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        //2. 영속화된 오브젝트를 수정 -> 더티체킹(업데이트 완료
        return userEntity;
        //더티체킹이 일어나서 업데이트가 완료됨
    }
}
