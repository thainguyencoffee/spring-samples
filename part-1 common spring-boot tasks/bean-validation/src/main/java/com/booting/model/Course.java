package com.booting.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Course {
    private long id;

    @NotBlank
    private String name;
    @NotBlank
    private String category;

    @Min(value = 1, message = "A Course should have a minimum of 1 rating")
    @Max(value = 5, message = "A Course should have a maximum of 5 rating")
    private int rating;

    @NotBlank
    private String description;

    // Constructor, Getter, and Setters

    public Course() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
