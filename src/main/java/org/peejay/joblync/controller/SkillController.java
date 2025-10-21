package org.peejay.joblync.controller;

import org.peejay.joblync.data.models.Skill;
import org.peejay.joblync.dtos.requests.SkillRequest;
import org.peejay.joblync.dtos.responses.ApiResponse;
import org.peejay.joblync.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "*")
public class SkillController {

    private final SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Skill>> createSkill(@RequestBody SkillRequest request) {
        try {
            Skill skill = skillService.createSkill(request);
            ApiResponse<Skill> response = new ApiResponse<>(true, "Skill created successfully", skill);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<Skill> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Skill>> getSkillById(@PathVariable String id) {
        try {
            return skillService.findSkillById(id)
                    .map(skill -> {
                        ApiResponse<Skill> response = new ApiResponse<>(true, "Skill retrieved successfully", skill);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        ApiResponse<Skill> response = new ApiResponse<>(false, "Skill not found", null);
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });
        } catch (Exception e) {
            ApiResponse<Skill> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<Skill>> getSkillByName(@PathVariable String name) {
        try {
            return skillService.findSkillByName(name)
                    .map(skill -> {
                        ApiResponse<Skill> response = new ApiResponse<>(true, "Skill retrieved successfully", skill);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        ApiResponse<Skill> response = new ApiResponse<>(false, "Skill not found", null);
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });
        } catch (Exception e) {
            ApiResponse<Skill> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Skill>>> getAllSkills() {
        try {
            List<Skill> skills = skillService.findAllSkills();
            ApiResponse<List<Skill>> response = new ApiResponse<>(true, "Skills retrieved successfully", skills);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<Skill>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Skill>>> getSkillsByCategory(@PathVariable String category) {
        try {
            List<Skill> skills = skillService.findSkillsByCategory(category);
            ApiResponse<List<Skill>> response = new ApiResponse<>(true, "Skills retrieved successfully", skills);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<Skill>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Skill>> updateSkill(@PathVariable String id, @RequestBody SkillRequest request) {
        try {
            Skill updatedSkill = skillService.updateSkill(id, request);
            ApiResponse<Skill> response = new ApiResponse<>(true, "Skill updated successfully", updatedSkill);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<Skill> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSkill(@PathVariable String id) {
        try {
            skillService.deleteSkill(id);
            ApiResponse<String> response = new ApiResponse<>(true, "Skill deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}