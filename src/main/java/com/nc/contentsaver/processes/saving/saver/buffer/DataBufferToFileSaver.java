package com.nc.contentsaver.processes.saving.saver.buffer;

import io.vertx.core.buffer.Buffer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class describes an object that can save files to disk.
 */
public class DataBufferToFileSaver implements DataBufferSaver {
    /**
     * Logger. Displays information about saving a file to disk.
     */
    private static final Logger LOG = Logger.getLogger(DataBufferToFileSaver.class.getSimpleName());
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
    public DataBufferToFileSaver(String folderPath) {
        this.folder = new File(folderPath);
        try {
            if (!folder.exists()) {
                Files.createDirectory(Paths.get(folder.getPath()));
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Не удалось создать папку", e);
        }
        if (folder.isFile()) {
            throw new IllegalArgumentException("Указанный путь принадлежит файлу, а не папке!");
        }
    }

    /**
     * Creates an object that allows you to save content to the {@link #DEFAULT} folder.
     */
    public DataBufferToFileSaver() {
        this(DEFAULT);
    }

    @Override
    public void saveBuffer(Buffer buffer, String hash) throws IOException {
        File file = Files.createFile(Paths.get(folder.getPath(), hash)).toFile();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(buffer.getBytes());
        }
        LOG.fine("Данные контента сохранены на диск!");
    }
}
