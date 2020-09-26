/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Serhan
 */
public class HeroSelectionField extends JTextField{
    
    private MatchupData data;
    
    private Hero selected_hero = null;
    private String last_text = "";
    private String last_valid = "";
    
    public HeroSelectionField(int a){
        super(a);
        initialize();
    }
    private void initialize(){
        setEnabled(false);
    }
    
    private void assessText(){
        String text = this.getText();
        text = text.toLowerCase();
        text = text.replace('ı', 'i');
        ArrayList<Hero> hero_fit = new ArrayList<>();
        
        for(int i = 0; i < data.hero_list.size(); i++){
            Hero h = data.hero_list.get(i);
            String name = h.name;
            name = name.toLowerCase();
            name = name.replace('ı', 'i');
            if(text.length() == 0 || (text.length() <= name.length() && text.equals(name.substring(0, text.length())))){
                hero_fit.add(h);
            }
        }
        
        if(hero_fit.isEmpty()){
            this.setText(last_valid);
        } else if(hero_fit.size() == 1){
            this.setText(hero_fit.get(0).name);
            this.last_valid = hero_fit.get(0).name;
            selected_hero = hero_fit.get(0);
        } else {
            boolean init = true;
            String s1 = null,s2;
            for(Hero h : hero_fit){
                String name = h.name;
                name = name.toLowerCase();
                name = name.replace('ı', 'i');
                if(init){
                    init = false;
                    s1 = name;
                }else{
                    s2 = name;
                    for(int i = 0;i < s1.length(); i++){
                        if(s1.charAt(i) != s2.charAt(i)){
                            if(i == 0){
                                s1 = "";
                            } else {
                                s1 = s1.substring(0, i);
                            }
                            break;
                        }
                    }
                }
            }
            this.setText(s1);
            this.last_valid = s1;
        }
    }
    
    public void activate(MatchupData data){
        this.selected_hero = null;
        this.last_valid = "";
        this.data = data;
        setEnabled(true);
    }
    public Hero getSelectedHero(){
        return selected_hero;
    }
        
    protected void cycle(){
        if(this.last_text.length() > this.getText().length()){
            this.setText("");
            this.last_text = "";
            this.last_valid = "";
        } else if(this.last_text.length() < this.getText().length()){
            assessText();
            int cp = this.getText().length();
            this.setCaretPosition(cp);
            this.last_text = this.getText();
        }
    }
    
    protected void clearSelection(){
        this.setText("");
        this.last_text = "";
        this.last_valid = "";
        this.selected_hero = null;
    }
    
}
