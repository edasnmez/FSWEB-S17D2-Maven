package com.workintech.s17d2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Developer {
    private int id;
    private String name;
    private double salary;
    private Experience experience;
}
