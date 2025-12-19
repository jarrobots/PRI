package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wmi.amu.edu.pl.pri.dto.CommentDto;
import wmi.amu.edu.pl.pri.services.CommentService;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {
    @Autowired
    private final CommentService service;

    @GetMapping("/view/version/{id}/comment")
    public ResponseEntity<CommentDto> getCommentByVersion(@PathVariable Long id){
        return ResponseEntity.ok().body(service.getCommentByVersion(id));
    }

    @GetMapping("/view/chapter/{id}/comment")
    public ResponseEntity<CommentDto> getCommentByChapter(@PathVariable Long id){
        return ResponseEntity.ok().body(service.getCommentByChapter(id));
    }

    @PostMapping( path = "/post/comment")
    public ResponseEntity<Long> addComment(@RequestBody CommentDto dto){
        return ResponseEntity.ok().body(service.addComment(dto));
    }
}
