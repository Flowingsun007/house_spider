package com.flowingbit.data.collect.house_spider.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Lyon
 * @date 2019/4/24 10:09
 * @description IOUtil
 **/
public class IOUtil{

    public static void outFile(String s, String filePath) throws Exception{
        File file = new File(filePath);
        try (FileOutputStream fop = new FileOutputStream(file)) {
            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            // get the content in bytes
            byte[] contentInBytes = s.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();
            System.out.println("Done");
        } catch (IOException e) {
            throw e;
        }
    }

    public static void toFile(String s, String filePath){
        File file = new File(filePath);
        try (FileOutputStream fop = new FileOutputStream(file)) {
            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            // get the content in bytes
            byte[] contentInBytes = s.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();
            System.out.println("outFile: " + filePath + " success!");
        } catch (IOException e) {
            System.out.println("outFile: " + filePath + " fail!");
            e.printStackTrace();
        }
    }
}
