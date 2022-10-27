package com.ssafy.gganbu.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "game")
public class game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @Column(name = "game_no", nullable = false)
    private Integer gameNo;

    @Column(name = "age_group", nullable = false)
    private Integer ageGroup;

    @Column(name = "game_score", nullable = false)
    private Integer gameScore;

}
