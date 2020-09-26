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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Serhan
 */
public class CheckboxHeroFilterPanel extends HeroFilterPanel{
    private ArrayList<JLabelWithTooltip> label_list = new ArrayList<>();
    private ArrayList<JCheckBox> button_list = new ArrayList<>();
    private JComboBox logic_combobox;
    private CheckboxHeroFilterPanelSynchronizer synchronizer;
    
    JLabel name_label;
    
    public CheckboxHeroFilterPanel(String name, ArrayList<HeroFilter> filters, boolean isAND) {
        super(name, filters);
        this.synchronizer = 
                new CheckboxHeroFilterPanelSynchronizer(isAND, filters.size());
        this.synchronizer.addChildren(this);
        name_label = new JLabel(name + " : ");
        initialize();
    }
    
    private CheckboxHeroFilterPanel(String name, ArrayList<HeroFilter> filters, 
            CheckboxHeroFilterPanelSynchronizer synchronizer){
        super(name, filters);
        this.synchronizer = synchronizer;
        this.synchronizer.addChildren(this);
        name_label = new JLabel(name + " : ");
        initialize();
    }

    private void initialize(){
        final HeroFilterPanel fp = this;
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JPanel label_panel = new JPanel();
        JPanel filter_main_panel = new JPanel();
        //filter_main_panel.setLayout(new GridLayout(1,0));
        label_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        String[] elements = {"AND", "OR"};
        logic_combobox = new JComboBox(elements);
//        if(isAND)
//            logic_combobox.setSelectedIndex(0);
//        else
//            logic_combobox.setSelectedIndex(1);
        logic_combobox.setName("LogicCombobox");
        logic_combobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fp.is_modified = true;
                String name = ((JComponent) e.getSource()).getName();
                synchronize(name);
            }
        });
        label_panel.add(name_label);
        label_panel.add(logic_combobox);
                
        this.add(label_panel);
        this.add(filter_main_panel);
        
        int index = 0;
        for(HeroFilter filter: filters){
            JPanel filter_panel = new JPanel();
            JLabelWithTooltip filter_label = new JLabelWithTooltip(
                                                    filter.getName() + " :");
            filter_label.setToolTipText(filter.getTooltip());
            JCheckBox filter_button = new JCheckBox();
            filter_button.setName("FilterButton" + index);
//            filter_button.setSelected(!isAND);
            filter_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fp.is_modified = true;
                    String name = ((JComponent) e.getSource()).getName();
                    synchronize(name);
                }
            });
            label_list.add(filter_label);
            button_list.add(filter_button);
            
            filter_panel.add(filter_label);
            filter_panel.add(filter_button);
            
            filter_main_panel.add(filter_panel);
            index++;
        }
        this.synchronizer.synchronize(this);
    }
    
    private void synchronize(String name){
        if(name.equals("LogicCombobox")){
            synchronizer.setLogic(logic_combobox.getSelectedIndex() == 0);
        } else if(name.contains("FilterButton")){
            int index = Integer.valueOf(name.substring(12));
            synchronizer.setFilterEnabled(index, 
                    button_list.get(index).isSelected());
        } else {
            throw new RuntimeException("Invalid component name.");
        }
    }
    
    protected void setLogic(boolean isAND){
        logic_combobox.setEnabled(false);
        if(isAND)
            logic_combobox.setSelectedIndex(0);
        else
            logic_combobox.setSelectedIndex(1);
        logic_combobox.setEnabled(true);
    }
    
    protected void setFilterEnabled(int index, boolean enabled){
        button_list.get(index).setSelected(enabled);
    }
    
    @Override
    public boolean filter(Hero h) {
        int temp = logic_combobox.getSelectedIndex();
        if(temp == 0){
            for(int i = 0; i < button_list.size(); i++){
                if(button_list.get(i).isSelected()){
                    if(!filters.get(i).filter(h))
                        return false;
                }
            }
            return true;
        } else {
            for(int i = 0; i < button_list.size(); i++){
                if(button_list.get(i).isSelected()){
                    if(filters.get(i).filter(h))
                        return true;
                }
            }
            return false;
        }
    }
    
    @Override
    protected void setFontSize(float fontsize){
        Font font1 = logic_combobox.getFont();
        Font newFont1 = font1.deriveFont(fontsize);
        
        logic_combobox.setFont(newFont1);
        name_label.setFont(newFont1);
        
        for(JLabelWithTooltip label: label_list){
            label.setFont(newFont1);
            label.setTooltipFontSize(fontsize);
        }

        for(JCheckBox cb: button_list){
            cb.setFont(newFont1);
        }
    }

    @Override
    public void clearAll() {
        for(JCheckBox cb: button_list){
            cb.setSelected(false);
        }
        logic_combobox.setSelectedIndex(0);
        this.is_modified = true;
    }
    
    @Override
    public CheckboxHeroFilterPanel createSynchronizedPair(){
        return new CheckboxHeroFilterPanel(name, filters, synchronizer);
    }
}
