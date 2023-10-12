package com.example.jenkins.controller;

import com.example.jenkins.controller.dto.request.CreateMemberRequest;
import com.example.jenkins.controller.dto.request.UpdateNicknameRequest;
import com.example.jenkins.controller.dto.request.mapper.ServiceLayerDtoMapper;
import com.example.jenkins.response.ApiResponse;
import com.example.jenkins.service.JenkinsService;
import com.example.jenkins.service.dto.response.MemberResponse;
import com.example.jenkins.service.dto.response.UpdateNicknameResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final JenkinsService jenkinsService;

    @GetMapping("/health_check")
    public ApiResponse<String> healthCheck() {
        return ApiResponse.ok("ok");
    }

    @PostMapping("/members")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemberResponse> signUp(@RequestBody @Valid CreateMemberRequest request) {
        MemberResponse response = jenkinsService.create(ServiceLayerDtoMapper.mapping(request));
        return ApiResponse.created(response);
    }

    @GetMapping("/members/{memberId}")
    public ApiResponse<MemberResponse> getOneMember(@PathVariable("memberId") Long memberId) {
        MemberResponse response = jenkinsService.getMember(memberId);
        return ApiResponse.ok(response);
    }

    @GetMapping("/members")
    public ApiResponse<List<MemberResponse>> getOneMember() {
        List<MemberResponse> response = jenkinsService.getAll();
        return ApiResponse.ok(response);
    }

    @PatchMapping("/members/{memberId}/nickname")
    public ApiResponse<UpdateNicknameResponse> changeNickname(@RequestBody @Valid UpdateNicknameRequest request,
                                                              @PathVariable("memberId") Long memberId) {

        UpdateNicknameResponse response = jenkinsService.changeNickname(ServiceLayerDtoMapper.mapping(request), memberId);
        return ApiResponse.ok(response);
    }

}
