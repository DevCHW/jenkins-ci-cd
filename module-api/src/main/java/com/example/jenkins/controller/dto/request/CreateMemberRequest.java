package com.example.jenkins.controller.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CreateMemberRequest {

    @NotBlank(message = "이름은 필수입니다.")
    private String name; // 이름

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email; // 이메일

    @NotBlank(message = "이름은 필수입니다.")
    private String nickname; //닉네임

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password; // 비밀번호

    @Builder
    public CreateMemberRequest(String name, String email, String nickname, String password) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

}
