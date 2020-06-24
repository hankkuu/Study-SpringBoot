package com.example.homework.model.request;

import com.example.homework.common.Query;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

//        요청변수명	타입  필수여부	 기본값	설명	비고
//        query	    string	-	-	검색을 원하는 문자열로서 UTF-8로 인코딩한다.	상세검색시 생략가능
//        display	integer	N	10(기본값), 100(최대)	검색 결과 출력 건수 지정	-
//        start	    integer	N	1(기본값), 1000(최대)	검색 시작 위치로 최대 1000까지 가능	-
//        sort	    string	N	sim(기본값), date	정렬 옵션: sim(유사도순), date(출간일순), count(판매량순)
//        d_titl	string	N	-	책 제목 검색	상세 검색만 해당
//        d_auth	string	N	-	저자명 검색	상세 검색만 해당
//        d_cont	string	N	-	목차 검색	상세 검색만 해당
//        d_isbn	string	N	-	isbn 검색	상세 검색만 해당
//        d_publ	string	N	-	출판사 검색	상세 검색만 해당
//        d_dafr	string	N	(ex.20000203)	출간 시작일	상세 검색만 해당
//        d_dato	string	N	(ex.20000203)	출간 종료일	상세 검색만 해당
//        d_catg	string	N	-	책 검색 카테고리(카테고리 목록 다운로드)	상세 검색만 해당
@Getter
@Setter
@ToString
public class BookRequest extends Query {

    private String query = "";

    @Max(100)
    @Min(10)
    private Integer display = 10;

    @Max(1000)
    @Min(1)
    private Integer start = 1;

    private String sort = "sim";
}
