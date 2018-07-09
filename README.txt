For the Lambda tutorial I made these two buckets in dev S3:
- lambdatestinbox
- lambdatestlongterm

They are in region "US East (Ohio)"

I copied 3 files to lambdatestinbox:
- file1.txt
- file2.txt
- projectsAndPeople.jpg

I created the IAM role "MailMan".

I created the policy "MailManPolicy"
-> has "Role ARN" = "arn:aws:iam::705242235450:role/MailMan"

This is what I ran from the command-line to make the lambda:
$ aws lambda create-function \
--region us-east-2 \
--function-name Mailman \
--zip-file fileb:///Users/gregorycartine/Dropbox\ \(RiskMatch\)/db_gcartine/IdeaProjects/mailman/build/distributions/mailman-1.0-SNAPSHOT.zip \
--role arn:aws:iam::705242235450:role/MailMan \
--handler mailman.Mailman \
--runtime java8 \
--profile default \
--timeout 10 \
--memory-size 1024

I used this json when I tested the lambda: (I used the "S3 Put" template)
{
  "Records": [
    {
      "eventVersion": "2.0",
      "eventTime": "1970-01-01T00:00:00.000Z",
      "requestParameters": {
        "sourceIPAddress": "127.0.0.1"
      },
      "s3": {
        "configurationId": "testConfigRule",
        "object": {
          "eTag": "0123456789abcdef0123456789abcdef",
          "sequencer": "0A1B2C3D4E5F678901",
          "key": "file1.txt",
          "size": 1024
        },
        "bucket": {
          "arn": "arn:aws:s3:::lambdatestinbox",
          "name": "lambdatestinbox",
          "ownerIdentity": {
            "principalId": "EXAMPLE"
          }
        },
        "s3SchemaVersion": "1.0"
      },
      "responseElements": {
        "x-amz-id-2": "EXAMPLE123/5678abcdefghijklambdaisawesome/mnopqrstuvwxyzABCDEFGH",
        "x-amz-request-id": "EXAMPLE123456789"
      },
      "awsRegion": "us-east-1",
      "eventName": "ObjectCreated:Put",
      "userIdentity": {
        "principalId": "EXAMPLE"
      },
      "eventSource": "aws:s3"
    }
  ]
}

-------------------------------------------------------------------

This is how you start dynamoDB locally:
-> Skip ahead to #3 at this link: https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html#DynamoDBLocal.DownloadingAndRunning

-------------------------------------------------------------------


-------------------------------------------------------------------


-------------------------------------------------------------------


-------------------------------------------------------------------

