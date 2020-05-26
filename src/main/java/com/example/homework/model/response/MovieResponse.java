package com.example.homework.model.response;

// 필드	            타입	설명
// rss	            -	    디버그를 쉽게 하고 RSS 리더기만으로 이용할 수 있게 하기 위해 만든 RSS 포맷의 컨테이너이며 그 외의 특별한 의미는 없다.
// channel	        -	    검색 결과를 포함하는 컨테이너이다. 이 안에 있는 title, link, description 등의 항목은 참고용으로 무시해도 무방하다.
// lastBuildDate	datetime	검색 결과를 생성한 시간이다.
// item/items	    -	    XML 포멧에서는 item 태그로, JSON 포멧에서는 items 속성으로 표현된다. 개별 검색 결과이며 title, link, image, subtitle, pubDate, director, actor, userRating을 포함한다.
// title	        string	검색 결과 영화의 제목을 나타낸다. 제목에서 검색어와 일치하는 부분은 태그로 감싸져 있다.
// link	            string	검색 결과 영화의 하이퍼텍스트 link를 나타낸다.
// image	        string	검색 결과 영화의 썸네일 이미지의 URL이다. 이미지가 있는 경우만 나타난다.
// subtitle	        string	검색 결과 영화의 영문 제목이다.
// pubDate	        date	검색 결과 영화의 제작년도이다.
// director	        string	검색 결과 영화의 감독이다.
// actor	        string	검색 결과 영화의 출연 배우이다.
// userRating	    integer	검색 결과 영화에 대한 유저들의 평점이다.

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@ToString
public class MovieResponse {

    String lastBuildDate;

    int total;

    int start;

    int display;

    // Inner class 로 사용이 되려면 static 선언이 되야 한다..
    List<Movie> items;

    @Getter
    @Setter
    @ToString
    public static class Movie {

        String title;

        String link;

        String image;

        String subtitle;

        String pubDate;

        String director;

        String actor;

        Double userRating;
    }

}

