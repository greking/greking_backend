package com.greking.Greking.Recommendation.repository;

import com.greking.Greking.Review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends JpaRepository<Review, Long> {
}
