package com.example.jenkins.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateNicknameResponse {
    private String oldNickname;
    private String changedNickname;
}
