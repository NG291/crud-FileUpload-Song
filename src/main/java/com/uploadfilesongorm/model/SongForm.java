package com.uploadfilesongorm.model;

import org.springframework.web.multipart.MultipartFile;


public class SongForm {
    private Long id;
    private String name;
    private String singer;
    private String category;
    private MultipartFile path;
    public SongForm(){}

    public SongForm(Long id, String name, String singer, String category, MultipartFile path) {
        this.id = id;
        this.name = name;
        this.singer = singer;
        this.category = category;
        this.path = path;
    }

    public SongForm(String name, String singer, String category, MultipartFile path) {
        this.name = name;
        this.singer = singer;
        this.category = category;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MultipartFile getPath() {
        return path;
    }

    public void setPath(MultipartFile path) {
        this.path = path;
    }
}
