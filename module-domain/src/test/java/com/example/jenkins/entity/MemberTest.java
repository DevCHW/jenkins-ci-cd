package com.example.jenkins.entity;

import com.example.jenkins.error.ErrorCode;
import com.example.jenkins.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @DisplayName("닉네임을 변경할 수 있다.")
    @Test
    void change_nickname_success() {
        // given
        String changeNickname = "changeNickname";
        Member member = createMember();
        // when
        member.changeNickname("changeNickname");

        // then
        assertThat(member.getNickname()).isEqualTo(changeNickname);
    }

    @DisplayName("변경하고자 하는 닉네임 값이 null이라면 BusinessException이 발생한다.")
    @Test
    void change_nickname_fail1() {
        // given
        String changeNickname = null;
        Member member = createMember();
        // when
        member.changeNickname("changeNickname");

        // then
        assertThatThrownBy(() -> member.changeNickname(changeNickname))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.CHANGE_NICKNAME_FAIL.getMessage());
    }

    @DisplayName("변경하고자 하는 닉네임 값이 공백이라면 BusinessException이 발생한다.")
    @Test
    void change_nickname_fail2() {
        // given
        String changeNickname = "";
        Member member = createMember();
        // when
        member.changeNickname("changeNickname");

        // then
        assertThatThrownBy(() -> member.changeNickname(changeNickname))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.CHANGE_NICKNAME_FAIL.getMessage());
    }

    // Factory Util
    private Member createMember() {
        return Member.builder()
                .name("test name")
                .email("testEmail@test.com")
                .nickname("testNickname")
                .build();
    }

}