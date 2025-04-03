package ru.job4j.socialmediaapi.repository.postimage;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmediaapi.entity.PostImage;

@Repository
public interface PostImageRepository extends ListCrudRepository<PostImage, Long> {

    @Modifying
    @Query("""
            DELETE FROM PostImage pi
            WHERE pi.post.id = :postId
            AND pi.image.id = :imageId
           """)
    void deleteByPostIdAndImageId(@Param("postId") Long postId, @Param("imageId") Long imageId);
}
