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
public class AllProfilesReportModel {
    private int id;
    private String profileName;
    private String proxyIp;
    private String profileType;
    private String projectName;
    private boolean isSelect;
 
    public AllProfilesReportModel(int id, String profileName, String proxyIp, String profileType, String projectName, boolean isSelect)
    {
        this.id = id;
        this.profileName = profileName;
        this.proxyIp = proxyIp;
        this.profileType = profileType;
        this.projectName = projectName;
        this.isSelect = isSelect;
    }
    
    public int getDataCount(){
        return 6;
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
    
    
    public String getProxyIp()
    {
        return proxyIp;
    }
 
    public void setProxyIp(String proxyIp)
    {
        this.proxyIp = proxyIp;
    }
    
    
    public String getProfileType()
    {
        return profileType;
    }
 
    public void setProfileType(String profileType)
    {
        this.profileType = profileType;
    }
    
    
    public String getProjectName()
    {
        return projectName;
    }
 
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }
}
