package com.justshop.product.client.reader;

import com.justshop.core.exception.BusinessException;
import com.justshop.product.client.FileClient;
import com.justshop.product.client.response.FileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.justshop.core.error.ErrorCode.INTERNAL_SERVER_ERROR;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileReader {

    private final FileClient fileClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public List<FileResponse> read(List<Long> fileIds) {
        try {
            return fakeRead();

//            TODO : 파일시스템 작성완료시 삭제하기 주석 살리기
//            CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
//            return circuitBreaker.run(() -> fileClient.getFileInfo(fileIds).getData(),
//                    throwable -> new ArrayList<>());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(INTERNAL_SERVER_ERROR, "파일 시스템 장애로 인하여 주문에 실패하였습니다.");
        }
    }

    // TODO : 임시 파일 Response. 파일시스템 작성완료시 삭제하기
    private List<FileResponse> fakeRead() {
        FileResponse fileResponse1 = new FileResponse(1L, generateRandomUUID(), "임시_상품이미지1.PNG", "./home/fake/file/path/");
        FileResponse fileResponse2 = new FileResponse(2L, generateRandomUUID(), "임시_상품이미지2.PNG", "./home/fake/file/path/");
        FileResponse fileResponse3 = new FileResponse(3L, generateRandomUUID(), "임시_상품이미지3.PNG", "./home/fake/file/path/");
        FileResponse fileResponse4 = new FileResponse(4L, generateRandomUUID(), "임시_상품이미지4.PNG", "./home/fake/file/path/");
        FileResponse fileResponse5 = new FileResponse(5L, generateRandomUUID(), "임시_상품이미지5.PNG", "./home/fake/file/path/");

        return List.of(fileResponse1, fileResponse2, fileResponse3, fileResponse4, fileResponse5);
    }

    private String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }
}
