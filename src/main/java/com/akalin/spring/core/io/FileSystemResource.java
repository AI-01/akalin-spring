package com.akalin.spring.core.io;

import java.io.*;
import java.nio.file.Files;

public class FileSystemResource implements Resource {

    private final File file;
    private final String path;

    public FileSystemResource(File file) {
        this.file = file;
        this.path = file.getPath();
    }

    public FileSystemResource(String path) {
        this.path = path;
        this.file = new File(path);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(path + " cannot be opened because it does not exist");
        }
        return Files.newInputStream(file.toPath());
    }

}
