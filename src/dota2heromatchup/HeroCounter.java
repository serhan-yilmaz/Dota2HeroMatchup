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
public class HeroCounter {
    private Hero hero;
    private double general_wr;
    private double matchup_wr;
    private double expected_wr;
    private double point;
    
    public HeroCounter(Hero h, ArrayList<Hero> ally_heroes, ArrayList<Hero> enemy_heroes, MatchupData data){
        this(h, ally_heroes, enemy_heroes, data, 1);
    }
    
    public HeroCounter(Hero h, ArrayList<Hero> ally_heroes, 
            ArrayList<Hero> enemy_heroes, MatchupData data, 
            double ally_advantage_odds){
        this.hero = h;
        double temp1P = 1, temp2P = 1;
        double temp1N = 1, temp2N = 1;
        for(Hero h2 : ally_heroes){
            temp1P *= (h2.win_rate * 1e-2) / 0.5;
            temp1N *= (1 - (h2.win_rate * 1e-2)) / 0.5;
            temp2P *= (h2.win_rate * 1e-2) / 0.5;
            temp2N *= (1 - (h2.win_rate * 1e-2)) / 0.5;
        }
        for(Hero h2 : enemy_heroes){
            temp1P *= data.getWinRate(h, h2) / h.win_rate;
            temp2P *= (1 - (h2.win_rate * 1e-2)) / 0.5;
            temp1N *= (100 - data.getWinRate(h, h2)) / (100 - h.win_rate);
            temp2N *= h2.win_rate * 1e-2 / 0.5;
        }
        double matchup_wrP, matchup_wrN;
        matchup_wrP = h.win_rate * temp1P;
        matchup_wrN = (100 - h.win_rate) * temp1N;
        matchup_wr = 100 * matchup_wrP / (matchup_wrP + matchup_wrN);
        double expected_wrP, expected_wrN;
        expected_wrP = h.win_rate * temp2P;
        expected_wrN = (100 - h.win_rate) * temp2N;
        expected_wr = 100 * expected_wrP / (expected_wrP + expected_wrN);
        point = computeAdvantage(matchup_wr, expected_wr);
        double expected_odds = expected_wr / (100 - expected_wr);
        expected_wr = 100 * (1 - 1/(expected_odds * ally_advantage_odds + 1));
        
        double matchup_odds = matchup_wr / (100 - matchup_wr);
        matchup_wr = 100 * (1 - 1/(matchup_odds * ally_advantage_odds + 1));
        
//        point = (matchup_wr - expected_wr) / 0.5;
//        point = 100 * (matchup_wr / expected_wr) - 100;
//        matchup_wr = h.win_rate * temp1;
//        point = 0.5 * temp1;
        
//        temp1 = Math.pow(temp1, 1 / (0.0 + heroes.size()));
//        temp2 = Math.pow(temp2, 1 / (0.0 + heroes.size()));
//        matchup_wr = 100 * temp1 / (temp1 + temp2);
//        temp1 = h.win_rate * 1e-2;
//        temp2 = 1;
//        for(Hero h2 : heroes){
//            temp2 *= h2.win_rate * 1e-2;
//        }
//        temp2 = Math.pow(temp2, 1 / (0.0 + heroes.size()));
//        temp1 *= (1 - temp2);
//        temp2 *= (1 - h.win_rate * 1e-2);
//        general_wr = 100 * temp1 / (temp1 + temp2);
//        point = (matchup_wr - general_wr);
        
        for(Hero h2 : enemy_heroes){
            if(h.equals(h2)){
                point = 0;
                matchup_wr = 50;
                expected_wr = 50;
            }
        }
    }
    
    public double getPoint(){
        return point;
    }
    public double getGeneralWR(){
        return general_wr;
    }
    public double getMatchupWR(){
        return matchup_wr;
    }
    public Hero getHero(){
        return hero;
    }
    public double getExpectedWR() {
        return expected_wr;
    }
    
    public static double computeAdvantage(double observed_wr, double expected_wr){
//        double n1 = Math.sqrt(observed_wr) * Math.sqrt(100 - observed_wr);
//        double n2 = Math.sqrt(expected_wr) * Math.sqrt(100 - expected_wr);
//        double d = 100 * (observed_wr - expected_wr) / (n1 * n2);
//        double advantage = 2 / (1 + Math.exp(-d)) - 1;
        double n1 = observed_wr * (100 - observed_wr) / 100;
        double n2 = expected_wr * (100 - expected_wr) / 100;
        double advantage = (observed_wr - expected_wr) / (n1 + n2);
//        double advantage = 2 / (1 + Math.exp(-d)) - 1;
        return 100 * advantage;
    }
    
}
