package com.example.homework.controller;

import com.example.homework.model.request.BookRequest;
import com.example.homework.model.request.MovieRequest;
import com.example.homework.model.response.BookResponse;
import com.example.homework.model.response.MovieResponse;
import com.example.homework.service.NaverService;
import io.swagger.annotations.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "HomeworkController")
@RestController
@RequestMapping("/naverApi")
public class NaverController {

    private final NaverService naverService;
    public NaverController(NaverService naverService) {
        this.naverService = naverService;
    }

    @ApiOperation(value = "naver 도서 검색", notes = "책 기본 검색")
    @GetMapping("/basicBook")
    public ResponseEntity<BookResponse> naverGetBook(
            @ApiParam(value = "", required = false, example = "")
            @Valid @ModelAttribute BookRequest req)
            throws Exception
    {
        ResponseEntity<BookResponse> response = naverService.searchBook(req);
        return response;
    }

//    @ApiOperation(value = "naver 도서 검색", notes = "책 상세 검색")
//    @GetMapping("/detailBook")
//    public void naverGetDetailBook(
//            @ApiParam(value = "", required = false, example = "")
//            @Valid @ModelAttribute BookRequest req)
//            throws Exception
//    {
//        return null
//    }

    @ApiOperation(value = "naver 영화 검색", notes = "영화 검색")
    @GetMapping("/movie")
    public ResponseEntity<MovieResponse> naverGetMovie(@Valid MovieRequest req) throws Exception {
        ResponseEntity<MovieResponse> response = naverService.searchMovie(req);
        return response;
    }
}
