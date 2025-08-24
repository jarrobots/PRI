package wmi.amu.edu.pl.pri.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wmi.amu.edu.pl.pri.dto.CommentDto;
import wmi.amu.edu.pl.pri.dto.CommentsDto;
import wmi.amu.edu.pl.pri.services.CommentService;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {
    @Autowired
    private final CommentService service;

    @GetMapping("/view/comments")
    public ResponseEntity<CommentsDto> getComments(@RequestParam(value="versionId") Long id){
        return ResponseEntity.ok().body(service.getComments(id));
    }

    @RequestMapping(method=POST, path = "/post/comment")
    public ResponseEntity<Long> addComment(@RequestBody CommentDto dto){
        return ResponseEntity.ok().body(service.addComment(dto));
    }

    @GetMapping("/view/comment")
    public ResponseEntity<Boolean> delComment(@RequestParam(value="id") Long id){
        return ResponseEntity.ok().body(service.deleteComment(id));
    }
}
