package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//Notnull = null값체크
//NotEmpty = 빈값이거나 null체크
//NotBlank = 빈값이거나 null체크 그리고 빈 공백까지

@Data
public class CommentDto {
    @NotBlank //빈값이거나 null체크 공백까지도 포함
    private String content;
    @NotNull //빈값체크
    private Integer imageId;
    //toEntity가 필요 없다. 이유는 ?

}
