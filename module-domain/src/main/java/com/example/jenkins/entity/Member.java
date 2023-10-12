package com.example.jenkins.entity;

import com.example.jenkins.error.ErrorCode;
import com.example.jenkins.exception.BusinessException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 이름

    @Column(nullable = false)
    private String email; // 이메일

    @Column(nullable = false)
    private String nickname; //닉네임

    @Column(nullable = false)
    private String password; //비밀번호

    @Builder
    public Member(String name, String email, String nickname, String password) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public void changeNickname(String nickname) {
        if (!StringUtils.hasText(nickname)) {
            throw new BusinessException(ErrorCode.CHANGE_NICKNAME_FAIL, ErrorCode.CHANGE_NICKNAME_FAIL.getMessage());
        }
        this.nickname = nickname;
    }
}
