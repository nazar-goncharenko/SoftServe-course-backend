package com.softserve.app.service.commentService;


import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.CommentDTO;
import com.softserve.app.dto.EstimationDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.Comment;
import com.softserve.app.models.Estimation;
import com.softserve.app.models.User;
import com.softserve.app.repository.CommentRepository;
import com.softserve.app.repository.EstimationRepository;
import com.softserve.app.service.userService.UserService;
import com.softserve.app.service.videoService.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {


    private final VideoService videoService;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final EstimationRepository estimationRepository;

    @Override
    public void addVideoComment(Long id, CommentDTO commentDTO, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Comment comment = Comment.builder()
                .comment(commentDTO.getComment())
                .author(user)
                .estimations(commentDTO.getEstimations()
                        .stream()
                        .map(EstimationDTO::ofEntity)
                        .collect(Collectors.toList()))
                .time(commentDTO.getTime())
                .build();
        comment = commentRepository.save(comment);
        videoService.addComment(id, comment);
    }

    @Override
    public List<CommentDTO> getAllByVideoId(Long id) {
        return videoService.getById(id).getComments();
    }

    @Override
    public void likeComment(Long id, EstimationDTO estimationDTO, Principal principal) {
        System.out.println("METHOD");
        Comment comment = this.commentRepository.getById(id)
                .orElseThrow(() -> new SportHubException(SportHubConstant.COMMENT_NOT_FOUND.getMessage(), 404));


        User user = userService.findByEmail(principal.getName());

        AtomicReference<Estimation> estimation = new AtomicReference<>(estimationDTO.ofEntity());
        Estimation finalEstimation = estimationDTO.ofEntity();

        AtomicBoolean isEstimated = new AtomicBoolean(false);
        AtomicBoolean deleteEstimated = new AtomicBoolean(false);


        comment.getEstimations().forEach(estimationFromComment -> {
            if (estimationFromComment.getUser().getId().equals(
                    finalEstimation.getUser().getId())) {
                isEstimated.set(true);
                if (estimationFromComment.isDislike() == finalEstimation.isDislike()) {
                    deleteEstimated.set(true);
                    estimation.set(estimationFromComment);
                } else {
                    estimationFromComment.setDislike(finalEstimation.isDislike());
                    this.estimationRepository.save(estimationFromComment);
                }
            }
        });
        if (deleteEstimated.get()) {
            comment.deleteEstimation(estimation.get());
            this.commentRepository.save(comment);
            this.estimationRepository.delete(estimation.get());
            return;
        }
        if (!isEstimated.get()) {
            Estimation newEstimation = this.estimationRepository.save(estimation.get());
            comment.addEstimation(newEstimation);
            this.commentRepository.save(comment);
        }
    }
}
