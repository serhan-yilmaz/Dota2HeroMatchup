/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JToolTip;

/**
 *
 * @author Serhan
 */
public class JLabelWithTooltip extends JLabel{
    
    JToolTip tooltip;
    float font_size = 12;
    
    public JLabelWithTooltip(){
        super();
    }
    
    public JLabelWithTooltip(String text){
        super(text);
    }
    
    @Override
    public JToolTip createToolTip() {
      JToolTip tip = super.createToolTip();
      Font font = tip.getFont();
      Font newfont = font.deriveFont(font_size);
      tip.setFont(newfont);
      tooltip = tip;
      return tip;
    }
    
    void setTooltipFontSize(float font_size){
        this.font_size = font_size;
        if(tooltip != null){
            Font font = tooltip.getFont();
            Font newfont = font.deriveFont(font_size);
            tooltip.setFont(newfont);
        }
    }
    
}
