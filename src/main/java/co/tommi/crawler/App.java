package co.tommi.crawler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

public class App {
	static Properties prop = new Properties();

	public static void main(String[] args) {
		App.loadProperties();

		/** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
		BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);
		BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>(1000);

        /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
		Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();

        // Optional: set up some followings and track terms
		List<Long> followings = Lists.newArrayList(1234L, 566788L);
		List<String> terms = Lists.newArrayList("twitter", "api");
		endpoint.followings(followings);
		endpoint.trackTerms(terms);

        Authentication hosebirdAuth = new OAuth1(
        		prop.getProperty("consumerKey"),
        		prop.getProperty("consumerSecret"),
        		prop.getProperty("token"),
        		prop.getProperty("secret"));

        ClientBuilder builder = new ClientBuilder()
	        .name("Hosebird-Client-01")		// optional: mainly for the logs
	        .hosts(hosebirdHosts)
	        .authentication(hosebirdAuth)
	        .endpoint(endpoint)
	        .processor(new StringDelimitedProcessor(msgQueue))
	        .eventMessageQueue(eventQueue);		// optional: use this if you want to process client events

		Client hosebirdClient = builder.build();
		hosebirdClient.connect();

		while (!hosebirdClient.isDone()) {
			String msg;
			try {
				msg = msgQueue.take();
				System.out.println(msg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

    }

	private static void loadProperties() {
		InputStream input = null;

		try {
			input = new FileInputStream("config.properties");
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
