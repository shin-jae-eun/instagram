package com.cos.photogramstart.domain.subscribe;

import com.cos.photogramstart.domain.user.User;
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
                        name="subscribe_uk",
                        columnNames = {"fromUserId", "toUserId"}
                )
        }
)
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략이 DB를 따라간다.
    private int id;

    @JoinColumn(name = "fromUserId")
    @ManyToOne(cascade = CascadeType.ALL)
    private User fromUser;

    @JoinColumn(name = "toUserId")
    @ManyToOne(cascade = CascadeType.ALL)
    private User toUser;


    private LocalDateTime createDate;

    @PrePersist //DB에 INSERT 되기 직전에 실행
    public void creaDate(){
        this.createDate = LocalDateTime.now();
    }

}
