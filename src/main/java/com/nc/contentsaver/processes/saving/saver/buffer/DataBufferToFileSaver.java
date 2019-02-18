package com.nc.contentsaver.processes.saving.saver.buffer;

import io.vertx.core.buffer.Buffer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataBufferToFileSaver implements DataBufferSaver {
    private static String DEFAULT = "innerfiles/";
    private File folder;

    public DataBufferToFileSaver(String folderPath) {
        this.folder = new File(folderPath);
        try {
            if (!folder.exists()) {
                Files.createDirectory(Paths.get(folder.getPath()));
            }
        } catch (IOException e) {
            System.out.println("Не удалось создать папку");
            e.printStackTrace();
        }
        if (folder.isFile()) {
            throw new IllegalArgumentException("Указанный путь принадлежит файлу, а не папке!");
        }
    }

    public DataBufferToFileSaver() {
        this(DEFAULT);
    }

    @Override
    public void saveBuffer(Buffer buffer, String hash) throws IOException {
        File file = Files.createFile(Paths.get(folder.getPath(), hash)).toFile();
        try (FileOutputStream fos = new FileOutputStream(file)){
            fos.write(buffer.getBytes());
        }
        System.out.println("Данные контента сохранены на диск!");
    }
}
