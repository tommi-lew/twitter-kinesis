package co.tommi.crawler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;

public class HelperTest  {
	@Test
	public void testProperties() {
		assertThat(Helper.properties(), is(notNullValue()));
    }

	@Test
	public void testSetupKinesisClient() {
		assertThat(Helper.setupKinesisClient(), is(notNullValue()));
	}
}
