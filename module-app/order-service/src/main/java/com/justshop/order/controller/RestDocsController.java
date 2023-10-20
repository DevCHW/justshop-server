package com.justshop.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestDocsController {

    @GetMapping("/restdocs")
    public String restdocs() {
        return "docs/order-index.html";
    }

}
