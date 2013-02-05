package de.fuberlin.livevoting.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Configuration {
	private static Logger log = Logger.getLogger(Configuration.class);
	private static Configuration instance = new Configuration();
	private static final String propertiesFile = "livevoting.server.properties";
	
	private int defaultQuestionTimeout=180; // in minutes
	private int displayQuestionTimeout=5;
	private String imagesDir = System.getProperty("user.dir") + File.separator + "images" + File.separator;
	private boolean loaded=false;
	

	private void load() {
        File propFile = new File(propertiesFile);
        if (propFile.exists()) {
            try {
                Properties prop = new Properties();
                prop.load(new FileInputStream(propFile));
                String aDisplayQuestionTimeout = prop.getProperty("displayQuestionTimeout");
                String aDefaultQuestionTimeout = prop.getProperty("defaultQuestionTimeout");
                String aImagesDir = prop.getProperty("imagesDir");

                if (aDisplayQuestionTimeout != null && Integer.valueOf(aDisplayQuestionTimeout)!=null) {
                	displayQuestionTimeout = Integer.valueOf(aDisplayQuestionTimeout);
                }
                if (aDefaultQuestionTimeout != null && Integer.valueOf(aDefaultQuestionTimeout)!=null) {
                	defaultQuestionTimeout = Integer.valueOf(aDefaultQuestionTimeout);
                }
                if (aImagesDir != null) {
                	imagesDir = aImagesDir;
                }
                log.debug("Properties file has been loaded.");
            } catch (Exception e) {
                log.warn("Could not load properties", e);
            }
        } else {
            log.warn("Could not find properties file. Creating a new one");
            try {
                createPropertiesFile(propFile);
            } catch (IOException e) {
                log.warn("Could not create properties file", e);
            }
        }
	}
	

    private void createPropertiesFile(File propFile) throws IOException {
        propFile.createNewFile();
        Properties prop = new Properties();
        prop.setProperty("defaultQuestionTimeout", String.valueOf(defaultQuestionTimeout));
        prop.setProperty("displayQuestionTimeout", String.valueOf(displayQuestionTimeout));
        prop.setProperty("imagesDir", imagesDir);
        prop.store(new FileOutputStream(propFile), "automaticly generated properties file");
    }



	public int getDefaultQuestionTimeout() {
		return defaultQuestionTimeout;
	}
	
	public int getDisplayQuestionTimeout() {
		return displayQuestionTimeout;
	}
	
	
	public static Configuration getInstance() {
		synchronized (instance) {
			if(instance.loaded==false) {
				instance.load();
				instance.loaded=true;
			}
		}
		return instance;
	}
	
	public static String md5(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] bytesOfMessage = password.getBytes("UTF-8");
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] messageDigest = md.digest(bytesOfMessage);
		StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < messageDigest.length; i++) {
            String hex = Integer.toHexString(0xFF & messageDigest[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
	}


	public String getImagesDir() {
		return imagesDir;
	}

}
