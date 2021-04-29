/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.customcontrol;

import com.MultiBrowse.MainWindow.Actions.AllProfilesReportModel;
import com.MultiBrowse.MainWindow.Actions.ExportAccsModel;
import com.MultiBrowse.MainWindow.Actions.ProfileTReportModel;
import com.MultiBrowse.MainWindow.Actions.ProxyMngModel;
import com.MultiBrowse.MainWindow.Settings.ProfileTypesModel;
import com.MultiBrowse.MainWindow.Settings.ProxySettingModel;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
/**
 *
 * @author title
 */
public class LocalDBConnect {
    public LocalDBConnect(){
        initDB();
    }
    
    private void initDB(){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                Statement stmt = con.createStatement();
                
                //Create DataBase
                //Create LogInTest Table(Will Be Disappear)
                stmt.executeUpdate( "CREATE TABLE LogInTest ( user varchar(50), password varchar(50) )" );
                stmt.executeUpdate( "INSERT INTO LogInTest ( user, password ) VALUES ( 'test@test.com', 'test' )" );
                //Create Project/Profile Table(type:0(Project), 1(Profile))
                stmt.executeUpdate( "CREATE TABLE Prj_Prf (id int(11) primary key auto_increment, name varchar(255), type tinyint(1), class varchar(255))" );
                stmt.executeUpdate( "INSERT INTO Prj_Prf (name, type, class) VALUES ('Projects', 0, '0000.')" );
                //Create Profile_Info Table
                stmt.executeUpdate( "CREATE TABLE Profile_Info(id int(11) primary key auto_increment, proxyid int(11), profiletypeid int(11), fname varchar(255), lname varchar(255), fullname varchar(255), email varchar(255), password varchar(255), phonenumber varchar(255), recoveryemail varchar(255), recoverymobile varchar(255), prjprfid int(11), useragent varchar(255))" );
                //Create Proxy_manage Table
                stmt.executeUpdate( "CREATE TABLE Proxy_manage(id int(11) primary key auto_increment, proxy_ip varchar(255), proxy_port varchar(127), proxy_username varchar(255), proxy_password varchar(255), order_id int(11))" );
                //Create Profile_types Table
                stmt.executeUpdate( "CREATE TABLE Profile_types(id int(11) primary key auto_increment, type_name varchar(255), order_id int(11))" );
                //Create Default Browser
                stmt.executeUpdate( "CREATE TABLE Default_browser(id int(11) primary key auto_increment, browser_type int(3))" );
                stmt.executeUpdate( "INSERT INTO Default_browser(browser_type) VALUES (0)" );
                //Create MultiBorwse License
                stmt.executeUpdate( "CREATE TABLE MultiBrowse_license(id int(11) primary key auto_increment, registerUser varchar(255), licenseType varchar(127), user varchar(127), addons varchar(127), validity varchar(127))" );
                stmt.executeUpdate( "INSERT INTO MultiBrowse_license(registerUser, licenseType, user, addons, validity) VALUES ('A', 'B', 'C', 'D', 'E')" );
            
                stmt.executeUpdate( "CREATE TABLE ProfileBookmarks(id int(11) primary key auto_increment, profile_id int(11), name varchar(255), url varchar(511), favIconPath varchar(255))" );
            stmt.close();
            con.close();
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
        }
    }
    
    public ArrayList<objProxyman> getProxies(int nType){
        ArrayList<objProxyman> lstResult = new ArrayList<objProxyman>();
        
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(String.format("Select id, proxy_ip, proxy_port, proxy_username, proxy_password, order_id from Proxy_manage"));
                if (nType == 0){
                    objProxyman objDef = new objProxyman();
                    objDef.ID = -1;
                    objDef.Proxy_Ip = "";
                    objDef.Proxy_Port = "";
                    objDef.Proxy_Username = "";
                    objDef.Proxy_Password = "";
                    objDef.Order = -1;
                    lstResult.add(objDef);
                }
                while (rs.next()){
                    objProxyman obj = new objProxyman();
                    obj.ID = rs.getInt(1);
                    obj.Proxy_Ip = rs.getString(2);
                    obj.Proxy_Port = rs.getString(3);
                    obj.Proxy_Username = rs.getString(4);
                    obj.Proxy_Password = rs.getString(5);
                    obj.Order = rs.getInt(6);
                    
                    lstResult.add(obj);
                }
            stmt.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return lstResult;
    }
    
    public ArrayList<objProfiletype> getProfileTypes(int nType){
        ArrayList<objProfiletype> lstResult = new ArrayList<objProfiletype>();
        
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM Profile_types"));
                if (nType == 0){
                    objProfiletype objDef = new objProfiletype();
                    objDef.ID = -1;
                    objDef.ProfileType = "";
                    objDef.Order = -1;
                    lstResult.add(objDef);
                }
                while( rs.next() )
                {
                    objProfiletype obj = new objProfiletype();
                    obj.ID = rs.getInt(1);
                    obj.ProfileType = rs.getString(2);
                    obj.Order = rs.getInt(3);

                    lstResult.add(obj);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return lstResult;
    }
    
    
//  In Login Dialog, Validate User    
    public boolean validateUser(String strUser, String strPassword){
        boolean b = false;
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM LogInTest where user = '%s' and password = '%s'", strUser, strPassword));
                while( rs.next() )
                {
                    b = true;
                    break;
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return b;
    }
    
//  Data In Main Tree
    public ArrayList<objProject> getProject(){
        ArrayList<objProject> lstProjects = new ArrayList<objProject>();
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(String.format("SELECT id, name, type, class FROM Prj_Prf Order By class"));
                while( rs.next() )
                {
                    objProject obj = new objProject();
                    obj.id = rs.getInt(1);
                    obj.strName = rs.getString(2);
                    obj.Type = rs.getInt(3);
                    obj.strClass = rs.getString(4);
                    
                    lstProjects.add(obj);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
//        System.out.print(lstProjects);
        return lstProjects;
    }
    
    //When Edit, Get Selected Project Info
    public objProject getProject(int nID){
        objProject objProjects = new objProject();
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                String strID = Integer.toString(nID);
                ResultSet rs = stmt.executeQuery(String.format("SELECT id, name, type, class FROM Prj_Prf Where id = '%s'", strID));
                while( rs.next() )
                {
                    objProjects.id = rs.getInt(1);
                    objProjects.strName = rs.getString(2);
                    objProjects.Type = rs.getInt(3);
                    objProjects.strClass = rs.getString(4);
                    
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return objProjects;
    }
    
//  In Create or Edit Project, Location Info
    public ArrayList<objProject> getProjectInfo(){
        ArrayList<objProject> lstResult = new ArrayList<objProject>();
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
//                ResultSet rs = stmt.executeQuery(String.format("SELECT id, CONCAT(REPEAT(' ', (LENGTH(class)/5 - 1) * 3), name) FROM Prj_Prf where type = 0 Order By class"));
                ResultSet rs = stmt.executeQuery(String.format("SELECT id, name, type, class FROM Prj_Prf where type = 0 Order By class"));
                while( rs.next() )
                {
                    objProject obj = new objProject();
                    obj.id = rs.getInt(1);
                    obj.strName = rs.getString(2);
                    obj.Type = rs.getInt(3);
                    obj.strClass = rs.getString(4);
                    
                    lstResult.add(obj);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
//        System.out.print(lstResult);
        return lstResult;
    }

    public ArrayList<objProject> getProfileInfo(){
        ArrayList<objProject> lstResult = new ArrayList<objProject>();
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
//                ResultSet rs = stmt.executeQuery(String.format("SELECT id, CONCAT(REPEAT(' ', (LENGTH(class)/5 - 1) * 3), name) FROM Prj_Prf where type = 0 Order By class"));
                ResultSet rs = stmt.executeQuery(String.format("SELECT id, name, type, class FROM Prj_Prf where type = 1 Order By class"));
                while( rs.next() )
                {
                    objProject obj = new objProject();
                    obj.id = rs.getInt(1);
                    obj.strName = rs.getString(2);
                    obj.Type = rs.getInt(3);
                    obj.strClass = rs.getString(4);
                    
                    lstResult.add(obj);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
//        System.out.print(lstResult);
        return lstResult;
    }
    
//  In Create Project Insert Project Info
    public boolean insertProject(objProject data){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                Statement stmt = con.createStatement();
                
                stmt.executeUpdate( String.format("INSERT INTO Prj_Prf (name, type, class) VALUES ('%s', '%s', '%s')", data.strName, Integer.toString(data.Type), data.strClass) );
            
            stmt.close();
            con.close();
            return true;
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
            return false;
        }
    }
    
    public boolean insertProfile(objProfile data){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                Statement stmt = con.createStatement();
                
                stmt.executeUpdate(String.format("INSERT INTO Profile_Info(proxyid, profiletypeid, fname, lname, fullname, email, password, phonenumber, recoveryemail, recoverymobile, prjprfid, useragent) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", Integer.toString(data.proxyid), Integer.toString(data.profiletypeid), data.fname, data.lname, data.fullname, data.email, data.password, data.phonenumber, data.recoveryemail, data.recoverymobile, Integer.toString(data.prjprfid), data.useragent) );
            
            stmt.close();
            con.close();
            return true;
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
            return false;
        }
    }
    
    public boolean removeNodes(List<String> lstIDs){
//        String strRemoveIDs = lstIDs.stream()
//                .map(n -> String.valueOf(n))
//                .collect(Collectors.joining(",", "(", ")"));
        
        for (int i = 0; i < lstIDs.size(); i ++){
            String strRemoveID = lstIDs.get(i);
            try
            {
                Class.forName("org.h2.Driver");
                    Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                    Statement stmt = con.createStatement();

                    stmt.executeUpdate( String.format("DELETE FROM Prj_Prf WHERE id = '%s'", strRemoveID) );

                stmt.close();
                con.close();
            }
            catch( Exception e )
            {
                System.out.println( e.getMessage() );
                return false;
            }
        }
        
        return true;
     
    }
    
    public int getProjectID(String strClass){
        int nProjectID = 0;
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(String.format("SELECT id FROM Prj_Prf where class = '%s'", strClass));
                while( rs.next() )
                {
                    nProjectID = rs.getInt(1);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return nProjectID;
    }
    
     public String getProjectName(String strClass){
        String strProjectName= "Projects";
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rsProject = stmt.executeQuery(String.format("SELECT name FROM Prj_Prf where class = '%s'", strClass));
                    
                while (rsProject.next()){
                    strProjectName = rsProject.getString(1);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return strProjectName;
    }
    
    public boolean updateProjectData(List lstNodes){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                Statement stmt = con.createStatement();
                
                String strRootID = lstNodes.get(0).toString().split(" ")[1];
                stmt.executeUpdate( String.format("UPDATE Prj_Prf SET class = '0000.' WHERE id != '%s'", strRootID) );
                    
                for (int i = 0; i< lstNodes.size(); i ++){
                    String strID = lstNodes.get(i).toString().split(" ")[0];
                    String strClass = getNextClass(lstNodes.get(i).toString().split(" ")[1], stmt);
                    stmt.executeUpdate( String.format("UPDATE Prj_Prf SET class = '%S' WHERE id = '%s'", strClass, strID) );
                }
            stmt.close();
            con.close();
            return true;
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
            return false;
        }
    }
    
    public String getNextClass(String strID, Statement stmt){
        String strClass = "";
        String strTmpClass = "";
        String strParentClass = "";
        try
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT class FROM Prj_Prf where id = '%s'", strID));
            while( rs.next() )
            {
                strParentClass = rs.getString(1);
            }
            
            
            ResultSet rs1 = stmt.executeQuery(String.format("SELECT max(class) FROM Prj_Prf WHERE LEFT(class,LENGTH(class) - 5) = '%s'", strParentClass));
            while( rs1.next() )
            {
                strTmpClass = rs1.getString(1);
            }
            
            if (strTmpClass == null)
                strClass = strParentClass + "0000.";
            else{
                //Get Next Class.
                String[] lstClass = strTmpClass.split("\\.");
                String strPrefix = strTmpClass.substring(0, strTmpClass.length() - 5);
                String strSufix = lstClass[lstClass.length - 1];
                
                int nCor = Integer.parseInt(strSufix) + 1;
                String strCor = Integer.toString(nCor);
                int nRestLen = 4 - strCor.length();
                String repeated = new String(new char[nRestLen]).replace("\0", "0");
                String strFSufix = repeated + strCor + ".";
                strClass = strPrefix + strFSufix;
            }
            
        }
        catch( Exception e )
        {
            System.out.println( "\nError:" + e.getMessage() );
        }
        
        return strClass;
    }
    
    public boolean updateProjectInfo(objProject data){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                Statement stmt = con.createStatement();
                
                stmt.executeUpdate( String.format("UPDATE Prj_Prf SET name = '%s' WHERE id = '%s'", data.strName, Integer.toString(data.id)) );
                   
            stmt.close();
            con.close();
            return true;
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
            return false;
        }
    }
    
    
    public objProfile getProfile(int nProfileID){
        objProfile objResult = new objProfile();
        
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(String.format("SELECT b.name, a.id, a.proxyid, a.profiletypeid, a.fname, a.lname, a.fullname, a.email, a.password, a.phonenumber, a.recoveryemail, a.recoverymobile, a.prjprfid, a.useragent FROM Profile_Info a left join Prj_Prf b on b.id = a.prjprfid Where a.prjprfid = '%s'", Integer.toString(nProfileID)));
                while( rs.next() )
                {
                    objResult.profilename = rs.getString(1);
                    objResult.id = rs.getInt(2);
                    objResult.proxyid = rs.getInt(3);
                    objResult.profiletypeid = rs.getInt(4);
                    objResult.fname = rs.getString(5);
                    objResult.lname = rs.getString(6);
                    objResult.fullname = rs.getString(7);
                    objResult.email = rs.getString(8);
                    objResult.password = rs.getString(9);
                    objResult.phonenumber = rs.getString(10);
                    objResult.recoveryemail = rs.getString(11);
                    objResult.recoverymobile = rs.getString(12);
                    objResult.prjprfid = rs.getInt(13);
                    objResult.useragent = rs.getString(14);

                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return objResult;
    }
    
    public boolean updateProfile(objProfile data){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                Statement stmt = con.createStatement();
                //Save In Prj_Prf
                String strPrjPrfID = Integer.toString(data.prjprfid);
                String strProfileName = data.profilename;
                
                stmt.executeUpdate( String.format("UPDATE Prj_Prf SET name = '%s' WHERE id = '%s'", strProfileName, strPrjPrfID) );
                
                //Save In Profile_Info               
                stmt.executeUpdate( String.format("UPDATE Profile_Info SET proxyid = '%s', profiletypeid = '%s', fname = '%s', lname = '%s', fullname = '%s', email = '%s', password = '%s', phonenumber = '%s', recoveryemail = '%s', recoverymobile = '%s', useragent = '%s' WHERE prjprfid = '%s'" , Integer.toString(data.proxyid), Integer.toString(data.profiletypeid), data.fname, data.lname, data.fullname, data.email, data.password, data.phonenumber, data.recoveryemail, data.recoverymobile, data.useragent, Integer.toString(data.prjprfid)) );
                   
            stmt.close();
            con.close();
            return true;
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
            return false;
        }
    }
    
    public ArrayList<ProfileTypesModel> getProxyTypes(){
        
        ArrayList<ProfileTypesModel> data = new ArrayList<ProfileTypesModel>();
        
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(String.format("SELECT id, type_name FROM Profile_types ORDER BY order_id"));
                
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();
                
                while( rs.next() )
                {
                    ProfileTypesModel oneData = new ProfileTypesModel(rs.getInt(1), rs.getString(2), false);
                    
                    data.add(oneData);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return data;
    }
    
    public boolean InsertProxy(ProfileTypesModel data){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                Statement stmt = con.createStatement();
                //Save In Prj_Prf
                String strOrderId = Integer.toString(data.getOrderId());
                String strTypeName = data.getTypeName();
                          
                stmt.executeUpdate( String.format("INSERT INTO Profile_types(type_name, order_id) VALUES ('%s', '%s')", strTypeName, strOrderId) );
                   
            stmt.close();
            con.close();
            return true;
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
            return false;
        }
    }
    
    public boolean UpdateProxy(ProfileTypesModel data){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                Statement stmt = con.createStatement();
                //Save In Prj_Prf
                String strOrderId = Integer.toString(data.getOrderId());
                String strTypeName = data.getTypeName();
                String strId = Integer.toString(data.getId());
                          
                stmt.executeUpdate( String.format("UPDATE Profile_types SET type_name = '%s', order_id = '%s' WHERE id = '%s'", strTypeName, strOrderId, strId) );
                   
            stmt.close();
            con.close();
            return true;
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
            return false;
        }
    }
    
    public boolean DeleteProxies(int nID){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                Statement stmt = con.createStatement();
                //Save In Prj_Prf
                String strId = Integer.toString(nID);
                          
                stmt.executeUpdate( String.format("DELETE FROM Profile_types WHERE id = '%s'", strId) );
                   
            stmt.close();
            con.close();
            return true;
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
            return false;
        }
    }
    
    //0:chorme, 1:firefoxf
    public int getDefaultBrowser(){
        int nBrowserType = 0;
        
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(String.format("SELECT browser_type FROM Default_browser LIMIT 1"));
                
                
                while( rs.next() )
                {
                    nBrowserType = rs.getInt(1);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return nBrowserType;
    }
    
    public boolean setDefaultBrowser(int nBrowserType){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                Statement stmt = con.createStatement();
                
                String strBrowserType = Integer.toString(nBrowserType);
                
                stmt.executeUpdate( String.format("UPDATE Default_browser SET browser_type = '%s'", strBrowserType) );
                   
            stmt.close();
            con.close();
            return true;
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
            return false;
        }
    }
    
    public ProxySettingModel getProxySetting(int nProxyId){
        ProxySettingModel oneData = null;
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(String.format("Select id, proxy_ip, proxy_port, proxy_username, proxy_password, order_id from Proxy_manage where id = '%s'", Integer.toString(nProxyId)));
                
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();
                
                while( rs.next() )
                {
                    oneData = new ProxySettingModel("", rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), false, rs.getInt(1));
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
       return oneData;
    }
    
    public ArrayList<ProxySettingModel> getProxySettings(){
        
        ArrayList<ProxySettingModel> data = new ArrayList<ProxySettingModel>();
        
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(String.format("Select id, proxy_ip, proxy_port, proxy_username, proxy_password, order_id from Proxy_manage"));
                
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();
                
                while( rs.next() )
                {
                    ProxySettingModel oneData = new ProxySettingModel("", rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), false, rs.getInt(1));
                    
                    data.add(oneData);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return data;
    }
    
    public boolean InsertProxySetting(ProxySettingModel data){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                Statement stmt = con.createStatement();
                //Save In Prj_Prf
                String strProxyIP = data.getIPAddress();
                String strProxyPort = Integer.toString(data.getPort());
                String strProxyUserName = data.getUserName();
                String strProxyPassword = data.getPassword();
                          
                stmt.executeUpdate( String.format("INSERT INTO Proxy_manage(proxy_ip, proxy_port, proxy_username, proxy_password) VALUES ('%s', '%s', '%s', '%s')", strProxyIP, strProxyPort, strProxyUserName, strProxyPassword) );
                   
            stmt.close();
            con.close();
            return true;
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
            return false;
        }
    }
    
    public boolean UpdateProxySetting(ProxySettingModel data){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                Statement stmt = con.createStatement();
                //Save In Prj_Prf
                String strProxyIP = data.getIPAddress();
                String strProxyPort = Integer.toString(data.getPort());
                String strProxyUserName = data.getUserName();
                String strProxyPassword = data.getPassword();
                String strId = Integer.toString(data.getId());
                          
                stmt.executeUpdate( String.format("UPDATE Proxy_manage SET proxy_ip = '%s', proxy_port = '%s', proxy_username = '%s', proxy_password = '%s' WHERE id = '%s'", strProxyIP, strProxyPort, strProxyUserName, strProxyPassword, strId) );
                   
            stmt.close();
            con.close();
            return true;
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
            return false;
        }
    }
    
    public boolean DeleteProxySetting(int nID){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                Statement stmt = con.createStatement();
                //Save In Prj_Prf
                String strId = Integer.toString(nID);
                          
                stmt.executeUpdate( String.format("DELETE FROM Proxy_manage WHERE id = '%s'", strId) );
                   
            stmt.close();
            con.close();
            return true;
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
            return false;
        }
    }
    
    public objLicenseData getMBLicense(){
        objLicenseData data = new objLicenseData();
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(String.format("Select id, registerUser, licenseType, user, addons, validity from MultiBrowse_license LIMIT 1"));
                
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();
                
                while( rs.next() )
                {
                    data.id = rs.getInt(1);
                    data.strRegisterUser = rs.getString(2);
                    data.strLicenseType = rs.getString(3);
                    data.strUsers = rs.getString(4);
                    data.strAddons = rs.getString(5);
                    data.strValidate = rs.getString(6);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
    public boolean backupDB(String strFilePath){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                con.prepareStatement(String.format("BACKUP TO '%s'", strFilePath)).executeUpdate();
               
            stmt.close();
            con.close();
            
            JOptionPane.showMessageDialog(null, "BackUp Successfully");
            return true;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
    public ArrayList<ProfileTReportModel> getReportProxyTypes(){
        ArrayList<ProfileTReportModel> lstResult = new ArrayList<ProfileTReportModel>();
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(String.format("SELECT t0.id, t0.type_name, GROUP_CONCAT(t2.name) FROM Profile_types t0 LEFT JOIN Profile_Info t1 ON t1.profiletypeid = t0.id LEFT JOIN Prj_Prf t2 ON t2.id = t1.prjprfid GROUP BY t0.id"));
                
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();
                
                while( rs.next() )
                {
                    ProfileTReportModel oneData = new ProfileTReportModel(rs.getInt(1), rs.getString(2), rs.getString(3), false);
                    
                    lstResult.add(oneData);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return lstResult;
    }
    
    public ArrayList<AllProfilesReportModel> getProfiles(int nProfileID, int nProxyID, int nProfileTypeID, String strParentClass){
        ArrayList<AllProfilesReportModel> lstResult = new ArrayList<AllProfilesReportModel>();
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(String.format("SELECT t0.id, t1.name, t3.proxy_ip, t2.type_name, t1.class, t1.id, t3.id, t2.id FROM Profile_Info t0 LEFT JOIN Prj_Prf t1 ON t1.id = t0.prjprfid LEFT JOIN Profile_types t2 ON t2.id = t0.profiletypeid LEFT JOIN Proxy_manage t3 ON t3.id = t0.proxyid WHERE t1.type = 1"));
                
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();
                
                while( rs.next() )
                {
                    String strClass = rs.getString(5);
                    String strProjectClass = strClass.substring(0, strClass.length() - 5);
                    
                    String strProjectName = getProjectName(strProjectClass);
                    AllProfilesReportModel aData = new AllProfilesReportModel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), strProjectName, false);
                    if (nProfileID > 0){
                        int ProfileID = rs.getInt(6);
                        if (ProfileID != nProfileID){
                            continue;
                        }
                    }
                    if (nProxyID > 0){
                        int ProxyID = rs.getInt(7);
                        if (ProxyID != nProxyID){
                            continue;
                        }
                        
                    }
                    if (nProfileTypeID > 0){
                        int ProfileTypeID = rs.getInt(8);
                        if (ProfileTypeID != nProfileTypeID){
                            continue;
                        }
                    }
                    if (!strParentClass.isEmpty()){
                        int ProjectID = rs.getInt(6);
                        if (!isBelongtoProject(ProjectID, strParentClass)){
                            continue;
                        }
                    }
                    
                    
                    lstResult.add(aData);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return lstResult;
    }
    
    private String getProjectClass(int nID){
        String strClass = "";
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(String.format("SELECT class FROM Prj_Prf where id = '%s'", Integer.toString(nID)));
                while( rs.next() )
                {
                    strClass = rs.getString(1);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return strClass;
    }
    
    private boolean isBelongtoProject(int nChildID, String strParentClass){
        String strChildClass = getProjectClass(nChildID);
        
        boolean iSBelong = strChildClass.startsWith(strParentClass);
        
        return iSBelong;
    }
    
    public int getPrjPrfID(int nID){
        int nPrjPrfID = 0;
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery( String.format("SELECT prjprfid FROM Profile_Info WHERE id = '%s'", Integer.toString(nID)) );
                while( rs.next() )
                {
                    nPrjPrfID = rs.getInt(1);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return nPrjPrfID;
    }
    
    public boolean DeleteProfile(int nID){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                Statement stmt = con.createStatement();
                //Save In Prj_Prf
                String strId = Integer.toString(nID);
                
                int nPrjPrfID = getPrjPrfID(nID);
                stmt.executeUpdate(String.format("DELETE FROM Prj_Prf WHERE id = '%s'", Integer.toString(nPrjPrfID)));
                stmt.executeUpdate( String.format("DELETE FROM Profile_Info WHERE id = '%s'", strId) );
            stmt.close();
            con.close();
            return true;
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
            return false;
        }
    }
    
    public ArrayList<ExportAccsModel> getProfiles(){
        ArrayList<ExportAccsModel> lstData = new ArrayList<ExportAccsModel>();
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery( String.format("SELECT t0.id, t1.name, t0.fname, t0.lname, t0.email, t0.password, t0.phonenumber, recoveryemail, t2.proxy_ip, t1.class, t3.type_name FROM Profile_Info t0 LEFT JOIN Prj_Prf t1 ON t0.prjprfid = t1.id LEFT JOIN Proxy_manage t2 ON t2.id = t0.proxyid LEFT JOIN Profile_types t3 ON t3.id = t0.profiletypeid") );
                while( rs.next() )
                {
                    String strProfileType = rs.getString(11);
                    String strProfileClass = rs.getString(10);
                    String strProjectClass = strProfileClass.substring(0, strProfileClass.length() - 5);
                    String strProjectName = getProjectName(strProjectClass);
                    
                    ExportAccsModel aData = new ExportAccsModel(rs.getInt(1), strProfileType, strProjectName, rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), false);
                    lstData.add(aData);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return lstData;
    }
    
    public ArrayList<ProxyMngModel> getProfileList(int nProxyID){
        ArrayList<ProxyMngModel> lstData = new ArrayList<ProxyMngModel>();
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery( String.format("SELECT t0.id, t0.name, t2.proxy_ip, t2.proxy_port, t2.proxy_username, t2.proxy_password, t2.id FROM Prj_Prf t0 LEFT JOIN Profile_Info t1 ON t1.prjprfid = t0.id LEFT JOIN Proxy_manage t2 ON t2.id = t1.proxyid WHERE t0.type = 1") );
                while( rs.next() )
                {
                    
                    if (nProxyID > 0){
                        int ProxyID = rs.getInt(7);
                        if (ProxyID != nProxyID){
                            continue;
                        }
                        
                    }
                    
                    ProxyMngModel aData = new ProxyMngModel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), false);
                    lstData.add(aData);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return lstData;
    }
    
    public boolean updateProxyInfo(int nProxyId, int nProfileId){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                Statement stmt = con.createStatement();
                stmt.executeUpdate(String.format("UPDATE Profile_Info SET proxyid = '%s' WHERE id = '%s'", Integer.toString(nProxyId), Integer.toString(nProfileId)));
            stmt.close();
            con.close();
            return true;
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
            return false;
        }
    }
    
    //get Profile_info table id from prjprf table id
    public int getProfileInfoID(int nPrjPrfId){
        int nProfileInfoId = 0;
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery( String.format("SELECT id FROM Profile_Info WHERE prjprfid = '%s' LIMIT 1", Integer.toString(nPrjPrfId)) );
                while( rs.next() )
                {
                    nProfileInfoId = rs.getInt(1);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return nProfileInfoId;
    }
    
    public void dropAllObject(){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                stmt.executeUpdate( "DROP ALL OBJECTS" );
                
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public int getProfileTypeId(String strType){
        int nId = 0;
        
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery( String.format("SELECT id FROM Profile_types WHERE type_name = '%s' LIMIT 1", strType) );
                while( rs.next() )
                {
                    nId = rs.getInt(1);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return nId;
    }
    
    public int getProjectId(String strProjectName){
        int nId = 0;
        
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery( String.format("SELECT id FROM Prj_Prf WHERE name = '%s' LIMIT 1", strProjectName) );
                while( rs.next() )
                {
                    nId = rs.getInt(1);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return nId;
    }
    
    public int getproxyId(String strProxyIps){
        int nId = 0;
        
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery( String.format("SELECT id FROM Proxy_manage WHERE proxy_ip = '%s' LIMIT 1", strProxyIps) );
                while( rs.next() )
                {
                    nId = rs.getInt(1);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return nId;
    }
    
    public String getProxy(int nProxyId){
        String strProxy = "";
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery( String.format("SELECT proxy_ip FROM Proxy_manage WHERE id = '%s' LIMIT 1", Integer.toString(nProxyId)) );
                while( rs.next() )
                {
                    strProxy = rs.getString(1);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return strProxy;
    }
    
    public String getProxyPort(int nProxyId){
        String strProxyPort = "";
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery( String.format("SELECT proxy_port FROM Proxy_manage WHERE id = '%s' LIMIT 1", Integer.toString(nProxyId)) );
                while( rs.next() )
                {
                    strProxyPort = rs.getString(1);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return strProxyPort;
    }
    
    public String getProxyUserName(int nProxyId){
        String strProxyUserName = "";
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery( String.format("SELECT proxy_username FROM Proxy_manage WHERE id = '%s' LIMIT 1", Integer.toString(nProxyId)) );
                while( rs.next() )
                {
                    strProxyUserName = rs.getString(1);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return strProxyUserName;
    }
    
    public String getProxyPassword(int nProxyId){
        String strProxyPassword = "";
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery( String.format("SELECT proxy_password FROM Proxy_manage WHERE id = '%s' LIMIT 1", Integer.toString(nProxyId)) );
                while( rs.next() )
                {
                    strProxyPassword = rs.getString(1);
                }
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return strProxyPassword;
    }
    
    public ArrayList<objBookmarks> getBookmarks(int nProfileId){
        
        ArrayList<objBookmarks> lstResult = new ArrayList<objBookmarks>();
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
               
                ResultSet rs = stmt.executeQuery( String.format("SELECT id, profile_id, name, url, favIconPath FROM ProfileBookmarks WHERE profile_id = '%s'", Integer.toString(nProfileId)) );
                while( rs.next() )
                {
                    objBookmarks oneData = new objBookmarks();
                    oneData.id = rs.getInt(1);
                    oneData.profileId = rs.getInt(2);
                    oneData.strBookmarkName = rs.getString(3);
                    oneData.strBookmarkUrl = rs.getString(4);
                    oneData.strFavIconPath = rs.getString(5);
                    
                    lstResult.add(oneData);
                }
                
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return lstResult;
    }
    
    
    public String getBookmarkUrl(String strID){
        
        String strUrl = "";
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=TRUE", "mbuser", "" );
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery( String.format("SELECT url FROM ProfileBookmarks WHERE id = '%s'", strID) );
                while( rs.next() )
                {
                    strUrl = rs.getString(1);
                }
                
            stmt.close();
            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return strUrl;
    }
    
    public boolean deleteBookmark(String strId){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                Statement stmt = con.createStatement();

                stmt.executeUpdate( String.format("DELETE FROM ProfileBookmarks WHERE id = '%s'", strId) );

            stmt.close();
            con.close();
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
            return false;
        }
        
        return true;
    }
    
    public boolean addBookmark(objBookmarks data){
        try
        {
            Class.forName("org.h2.Driver");
                Connection con = DriverManager.getConnection("jdbc:h2:db/mbdb;IFEXISTS=False", "mbuser", "" );
                Statement stmt = con.createStatement();
                
                stmt.executeUpdate( String.format("INSERT INTO ProfileBookmarks (profile_id, name, url, favIconPath ) VALUES ('%s', '%s', '%s', '%s')", Integer.toString(data.profileId), data.strBookmarkName, data.strBookmarkUrl, data.strFavIconPath) );
            
            stmt.close();
            con.close();
            return true;
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
            return false;
        }
    }
}
