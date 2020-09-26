/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 *
 * @author Serhan
 */
public class HeroCounterTable extends JTable{
    HeroCounterDataModel hc_model = null;
    
    @Override
    public void setModel(TableModel mdl){
        super.setModel(mdl);
        hc_model = null;
    }
    
    public void setModel(HeroCounterDataModel mdl){
        super.setModel(mdl);
        hc_model = mdl;
    }
    
    
    @Override
    public Component prepareRenderer(
        TableCellRenderer renderer, int row, int column)
    {
        Component c = super.prepareRenderer(renderer, row, column);

        if(hc_model != null){
            Color color = hc_model.getRowColor(row);
            if(!this.isRowSelected(row)){
                c.setBackground(color);
//                color = Color.lightGray;
            }
            
        }

        return c;
    }
    
}
