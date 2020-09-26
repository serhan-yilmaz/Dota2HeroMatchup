/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Serhan
 */
public class ComboboxHeroFilterPanel extends HeroFilterPanel{
    private final JComboBox combobox;
    private ComboboxHeroFilterPanelSynchronizer synchronizer;
    
    JLabel name_label;
    
    public ComboboxHeroFilterPanel(String name, ArrayList<HeroFilter> filters){
        super(name,filters);
        combobox = new JComboBox(filter_strings);
        this.synchronizer = new ComboboxHeroFilterPanelSynchronizer(0);
        this.synchronizer.addChildren(this);
        initialize();
    }
    
    private ComboboxHeroFilterPanel(String name, ArrayList<HeroFilter> filters, 
            ComboboxHeroFilterPanelSynchronizer synchronizer){
        super(name,filters);
        combobox = new JComboBox(filter_strings);
        this.synchronizer = synchronizer;
        this.synchronizer.addChildren(this);
        initialize();
    }
    
    private void initialize(){
        final ComboboxHeroFilterPanel fp = this;
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JPanel label_panel = new JPanel();
        label_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        name_label = new JLabel(name + " : ");
        
        combobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fp.is_modified = true;
                synchronizer.setSelectedIndex(combobox.getSelectedIndex(), fp);
            }
        });
        
        combobox.setSelectedIndex(0);
        
        label_panel.add(name_label);
        
        this.add(label_panel);
        this.add(combobox);
        synchronizer.synchronize(this);
    }
    
    protected void setSelectedIndex(int index){
        is_modified = true;
        combobox.setSelectedIndex(index);
    }
    
    @Override
    public boolean filter(Hero h){
        int index = combobox.getSelectedIndex();
        return filters.get(index).filter(h);
    }
    
    @Override
    protected void setFontSize(float fontsize){
        Font font1 = combobox.getFont();
        Font newFont1 = font1.deriveFont(fontsize);
        
        combobox.setFont(newFont1);
        name_label.setFont(newFont1);
    }

    @Override
    public void clearAll() {
        combobox.setSelectedIndex(0);
        this.is_modified = true;
    }
    
    @Override
    public ComboboxHeroFilterPanel createSynchronizedPair(){
        return new ComboboxHeroFilterPanel(name, filters, synchronizer);
    }
    
}
