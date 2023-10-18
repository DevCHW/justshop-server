package com.justshop.response.paging;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class CustomPage<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> result;
    private int total;

    private CustomPage(List<T> result, int total) {
        this.result = result;
        this.total = total;
    }

    // Factory method
    public static <T> CustomPage<T> of(List<T> result, int total) {
        return new CustomPage<>(result, total);
    }
}
