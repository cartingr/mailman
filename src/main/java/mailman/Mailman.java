package mailman;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;

import java.io.IOException;
import java.net.URLDecoder;

public class Mailman implements
        RequestHandler<S3Event, String> {

    private final static String SRC_BUCKET =  "lambdatestinbox";
    private final static String DST_BUCKET = "lambdatestlongterm";

    private final static String FMT_WRONG_SRC_BUCKET = "Skipping object %s in bucket %s: Not cataloging this bucket.";
    private final static String FMT_SAME_BUCKET = "Destination bucket must not match source bucket. (Both named %s.)";

    public String handleRequest(S3Event s3event, Context context) {
        try {
            S3EventNotificationRecord record = s3event.getRecords().get(0);

            String srcBucket = record.getS3().getBucket().getName();
            // Object key may have spaces or unicode non-ASCII characters.
            String srcKey = record.getS3().getObject().getKey()
                    .replace('+', ' ');
            srcKey = URLDecoder.decode(srcKey, "UTF-8");

            if (! srcBucket.equals(SRC_BUCKET)) {
                String s = String.format(FMT_WRONG_SRC_BUCKET, srcKey, srcBucket);
                System.out.println(s);
                return s;
            }

            String dstKey = "copied-" + srcKey;

            // Sanity check: validate that source and destination are different buckets.
            // Otherwise we will go into an infinite loop, copying an object into S3 on each iteration
            if (srcBucket.equalsIgnoreCase(DST_BUCKET)) {
                final String s = String.format(FMT_SAME_BUCKET, srcBucket);
                throw new RuntimeException(s);
            }

            AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

            // Uploading to S3 destination bucket
            System.out.println("Copying to: " + DST_BUCKET + "/" + dstKey);
            s3Client.copyObject(srcBucket, srcKey, DST_BUCKET, dstKey);
            System.out.println("Successfully copied " + srcBucket + "/" + srcKey + " to " + DST_BUCKET + "/" + dstKey);
            return "Ok";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}