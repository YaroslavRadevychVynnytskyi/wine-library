package com.application.winelibrary.controller;

import com.application.winelibrary.dto.comment.CommentResponseDto;
import com.application.winelibrary.dto.comment.PostCommentRequestDto;
import com.application.winelibrary.dto.rating.AddRatingRequestDto;
import com.application.winelibrary.dto.rating.AverageRatingResponseDto;
import com.application.winelibrary.dto.rating.RatingResponseDto;
import com.application.winelibrary.dto.wine.CreateWineRequestDto;
import com.application.winelibrary.dto.wine.WineDetailedResponseDto;
import com.application.winelibrary.dto.wine.WineSearchParameters;
import com.application.winelibrary.entity.User;
import com.application.winelibrary.service.comment.CommentService;
import com.application.winelibrary.service.rating.RatingService;
import com.application.winelibrary.service.wine.WineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Wine management", description = "Endpoints for managing wine")
@RestController
@RequestMapping("/wines")
@RequiredArgsConstructor
public class WineController {
    private final CommentService commentService;
    private final RatingService ratingService;
    private final WineService wineService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Operation(summary = "Add a new wine",
            description = "Admin access endpoint. Saves new wine into db")
    public WineDetailedResponseDto save(@RequestBody @Valid CreateWineRequestDto requestDto) {
        return wineService.save(requestDto);
    }

    @GetMapping
    @Operation(
            summary = "Get a list of wines",
            description = "Everyone access endpoint. "
                    + "Provides a list of all wines with pagination available"
    )
    public List<WineDetailedResponseDto> findAll(Pageable pageable) {
        return wineService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get wine's detailed information",
            description = "Everyone access endpoint. "
                    + "Retrieves wine's detailed information based of wine's ID"
    )
    public WineDetailedResponseDto findById(@PathVariable Long id) {
        return wineService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update wine",
            description = "Admin access endpoint. Updates a wine by ID")
    public WineDetailedResponseDto updateById(
            @PathVariable Long id,
            @RequestBody @Valid CreateWineRequestDto requestDto
    ) {
        return wineService.updateById(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove wine",
            description = "Admin access endpoint. Removes a wine from db by ID")
    public void deleteById(@PathVariable Long id) {
        wineService.deleteById(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/{wineId}/comments")
    @Operation(summary = "Leave comment", description = "User endpoint to leave comment on wine")
    public CommentResponseDto postComment(Authentication authentication,
                                          @PathVariable Long wineId,
                                          @RequestBody @Valid PostCommentRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return commentService.postComment(user.getId(), wineId, requestDto);
    }

    @GetMapping("/{wineId}/comments")
    @Operation(summary = "Get all comments",
            description = "Everyone access endpoint to retrieve all comments of certain wine")
    public List<CommentResponseDto> getAllComments(@PathVariable Long wineId) {
        return commentService.getAllComments(wineId);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{commentId}/comments")
    @Operation(summary = "Remove comment",
            description = "Both User/Admin access endpoint to delete a comment")
    public void deleteComment(Authentication authentication,
                              @PathVariable Long commentId) {
        User user = (User) authentication.getPrincipal();
        commentService.deleteComment(user.getId(), commentId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/{wineId}/ratings")
    @Operation(summary = "Rate the wine", description = "User endpoint to set wine rating")
    public RatingResponseDto addRating(Authentication authentication,
                                       @PathVariable Long wineId,
                                       @RequestBody @Valid AddRatingRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return ratingService.addRating(user.getId(), wineId, requestDto);
    }

    @GetMapping("/{wineId}/ratings")
    @Operation(summary = "Get average rating of certain wine",
            description = "User/Admin access endpoint to receive average rating of certain wine")
    public AverageRatingResponseDto getAverageRating(@PathVariable Long wineId) {
        return ratingService.getAverageRating(wineId);
    }

    @GetMapping("/search")
    @Operation(summary = "Dynamic query params search",
            description = "Everyone access endpoint for dynamic filtering")
    public List<WineDetailedResponseDto> search(WineSearchParameters searchParameters) {
        return wineService.search(searchParameters);
    }
}
