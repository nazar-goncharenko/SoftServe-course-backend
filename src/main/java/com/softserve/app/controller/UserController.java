package com.softserve.app.controller;

import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.dto.UserDTO;
import com.softserve.app.exception.SportHubException;
import com.softserve.app.models.User;
import com.softserve.app.repository.UserRepository;
import com.softserve.app.request.LoginRequest;
import com.softserve.app.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;


@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    public UserRepository getUserRepository() {
        return userRepository;
    }

    @GetMapping("/users")
    List<User> getAllUser() {
        return userRepository.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAuth(@RequestBody LoginRequest loginrequest) {
        User userFromDb = userRepository.findByEmail(loginrequest.getEmail()).orElseThrow(() ->
                new SportHubException(SportHubConstant.USER_NOT_FOUND.getMessage(), 401));
        if (userFromDb == null) {
            //якщо нема юзера то відповідний статус (який-ше треба почитати)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "WRONG USER");
        }
        try {
            ////checking passwords
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginrequest.getEmail(),
                    loginrequest.getPassword()));

        } catch (
                BadCredentialsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "WRONG CREDENTIALS", e);
        }
        //userDetailsService.loadUserByUsername(loginrequest.getEmail());
        System.out.println();
        return ResponseEntity.ok("You logged in successfully");
    }
    // todo: user registration and login


    // show  profile
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> showProfile(
            @RequestBody UserDTO userDto
    ) {
        User usr = userService.findByEmail(userDto.getEmail());
        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return ResponseEntity.ok(usr);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    // update profile  'Personal' tab // todo: update  avatar
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseEntity<Object> updatePersonal(
            @RequestBody UserDTO userDto
    ) {
        User usr = userService.findByEmail(userDto.getEmail());
        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return ResponseEntity.ok(userService.updateUser(usr));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    // todo: update PASSWORD
/*
    // show Surveys
    @RequestMapping(value = "/profile/surveys", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Survey>> showSurveys(
            @RequestBody UserDTO userDto
    ) {
        User usr = userService.findByEmail(userDto.getEmail());
        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return ResponseEntity.ok(// return list of surveys); // todo: list of surveys !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // update Survey
    @RequestMapping(value = "/profile/surveys", method = RequestMethod.POST)
    public ResponseEntity<List<Survey>> updateSurvey(
            @RequestParam("id") Long id, // user id
            @RequestParam("surveyId") Long surveyId
    ) {
        User usr = userService.findById(id);
        if (Objects.equals(userService.getCurrentUser().getId(), usr.getId())) {
            return ResponseEntity.ok(// return list of surveys); // todo: update surveys -> SurveyRepo and SurveyService!!!!!!!!!!!!!!!!!!
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    // show Favourites
    @RequestMapping(value = "/profile/favourites", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Object>> showFavourites(
            @RequestBody UserDTO userDto
    ) {
        User usr = userService.findByEmail(userDto.getEmail());
        if (Objects.equals(userService.getCurrentUser(), usr)) {
            return ResponseEntity.ok(// return list of favourites); // todo: update favourites -> list of favourites !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    //Favourite update - on Favourite article
*/

}
