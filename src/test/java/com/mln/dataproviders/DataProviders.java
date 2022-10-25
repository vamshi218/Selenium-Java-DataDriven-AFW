package com.mln.dataproviders;

import com.mln.utils.ExcelReaders;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.lang.reflect.Method;

public class DataProviders {

    static String[][] strLoginCredentials = {
            {"lakme88@gmail.com","123","invalid"},
            {"lakme8@gmail.com","Password@123","invalid"},
            {"lakme8@gmail.com","12443","invalid"},
            {"lakme88@gmail.com","Password@123","valid"}
    };

    @DataProvider(name = "HardcodedData")
    public static Object[][] login_data_provider_classdata(){

        return strLoginCredentials;

    }

    @DataProvider(name = "ExcelData" , parallel = true)
    public static Object[][] login_data_provider_exceldata(Method method) throws IOException {

        //return getExcelData();
        return ExcelReaders.getExcelDataMethod1(method.getName()+".xlsx","Data");

    }

}
