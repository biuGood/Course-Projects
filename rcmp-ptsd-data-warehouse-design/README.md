# Data Warehouse – RCMP PTSD Longitudinal Study | SQL & Cloud Architecture

This project presents a proposed cloud-based data warehouse design for the RCMP PTSD Longitudinal Study. The goal was to design a scalable data architecture that could integrate research data from multiple source systems and support reporting, dashboarding, analytics, and research data exports.

## Project Overview

The RCMP PTSD Longitudinal Study required a data warehouse solution to make historical research data available through a single point of access for researchers. The proposed system needed to support data acquisition, data storage, ETL processing, data security, reporting, dashboarding, and analytical use cases.

The data sources included survey systems, participant activity records, wearable health data, biometric data, and clinical assessment data.

## Business Requirements

The proposed data warehouse was designed to support:

- Data acquisition from multiple systems through APIs, SFTP, XML, JSON, WAV, and other data formats
- ETL processing for raw data ingestion and relational reporting structures
- Historical research data access through a central warehouse
- Reports and dashboards for researchers
- A self-serve data cube for multi-dimensional analysis
- Data quality monitoring
- Secure access control, audit logging, and privacy protection
- Export of cleansed data for external research tools such as SPSS

## Proposed Architecture

The proposed solution included the following layers:

### 1. Data Source Layer

Potential source systems included:

- Moodle participant activity data
- Qualtrics survey data
- Apple Health data
- LLA / biometric recordings
- nView clinical assessment data

### 2. ETL Layer

Matillion was proposed as the ETL tool because it can integrate with multiple data sources, including APIs, applications, databases, and files.

The ETL workflow included:

- Data extraction from source systems
- Data cleaning and standardization
- Data association across systems
- Data transformation for analysis
- Data cube preparation
- Loading data into the cloud warehouse

### 3. Data Warehouse Layer

Google BigQuery was proposed as the cloud data warehouse platform.

BigQuery was selected to support:

- Large-scale data storage
- SQL-based querying
- Serverless data processing
- Streaming data ingestion
- Integration with analytics and BI tools

### 4. Data Modeling Layer

A Galaxy Schema was proposed for the warehouse model.

This schema was selected because the study involved multiple fact tables that could share common dimensions, such as participant, time, cohort, assessment type, and data source.

### 5. Analytics and Reporting Layer

Tableau was proposed for dashboards and reporting.

The reporting layer was designed to support:

- Participant activity reports
- Psychological and biometric index reports
- Compliance and adherence reports
- Survey data exports
- Research dashboards
- Drill-down analysis by participant, cohort, troop, and time period

## Key Use Cases

The data warehouse was designed to support use cases such as:

- Correlating psychological survey data with biometric data
- Tracking participant activity and engagement
- Exporting survey data in SPSS-ready format
- Analyzing significant event impacts on mental health
- Monitoring compliance and adherence rates
- Supporting research publications using cleaned and aggregated data

## Security and Privacy Considerations

The proposed solution considered:

- De-identified participant data
- Encrypted data in transit and at rest
- Access control lists
- Audit logging
- Data-loss prevention
- Restricted access through campus or VPN connection
- Canadian data storage requirements

## Skills Demonstrated

- Data warehouse architecture design
- ETL workflow design
- Cloud data warehouse planning
- SQL-based analytics design
- BigQuery solution design
- Dimensional modeling
- Galaxy Schema modeling
- Dashboard and reporting requirements analysis
- Data privacy and security considerations
- Cost estimation and project planning
- Business requirements interpretation

## Resume Summary

This project demonstrates experience designing a cloud-based data warehouse and ETL architecture for a multi-source longitudinal research study, with a focus on scalable analytics, reporting, dashboarding, and secure research data access.
