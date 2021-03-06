package co.tommi.crawler;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.model.DescribeStreamRequest;
import com.amazonaws.services.kinesis.model.DescribeStreamResult;
import com.amazonaws.services.kinesis.model.GetRecordsRequest;
import com.amazonaws.services.kinesis.model.GetRecordsResult;
import com.amazonaws.services.kinesis.model.GetShardIteratorRequest;
import com.amazonaws.services.kinesis.model.GetShardIteratorResult;
import com.amazonaws.services.kinesis.model.Record;
import com.amazonaws.services.kinesis.model.Shard;

public class TweetsProcessor {
	public static void main(String[] args) {
		AmazonKinesisClient kinesisClient = Helper.setupKinesisClient();

		// Retrieve the Shards from a Stream
		DescribeStreamRequest describeStreamRequest = new DescribeStreamRequest();
		describeStreamRequest.setStreamName(Helper.properties().getProperty("kinesisStreamName"));
		DescribeStreamResult describeStreamResult;
		List<Shard> shards = new ArrayList<>();
		String lastShardId = null;

		do {
		    describeStreamRequest.setExclusiveStartShardId(lastShardId);
		    describeStreamResult = kinesisClient.describeStream(describeStreamRequest);
		    shards.addAll(describeStreamResult.getStreamDescription().getShards());
		    if (shards.size() > 0) {
		        lastShardId = shards.get(shards.size() - 1).getShardId();
		    }
		} while (describeStreamResult.getStreamDescription().getHasMoreShards());

		// Get Data from the Shards in a Stream
		// Hard-coded to use only 1 shard
		String shardIterator;
		GetShardIteratorRequest getShardIteratorRequest = new GetShardIteratorRequest();
		getShardIteratorRequest.setStreamName(Helper.properties().getProperty("kinesisStreamName"));
		getShardIteratorRequest.setShardId(shards.get(0).getShardId());
		getShardIteratorRequest.setShardIteratorType("TRIM_HORIZON");

		GetShardIteratorResult getShardIteratorResult = kinesisClient.getShardIterator(getShardIteratorRequest);
		shardIterator = getShardIteratorResult.getShardIterator();

		// Continuously read data records from shard.
		List<Record> records;
		while (true) {
			// Create new GetRecordsRequest with existing shardIterator.
			// Set maximum records to return to 1000.
			GetRecordsRequest getRecordsRequest = new GetRecordsRequest();
			getRecordsRequest.setShardIterator(shardIterator);
			getRecordsRequest.setLimit(1000);

			GetRecordsResult result = kinesisClient.getRecords(getRecordsRequest);

			// Put result into record list. Result may be empty.
			records = result.getRecords();

			// Print records
			for (Record record : records) {
				ByteBuffer byteBuffer = record.getData();
				System.out.println(String.format("Seq No: %s - %s", record.getSequenceNumber(),
						new String(byteBuffer.array())));
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException exception) {
				throw new RuntimeException(exception);
			}

			shardIterator = result.getNextShardIterator();
		}
	}
}
