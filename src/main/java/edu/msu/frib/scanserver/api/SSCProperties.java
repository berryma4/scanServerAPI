package edu.msu.frib.scanserver.api;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: berryman
 * Date: 5/21/13
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * The order in which these files will be read.
 * 1. properties file specified using the system property <tt>scanServer.properties</tt>.
 * 2. scanServer.properties file in the users home directory.
 * 3. scanServer.properties file in the C:/ on windows and /etc/ on linux.
 * 4. scanServer.properties default file packaged with the library.
 */

class SSCProperties {
    private static Properties defaultProperties;
    private static Properties userSSCProperties;
    private static Properties userHomeSSCProperties;
    private static Properties systemSSCProperties;

    /**
     * creates a SSCProperties object which is initialized by the scanServer.properties file.
     *
     */
    public SSCProperties() {

        try {
            File userSSCPropertiesFile = new File(System.getProperty(
                    "config/scanServer.properties", ""));
            File userHomeSSCPropertiesFile = new File(
                    System.getProperty("user.home")
                            + "/config/scanServer.properties");
            File systemSSCPropertiesFile = null;
            if (System.getProperty("os.name").startsWith("Windows")) {
                systemSSCPropertiesFile = new File("/config/scanServer.properties");
            } else if (System.getProperty("os.name").startsWith("Linux")) {
                systemSSCPropertiesFile = new File(
                        "/etc/scanServer.properties");
            } else {
                systemSSCPropertiesFile = new File(
                        "/etc/scanServer.properties");
            }

            // File defaultPropertiesFile = new
            // File(this.getClass().getResource(
            // "/config/scanServer.properties").getPath());

            defaultProperties = new Properties();
            try {
                defaultProperties.load(this.getClass().getResourceAsStream(
                        "/config/scanServer.properties"));
            } catch (Exception e) {
                // The jar has been modified and the default packaged properties
                // file has been moved.
                // Simply use the empty defaultProperties.
            }

            // Not using to new Properties(default Properties) constructor to
            // make the hierarchy clear.
            systemSSCProperties = new Properties(defaultProperties);
            if (systemSSCPropertiesFile.exists()) {
                systemSSCProperties.load(new FileInputStream(
                        systemSSCPropertiesFile));
            }
            userHomeSSCProperties = new Properties(systemSSCProperties);
            if (userHomeSSCPropertiesFile.exists()) {
                userHomeSSCProperties.load(new FileInputStream(
                        userHomeSSCPropertiesFile));
            }
            userSSCProperties = new Properties(userHomeSSCProperties);
            if (userSSCPropertiesFile.exists()) {
                userSSCProperties
                        .load(new FileInputStream(userSSCPropertiesFile));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * check java preferences for the requested key - then checks the various
     * default properties files.
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String getPreferenceValue(String key, String defaultValue) {
        if (userSSCProperties.containsKey(key))
            return userSSCProperties.getProperty(key);
        else if (userHomeSSCProperties.containsKey(key))
            return userHomeSSCProperties.getProperty(key);
        else if (systemSSCProperties.containsKey(key))
            return systemSSCProperties.getProperty(key);
        else if (defaultProperties.containsKey(key))
            return defaultProperties.getProperty(key);
        else
            return defaultValue;
    }
}
