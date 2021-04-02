package com.softserve.app.controllers;

import com.softserve.app.models.User;
import com.softserve.app.services.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


@Controller
@RequestMapping("/profile")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return userService.findById(((User) auth.getPrincipal()).getId());
    }


    // show Personal
    @GetMapping("/personal")
    public String showProfile(Model model)
    {
        model.addAttribute("user", getCurrentUser());
        return "personal";
    }


    // update 'Personal'        ! NOT PASSWORD ! // todo: update PASSWORD
    @PostMapping("/personal")
    public String updateProfile(Model model,
                                @ModelAttribute User user,
                                @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
            // user id cannot be Null
            user.setId(Objects.requireNonNull(getCurrentUser()).getId());

            // user avatar
            if(file != null && !file.isEmpty())
            {
                user.setPhotoUrl(getCurrentUser().getId() + getCurrentUser().getUsername());
            }

            if(userService.update(user)){
                if ((file != null) && (!file.isEmpty())) {
                    file.transferTo(new File("C:\\Users\\User\\Desktop\\Projects\\SoftServe-course-backend\\src\\main\\resources\\userAvatar" + user.getPhotoUrl() + ".png"));
                }
            } else{
                model.addAttribute("error", true);
            }
            return showProfile(model);
        }

    // show Surveys
    @GetMapping("/surveys")
    public String showSurveys(Model model)
    {
        model.addAttribute("user", getCurrentUser());
        return "surveys";
    }

    // update Survey
    @PostMapping("/surveys")
    public String updateSurvey(Model model,
                               @ModelAttribute User user,
                               @RequestParam(value = "surveyId", required = true) Long surveyId){

                                                                 // todo: update surveys -> SurveyRepo and SurveyService

        return showSurveys(model);
    }


    // show Favourites
    @GetMapping("/favourites")
    public String showFavourites(Model model)
    {
        model.addAttribute("user", getCurrentUser());
        return "favourites";
    }
    //Favourite update - on Favourite article

}
