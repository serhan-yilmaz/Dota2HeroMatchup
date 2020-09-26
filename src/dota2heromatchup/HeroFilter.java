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
public interface HeroFilter {
    
    
    public boolean filter(Hero h);
    
    public String getName();
    
    public void setTooltip(String s);
    
    public String getTooltip();
    
    public boolean isCategorization();
    
}
