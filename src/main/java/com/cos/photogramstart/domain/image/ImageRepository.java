package com.cos.photogramstart.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query(value = "SELECT * FROM new_insta.image WHERE userId IN(SELECT toUserId FROM new_insta.subscribe WHERE fromUserId = :principalId) ORDER BY id DESC", nativeQuery = true)
    Page<Image> mStory(int principalId, Pageable pageable);


    @Query(value = "SELECT i.* FROM new_insta.image i INNER JOIN (SELECT imageId, COUNT(imageId) likeCount FROM new_insta.likes GROUP BY imageid) c ON i.id = c.imageId ORDER BY likeCount DESC", nativeQuery = true)
    List<Image> mpopular();

    @Transactional
    void deleteByUserId(int userId);
}
