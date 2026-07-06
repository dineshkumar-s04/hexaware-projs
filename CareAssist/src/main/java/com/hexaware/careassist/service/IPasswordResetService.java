package com.hexaware.careassist.service;

public interface IPasswordResetService {

	String forgotPassword(String email);

    String resetPassword(String token, String newPassword);

}
