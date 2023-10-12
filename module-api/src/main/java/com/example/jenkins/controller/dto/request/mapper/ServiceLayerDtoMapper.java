package com.example.jenkins.controller.dto.request.mapper;

import com.example.jenkins.controller.dto.request.CreateMemberRequest;
import com.example.jenkins.controller.dto.request.UpdateNicknameRequest;
import com.example.jenkins.service.dto.request.CreateMemberServiceRequest;
import com.example.jenkins.service.dto.request.UpdateNicknameServiceRequest;

public class ServiceLayerDtoMapper {

    private ServiceLayerDtoMapper() {

    }

    public static CreateMemberServiceRequest mapping(CreateMemberRequest request) {
        return CreateMemberServiceRequest.builder()
                .name(request.getName())
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(request.getPassword())
                .build();
    }

    public static UpdateNicknameServiceRequest mapping(UpdateNicknameRequest request) {
        return new UpdateNicknameServiceRequest(request.getNickname());
    }
}
