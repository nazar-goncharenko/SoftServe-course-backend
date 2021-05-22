package com.softserve.app.controller;


import com.softserve.app.dto.CommentDTO;
import com.softserve.app.dto.EstimationDTO;
import com.softserve.app.service.commentService.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/videoComment/{videoId}")
    public void addVideoComment(@PathVariable Long videoId, @RequestBody CommentDTO commentDTO,
                                Principal principal) {
        commentService.addVideoComment(videoId, commentDTO, principal);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/videoComments/{id}/like")
    public void likeVideoComment(@PathVariable Long id, @RequestBody EstimationDTO estimationDTO, Principal principal) {
        commentService.likeComment(id, estimationDTO, principal);
    }
}
