package com.greking.Greking.User.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Embeddable
@Getter
@Setter
public class Grade {

    private int level = 1; //레벨
    private int experience = 0; //숙련도

    //레벨업에 필요한 경험치
    public static final int LEVEL_UP_THRESHOLD = 6000;

    //난이도별 경험치 (이 값은 서비스 로지에서 사용)
    public static final Map<Integer, Integer> DIFFICULTY_EXPERIENCE_MAP = Map.of(
            1, 100,
            2, 300,
            3, 600,
            4, 1000,
            5, 1500
    );
}
