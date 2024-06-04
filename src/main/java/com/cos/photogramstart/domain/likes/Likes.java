package com.cos.photogramstart.domain.likes;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="likes_uk",
                        columnNames = {"imageId", "userId"}
                )
        }
)
public class Likes { // N

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략이 DB를 따라간다.
    private int id;

    //return시, images를 return할 때 image를 읽다가 likes를 return한다.
    //그러나 여기서도 image를 리턴해서 무한참조된다.
    @JoinColumn(name = "imageId")
    @ManyToOne
    private Image image; //1

    //오류가 터지고나서 잡아봅시다.
    @JsonIgnoreProperties({"images"})
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user; //1

    private LocalDateTime createDate;

    @PrePersist //DB에 INSERT 되기 직전에 실행
    public void creaDate(){
        this.createDate = LocalDateTime.now();
    }

}
