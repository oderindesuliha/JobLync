package org.peejay.joblync.controller;

import org.peejay.joblync.data.models.SkillLevel;
import org.peejay.joblync.data.models.UserSkill;
import org.peejay.joblync.dtos.requests.UserSkillRequest;
import org.peejay.joblync.dtos.responses.ApiResponse;
import org.peejay.joblync.services.UserSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-skills")
@CrossOrigin(origins = "*")
public class UserSkillController {

    private final UserSkillService userSkillService;

    @Autowired
    public UserSkillController(UserSkillService userSkillService) {
        this.userSkillService = userSkillService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserSkill>> addUserSkill(@RequestBody UserSkillRequest request) {
        try {
            UserSkill userSkill = userSkillService.addUserSkill(request);
            ApiResponse<UserSkill> response = new ApiResponse<>(true, "User skill added successfully", userSkill);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<UserSkill> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserSkill>> getUserSkillById(@PathVariable String id) {
        try {
            return userSkillService.findUserSkillById(id)
                    .map(userSkill -> {
                        ApiResponse<UserSkill> response = new ApiResponse<>(true, "User skill retrieved successfully", userSkill);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        ApiResponse<UserSkill> response = new ApiResponse<>(false, "User skill not found", null);
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });
        } catch (Exception e) {
            ApiResponse<UserSkill> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<UserSkill>>> getUserSkillsByUser(@PathVariable String userId) {
        try {
            List<UserSkill> userSkills = userSkillService.findUserSkillsByUser(userId);
            ApiResponse<List<UserSkill>> response = new ApiResponse<>(true, "User skills retrieved successfully", userSkills);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<UserSkill>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/skill/{skillId}")
    public ResponseEntity<ApiResponse<List<UserSkill>>> getUserSkillsBySkill(@PathVariable String skillId) {
        try {
            List<UserSkill> userSkills = userSkillService.findUserSkillsBySkill(skillId);
            ApiResponse<List<UserSkill>> response = new ApiResponse<>(true, "User skills retrieved successfully", userSkills);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<UserSkill>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/proficiency/{proficiencyLevel}")
    public ResponseEntity<ApiResponse<List<UserSkill>>> getUserSkillsByProficiencyLevel(@PathVariable SkillLevel proficiencyLevel) {
        try {
            List<UserSkill> userSkills = userSkillService.findUserSkillsByProficiencyLevel(proficiencyLevel);
            ApiResponse<List<UserSkill>> response = new ApiResponse<>(true, "User skills retrieved successfully", userSkills);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<UserSkill>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserSkill>> updateUserSkill(@PathVariable String id, @RequestBody UserSkillRequest request) {
        try {
            UserSkill updatedUserSkill = userSkillService.updateUserSkill(id, request);
            ApiResponse<UserSkill> response = new ApiResponse<>(true, "User skill updated successfully", updatedUserSkill);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<UserSkill> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> removeUserSkill(@PathVariable String id) {
        try {
            userSkillService.removeUserSkill(id);
            ApiResponse<String> response = new ApiResponse<>(true, "User skill removed successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}/gaps")
    public ResponseEntity<ApiResponse<List<UserSkill>>> getSkillGapsForUser(@PathVariable String userId) {
        try {
            List<UserSkill> skillGaps = userSkillService.findSkillGapsForUser(userId);
            ApiResponse<List<UserSkill>> response = new ApiResponse<>(true, "Skill gaps retrieved successfully", skillGaps);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<UserSkill>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}