package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import com.cos.photogramstart.domain.comment.Comment;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략이 DB를 따라간다.
    private int id;
    private String caption;
    private String postImageUrl; //사진을 전송받아서 그 사진을 서버 속 특정 폴더에 저장 - DB에 그 저장된 경로를 insert

    @JsonIgnoreProperties({"images"})
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user; //

    //이미지 좋아요
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes;

    @Transient //DB에 칼럼이 만들어지지 않는다.
    private boolean likeState;

    @Transient
    private int likeCount;

    //이미지 댓글 기능
    @OrderBy("id DESC")
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    private LocalDateTime createDate;

    @PrePersist //DB에 INSERT 되기 직전에 실행
    public void creaDate(){
        this.createDate = LocalDateTime.now();
    }

//    @Override
//    public String toString() {
//        return "Image{" +
//                "id=" + id +
//                ", caption='" + caption + '\'' +
//                ", postImageUrl='" + postImageUrl + '\'' +
//                ", createDate=" + createDate +
//                '}';
//    } 오브젝트를 콘솔에 출력할 때 문제가 될 수 있어서 (무한참조) User 부분을 출력되지 않게 함
}
