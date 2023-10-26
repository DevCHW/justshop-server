package com.justshop.member;

import com.justshop.member.domain.entity.Member;
import com.justshop.member.domain.entity.enums.Gender;
import com.justshop.member.domain.entity.enums.MemberStatus;
import com.justshop.member.domain.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class DBInit {

    private final InitService initService;

//    @PostConstruct
    public void init() {
        initService.initializeMember();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    public static class InitService {
        private final EntityManager entityManager;
        private final PasswordEncoder passwordEncoder;

        public void initializeMember() {
            Member member = Member.builder()
                    .email("test123@naver.com")
                    .password("qwer1234$")
                    .name("최현우")
                    .nickname("현우")
                    .point(0)
                    .birthday(LocalDate.of(1997, 1, 3))
                    .memberRole(Role.USER)
                    .gender(Gender.MAN)
                    .status(MemberStatus.ACTIVE)
                    .build();

            entityManager.persist(member);
        }
    }
}
