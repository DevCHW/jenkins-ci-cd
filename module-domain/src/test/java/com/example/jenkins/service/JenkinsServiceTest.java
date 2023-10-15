package com.example.jenkins.service;


import com.example.jenkins.entity.Member;
import com.example.jenkins.repository.MemberRepository;
import com.example.jenkins.service.dto.request.CreateMemberServiceRequest;
import com.example.jenkins.service.dto.request.UpdateNicknameServiceRequest;
import com.example.jenkins.service.dto.response.MemberResponse;
import com.example.jenkins.service.dto.response.UpdateNicknameResponse;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class JenkinsServiceTest {

    @Autowired
    private JenkinsService jenkinsService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;
    @DisplayName("회원가입에 필요한 정보를 받아서 회원가입을 할 수 있다.")
    @Test
    void create_success() {
        // given
        String name = "test_name";
        String email = "example@test.com";
        String nickname = "test_nickname";
        String password = "test_password";
        CreateMemberServiceRequest request = CreateMemberServiceRequest.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .password(password)
                .build();

        // when
        MemberResponse result = jenkinsService.create(request);
        entityManager.flush();
        entityManager.clear();
        // then

        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getNickname()).isEqualTo(nickname);
    }
    
    @DisplayName("회원 ID를 받아 회원 정보를 조회할 수 있다.")
    @Test
    void get_member_success() {
        // given
        Member member = createMember("TEST_NAME", "test@test.com", "test_nickname", "test_password");

        Member savedMember = memberRepository.save(member);
        // when

        MemberResponse result = jenkinsService.getMember(savedMember.getId());

        // then
        assertThat(result.getId()).isEqualTo(savedMember.getId());
        assertThat(result.getName()).isEqualTo(savedMember.getName());
        assertThat(result.getEmail()).isEqualTo(savedMember.getEmail());
        assertThat(result.getNickname()).isEqualTo(savedMember.getNickname());
    }
    
    @DisplayName("회원 전체조회를 할 수 있다.")
    @Test
    void get_all_success() {
        // given
        Member member1 = createMember("TEST_NAME1", "test1@test.com", "test_nickname1", "test_password1");
        Member member2 = createMember("TEST_NAME2", "test2@test.com", "test_nickname2", "test_password2");
        Member member3 = createMember("TEST_NAME3", "test3@test.com", "test_nickname3", "test_password3");
        List<Member> members = memberRepository.saveAll(List.of(member1, member2, member3));
        // when
        List<MemberResponse> result = jenkinsService.getAll();

        // then
        assertThat(result).hasSize(3)
                .extracting("id", "name", "email", "nickname")
                .containsExactlyInAnyOrder(
                        // 실패하는 테스트
                        tuple(members.get(1).getId(), members.get(0).getName(), members.get(0).getEmail(), members.get(0).getNickname()),
                        // 성공하는 테스트는 주석처리
//                        tuple(members.get(0).getId(), members.get(0).getName(), members.get(0).getEmail(), members.get(0).getNickname()),
                        tuple(members.get(1).getId(), members.get(1).getName(), members.get(1).getEmail(), members.get(1).getNickname()),
                        tuple(members.get(2).getId(), members.get(2).getName(), members.get(2).getEmail(), members.get(2).getNickname())
                );
    }
    
    @DisplayName("변경할 닉네임과 회원 ID를 받아 회원 닉네임을 변경할 수 있다.")
    @Test
    void change_nickname_success() {
        // given
        Member member = createMember("TEST_NAME", "test@test.com", "test_nickname", "test_password");
        Member savedMember = memberRepository.save(member);

        String oldNickname = savedMember.getNickname();
        String changedNickname = "changed_nickname";
        UpdateNicknameServiceRequest request = new UpdateNicknameServiceRequest(changedNickname);
        Long memberId = savedMember.getId();

        // when
        UpdateNicknameResponse result = jenkinsService.changeNickname(request, memberId);

        // then
        assertThat(result.getOldNickname()).isEqualTo(oldNickname);
        assertThat(result.getChangedNickname()).isEqualTo(changedNickname);
    }
    
    private Member createMember(String name, String email, String nickname, String password) {
        return Member.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .password(password)
                .build();
    }

}