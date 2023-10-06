package com.justshop.order.client.reader;

import com.justshop.core.exception.BusinessException;
import com.justshop.order.client.MemberServiceClient;
import com.justshop.order.client.response.MemberResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.justshop.core.error.ErrorCode.ORDER_FAIL;

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
            throw new BusinessException(ORDER_FAIL, "회원 시스템 장애로 인하여 주문에 실패하였습니다.");
        }
    }

}
