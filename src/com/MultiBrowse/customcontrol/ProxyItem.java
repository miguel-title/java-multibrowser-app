/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.customcontrol;

/**
 *
 * @author title
 */
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.util.Pair;

import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;


public class ProxyItem{
    public String ip;
    public String port;


    public ProxyItem(String str){
            String[] tokens = str.split(":");
            ip = tokens[0];
            port = tokens[1];
    }

    public boolean isWorking(){
        boolean connectionStatus=false;

        try {
            InetAddress addr=InetAddress.getByName(ip);//here type proxy server ip      
            if(addr.isReachable(15000)) // 1 second time for response
                connectionStatus = true;
        }                               
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }

        return connectionStatus;

    }

//    public boolean isWorking(){
//        boolean connectionStatus=false;
//
//        try{
//            InetAddress addr=InetAddress.getByName(ip);
//            if(addr.isReachable(5000)){
//                System.out.println("reached");
//                connectionStatus = ensocketize(ip,Integer.parseInt(port));
//            }
//        }catch(Exception ex){ex.printStackTrace();}
//
//            return connectionStatus;
//
//    }
//        
//    public boolean ensocketize(String host,int port){
//        try{
//            HttpClient client=new DefaultHttpClient();
//            HttpGet get=new HttpGet("http://blanksite.com/");
//            HttpHost proxy=new HttpHost(host,port);
//            client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
//            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
//            
//            HttpResponse response=client.execute(get);
//            HttpEntity enti=response.getEntity();
//            if(response!=null){
//                System.out.println(response.getStatusLine());
//                System.out.println(response.toString());
//                System.out.println(host+":"+port+" @@ working");
//                return true;
//            }
//        }catch(Exception ex){
//            System.out.println("Proxy failed");
//            return false;
//        }
//        
//        return false;
//    }
        
}
