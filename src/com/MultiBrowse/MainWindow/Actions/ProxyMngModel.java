/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Actions;

/**
 *
 * @author title
 */
public class ProxyMngModel {
    private int id;
    private String ProfileName;
    private String ProxyIPAddress;
    private String Port;
    private String UserName;
    private String Password;
    private boolean isSelect;
 
    public ProxyMngModel(int id, String ProfileName, String ProxyIPAddress, String Port, String UserName, String Password, boolean isSelect)
    {
        this.id = id;
        this.ProfileName = ProfileName;
        this.ProxyIPAddress = ProxyIPAddress;
        this.Port = Port;
        this.UserName = UserName;
        this.Password = Password;
        this.isSelect = isSelect;
    }
    
    public int getDataCount(){
        return 7;
    }
 
    public int getId()
    {
        return id;
    }
    
    
    public void setId(int id)
    {
        this.id = id;
    }
 
    public String getProfileName()
    {
        return ProfileName;
    }
 
    public void setProfileName(String ProfileName)
    {
        this.ProfileName = ProfileName;
    }
  
    public String getProxyIPAddress()
    {
        return ProxyIPAddress;
    }
 
    public void setProxyIPAddress(String ProxyIPAddress)
    {
        this.isSelect = isSelect;
    }
    
    
    public String getPort()
    {
        return Port;
    }
 
    public void setPort(String Port)
    {
        this.Port = Port;
    }
    
    public String getUserName(){
        return UserName;
    }
    
    public void setUserName(String UserName){
        this.UserName = UserName;
    }
    
    public String getPassword(){
        return Password;
    }
    
    public void setPassword(String Password){
        this.Password = Password;
    }
    
    public boolean getSelect(){
        return isSelect;
    }
    
    public void setSelect(boolean isSelect){
        this.isSelect = isSelect;
    }
}
