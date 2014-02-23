# Twitter Crawler

## How to use?
1. Ensure you have maven installed with at least Java 1.7
2. Clone the repo
3. Go into the project folder, run `mvn package`
4. Start the collector: `mvn exec:java -Dexec.mainClass="co.tommi.crawler.TweetsCollector"`
5. Start the processor: `mvn exec:java -Dexec.mainClass="co.tommi.crawler.TweetsProcessor"`

## What TweetsProcesser.java does
* Get tweets using Twitter's Streaming API
* Add these tweets to Amazon Kinesis


## What TweetsCollector.java does
* Retrieve tweets from Amazon Kinesis


## References
* [Hosebird Client ](https://github.com/twitter/hbc) - a huge chunk of code is taken from this repo
* [Kinesis using Java API](http://docs.aws.amazon.com/kinesis/latest/dev/kinesis-using-api-java.html)
* [AWS SDK Javadoc](http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/)
