package co.tommi.crawler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kinesis.AmazonKinesisClient;

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

	public static AmazonKinesisClient setupKinesisClient() {
		// Load AWS Credentials
		BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(Helper.properties().getProperty(
				"awsSecretKey"), Helper.properties().getProperty("awsAccessKey"));

		// Create the Amazon Kinesis Client
		AmazonKinesisClient kinesisClient = new AmazonKinesisClient(basicAWSCredentials);
		kinesisClient.setEndpoint("https://kinesis.us-east-1.amazonaws.com", "kinesis", "us-east-1");

		return kinesisClient;
	}
}
