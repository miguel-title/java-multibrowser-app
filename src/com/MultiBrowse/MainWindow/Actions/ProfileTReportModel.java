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
public class ProfileTReportModel {
    private int id;
    private String typename;
    private boolean isSelect;
    private String strProfiles;
 
    public ProfileTReportModel(int id, String typename, String strProfiles, boolean isSelect)
    {
        this.id = id;
        this.typename = typename;
        this.isSelect = isSelect;
        this.strProfiles = strProfiles;
    }
    
    public int getDataCount(){
        return 4;
    }
 
    public int getId()
    {
        return id;
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
    
    
    public String getProfiles()
    {
        return strProfiles;
    }
 
    public void setProfiles(String strProfiles)
    {
        this.strProfiles = strProfiles;
    }
}
