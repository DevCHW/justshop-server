package com.justshop.member.api.member.presentation;

import com.justshop.member.api.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    // TODO: Rest docs 설정

    // TODO: 내정보 조회

    // TODO: 로그인

    // TODO: 로그아웃

    // TODO: 회원 탈퇴

    // TODO: 내정보 수정

    // TODO: 내정보 조회
}
