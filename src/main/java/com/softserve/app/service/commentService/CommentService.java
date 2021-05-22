package com.softserve.app.service.commentService;

import com.softserve.app.dto.CommentDTO;
import com.softserve.app.dto.EstimationDTO;

import java.security.Principal;
import java.util.List;

public interface CommentService {

    void addVideoComment(Long id, CommentDTO commentDTO, Principal principal);
    List<CommentDTO> getAllByVideoId(Long id);

    void likeComment(Long id, EstimationDTO estimationDTO, Principal principal);
}
