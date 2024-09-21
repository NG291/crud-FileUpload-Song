package com.uploadfilesongorm.controller;

import com.uploadfilesongorm.model.Song;
import com.uploadfilesongorm.model.SongForm;
import com.uploadfilesongorm.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {
    @Autowired
    private ISongService songService;
    @GetMapping
    public String showList(Model model){
        List<Song> songs = songService.findAll();
        model.addAttribute("songs", songs);
        return "list";
    }
    @GetMapping("/create")
    public String ShowForm(Model model){
     model.addAttribute("songForm",new SongForm());
     return "/create";
    }
    @Value("${file-upload")
    private String fileUpload;
    @PostMapping("/save")
    public String save(Model model, @ModelAttribute SongForm songForm){
        MultipartFile multipartFile = songForm.getPath();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(songForm.getPath().getBytes(),new File(fileUpload + fileName));
        }catch (IOException ex){
            ex.printStackTrace();
        }
        Song song = new Song(songForm.getId(),songForm.getName(),songForm.getSinger(),songForm.getCategory(),fileName);
        songService.save(song);
        model.addAttribute("songForm",songForm);
        model.addAttribute("message","create successfully");
        return "/create";
    }
    @GetMapping("{id}/view")
    public String view( @PathVariable("id") Long id, Model model){
        model.addAttribute("songForm", songService.finById(id));
        return "/view";
    }
    @GetMapping("{id}/delete")
    public String showFormDelete(@PathVariable("id") Long id,Model model){
        model.addAttribute("songForm",songService.finById(id));
        return "/delete";
    }
    @PostMapping("/delete")
    public String delete(Song song){
        songService.remove(song.getId());
     return "redirect:/songs";
    }
    @GetMapping("{id}/edit")
    public String showFormUpdate(@PathVariable Long id, Model model) {
        model.addAttribute("songForm", songService.finById(id));
        return "/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute SongForm songForm, RedirectAttributes redirect) {
        Song existingSong = songService.finById(songForm.getId());
        MultipartFile multipartFile = songForm.getPath();

        if (!multipartFile.isEmpty()) {
            try {
                FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload + multipartFile.getOriginalFilename()));
                existingSong.setPath(multipartFile.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        existingSong.setName(songForm.getName());
        existingSong.setSinger(songForm.getSinger());
        existingSong.setCategory(songForm.getCategory());
        songService.save(existingSong);

        redirect.addFlashAttribute("message", "Update successfully");
        return "redirect:/songs";
    }

}
