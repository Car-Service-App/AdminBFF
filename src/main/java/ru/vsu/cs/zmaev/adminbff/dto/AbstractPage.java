package ru.vsu.cs.zmaev.adminbff.dto;

import lombok.Data;

import java.util.List;

@Data
public class AbstractPage<T> {
    private List<T> content;
    private int number;
    private int size;
    private int totalElements;
    private int totalPages;
    private boolean last;
}
