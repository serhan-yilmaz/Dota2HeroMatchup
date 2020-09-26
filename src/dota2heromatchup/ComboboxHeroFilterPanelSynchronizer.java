/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Serhan
 */
public class ComboboxHeroFilterPanelSynchronizer {
    private ArrayList<ComboboxHeroFilterPanel> children = new ArrayList<>();
    private int selectedIndex;
    
    public ComboboxHeroFilterPanelSynchronizer(int selectedIndex){
        this.selectedIndex = selectedIndex;
    }
    
    public void addChildren(ComboboxHeroFilterPanel panel){
        children.add(panel);
    }
    
    public void setSelectedIndex(int index){
        setSelectedIndex(index, null);
    }
    
    public void setSelectedIndex(int index, ComboboxHeroFilterPanel caller){
        this.selectedIndex = index;
        for(ComboboxHeroFilterPanel panel : children){
            if(panel != caller){
                panel.setSelectedIndex(index);
            }
        }
    }
    
    public void synchronize(ComboboxHeroFilterPanel target){
        target.setSelectedIndex(selectedIndex);
    }
    
}
