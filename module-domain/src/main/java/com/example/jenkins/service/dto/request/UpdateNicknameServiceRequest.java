package com.example.jenkins.service.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateNicknameServiceRequest {

    private String nickname;

    public UpdateNicknameServiceRequest(String nickname) {
        this.nickname = nickname;
    }

}
