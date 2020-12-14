package com.aokellermann.nutritionapp.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for manipulating files.
 */
public abstract class FileUtils {
    /**
     * Downloads a file from a URL.
     *
     * @param url target URL to download
     * @param out out file path
     * @throws IOException what org.apache.commons.io.FileUtils.copyURLToFile throws
     */
    public static void download(URL url, File out) throws IOException {
        org.apache.commons.io.FileUtils.copyURLToFile(url, out);
    }

    /**
     * Unzips an archive.
     *
     * @param in  zip archive path
     * @param out output path
     * @throws ZipException what ZipFile.extractAll throws
     */
    public static void unzip(File in, File out) throws ZipException {
        new ZipFile(in).extractAll(out.getPath());
    }

    public static List<String[]> getLinesFromCSVInputStream(InputStream is) {
        return getLinesFromCSVBufferedReader(new BufferedReader(new InputStreamReader(is)));
    }

    /**
     * Gets tokens from a CSV.
     *
     * @param file the input CSV file
     * @return List of tokens
     */
    public static List<String[]> getLinesFromCSV(File file) {
        try {
            return getLinesFromCSVBufferedReader(new BufferedReader(new FileReader(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String[]> getLinesFromCSVBufferedReader(BufferedReader br) {
        List<String[]> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(br)) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                records.add(values);
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }

        // Remove descriptions
        records.remove(0);
        return records;
    }

    public static byte[] readFile(File file) {
        try {
            return org.apache.commons.io.FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            return null;
        }
    }

    public static byte[] readFileInputStream(InputStream is) {
        try {
            return org.apache.commons.io.IOUtils.toByteArray(is);
        } catch (IOException e) {
            return null;
        }
    }

    public static void writeFile(File file, byte[] bytes) {
        try {
            org.apache.commons.io.FileUtils.writeByteArrayToFile(file, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}