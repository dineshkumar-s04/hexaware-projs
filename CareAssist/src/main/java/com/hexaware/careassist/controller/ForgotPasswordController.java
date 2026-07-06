package com.hexaware.careassist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.careassist.dto.ForgotPasswordRequestDTO;
import com.hexaware.careassist.dto.ResetPasswordRequestDTO;
import com.hexaware.careassist.service.IPasswordResetService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class ForgotPasswordController {

    @Autowired
    private IPasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody ForgotPasswordRequestDTO request) {

        String response =
                passwordResetService.forgotPassword(request.getEmail());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody ResetPasswordRequestDTO request) {

        String response =
                passwordResetService.resetPassword(
                        request.getToken(),
                        request.getNewPassword());

        return ResponseEntity.ok(response);
    }

}