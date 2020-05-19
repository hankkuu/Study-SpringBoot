package com.example.homework.controller;

import com.example.homework.model.request.BookRequest;
import com.example.homework.model.response.BookResponse;
import com.example.homework.service.NaverService;
import io.swagger.annotations.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Api(value = "HomeworkController")
@RestController
@RequestMapping("/naverApi")
public class HomeworkController {

    private final NaverService naverService;

    // @Autowired Spring 4.3 이상부터 생략해도 됨
    public HomeworkController(NaverService naverService) {
        this.naverService = naverService;
    }

    // Swagger를 통해 Test 및 UI 를 통해 요청을 한다 (이렇게 안하면 피들러나 포스트맨이 필요)
    @ApiOperation(value = "naver 도서 검색", notes = "책 기본 검색")
    @GetMapping("")
    public ResponseEntity<BookResponse> naverApi(@ApiParam(value = "", required = false, example = "") @ModelAttribute BookRequest req) throws Exception {
        System.out.println(req.toString());
        ResponseEntity<BookResponse> response = naverService.searchBook(req);
        return response;
    }
}
