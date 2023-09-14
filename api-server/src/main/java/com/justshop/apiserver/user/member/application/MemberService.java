package com.justshop.apiserver.user.member.application;

import com.justshop.apiserver.user.member.application.dto.response.MemberResponse;
import com.justshop.core.member.implementation.command.MemberManager;
import com.justshop.core.member.implementation.query.MemberReader;
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

    // 회원 단건 조회
    public MemberResponse getMyInfo(Long id) {
        return MemberResponse.from(memberReader.read(id));
    }

}
