package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropUtils {

	/*****************************************************************************
	 * Name : getPropFile Description : Gives the Relative path of Properties file,
	 * which is used as Object repository Input Content : name and path of the
	 * properties file
	 *****************************************************************************/
	public static File getPropFile(final String FILE_PATH, final String FILE_NAME) {
		// Returns the Properties File
		return new File(FILE_PATH, FILE_NAME);
	}

	/*****************************************************************************
	 * Name : getProps Description : Reads the Properties file Input Content :
	 * Instance of the properties file
	 *****************************************************************************/
	public static Properties getProps(final File file) {
		Properties properties = null;
		try {

			properties = new Properties();
			// Reading the properties file
			properties.load(new FileInputStream(file));
		} catch (FileNotFoundException fileNotFoundException) {
			System.err.println(fileNotFoundException);
		} catch (IOException ioException) {
			System.err.println(ioException);
		}
		return properties;
	}

	/*****************************************************************************
	 * Name : getPropValue Description : Returns the the Property value depending on
	 * the key Input Content : Properties Object, key of the required property
	 *****************************************************************************/
	public static String getPropValue(Properties properties, String key) {
		// Returning the Property value depends on Key
		return properties.getProperty(key);
	}

	/*****************************************************************************
	 * Name : setProps Description : Set the properties Instance Input Content :
	 * Properties Object
	 *****************************************************************************/
	public static void setProps(Properties properties, String key, String value, File fileDirectory) {
		Properties props = new Properties();
		try {
			//First load old one:
			FileInputStream configStream = new FileInputStream(fileDirectory);
			props.load(configStream);
			configStream.close();
			//Modifies existing or adds new property
			props.setProperty(key, value);
			//Saving modified property file
			FileOutputStream output = new FileOutputStream(fileDirectory);
			props.store(output, "This description goes to the header of a file");
			output.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	/*****************************************************************************
	 * Name : clearProps Description : Clearing the properties Instance Input
	 * Content : Properties Object
	 *****************************************************************************/
	public static void clearProps(Properties properties) {
		// Clearing the Properties Object
		properties.clear();
	}
}