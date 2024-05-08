package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final EntityManager em; //Repository는 EntityManager를 구현해서 만들어져잇는 구현체!

    @Transactional(readOnly = true)
    public List<SubscribeDto> 구독리스트(Integer principalId, Integer pageUserId){
        //쿼리 준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
        sb.append("(SELECT 1 FROM new_insta.subscribe WHERE fromUserId = ? AND toUserId = u.id) subscribeState, ");
        sb.append("if((u.id = ?), 1, 0) equalUserState ");
        sb.append("FROM new_insta.user u INNER JOIN new_insta.subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?"); //세미콜론 첨부하면 안됨

        //1. 물음표 principalId
        //2. 물음표 principalId
        //3. 마지막물음표 pageUserId

        //쿼리에 바인딩
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);
        //쿼리 실행 qlrm 라이브러리 필요 = Dto에 DB결과를 매핑하기 위해서
        List<SubscribeDto> subscribeDtos = new ArrayList<>();
        List<Object[]> resultList = query.getResultList();
        for (Object[] row : resultList) {
            subscribeDtos.add(new SubscribeDto(
                    (BigInteger) row[0],
                    (String) row[1],
                    (String) row[2],
                    (Integer) row[3],
                    (Integer) row[4]
            ));
        }
        return subscribeDtos;
    }
    @Transactional
    public void 구독하기(int fromUserId, int toUserId){
        try{
            subscribeRepository.mSubscribe(fromUserId, toUserId);
        } catch (Exception e){
            throw new CustomApiException("이미 구독을 하였습니다.");
        }
    }

    @Transactional
    public void 구독취소하기(int fromUserId, int toUserId){
        subscribeRepository.mUnSubscribe(fromUserId, toUserId);
    }

}
