package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/", "/image/story"})
    public String story(){
        return "image/story";
    }
    @GetMapping("/image/popular")
    public String popular(Model model){
        //Api는 데이터를 return하는 서버!! (=ajax사용하는 경우)
        //이건 그냥 모델에 담고 데이터를 들고 가기만 하면 된다!!
        List<Image> images = imageService.인기사진();
        model.addAttribute("images", images);
        return "image/popular";
    }
    @GetMapping("/image/upload")
    public String upload(){
        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails){

        //깍두기 .. (코드가 똑같지 않아서 Advice에서 낚아챌 수 없었음)
        if(imageUploadDto.getFile().isEmpty()){
            throw new CustomValidationException("이미지가 첨부되지 않았습니다", null);
        }
        //서비스 호출
        imageService.사진업로드(imageUploadDto, principalDetails);
        return "redirect:/user/"+principalDetails.getUser().getId();
    }
}
