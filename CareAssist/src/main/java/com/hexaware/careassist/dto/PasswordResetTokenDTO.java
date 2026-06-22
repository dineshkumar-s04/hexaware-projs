package com.hexaware.careassist.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetTokenDTO {

	private int tokenId;
	private int userId;
	private String token;
	private LocalDateTime expiryTime;
	private boolean used;
}
