/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

/**
 *
 * @author Serhan
 */
public class AllpassFilter implements HeroFilter{
    
    @Override
    public boolean filter(Hero h) {
        return true;
    }

    @Override
    public String getName(){
            return "All";
    }

    @Override
    public void setTooltip(String s) {
        
    }

    @Override
    public String getTooltip() {
        return "";
    }

    @Override
    public boolean isCategorization() {
        return false;
    }
    
}
