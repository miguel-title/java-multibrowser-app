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
public class ExportAccsModel {
    private int id;
    private String ProfileType;
    private String ProjectName;
    private String ProfileName;
    private String FirstName;
    private String Surname;
    private String Email;
    private String Password;
    private String Phone;
    private String Rec_Email;
    private String Proxy;
    private Boolean isSelect;
    
 
    public ExportAccsModel(int id, String ProfileType, String ProjectName, String ProfileName, String FirstName, String SurName, String Email, String Password, String Phone, String Rec_Email, String Proxy, boolean isSelect)
    {
        this.id = id;
        this.ProfileType = ProfileType;
        this.ProjectName = ProjectName;
        this.ProfileName = ProfileName;
        this.FirstName = FirstName;
        this.Surname = SurName;
        this.Email = Email;
        this.Password = Password;
        this.Phone = Phone;
        this.Rec_Email = Rec_Email;
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
 
    public String getProfileType(){
        return ProfileType;
    }
    
    public void setProfileType(String ProfileType){
        this.ProfileType = ProfileType;
    }
    
    public String getProjectName()
    {
        return ProjectName;
    }
 
    public void setProjectName(String ProjectName)
    {
        this.ProjectName = ProjectName;
    }
  
    public String getProfileName()
    {
        return ProfileName;
    }
 
    public void setProfileName(String ProfileName)
    {
        this.ProfileName = ProfileName;
    }
    
    
    public String getFirstName()
    {
        return FirstName;
    }
 
    public void setFirstName(String FirstName)
    {
        this.FirstName = FirstName;
    }
    
    public String getSurName(){
        return Surname;
    }
    
    public void setSurName(String Surname){
        this.Surname = Surname;
    }
    
    public String getEmail(){
        return Email;
    }
    
    public void setEmail(String Email){
        this.Email = Email;
    }
    
    public String getPasssword(){
        return Password;
    }
    
    public void setPassword(String Password){
        this.Password = Password;
    }
    
    public String getPhone(){
        return Phone;
    }
    
    public void setPhone(String Phone){
        this.Phone = Phone;
    }
    
    public String getRec_Email(){
        return Rec_Email;
    }
    
    public void setRec_Email(String Rec_Email){
        this.Rec_Email = Rec_Email;
    }
    
    public boolean getSelect(){
        return isSelect;
    }
    
    public void setSelect(boolean isSelect){
        this.isSelect = isSelect;
    }
    
    public String getProxy(){
        return Proxy;
    }
    
    public void setProxy(String Proxy){
        this.Proxy = Proxy;
    }
}
