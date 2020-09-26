/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import java.util.Comparator;

/**
 *
 * @author Serhan
 */
public class HeroCounterComparator implements Comparator<HeroCounter>{
    public final static int COUNTER_NAME = 1;
    public final static int COUNTER_ADVANTAGE = 2;
    public final static int COUNTER_WINRATE = 3;
    public final static int COUNTER_GENERALWR = 4;
    public final static int COUNTER_PICKRATE = 5;
    
    int type;
    
    public HeroCounterComparator(){
        this.type = COUNTER_ADVANTAGE;
    }
    
    public HeroCounterComparator(int type){
        this.type = type;
    }
    
    @Override
    public int compare(HeroCounter o1, HeroCounter o2) {
        if(type == COUNTER_NAME){
            String s1 = o1.getHero().name;
            String s2 = o2.getHero().name;
            return s1.compareTo(s2);
        }
        double d1 = getValue(o1);
        double d2 = getValue(o2);
        return Double.compare(d2, d1);
    }
    
    private double getValue(HeroCounter hc){
        switch(type){
            case COUNTER_ADVANTAGE:
                return hc.getPoint();
            case COUNTER_WINRATE:
                return hc.getMatchupWR();
            case COUNTER_GENERALWR:
                return hc.getHero().win_rate;
            case COUNTER_PICKRATE:
                return hc.getHero().pick_rate;
            default:
                throw new RuntimeException("Invalid type");
        }
    }
    
    
}
