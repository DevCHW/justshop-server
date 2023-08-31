package com.justshop.api.external.member.controller;

import com.justshop.api.external.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    // TODO : 회원 단일조회 기능 구현하기
    @GetMapping
    public String ok() {
        return "ok";
    }
}
