package dev.jayms.evergreen.battle;

import dev.jayms.evergreen.arena.Arena;
import dev.jayms.evergreen.team.Team;


public class Battle {

    private Arena map;
    private Team defenders;
    private Team attackers;

    public Battle(Arena map) {
        this.map = map;
        this.defenders = new Team("Defenders");
        this.attackers = new Team("Attackers");
    }

}
