package com.hexaware.cricketmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {

    @NotNull(message = "Player ID is required")
    private Integer playerId;

    @NotBlank(message = "Player Name is required")
    private String playerName;

    @NotNull(message = "Jersey Number is required")
    @Min(value = 1, message = "Jersey Number must be greater than 0")
    private Integer jerseyNumber;

    @NotBlank(message = "Role is required")
    private String role;

    @NotNull(message = "Total Matches is required")
    @Min(value = 0, message = "Total Matches cannot be negative")
    private Integer totalMatches;

    @NotBlank(message = "Team Name is required")
    private String teamName;

    @NotBlank(message = "Country/State Name is required")
    private String countryStateName;

    @NotBlank(message = "Description is required")
    private String description;
}