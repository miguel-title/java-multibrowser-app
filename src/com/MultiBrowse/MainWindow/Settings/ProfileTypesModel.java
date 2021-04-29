/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Settings;

import java.util.ArrayList;

/**
 *
 * @author title
 */
public class ProfileTypesModel {
    private int id;
    private String typename;
    private boolean isSelect;
    private int order_id;
 
    public ProfileTypesModel(int id, String typename, boolean isSelect)
    {
        this.id = id;
        this.typename = typename;
        this.isSelect = isSelect;
    }
    
    public int getDataCount(){
        return 3;
    }
 
    
    public int getId()
    {
        return id;
    }
    
    public int getOrderId(){
        return order_id;
    }
 
    public void setOrderId(int orderId){
        this.order_id = orderId;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
 
    public String getTypeName()
    {
        return typename;
    }
 
    public void setTypeName(String typename)
    {
        this.typename = typename;
    }
  
    public boolean getSelect()
    {
        return isSelect;
    }
 
    public void setSelect(boolean isSelect)
    {
        this.isSelect = isSelect;
    }
}
