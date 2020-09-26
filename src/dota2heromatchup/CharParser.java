/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

/**
 *
 * @author Serhan
 */
public final class CharParser {
    
    private boolean recording_enabled = false;
    private String target = "";
    private int target_counter;
    private String recording;
    
    public CharParser(){
        reset();
    }
    
    public CharParser(String target){
        reset();
        setTarget(target);
    }
    
    public void setTarget(String target){
        target_counter = 0;
        this.target = target;
    }
    
    public final void parse(char c){
        if(!isTargetReached()){
            char c2 = target.charAt(target_counter);
            if(c2 == c){
                target_counter++;
            } else {
                target_counter = 0;
            }
        }
        if(!isTargetReached()){
            if(recording_enabled){
                recording = recording + c;
            }
        }
    }
    
    public final void reset(){
        target_counter = 0;
        recording = "";
    }
    public final void record(){
        recording_enabled = true;
        recording = "";
    }
    public final String stopRecording(){
        if(recording_enabled){
            recording_enabled = false;
            String recording = this.recording;
            reset();
            return recording;
        }
        return null;
    }
    
    public boolean isTargetReached(){
        return target == null || target.length() <= target_counter;
    }
}
