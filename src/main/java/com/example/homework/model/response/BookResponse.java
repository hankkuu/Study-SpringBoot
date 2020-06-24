package com.example.homework.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BookResponse {

    @JsonProperty
    String lastBuildDate;
    @JsonProperty
    int total;
    @JsonProperty
    int start;
    @JsonProperty
    int display;
    @JsonProperty
    List<Book> items;
}

@Getter
@Setter
@ToString
class Book {
    @JsonProperty
    public String title;
    @JsonProperty
    public String link;
    @JsonProperty
    public String image;
    @JsonProperty
    public String author;
    @JsonProperty
    public String price;
    @JsonProperty
    public String discount;
    @JsonProperty
    public String publisher;
    @JsonProperty
    public String pubdate;
    @JsonProperty
    public String isbn;
    @JsonProperty
    public String description;
}