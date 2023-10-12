package com.example.jenkins.service;

import com.example.jenkins.entity.Member;
import com.example.jenkins.error.ErrorCode;
import com.example.jenkins.exception.BusinessException;
import com.example.jenkins.repository.MemberRepository;
import com.example.jenkins.service.dto.request.CreateMemberServiceRequest;
import com.example.jenkins.service.dto.request.UpdateNicknameServiceRequest;
import com.example.jenkins.service.dto.response.MemberResponse;
import com.example.jenkins.service.dto.response.UpdateNicknameResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JenkinsService {

    private final MemberRepository memberRepository;

    /* 회원 생성 */
    @Transactional
    public MemberResponse create(CreateMemberServiceRequest request) {
        Member savedMember = memberRepository.save(request.toEntity());
        log.info("회원가입 성공, 회원 ID = {}", savedMember.getId());
        return MemberResponse.from(savedMember);
    }

    /* 회원 단일 조회 */
    public MemberResponse getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR));
        return MemberResponse.from(member);
    }

    /* 전체 회원 조회 */
    public List<MemberResponse> getAll() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(member -> MemberResponse.from(member))
                .collect(Collectors.toList());
    }

    /* 닉네임 변경 */
    @Transactional
    public UpdateNicknameResponse changeNickname(UpdateNicknameServiceRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR));
        String oldNickname = member.getNickname();
        member.changeNickname(request.getNickname());
        String changedNickname = member.getNickname();
        return new UpdateNicknameResponse(oldNickname, changedNickname);
    }
}
