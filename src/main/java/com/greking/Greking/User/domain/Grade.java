package com.greking.Greking.User.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Grade {
    private int level = 1; //레벨
    private int experience = 0; //숙련도

}
