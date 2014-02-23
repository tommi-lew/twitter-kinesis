package co.tommi.crawler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Helper {
	private static Properties properties;

	public static Properties properties() {
		if (properties == null) {
			InputStream input = null;

			try {
				input = new FileInputStream("config.properties");
				properties = new Properties();
				properties.load(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return properties;
	}
}
