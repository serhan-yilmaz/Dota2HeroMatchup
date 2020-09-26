/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Serhan
 */
public class Hero implements Serializable{
    protected String name;
    protected String link;
    protected double win_rate;
    protected double pick_rate;
    protected double kda;
    protected int id;
    
    public Hero(String name){
        this.name = name;
    }
    
    public void print(){
        NumberFormat formatter = new DecimalFormat("#0.000",DecimalFormatSymbols.getInstance(Locale.US));   
        System.out.printf("%-30s", "Name : " + name);
        System.out.printf("%-30s", ", Link : " + link);
        System.out.printf("%-25s", ", Win Rate : %" + formatter.format(win_rate));
        System.out.printf("%-25s", ", Pick Rate : %" + formatter.format(pick_rate));
        System.out.printf("%-20s\n", ", KDA : " + formatter.format(kda));
    }
    
}
