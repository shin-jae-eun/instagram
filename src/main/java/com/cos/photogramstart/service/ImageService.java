package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<Image> 인기사진(){
        return imageRepository.mpopular();
    }

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional(readOnly = true) //영속성 컨텍스트에서 변경감지를 해서 더치테킹, flush 반영
    public Page<Image> 이미지스토리(int principalId, Pageable pageable){
        Page<Image> images = imageRepository.mStory(principalId, pageable);

        //2(cos)로 로그인하
        //images에 좋아요 담기
        images.forEach((image)->{
            image.setLikeCount(image.getLikes().size());

            image.getLikes().forEach((like)->{
                if(like.getUser().getId() == principalId){
                    //해당이미지에 좋아요한 사람들을 찾아서 현재 로긘한 사람이 좋아요한 것인지 비교
                    image.setLikeState(true);
                }
            });
        });

        return images;
    }

    @Transactional
    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails){
        UUID uuid = UUID.randomUUID(); //uuid로 이미지 파일에 고유이름을 붙여줌.
        String imageFileName = uuid+ "_" + imageUploadDto.getFile().getOriginalFilename(); //1.jpg같은 실제 파일 이름
        System.out.println("imageFileName = " + imageFileName);

        Path imageFilePath = Paths.get(uploadFolder+imageFileName);

        //통신, I/O가 일어날 때 예외가 발생할 수 있다.
        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }

        //image 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser()); //f9f73caf-1611-4e44-9cf1-8971194e3251_KakaoTalk_20221002_155003318_06.jpg
        Image imageEntity = imageRepository.save(image);
//        System.out.println("imageEntity = " + imageEntity);
    }
}
