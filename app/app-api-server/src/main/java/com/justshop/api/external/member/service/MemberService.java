package com.justshop.api.external.member.service;

import com.justshop.api.external.member.service.response.MemberResponse;
import com.justshop.domain.member.command.MemberReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberReader memberReader;

    /**
     * 회원 단건 조회
     */
    public MemberResponse getOneMember(Long id) {
        return MemberResponse.of(memberReader.read(id));
    }

}
