package com.sadeghi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Tezi on 04/07/16.
 */
public class App {
    public static void main(String[] args) {

        ApplicationContext appContext=new ClassPathXmlApplicationContext("WebApplicationContext.xml");

        DBBean dbBean = (DBBean)appContext.getBean("DBBean");
        dbBean.getTables(true,"SAMPLE");
        System.out.println("******************");
        dbBean.getTableMetaData("PERSON",false);
//        dbBean.getViewMetaData("VW_CONTACT");
//        ResultSet tables = dbBean.getTables();
        /*try {
            while(tables.next()){
                System.out.println(tables.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }
}
