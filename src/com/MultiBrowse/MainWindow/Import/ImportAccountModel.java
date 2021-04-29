/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Import;


/**
 *
 * @author title
 */
public class ImportAccountModel {
    private int id;
    private ProfileTypesComboItem profileType;
    private ProjectNameComboItem projectName;
    private String profileName;
    private String firstName;
    private String surName;
    private String email;
    private String password;
    private String phone;
    private String recEmail;
    private ProxyComboItem Proxy;
    private boolean isSelect;
 
    public ImportAccountModel(int id, ProfileTypesComboItem profileType, ProjectNameComboItem projectName, String profileName, String firstName, String surName, String email, String password, String phone, String recEmail, ProxyComboItem Proxy, boolean isSelect)
    {
        this.id = id;
        this.profileType = profileType;
        this.projectName = projectName;
        this.profileName = profileName;
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.recEmail = recEmail;
        this.Proxy = Proxy;
        this.isSelect = isSelect;
    }
    
    public int getDataCount(){
        return 12;
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
        return profileName;
    }
 
    public void setProfileName(String profileName)
    {
        this.profileName = profileName;
    }
  
    public boolean getSelect()
    {
        return isSelect;
    }
 
    public void setSelect(boolean isSelect)
    {
        this.isSelect = isSelect;
    }
    
    
    public ProxyComboItem getProxy()
    {
        return Proxy;
    }
 
    public void setProxy(ProxyComboItem Proxy)
    {
        this.Proxy = Proxy;
    }
    
    
    public ProfileTypesComboItem getProfileType()
    {
        return profileType;
    }
 
    public void setProfileType(ProfileTypesComboItem profileType)
    {
        this.profileType = profileType;
    }
    
    
    public ProjectNameComboItem getProjectName()
    {
        return projectName;
    }
 
    public void setProjectName(ProjectNameComboItem projectName)
    {
        this.projectName = projectName;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public void setFirstName(String strFirstName){
        this.firstName = strFirstName;
    }
    
    public String getSurName(){
        return surName;
    }
    
    public void setSurName(String strSurName){
        this.surName = strSurName;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String strEmail){
        this.email = strEmail;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String strPassword){
        this.password = strPassword;
    }
    
    public String getPhone(){
        return phone;
    }
    
    public void setPhone(String strPhone){
        this.phone = strPhone;
    }
    
    public String getRecEmail(){
        return recEmail;
    }
    
    public void setRecEmail(String strRecEmail){
        this.recEmail = strRecEmail;
    }
}
