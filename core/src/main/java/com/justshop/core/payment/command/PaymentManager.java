package com.justshop.core.payment.command;

import com.justshop.core.member.domain.Member;
import com.justshop.core.payment.domain.enums.PaymentGroup;
import com.justshop.core.payment.domain.enums.PaymentType;
import com.justshop.core.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentManager {

    private final PaymentRepository paymentRepository;

    // TODO : 결제로직 작성
    public void append(int payAmount, Member consumer, PaymentGroup group, PaymentType type) {

    }
}
