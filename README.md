# File Service

## Overview

File Service is a lightweight service designed to store and manage files reliably at scale. It enables microservices
to upload, retrieve, and delete files without handling cloud storage infrastructure directly, promoting loose coupling
and reusability across systems. It is designed to be easily integrated into existing microservices architectures
with little or no modification.

Built with Spring Boot, the service provides strongly typed APIs with validation, consistent error handling, and
automatic API documentation using Springdoc OpenAPI (Swagger UI).

## Features

- Upload up to 10 files per request with configurable public or private visibility.
- Generate temporary presigned URLs for secure, time-limited access to private files.
- Batch delete files from cloud storage using their file IDs.
- Retrieve file metadata (name, size, content type, visibility, URL) for up to 10 files per request.
- Strongly typed request/response models with validation and consistent error handling.
- Request ID tracking on every response for end-to-end observability.
- Built-in Swagger UI for interactive API exploration and testing.

<br/>
<br/>

## Getting Started

### 1. Set Up Cloud Storage and MongoDB

Before running the service, you must have:

- An **S3-compatible bucket** (e.g., AWS S3) with appropriate IAM credentials. Public file uploads require the bucket
  to have public ACLs enabled.
- A running **MongoDB** instance for storing file metadata.

Required environment variable values:

**Storage**
- `STORAGE_BUCKET` → S3 bucket name
- `STORAGE_REGION` → AWS region (e.g., `us-east-1`)
- `STORAGE_ACCESS_KEY` → AWS access key ID
- `STORAGE_SECRET_KEY` → AWS secret access key
- `STORAGE_SIGNED_URL_EXPIRY` → Presigned URL expiry duration (optional, defaults to `PT15M`)

**MongoDB**
- `MONGO_HOST` → MongoDB host
- `MONGO_PORT` → MongoDB port
- `MONGO_USERNAME` → MongoDB username
- `MONGO_PASSWORD` → MongoDB password
- `MONGO_DATABASE` → Target database name
- `MONGO_AUTH_DATABASE` → Authentication database (optional, defaults to `admin`)

### 2. Running Locally

#### Prerequisites

- Java 25
- Maven 3.9+
- S3-compatible storage credentials
- Running MongoDB instance

#### Configuration

Before running the application, set the required environment variables:

```bash
# Storage
export STORAGE_BUCKET=your-s3-bucket-name
export STORAGE_REGION=us-east-1
export STORAGE_ACCESS_KEY=your-access-key-id
export STORAGE_SECRET_KEY=your-secret-access-key

# MongoDB
export MONGO_HOST=localhost
export MONGO_PORT=27017
export MONGO_USERNAME=your-mongo-username
export MONGO_PASSWORD=your-mongo-password
export MONGO_DATABASE=fileservice
```

#### Steps

```bash
# Clone the repo
git clone -b main https://github.com/anish-anantharaman/fileservice.git

# Build the project
mvn clean install

# Run the unit and integration tests (Optional)
> Currently, the application does not include any automated tests.
> You can add your own unit or integration tests as needed, and then run:
mvn test

# Run the application
mvn spring-boot:run
```

### 3. Running with Docker Compose (Recommended)

Docker Compose starts both the application and MongoDB together. No local Java or Maven installation is required.

Create a `.env` file in the project root with your environment variables:

```env
# Storage
STORAGE_BUCKET=your-s3-bucket-name
STORAGE_REGION=us-east-1
STORAGE_ACCESS_KEY=your-access-key-id
STORAGE_SECRET_KEY=your-secret-access-key

# MongoDB
MONGO_USERNAME=your-mongo-username
MONGO_PASSWORD=your-mongo-password
MONGO_DATABASE=fileservice
```

Then start the services:

```bash
docker compose up --build
```

### 4. Running with Docker (Optional)

For a quick start without Docker Compose, you can run the application container standalone against an existing
MongoDB instance. No tests are included in this Docker build.

```bash
# Build Docker image
docker build -t fileservice .

# Run the container with environment variables
docker run \
  -e STORAGE_BUCKET="your-s3-bucket-name" \
  -e STORAGE_REGION="us-east-1" \
  -e STORAGE_ACCESS_KEY="your-access-key-id" \
  -e STORAGE_SECRET_KEY="your-secret-access-key" \
  -e MONGO_HOST="your-mongo-host" \
  -e MONGO_PORT="27017" \
  -e MONGO_USERNAME="your-mongo-username" \
  -e MONGO_PASSWORD="your-mongo-password" \
  -e MONGO_DATABASE="fileservice" \
  -p 8080:8080 \
  fileservice
```

### 5. Uploading a File

Once the service is running, you can upload a file using the curl command below:

```bash
curl --location 'http://localhost:8080/api/v1/files' \
  --form 'files=@"/path/to/your/file.pdf"' \
  --form 'visibility="public"'
```

### 6. Generating a Presigned URL

To generate a temporary presigned URL for a private file:

```bash
curl --location 'http://localhost:8080/api/v1/files/{fileId}/presigned-url'
```

### 7. Swagger API Documentation

Once the application is running, open your browser to:

http://localhost:8080/swagger-ui/index.html

<br/>
<br/>

## Contributing

This project is open for suggestions, improvements, and PRs.
Feel free to fork the repo, make changes, and submit a PR. Your contributions are welcome!