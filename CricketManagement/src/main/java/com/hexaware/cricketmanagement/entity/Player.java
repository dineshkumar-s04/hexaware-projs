package com.hexaware.cricketmanagement.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "players")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    private Integer playerId;

    @Column(nullable = false)
    private String playerName;

    @Column(nullable = false)
    private Integer jerseyNumber;

    @Column(nullable = false)
    private String role;

    private Integer totalMatches;

    private String teamName;

    private String countryStateName;

    private String description;
}