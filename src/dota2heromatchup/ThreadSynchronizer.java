/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Serhan
 */
public class ThreadSynchronizer {
    private final int thread_count;
    private int current_thread_count;
    
    public ThreadSynchronizer(int tc){
        this.thread_count = tc;
        this.current_thread_count = 0;
    }
    
    public synchronized void submit(){
        current_thread_count++;
        System.out.println(current_thread_count);
        while(current_thread_count < thread_count){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadSynchronizer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        notifyAll();
    }
}
