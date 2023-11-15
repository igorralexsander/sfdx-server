package com.igorralexsander.sfdxserver.model;

import java.io.File;
import java.io.IOException;

public interface FileReader {
    File getFile(String filePath) throws IOException;
}
