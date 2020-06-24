package com.example.homework.model.response;

import com.example.homework.common.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BookResponse extends Response {

    String lastBuildDate;

    int total;

    int start;

    int display;

    List<Book> items;

    @Getter
    @Setter
    @ToString
    public static class Book {

        public String title;

        public String link;

        public String image;

        public String author;

        public String price;

        public String discount;

        public String publisher;

        public String pubdate;

        public String isbn;

        public String description;
    }
}

