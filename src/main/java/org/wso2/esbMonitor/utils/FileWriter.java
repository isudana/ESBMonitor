package org.wso2.esbMonitor.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Dinanjana on 01/06/2016.
 */
public class FileWriter {
    private static Logger logger = Logger.getLogger(FileWriter.class);
    public static void writeFile(String fileName,byte [] data){

        Path file = Paths.get(fileName);
        try {
            Files.write(file, data);
        } catch (IOException e) {
            logger.error("File writing error",e);
        }
    }
}
