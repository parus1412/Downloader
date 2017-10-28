//package DownloaderMain;

//import Downloader.Downloader;

//import Downloader.Downloader;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.lang.System.*;

public class Downloader {

    public static void  main (String[] args) throws IOException {

        try {
            if (args.length < 1) {
                System.out.println("Usage: java Downloader [url]");
                return;
            }

            URL fileUrl = new URL(args[0]);
            HttpURLConnection fileConnection = (HttpURLConnection) fileUrl.openConnection();
            int httpCode = fileConnection.getResponseCode();

            if ( httpCode == HttpURLConnection.HTTP_MOVED_PERM
                    || httpCode == HttpURLConnection.HTTP_MOVED_TEMP ) {
                fileUrl = new URL(fileConnection.getHeaderField("Location"));
            }

            InputStream source = fileUrl.openStream();
            OutputStream target = new FileOutputStream(new String (fileUrl.toString().substring(fileUrl.toString().lastIndexOf('/') + 1)));
            byte[] data = new byte[2048];
            int length;

            while ((length = source.read(data)) != -1) {
                target.write(data, 0, length);
            }

            source.close();
            target.close();

        } catch (MalformedURLException e) {
            System.out.println("Site " + "\"" + args[0].toString() + "\"" + " can’t be reached");
        }

    }

    private static String createOutputFile(String fileURL) {
        String fileName = new String (fileURL.substring(fileURL.lastIndexOf('/') + 1));
        System.out.println("File name: " + fileName);
        return fileName;
    }

}


