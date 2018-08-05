package com.markava.itcompany.deadlock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.markava.itcompany.jdbc.AddressJDBC;

public class MyDeadlock extends Thread{
	private final static Logger LOGGER = LogManager.getLogger(AddressJDBC.class);
	String str1 = "Dog";
    String str2 = "Cat";
     
    Thread trd1 = new Thread("My Thread 1"){
        public void run(){
            while(true){
                synchronized(str1){
                	LOGGER.info(trd1 +  " locks " + str1);  
                    synchronized(str2){
                    LOGGER.info(trd1 +  " locks " + str2);         
                    }
                }
            }
        }
    };
     
    Thread trd2 = new Thread("My Thread 2"){
        public void run(){
            while(true){
                synchronized(str2){
                	LOGGER.info(trd2 +  " locks " + str2);
                    synchronized(str1){
                    LOGGER.info(trd2 +  " locks " + str1); 
                    }
                }
            }
        }
    };
     
    public static void main(String a[]){
        MyDeadlock mdl = new MyDeadlock();
        mdl.trd1.start();
        mdl.trd2.start();
    }
}