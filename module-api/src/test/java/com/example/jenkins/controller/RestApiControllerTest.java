package com.example.jenkins.controller;

import com.example.jenkins.RestDocsSupport;
import com.example.jenkins.controller.dto.request.CreateMemberRequest;
import com.example.jenkins.controller.dto.request.UpdateNicknameRequest;
import com.example.jenkins.service.JenkinsService;
import com.example.jenkins.service.dto.request.CreateMemberServiceRequest;
import com.example.jenkins.service.dto.request.UpdateNicknameServiceRequest;
import com.example.jenkins.service.dto.response.MemberResponse;
import com.example.jenkins.service.dto.response.UpdateNicknameResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestApiControllerTest extends RestDocsSupport {

    private JenkinsService jenkinsService = Mockito.mock(JenkinsService.class);
    @Override
    protected Object initController() {
        return new RestApiController(jenkinsService);
    }

    @DisplayName("ok를 반환한다.")
    @Test
    void health_check_success() throws Exception {
        // when & then
        mockMvc.perform(
                        get("/health_check")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").value("ok"))
                .andDo(document("jenkins-server-health-check",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data").type(JsonFieldType.STRING).description("ok 메시지")
                                )
                        )
                );
    }

    @DisplayName("회원가입에 필요한 정보를 받아 회원가입을 할 수 있다.")
    @Test
    void signUp_success() throws Exception {
        Long id = 1L;
        String name = "test name";
        String email = "test123@test.com";
        String nickname = "test nickname";
        String password = "test password";
        CreateMemberRequest request = CreateMemberRequest.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .password(password)
                .build();

        MemberResponse response = MemberResponse.builder()
                .id(id)
                .name(name)
                .email(email)
                .nickname(nickname)
                .build();

        given(jenkinsService.create(any(CreateMemberServiceRequest.class)))
                        .willReturn(response);
        // when & then
        mockMvc.perform(
                        post("/members")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("201"))
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.message").value("CREATED"))
                .andExpect(jsonPath("$.data").exists())
                .andDo(document("member-create",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("회원 정보"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("회원 ID"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("닉네임")
                                )
                        )
                );
    }

    @DisplayName("회원 ID를 받아 회원정보를 조회할 수 있다.")
    @Test
    void get_one_member_success() throws Exception {
        Long memberId = 1L;
        String name = "test name";
        String email = "test123@test.com";
        String nickname = "test nickname";

        MemberResponse response = MemberResponse.builder()
                .id(memberId)
                .name(name)
                .email(email)
                .nickname(nickname)
                .build();

        given(jenkinsService.getMember(anyLong()))
                .willReturn(response);
        // when & then
        mockMvc.perform(
                        get("/members/{memberId}", memberId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").exists())
                .andDo(document("member-get-one",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("memberId").description("회원 ID")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("회원 정보"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("회원 ID"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("닉네임")
                                )
                        )
                );
    }

    @DisplayName("변경할 닉네임과 회원 ID를 받아 회원 닉네임을 변경할 수 있다.")
    @Test
    void change_nickname_success() throws Exception {
        Long memberId = 1L;
        String oldNickname = "testOldNickname";
        String changedNickname = "testChangedNickname";

        UpdateNicknameRequest request = new UpdateNicknameRequest(changedNickname);
        UpdateNicknameResponse response = new UpdateNicknameResponse(oldNickname, changedNickname);
        given(jenkinsService.changeNickname(any(UpdateNicknameServiceRequest.class), anyLong()))
                .willReturn(response);
        // when & then
        mockMvc.perform(
                        patch("/members/{memberId}/nickname", memberId)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").exists())
                .andDo(document("member-update-nickname",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("memberId").description("회원 ID")
                                ),
                                requestFields(
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("변경 닉네임 정보"),
                                        fieldWithPath("data.oldNickname").type(JsonFieldType.STRING).description("변경 전 닉네임"),
                                        fieldWithPath("data.changedNickname").type(JsonFieldType.STRING).description("변경 후 닉네임")
                                )
                        )
                );
    }

    @DisplayName("회원 정보 목록을 조회한다.")
    @Test
    void get_all_success() throws Exception {
        Long memberId = 1L;
        String name = "testName";
        String email = "test123@test.com";
        String nickname = "testNickname";

        MemberResponse memberResponse1 = new MemberResponse(memberId, name, email, nickname);
        MemberResponse memberResponse2 = new MemberResponse(memberId + 1, name + 1, email + 1, nickname + 1);
        MemberResponse memberResponse3 = new MemberResponse(memberId + 2, name + 2, email + 2, nickname + 2);
        MemberResponse memberResponse4 = new MemberResponse(memberId + 3, name + 3, email + 3, nickname + 3);
        MemberResponse memberResponse5 = new MemberResponse(memberId + 4, name + 4, email + 4, nickname + 4);
        List<MemberResponse> response = List.of(memberResponse1, memberResponse2, memberResponse3, memberResponse4, memberResponse5);

        given(jenkinsService.getAll())
                .willReturn(response);

        // when & then
        mockMvc.perform(
                        get("/members")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document("member-get-all",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("상태 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("회원 정보 목록"),
                                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("회원 ID"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data[].email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data[].nickname").type(JsonFieldType.STRING).description("닉네임")
                                )
                        )
                );
    }


}