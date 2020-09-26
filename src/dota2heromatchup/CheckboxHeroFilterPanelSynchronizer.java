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
public class CheckboxHeroFilterPanelSynchronizer {
    private ArrayList<CheckboxHeroFilterPanel> children = new ArrayList<>();
    private final ArrayList<Boolean> filterFlags = new ArrayList<>();
    private boolean isAND;
    
    public CheckboxHeroFilterPanelSynchronizer(boolean isAND, int n){
        filterFlags.ensureCapacity(n);
        for(int i = 0; i < n; i++){
            filterFlags.add(!isAND);
        }
        this.isAND = isAND;
    }
    
    public void addChildren(CheckboxHeroFilterPanel panel){
        children.add(panel);
    }
    
    public void setLogic(boolean isAND){
        setLogic(isAND, null);
    }
    
    public void setLogic(boolean isAND, CheckboxHeroFilterPanel caller){
        this.isAND = isAND;
        for(CheckboxHeroFilterPanel panel : children){
            if(panel != caller){
                panel.setLogic(isAND);
            }
        }
    }
    
    public void setFilterEnabled(int index, boolean enabled){
        setFilterEnabled(index, enabled, null);
    }
    
    public void setFilterEnabled(int index, boolean enabled, 
            CheckboxHeroFilterPanel caller){
        filterFlags.set(index, enabled);
        for(CheckboxHeroFilterPanel panel : children){
            if(panel != caller){
                panel.setFilterEnabled(index, enabled);
            }
        }
    }
    
    public void synchronize(CheckboxHeroFilterPanel target){
        target.setLogic(isAND);
        for(int i = 0; i < filterFlags.size(); i++){
            target.setFilterEnabled(i, filterFlags.get(i));
        }
    }
    
}
