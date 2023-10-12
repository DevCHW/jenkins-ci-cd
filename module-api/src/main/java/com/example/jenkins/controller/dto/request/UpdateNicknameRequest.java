package com.example.jenkins.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdateNicknameRequest {

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    public UpdateNicknameRequest(String nickname) {
        this.nickname = nickname;
    }
}
