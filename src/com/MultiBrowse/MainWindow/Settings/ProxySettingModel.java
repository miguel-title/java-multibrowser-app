/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Settings;

/**
 *
 * @author title
 */
public class ProxySettingModel {
    
    private int id;
    private String ProxyIPAddress;
    private int Port;
    private String UserName;
    private String Password;
    private boolean isSelect;
    private String Status;
    private int nRowIndex;
 
    public ProxySettingModel(String Status, String ProxyIPAddress, int Port, String UserName, String Password, boolean isSelect, int id)
    {
        this.id = id;
        this.ProxyIPAddress = ProxyIPAddress;
        this.Port = Port;
        this.UserName = UserName;
        this.Password = Password;
        this.isSelect = isSelect;
        this.Status = Status;
    }
    
    public int getDataCount(){
        return 7;
    }
 
    public int getRowIndex()
    {
        return nRowIndex;
    }
    
    public void setRowIndex(int nRowIndex)
    {
        this.nRowIndex = nRowIndex;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
 
    public String getIPAddress(){
        return ProxyIPAddress;
    }
 
    public void setIPAddress(String IPAddress){
        this.ProxyIPAddress = ProxyIPAddress;
    }
    
    public int getPort()
    {
        return Port;
    }
 
    public void setPort(int Port)
    {
        this.Port = Port;
    }
  
    public String getUserName(){
        return UserName;
    }
    
    public void setUserName(String UserName)
    {
        this.UserName = UserName;
    }
    
    public String getPassword(){
        return Password;
    }
    
    public void setPassword(String Password){
        this.Password = Password;
    }
    
    public String getStatus(){
        return Status;
    }
    
    public void setStatus(String Status){
        this.Status = Status;
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
