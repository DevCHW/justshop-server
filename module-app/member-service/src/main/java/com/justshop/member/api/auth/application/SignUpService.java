package com.justshop.member.api.auth.application;

import com.justshop.member.api.auth.application.dto.request.SignUpServiceRequest;
import com.justshop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void signUp(SignUpServiceRequest request) {
        // TODO: 추후 이벤트 발행
        memberRepository.save(request.toEntity(passwordEncoder));
    }

}
