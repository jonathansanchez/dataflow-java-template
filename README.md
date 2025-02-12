# Apache Beam ETL Pipeline Flex Template

### Use Case:

* Implemented an ETL pipeline DataFlow to load from Cloud Storage CSV file (data source) into BigQuery.
* Implemented some Apache Beam PTransforms and business rules.
* Stored the raw and aggregated data into BigQuery.


### Prerequisites to run this pipeline:

* Java 17
* Maven 3.8

### Instructions:

#### Set environment variables:
```shell
export REGION="us-central1"
export REPOSITORY="apm-repository"
export BUCKET="[BUCKET_NAME]"
export PROJECT="[GCP_PROJECT_NAME]"
export DATASET="[DATASET.TABLE_NAME]"
```


### Set environment variables for testing:
```shell
export PROJECT_ID="[GCP_PROJECT_NAME]"
export BUCKET_NAME="[BUCKET_NAME]/apm"
export DATASET_NAME="[DATASET]"
export TABLE_NAME="[TABLE_NAME]"
```



#### Create the Artifact:
```shell
gcloud artifacts repositories create $REPOSITORY --repository-format=docker --location=$REGION
```



#### Create the template:
```shell
gcloud dataflow flex-template build gs://$BUCKET/apm/templates/apm.json \
--image-gcr-path "$REGION-docker.pkg.dev/$PROJECT/$REPOSITORY/apm:0.0.1-SNAPSHOT" \
--sdk-language "JAVA" \
--flex-template-base-image JAVA17 \
--metadata-file "metadata.json" \
--jar "target/apm-0.0.1-SNAPSHOT.jar" \
--env FLEX_TEMPLATE_JAVA_MAIN_CLASS="com.verix.apm.Application"
```


#### Run the template:
```shell
gcloud dataflow flex-template run "apm"  \
    --template-file-gcs-location "gs://$BUCKET/apm/templates/apm.json" \
    --region $REGION \
    --parameters input=gs://$BUCKET/apm/data/apm.csv,outputTable=$DATASET,tempBucket=gs://$BUCKET/apm/data/temp-files,tempLocation=gs://$BUCKET/apm/temp-files,stagingLocation=gs://$BUCKET/apm/temp-files
```

