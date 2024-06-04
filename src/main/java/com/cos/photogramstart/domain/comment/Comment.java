package com.cos.photogramstart.domain.comment;

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
public class Comment { //n, n
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략이 DB를 따라간다.
    private int id;

    @Column(length = 100, nullable = false)
    private String content;

    @JsonIgnoreProperties({"images"})
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user; //1

    @ManyToOne
    @JoinColumn(name = "imageId")
    private Image image; //1

    private LocalDateTime createDate;

    @PrePersist //DB에 INSERT 되기 직전에 실행
    public void creaDate(){
        this.createDate = LocalDateTime.now();
    }
}
