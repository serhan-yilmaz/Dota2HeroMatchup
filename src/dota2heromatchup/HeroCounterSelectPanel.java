/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Serhan
 */
public class HeroCounterSelectPanel extends JPanel{
    
    private NumberFormat formatter = new DecimalFormat("#0.0", 
            DecimalFormatSymbols.getInstance(Locale.US));
    private Hero hero;
    private final int id;
    
    JLabel label;
    private JLabel name;
    private final JButton delete = new JButton("x");
    private final JButton select = new JButton("Select");
//    private final JLabel advantage_label = new JLabel("Adv:");
    private final JLabel advantage = new JLabel("");
    private final JLabel win_rate = new JLabel("");
    
    private boolean selection_request = false;
    private boolean deletion_request = false;
    
    public HeroCounterSelectPanel(int id){
        super();
        this.id = id;
        label = new JLabel(id + " : ");;
        initialize();
    }
    
    private void initialize(){
        final HeroCounterSelectPanel sp = this;
        
//        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setLayout(new GridLayout(1,0));
        

        name = new JLabel("None");
        name.setForeground(Color.red);
        
        delete.setEnabled(false);
        select.setEnabled(false);
        
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp.deletion_request = true;
            }
        });
        
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp.selection_request = true;
            }
        });
        JPanel info_panel_wrapper = new JPanel();
        JPanel info_panel = new JPanel();
//        advantage_panel.add(advantage_label);
        info_panel.setLayout(new BoxLayout(info_panel, BoxLayout.Y_AXIS));
        
        
        JPanel win_rate_panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        win_rate_panel.add(win_rate);
        JPanel advantage_panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        advantage_panel.add(advantage);
        
        info_panel.add(win_rate_panel);
        info_panel.add(advantage_panel);
//        new FlowLayout();
        info_panel_wrapper.setLayout(new GridBagLayout());
        info_panel_wrapper.add(info_panel);
        
        
        JPanel button_panel = new JPanel();
        button_panel.setLayout(new GridLayout(1, 0));
        button_panel.add(delete);
        button_panel.add(select);
        button_panel.add(info_panel_wrapper);
        
        JPanel hero_name_panel = new JPanel();
        hero_name_panel.setLayout(new GridBagLayout());
        hero_name_panel.add(name);
        
        JPanel naming_panel = new JPanel();
        naming_panel.setLayout(new BorderLayout());
        naming_panel.add(label, BorderLayout.WEST);
        naming_panel.add(hero_name_panel, BorderLayout.CENTER);
        
        this.add(naming_panel);
//        this.add(label);
//        this.add(name);
//        this.add(delete);
//        this.add(select);
        this.add(button_panel);
//        this.add(advantage_panel_wrapper);
    }
    
    protected void selectHero(Hero h){
        this.hero = h;
        name.setText(h.name);
        name.setForeground(Color.blue);
        this.delete.setEnabled(true);
        this.advantage.setText("");
        this.win_rate.setText("WR: " + formatter.format(h.win_rate) + "%" );
//        this.advantage.setText("Adv: 0.0%");
    }
    
    protected void setAdvantage(double d){
        this.advantage.setText("Adv: " + formatter.format(d) + "%");
    }
    
    protected void deactivate(){
        this.hero = null;
        this.delete.setEnabled(false);
        this.name.setText("None");
        this.name.setForeground(Color.red);
        this.advantage.setText("");
        this.win_rate.setText("");
    }
    protected void setSelectionEnabled(boolean bo){
        this.select.setEnabled(bo);
        if(!bo){
            selection_request = false;
        }
    }
    
    protected Hero getSelectedHero(){
        return hero;
    }
    protected boolean isSelectionRequested(){
        boolean temp = selection_request;
        selection_request = false;
        return temp;
    }
    
    protected boolean isDeletionRequested(){
        boolean temp = deletion_request;
        deletion_request = false;
        return temp;
    }
    
    protected void setFontSize(float fontsize){
        Font font1 = name.getFont();
        Font newFont1 = font1.deriveFont(fontsize);
        
        name.setFont(newFont1);
        delete.setFont(newFont1);
        select.setFont(newFont1);
        label.setFont(newFont1);
//        advantage_label.setFont(newFont1);
        win_rate.setFont(newFont1);
        advantage.setFont(newFont1);
    }
    
}
