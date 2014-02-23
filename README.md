# Twitter Kinesis
A small Java exploration project with [Twitter Streaming API](https://dev.twitter.com/docs/streaming-apis) and [Amazon Kinesis](http://aws.amazon.com/kinesis/).

## How to use?
1. Spin up a Amazon Kinesis stream
2. Ensure you have maven installed with at least Java 1.7
3. Clone the repo
4. Go into the project folder, run `mvn package`
5. Create the config file: `cp config.properties.sample config.properties`
6. Add the necessary keys in the config file.
7. Start the collector: `mvn exec:java -Dexec.mainClass="co.tommi.crawler.TweetsCollector"`
8. Start the processor: `mvn exec:java -Dexec.mainClass="co.tommi.crawler.TweetsProcessor"`
9. Don't forget to spin down your Kinesis stream when you are done!

## What TweetsProcessor.java does
* Get tweets using Twitter's Streaming API
* Add these tweets to Amazon Kinesis


## What TweetsCollector.java does
* Retrieve tweets from Amazon Kinesis


## References
* [Hosebird Client ](https://github.com/twitter/hbc) - a huge chunk of code is taken from this repo
* [Kinesis using Java API](http://docs.aws.amazon.com/kinesis/latest/dev/kinesis-using-api-java.html)
* [AWS SDK Javadoc](http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/)
