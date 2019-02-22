package com.nc.contentsaver.processes.managing.manager.buffer;

import io.vertx.core.buffer.Buffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class describes an object that can save files to disk.
 */
public class DataBufferToFileManager implements DataBufferManager {
    /**
     * Logger. Displays information about managing a file to disk.
     */
    private static final Logger LOG = Logger.getLogger(DataBufferToFileManager.class.getSimpleName());
    /**
     * Default folder to save content to.
     */
    private static final String DEFAULT = "innerfiles/";
    /**
     * Folder to save content to.
     */
    private File folder;

    /**
     * Creates an object that allows you to save content to a folder specified by the user.
     *
     * @param folderPath folder to save content to
     */
    public DataBufferToFileManager(String folderPath) {
        this.folder = new File(folderPath);
        try {
            if (!folder.exists()) {
                Files.createDirectory(Paths.get(folder.getPath()));
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Failed to create folder.", e);
        }
        if (folder.isFile()) {
            throw new IllegalArgumentException("The specified path belongs to the file, not the folder.");
        }
    }

    /**
     * Creates an object that allows you to save content to the {@link #DEFAULT} folder.
     */
    public DataBufferToFileManager() {
        this(DEFAULT);
    }

    @Override
    public void saveBuffer(Buffer buffer, String hash) throws IOException {
        File file = Paths.get(folder.getPath(), hash).toFile();
        if (file.exists()) {
            return;
        }
        if (file.createNewFile()) {
            LOG.fine("Created file: " + file.getName());
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(buffer.getBytes());
        }
        LOG.fine("Content data saved to disk.");
    }

    @Override
    public Buffer restoreBuffer(String hash) throws IOException {
        File file = Paths.get(folder.getPath(), hash).toFile();
        Buffer buffer = Buffer.buffer();
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                byte i;
                while ((i = (byte) fis.read()) != -1) {
                    buffer.appendByte(i);
                }
            }
        } else {
            throw new IOException();
        }
        return buffer;
    }
}
