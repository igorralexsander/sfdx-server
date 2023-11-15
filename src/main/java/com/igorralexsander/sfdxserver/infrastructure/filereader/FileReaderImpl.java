package com.igorralexsander.sfdxserver.infrastructure.filereader;

import com.igorralexsander.sfdxserver.model.FileReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class FileReaderImpl implements FileReader {
    @Override
    public File getFile(String filePath) throws IOException {
        return Paths.get(filePath).toFile();
    }
}
