package com.cos.photogramstart.domain.user;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

// JPA - Java Persistence API ( 자바로 데이터를 영구적으로 저장할 수 있는 API를 제공)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략이 DB를 따라간다.
    private int id;
    @Column(unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    private String website; //웹사이트
    private String bio; //자기소개
    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;
    private String profileImageUrl;
    private String role; //권한

    //User를 셀렉트할때 해당 User id로 등록된 image들을 다 가져와
    //Lazy = User를 select할 때 해당 user id로 등록된 image를 다 가져와! 대신 getimages()가 호출될 때 가져와
    //Eager = user를 select할 때 해당 user id로 등록된 image를 전부 join해서 가져와
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true) // 나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼 만들지X
    @JsonIgnoreProperties({"user"})
    private List<Image> images;

    private LocalDateTime createDate;
    @PrePersist //DB에 INSERT 되기 직전에 실행
    public void creaDate(){
        this.createDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", bio='" + bio + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", role='" + role + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
