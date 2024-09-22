package com.greking.Greking.Recommendation.service;


import com.greking.Greking.Contents.domain.Course;
import com.greking.Greking.User.domain.User;

import java.util.List;

public interface RecommendationService {

    List<Course> recommendCoursesForUser(String userId);
}
