package com.sadeghi;

import freemarker.template.TemplateException;
import org.apache.commons.lang.WordUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Tezi on 04/07/16.
 */
public class App {
    public static void main(String[] args) {

        ApplicationContext appContext=new ClassPathXmlApplicationContext("WebApplicationContext.xml");

        DBBean dbBean = (DBBean)appContext.getBean("DBBean");
        dbBean.getTables(true,"SAMPLE");
        System.out.println("******************");
        TableMetaData tableMetaData = dbBean.getTableMetaData("PERSON", false);
        try {
            FreeMarkerEngine.fillTemplate("entity.ftl", tableMetaData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        String aliSadeghi = StringUtil.camelCaseToUnderscore("TBL_" , "aliSadeghi");
        System.out.println(aliSadeghi);

        String underscoreToCamelCase = StringUtil.underscoreToCamelCase("TBL_",true, "TBL_ALI_SADEGHI");
        System.out.println(underscoreToCamelCase);
    }


}
