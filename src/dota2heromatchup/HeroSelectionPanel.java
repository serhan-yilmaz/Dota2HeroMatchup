/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Serhan
 */
public class HeroSelectionPanel extends JPanel{
    JLabel label1 = new JLabel("Select Hero : ");
    JLabel label2 = new JLabel("Selected : ");
    
    private HeroSelectionField textfield;
    private JLabel selected_label;
    
    private boolean action_request = false;
    
    public HeroSelectionPanel(){
        super();
        initialize();   
    }
    
    private void initialize(){
        final HeroSelectionPanel sp = this;
        
        this.setLayout(new GridLayout(1,0));
        this.setBorder(new LineBorder(Color.gray));
        
        JPanel pane1 = new JPanel();
        JPanel pane2 = new JPanel();
        
        textfield = new HeroSelectionField(13);
        
        textfield.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp.action_request = true;
            }
        });
        
        selected_label = new JLabel("None");
        selected_label.setForeground(Color.red);
        
        pane1.add(label1);
        pane1.add(textfield);
        pane2.add(label2);
        pane2.add(selected_label);
        
        this.add(pane1);
        this.add(pane2);
        
    }
    
    protected void cycle(){
        if(textfield.isEnabled()){
            Hero h = textfield.getSelectedHero();
            if(h == null){
                selected_label.setForeground(Color.red);
                selected_label.setText("None");
            } else {
                selected_label.setForeground(Color.blue);
                selected_label.setText(h.name);
            }
            textfield.cycle();
        }
    }
    
    protected void activate(MatchupData data){
        textfield.activate(data);
    }
    
    protected Hero getSelectedHero(){
        return textfield.getSelectedHero();
    }
    
    protected boolean isActionRequested(){
        boolean temp = action_request;
        action_request = false;
        return temp;
    }
    
    protected void clearSelection(){
        textfield.clearSelection();
    }
    
    protected void setFontSize(float fontsize){
        Font font1 = label1.getFont();
        Font newFont1 = font1.deriveFont(fontsize);
        Font font2 = textfield.getFont();
        Font newFont2 = font2.deriveFont(fontsize);
        
        label1.setFont(newFont1);
        label2.setFont(newFont1);
        selected_label.setFont(newFont1);
        textfield.setFont(newFont2);
    }
    
}
