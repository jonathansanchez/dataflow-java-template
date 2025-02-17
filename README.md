# Apache Beam ETL Pipeline Flex Template

Use Case:

* Implemented an ETL pipeline DataFlow to load from Cloud Storage CSV file (data source) into BigQuery.
* Implemented some Apache Beam PTransforms and business rules.
* Stored the raw and aggregated data into BigQuery.

### Prerequisites to run this pipeline:

* Java 17
* Maven 3.8

### Instructions:

#### Set environment variables for build:
```shell
export REGION="us-central1"
export REPOSITORY="current-forecast-repository"
export BUCKET="[BUCKET_NAME]"
export PROJECT="[GCP_PROJECT_NAME]"
export TABLE_IN="[GCP_PROJECT_NAME.DATASET.TABLE_IN]"
export TABLE_OUT="[GCP_PROJECT_NAME.DATASET.TABLE_OUT]"
export COUNTRY="[COUNTRY]"
```

#### Set environment variables for testing:
```shell
export PROJECT_ID="[GCP_PROJECT_NAME]"
export BUCKET_NAME="[BUCKET_NAME]/forecast"
export DATASET_NAME="[DATASET]"
export TABLE_IN="[TABLE_IN]"
export TABLE_OUT="[TABLE_OUT]"
export COUNTRY="[COUNTRY]"
```

#### Create the Artifact:
```shell
gcloud artifacts repositories create $REPOSITORY --repository-format=docker --location=$REGION
```

#### Create the Template:
```shell
gcloud dataflow flex-template build gs://$BUCKET/forecast/templates/current-forecast.json \
--image-gcr-path "$REGION-docker.pkg.dev/$PROJECT/$REPOSITORY/current-forecast:0.0.1-SNAPSHOT" \
--sdk-language "JAVA" \
--flex-template-base-image JAVA17 \
--metadata-file "metadata.json" \
--jar "target/current-forecast-0.0.1-SNAPSHOT.jar" \
--env FLEX_TEMPLATE_JAVA_MAIN_CLASS="com.verix.forecast.Application"
```

#### Run the Template:
```shell
gcloud dataflow flex-template run "current-forecast-$COUNTRY"  \
    --template-file-gcs-location "gs://$BUCKET/forecast/templates/current-forecast.json" \
    --region $REGION \
    --parameters country=$COUNTRY,input=$TABLE_IN,output=$TABLE_OUT,temp=gs://$BUCKET/forecast/data/temp-files,tempLocation=gs://$BUCKET/forecast/temp-files,stagingLocation=gs://$BUCKET/forecast/temp-files
```