package com.greking.Greking.User.domain;


import com.greking.Greking.Survey.domain.FitnessLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_entity")

public class User {

    @Id
    private String userid;

    @Email
    @Size(max = 25)
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @Size(max = 20)
    @NotBlank
    private String nickname;

    private boolean termsOfServiceAccepted; //개인정보 활용동의
    private boolean privacyPolicyAccepted; //서비스 이용약관 동의

    @Embedded
    private Grade grade = new Grade(); //Grade필드를 포함시킴

    @Enumerated(EnumType.STRING)
    private FitnessLevel fitnessLevel; //피트니스 레벨

}
