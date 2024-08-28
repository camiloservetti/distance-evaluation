# Contact Duplicate Detector

The **Contact Duplicate Detector** is a Java application that identifies potential duplicate contacts based on various attributes such as name, email, address, etc. The application can use different 
string similarity algorithms (e.g., Jaro-Winkler, Levenshtein) to compute similarity scores between contacts and categorize them as potential duplicates.
Reference:
https://srinivas-kulkarni.medium.com/jaro-winkler-vs-levenshtein-distance-2eab21832fd6

## Features

- Detects possible duplicate contacts from a list.
- Computes a similarity score for each contact pair.
- Supports multiple similarity algorithms (e.g., Jaro-Winkler, Levenshtein).
- Configurable thresholds to categorize similarity scores as LOW, MEDIUM, or HIGH.
- Reads contact data from a CSV file.
