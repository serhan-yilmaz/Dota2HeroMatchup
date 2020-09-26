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
public class StringParser {
    
    private String text;
    private int iterator = 0;
    
    public StringParser(String str){
        this.text = str;
    }
    
    public void rewind(){
        iterator = 0;
    }
    
    public String parseUntil(String target){
        CharParser cp = new CharParser(target);
        cp.record();
        while(iterator < text.length()){
            char c = text.charAt(iterator);
            cp.parse(c);
            iterator++;
            if(cp.isTargetReached()){
                return cp.stopRecording();
            }
        }
        return "";
    }
    
    public boolean isCompleted(){
        return iterator >= text.length();
    }
    
    public int getIterator(){
        return iterator;
    }
    
}
