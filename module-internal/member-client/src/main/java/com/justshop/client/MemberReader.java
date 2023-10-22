package com.justshop.client;

import com.justshop.client.dto.MemberResponse;
import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberReader {

    private final MemberServiceClient memberServiceClient;

    public MemberResponse read(Long memberId) {
        try {
            return memberServiceClient.getMemberInfo(memberId).getData();
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new BusinessException(ErrorCode.ORDER_FAIL, "회원 시스템 장애로 인하여 주문에 실패하였습니다.");
        }
    }

}
