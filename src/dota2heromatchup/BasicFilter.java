/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import java.util.ArrayList;

/**
 *
 * @author Serhan
 */
public class BasicFilter implements HeroFilter{
    private String tooltip = "";
    
    private final String name;
    private final ArrayList<String> heroes;
    
    public BasicFilter(String name, ArrayList<String> heroes){
        this.name = name;
        this.heroes = heroes;
    }
    
    @Override
    public boolean filter(Hero h) {
        return heroes.contains(h.name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setTooltip(String s) {
        this.tooltip = s;
    }

    @Override
    public String getTooltip() {
        return tooltip;
    }

    @Override
    public boolean isCategorization() {
        return true;
    }
    
}
