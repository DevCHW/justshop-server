package com.justshop.member.api.auth.application;

import com.justshop.member.api.auth.application.dto.request.SignUpServiceRequest;
import com.justshop.member.infrastructure.kafka.producer.MemberCreateProducer;
import com.justshop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberCreateProducer memberCreateProducer;

    @Transactional
    public Long signUp(SignUpServiceRequest request) {
        Long savedMemberId = memberRepository.save(request.toEntity(passwordEncoder)).getId();
        memberCreateProducer.send(savedMemberId);
        return savedMemberId;
    }

}
