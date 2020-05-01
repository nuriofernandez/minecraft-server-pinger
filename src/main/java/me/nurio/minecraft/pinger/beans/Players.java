package me.nurio.minecraft.pinger.beans;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Players {

    private int max;
    private int online;
    private List<Player> sample = new ArrayList<>();

}

