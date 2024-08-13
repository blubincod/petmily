package com.concord.petmily.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 페이지 요청을 처리하는 컨트롤러
 *
 * 홈 페이지
 * 산책 페이지
 */
@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/walk")
    public String walk() {
        return "walk";
    }
}
