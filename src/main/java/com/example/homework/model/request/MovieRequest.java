package com.example.homework.model.request;

//        요청 변수명	타입  필수 여부	    기본값	            설명
//        query	      string   (필수)Y	      -	            검색을 원하는 질의. UTF-8 인코딩이다.
//        display	  integer	N	기본값 10, 최대 100	    검색 결과 출력 건수를 지정한다. 최대 100까지 가능하다.
//        start	      integer	N	기본값 1, 최대 1000	    검색의 시작 위치를 지정할 수 있다. 최대 1000까지 가능하다.
//        genre	      string	N	          -	            검색을 원하는 장르 코드를 의미한다. 영화 코드는 다음과 같다.
                                                                //        1: 드라마 2: 판타지
                                                                //        3: 서부 4: 공포
                                                                //        5: 로맨스 6: 모험
                                                                //        7: 스릴러 8: 느와르
                                                                //        9: 컬트 10: 다큐멘터리
                                                                //        11: 코미디 12: 가족
                                                                //        13: 미스터리 14: 전쟁
                                                                //        15: 애니메이션 16: 범죄
                                                                //        17: 뮤지컬 18: SF
                                                                //        19: 액션20: 무협
                                                                //        21: 에로 22: 서스펜스
                                                                //        23: 서사 24: 블랙코미디
                                                                //        25: 실험 26: 영화카툰
                                                                //        27: 영화음악 28: 영화패러디포스터
//        country	  string	N	         -              검색을 원하는 국가 코드를 의미한다. 국가코드는 대문자만 사용 가능하며,
                                                                //        분류는 다음과 같다.
                                                                //        한국 (KR), 일본 (JP), 미국 (US), 홍콩 (HK),
                                                                //        영국 (GB), 프랑스 (FR), 기타 (ETC)
//        yearfrom	  integer(ex : 2000)	N	-	        검색을 원하는 영화의 제작년도(최소)를 의미한다. yearfrom은 yearto와 함께 사용되어야 한다.
//        yearto	  integer(ex : 2008)	N	-	        검색을 원하는 영화의 제작년도(최대)를 의미한다. yearto는 yearfrom과 함께 사용되어야 한다.

import com.example.homework.common.Query;
import com.example.homework.common.QueryStringAnnotation;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
public class MovieRequest extends Query {

    @NotNull
    private String query;

    @Min(10)
    @Max(100)
    private Integer display = 10;       // 최대 100

    @Min(1)
    @Max(1000)
    private Integer start = 1;          // 최대 1000

    @Pattern(regexp = "^[0-9]+$", message = "숫자만 가능합니다")
    private String genre;

    @Pattern(regexp = "^[A-Z]+$" ,message = "대문자만 가능합니다")
    private String country;

    private Integer yearfrom;           // (ex : 2000)

    private Integer yearto;             // (ex : 2000)

    @QueryStringAnnotation(isIgnore = true)
    private boolean isIgnoreZeroRating = false;
}
