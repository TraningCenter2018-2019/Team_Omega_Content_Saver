package com.nc.contentsaver.utils;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class allows you to interact with the text data of resource files.
 */
public final class ResourceManager {
    /**
     * Resource manager logger.
     */
    private static final Logger LOG = Logger.getLogger(ResourceManager.class.getSimpleName());
    /**
     * The utility class should not have a public constructor.
     */
    private ResourceManager() {
    }

    /**
     * Reads data from a resource file.
     *
     * @param fileName resource file name
     * @return data from a resource file or null if file does not exist
     */
    public static String getStringDataFromFile(String fileName) {
        URL resource = ResourceManager.class.getResource("../../../../" + fileName);
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(resource.getFile()))) {
            StringWriter writer = new StringWriter();
            int i;
            while ((i = reader.read()) != -1) {
                char c = (char) i;
                writer.write(c);
            }
            return writer.toString();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "File reading error", e);
            return null;
        }
    }

    /**
     * Writes data to a resource file.
     *
     * @param data     data to write
     * @param fileName resource file name
     */
    public static void writeToResource(String data, String fileName) {
        URL resource = ResourceManager.class.getResource("../../../../" + fileName);
        try (FileOutputStream fos = new FileOutputStream(resource.getFile())) {
            fos.write(data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "File writing error", e);
        }
    }
}
