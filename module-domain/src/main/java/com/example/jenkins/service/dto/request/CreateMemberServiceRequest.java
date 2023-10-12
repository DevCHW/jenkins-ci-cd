package com.example.jenkins.service.dto.request;

import com.example.jenkins.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateMemberServiceRequest {

    private String name;
    private String email;
    private String nickname;
    private String password;

    @Builder
    public CreateMemberServiceRequest(String name, String email, String nickname, String password) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .password(password)
                .build();
    }

}
