package com.justshop.apiserver.api.user.member.service;

import com.justshop.apiserver.api.user.member.service.request.MemberCreateServiceRequest;
import com.justshop.apiserver.api.user.member.service.response.MemberResponse;
import com.justshop.core.member.command.MemberManager;
import com.justshop.core.member.domain.Member;
import com.justshop.core.member.query.MemberReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberReader memberReader;
    private final MemberManager memberManager;

    /**
     * 회원 단건 조회
     */
    public MemberResponse getOneMember(Long id) {
        return MemberResponse.of(memberReader.read(id));
    }

    /**
     * 회원 가입
     */
    @Transactional
    public MemberResponse signUp(MemberCreateServiceRequest request) {
        Member member = request.toEntity();
        return MemberResponse.of(memberManager.create(member));
    }

}