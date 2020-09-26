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
public class HeroComparator implements Comparator<Hero>{

    @Override
    public int compare(Hero o1, Hero o2) {
        return o1.name.compareTo(o2.name);
    }
    
}
