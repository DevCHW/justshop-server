package com.justshop.product.client;

import com.justshop.product.client.response.FileResponse;
import com.justshop.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

// TODO : File System 완성시 작성완료하기
@FeignClient(name = "file-service", url = "http://127.0.0.1:8083/api/v1/internal/files")
public interface FileClient {

    @GetMapping
    ApiResponse<List<FileResponse>> getFileInfo(@SpringQueryMap List<Long> fileIds);
}
