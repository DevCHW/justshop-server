package com.justshop.product.client.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {

    private Long fileId;
    private String saveFileName;
    private String originFileName;
    private String filePath;

}
