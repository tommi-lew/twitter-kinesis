package co.tommi.crawler;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.junit.Test;

import com.amazonaws.services.kinesis.AmazonKinesisClient;

public class HelperTest  {
	@Test
	public void testProperties() {
    	assertThat(Helper.properties(), instanceOf(Properties.class));
    }

	@Test
	public void testSetupKinesisClient() {
		assertThat(Helper.setupKinesisClient(), instanceOf(AmazonKinesisClient.class));
	}
}
