package com.justshop.order.client;

import com.justshop.order.client.response.MemberResponse;
import com.justshop.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "member-service")
// TODO: API Gateway 로드밸런싱 적용시 url 삭제
@FeignClient(name = "member-service", url = "http://127.0.0.1:8083/api/v1/internal/members")
public interface MemberServiceClient {

    @GetMapping("{memberId}")
    ApiResponse<MemberResponse> getMemberInfo(@PathVariable("memberId") Long memberId);
}
