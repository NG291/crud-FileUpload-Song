package com.uploadfilesongorm.service;

import com.uploadfilesongorm.model.Song;

import java.util.List;

public interface ISongService {
    List<Song> findAll();

    Song finById(Long id);

    void save(Song song);

    void remove(Long id);
}
