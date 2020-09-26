/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dota2heromatchup;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Serhan
 */
public class HeroCounterDataModel extends DefaultTableModel{
    private final static Color DEFAULT_ROW_COLOR = Color.WHITE;
    private final ArrayList<Color> colors = new ArrayList<>();
    private boolean isColorAdded;
    
    public HeroCounterDataModel(){
        super();
    }
    
    public HeroCounterDataModel(int n1, int n2){
        super(n1, n2);
    }
    
    @Override
    public void setRowCount(int rowCount){
        super.setRowCount(rowCount);
        if(rowCount < colors.size()){
            colors.subList(rowCount, colors.size()).clear();
        } else {
            while(rowCount > colors.size()){
                System.out.println("a");
                colors.add(DEFAULT_ROW_COLOR);
            }
        }
    }
    
    @Override
    public void addRow(Vector rowData){
        super.addRow(rowData);
        if(!isColorAdded){
            colors.add(DEFAULT_ROW_COLOR);
        }
    }
    
    @Override
    public void addRow(Object[] rowData){
        if(!isColorAdded){
            colors.add(DEFAULT_ROW_COLOR);
            isColorAdded = true;
            super.addRow(rowData);
            isColorAdded = false;
        } else {
            super.addRow(rowData);
        }
    }
    
    public void addRow(Object[] rowData, Color color){
        isColorAdded = true;
        colors.add(color);
        super.addRow(rowData);
        isColorAdded = false;
    }
    
    public Color getRowColor(int row){
        return colors.get(row);
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
       //all cells false
       return false;
    }
}
