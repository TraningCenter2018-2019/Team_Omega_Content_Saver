package com.nc.contentsaver.utils;

import java.io.*;
import java.net.URL;

public class ResourceManager {
    private ResourceManager() {
    }

    public static String getStringDataFromFile(String fileName) {
        URL resource = ResourceManager.class.getResource("../../../../" + fileName);
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(resource.getFile()))) {
            StringWriter writer = new StringWriter();
            int i = -1;
            while ((i = reader.read()) != -1) {
                char c = (char) i;
                writer.write(c);
            }
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeToResource(String data, String fileName) {
        URL resource = ResourceManager.class.getResource("../../../../" + fileName);
        try (FileOutputStream fos = new FileOutputStream(resource.getFile())) {
            fos.write(data.getBytes("UTF-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
