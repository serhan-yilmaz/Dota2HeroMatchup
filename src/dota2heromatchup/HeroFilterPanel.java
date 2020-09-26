/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Serhan
 */
public abstract class HeroFilterPanel extends JPanel{
    final String name;
    final ArrayList<HeroFilter> filters;
    final String[] filter_strings;
    
    boolean is_modified = false;
    
    public HeroFilterPanel(String name, ArrayList<HeroFilter> filters){
        super();
        this.name = name;
        this.filters = filters;
        this.filter_strings = new String[filters.size()];
        for(int i = 0; i < filters.size(); i++){
            filter_strings[i] = filters.get(i).getName();
        }
    }
    
    public abstract boolean filter(Hero h);
    protected abstract void setFontSize(float fontsize);
    public abstract void clearAll();
    public abstract HeroFilterPanel createSynchronizedPair();
    
    public final boolean isModified(){
        boolean temp = is_modified;
        is_modified = false;
        return temp;
    }
    
    public final ArrayList<String> getValidFilterNames(Hero h){
        ArrayList<String> str = new ArrayList<>();
        for(HeroFilter f : filters){
            if(f.filter(h) && f.isCategorization())
                str.add(f.getName());
        }
        return str;
    }
    
    public final ArrayList<HeroFilter> getFilters(){
        return filters;
    }
    
}
