package com;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FileReader {
    private static byte[] BUFFER = new byte[1024];

    public static void main(String[] args) {
        RandomAccessFile reader = null;
        RandomAccessFile writer = null;
        try {
            reader = new RandomAccessFile("C:\\Users\\CRR\\Desktop\\index.html", "r");
            writer = new RandomAccessFile("C:\\Users\\CRR\\Desktop\\index1.html", "rw");
            int len = 0;
            while ((len = reader.read(BUFFER)) != -1) {
                if(BUFFER[0] == -17 && BUFFER[1] == -69 && BUFFER[2] == -65) {
                    writer.write(BUFFER, 3, len - 3);
                } else {
                    writer.write(BUFFER, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
