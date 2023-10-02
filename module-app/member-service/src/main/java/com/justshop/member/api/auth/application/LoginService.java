package com.justshop.member.api.auth.application;

import com.justshop.member.entity.Member;
import com.justshop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(member.getEmail(), member.getPassword(),
                true, true, true, true,
                new ArrayList<>());
    }

    // 이메일로 회원 아이디 조회
    public Long getMemberIdByEmail(String email) {
        Long memberId = memberRepository.findIdByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email)).getId();
        return memberId;
    }

}
