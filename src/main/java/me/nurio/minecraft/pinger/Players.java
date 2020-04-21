package me.nurio.minecraft.pinger;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Players {

    private int max;
    private int online;
    private List<Player> sample = new ArrayList<>();

}

