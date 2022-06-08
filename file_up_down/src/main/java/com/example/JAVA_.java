package com.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class JAVA_ {
    public static void main(String[] args) throws IOException {
        final File file = new File("D:\\spring_file_up_down_temp\\tang\\dada");
        System.out.println("true");
        if (!file.isDirectory()){
            System.out.println("file.isDirectory() == false");
            file.mkdirs();
        }else {
            final boolean newFile = new File(file, "aaa.txt").createNewFile();
            System.out.println("newFile = " + newFile);
        }
    }
}
