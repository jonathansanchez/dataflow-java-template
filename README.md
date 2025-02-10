# Apache Beam ETL Pipeline Flex Template

Use Case:

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
export REPOSITORY="sam-repository"
export BUCKET="[BUCKET_NAME]"
export PROJECT="[GCP_PROJECT_NAME]"
export DATASET="[DATASET.TABLE_NAME]"
```

#### Create the Artifact:
```shell
gcloud artifacts repositories create $REPOSITORY --repository-format=docker --location=$REGION
```

#### Create the template:
```shell
gcloud dataflow flex-template build gs://$BUCKET/sam/templates/sam.json \
--image-gcr-path "$REGION-docker.pkg.dev/$PROJECT/$REPOSITORY/sam:0.0.1-SNAPSHOT" \
--sdk-language "JAVA" \
--flex-template-base-image JAVA17 \
--metadata-file "metadata.json" \
--jar "target/sam-0.0.1-SNAPSHOT.jar" \
--env FLEX_TEMPLATE_JAVA_MAIN_CLASS="com.verix.sam.Application"
```

#### Run the template:
```shell
gcloud dataflow flex-template run "sam"  \
    --template-file-gcs-location "gs://$BUCKET/sam/templates/sam.json" \
    --region $REGION \
    --parameters input=gs://$BUCKET/sam/data/sam.csv,output=$DATASET,temp=gs://$BUCKET/sam/data/temp-files,tempLocation=gs://$BUCKET/sam/temp-files,stagingLocation=gs://$BUCKET/sam/temp-files
```