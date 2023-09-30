package com.justshop.response.paging;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PagingResponse<T> {

    private List<T> list = new ArrayList<>();
    private PageInfo pageInfo;

}
