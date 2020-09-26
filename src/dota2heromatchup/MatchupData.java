/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Serhan
 */
public class MatchupData implements Serializable{
    private static final long serialVersionUID = 6383752264211883154L;
    
    ArrayList<Hero> hero_list;
    protected double[][] advantage;
    protected double[][] win_rate;
    protected double[][] match_amount;
    protected Date date = new Date();
    
    public MatchupData(ArrayList<Hero> hero_list){
        this.hero_list = (ArrayList<Hero>) hero_list.clone();
        int size = hero_list.size();
        advantage = new double[size][];
        win_rate = new double[size][];
        match_amount = new double[size][];
        for(int i = 0; i < size; i++){
            advantage[i] = new double[size];
            win_rate[i] = new double[size];
            match_amount[i] = new double[size];
        }
    }
    
    public void printHeroes(){
        for(Hero h : hero_list){
            h.print();
        }
    }
    public String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(date);
    }
    
    public double getWinRate(Hero h1, Hero h2){
        return win_rate[h1.id][h2.id];
    }
    
}
