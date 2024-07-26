package com.greking.Greking.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_entity")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Size(max = 25)
    @NotBlank
    private String email;

    @Size(min = 8, max = 20)
    @NotBlank
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @Size(max = 20)
    @NotBlank
    private String nickname;

    private boolean termsOfServiceAccepted; //개인정보 활용동의
    private boolean privacyPolicyAccepted; //서비스 이용약관 동의


    private int level = 1; //레벨
    private int experience = 0; //숙련도

}
