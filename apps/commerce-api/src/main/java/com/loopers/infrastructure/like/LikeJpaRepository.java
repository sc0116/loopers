package com.loopers.infrastructure.like;

import com.loopers.domain.like.Like;
import com.loopers.domain.like.LikeTarget;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeJpaRepository extends JpaRepository<Like, Long> {

	boolean existsByUserIdAndTarget(Long userId, LikeTarget target);

	Optional<Like> findByUserIdAndTarget(Long userId, LikeTarget target);
}
