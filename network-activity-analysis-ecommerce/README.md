# Network Activity Analysis on E-Commerce Platform | PostgreSQL

This project analyzes access request logs from an e-commerce platform to identify suspicious network activities and potential web attacks. The analysis focuses on SQL injection, brute-force login attempts, suspicious URL enumeration, and DDoS-like request patterns.

## Project Overview

The goal of this project was to examine web access requests and detect abnormal behavior that could indicate cyberattacks.

The project focused on three major types of suspicious activities:

- SQL Injection
- Brute-force login attempts
- Brute-force URL enumeration
- DDoS-like high-volume request patterns

The analysis used request URLs, SQL-related keywords, HTTP status codes, request frequency, and time-based grouping to identify abnormal traffic patterns.

## Tools and Technologies

- PostgreSQL
- SQL
- Web access log analysis
- Security pattern detection
- Time-based request analysis
- SQL injection detection logic
- Brute-force attack analysis

## Attack Types Analyzed

### 1. SQL Injection

SQL injection occurs when attackers modify normal SQL queries through request parameters in order to access unauthorized data or bypass constraints.

The project analyzed suspicious SQL-related keywords and patterns, including:

- `UNION`
- `UNION ALL`
- `SELECT`
- `FROM`
- `WHERE`
- `AND`
- `NULL`
- Boolean expressions such as `1=2`

Requests containing highly suspicious SQL structures were reviewed to identify possible SQL injection attempts.

### 2. Brute-Force Login Attempts

Brute-force attacks involve repeatedly trying different username and password combinations. These attacks often create a large number of repeated login requests within a short time period.

The project analyzed login request frequency over time and identified abnormal spikes around login endpoints such as:

- `/#/login`

HTTP 500-level status codes were also reviewed because repeated failed requests may overload the server or indicate DDoS-like behavior.

### 3. Brute-Force URL Enumeration

URL brute-force attacks occur when attackers repeatedly try similar URLs to discover hidden pages or resources.

The project identified suspicious patterns by grouping similar request URLs and analyzing whether a large number of similar requests occurred in a short time window.

## Methodology

The analysis followed these steps:

1. Collected and reviewed platform access request logs
2. Filtered SQL-related request patterns to identify SQL injection candidates
3. Grouped requests by URL and timestamp to detect unusual request spikes
4. Analyzed HTTP status codes to identify failed or abnormal server responses
5. Investigated repeated login attempts and similar URL patterns
6. Discussed false positives and false negatives in rule-based attack detection

## Key Findings

- SQL injection attempts could be identified through suspicious SQL structures such as `UNION ALL` and static Boolean conditions
- Brute-force login behavior was visible through sudden spikes in repeated login requests
- Some brute-force patterns may also create DDoS-like effects due to high request volume
- URL enumeration attacks could be detected by identifying large numbers of similar URLs requested within a short period
- Rule-based detection is useful for preliminary analysis but may require manual review to reduce false positives

## Limitations

Rule-based detection can produce false positives and false negatives.

For example:

- A normal user repeatedly trying to log in may look like a brute-force attempt
- Some legitimate queries may contain SQL keywords such as `SELECT` or `FROM`
- Attackers may use obfuscated payloads that bypass simple keyword filtering
- Sudden traffic spikes may be caused by normal user activity, not necessarily attacks

Because of these limitations, suspicious requests should be reviewed using multiple signals rather than a single keyword or rule.

## Skills Demonstrated

- PostgreSQL querying
- Web access log analysis
- SQL injection detection
- Brute-force attack analysis
- HTTP status code analysis
- Time-series request pattern analysis
- Cybersecurity data analysis
- False positive and false negative evaluation
- Rule-based anomaly detection

## Resume Summary

This project demonstrates experience analyzing web access logs and database request patterns to detect suspicious network activities, including SQL injection, brute-force login attempts, and URL enumeration attacks.
