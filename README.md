# Gas Weight Predictor Mobile App

## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
   - [User Input Page](#user-input-page)
   - [Prediction Page](#prediction-page)
   - [Gas Weight Chart Page](#gas-weight-chart-page)
   - [Gas Leak Detection Page](#gas-leak-detection-page)
3. [Tech Stack](#tech-stack)
4. [Usage](#usage)
5. [Machine Learning Model](#machine-learning-model)
6. [Documentation](#documentation)
7. [User Guide](#user-guide).
9. [Installation](#installation)

## Overview

The **Gas Weight Predictor Mobile App** is an Android application developed in Java to monitor and manage household or industrial gas consumption. This app allows users to input details about their gas setup, track real-time gas usage data, predict gas depletion, and detect gas leaks remotely. With data synchronized in real-time through Firebase, users can view the remaining gas, estimate how long it will last, and even control alarms remotely to ensure safety and efficient management.

## Features

### User Input Page
- **Purpose**: The User Input Page serves as the primary setup screen, where users enter essential details to initialize the app’s gas tracking capabilities.

| <img src="https://github.com/user-attachments/assets/379a55ed-7935-4cc7-8499-47564c84b47e" alt="Home Page" width="300" /> | <img src="https://github.com/user-attachments/assets/e09c19a6-f642-4a1f-9465-99f784d912f5" alt="Home Type Select" width="300" /> | <img src="https://github.com/user-attachments/assets/b9b4b1d7-c903-4c2f-bbb7-08bc73133238" alt="Date Select" width="300" /> |
|:--:|:--:|:--:|
| **Figure 1:** Home Page | **Figure 2:** Home Type Selection | **Figure 3:** Date Selection |


- **Details Captured**:
  - **Name**: The user’s full name for personalized record-keeping.
  - **Email**: User’s email address for notifications or account linking.
  - **Gas Type**: The type of gas (e.g., propane, natural gas), allowing for tailored monitoring.
  - **Starting Date**: The date the gas usage began, to establish a baseline for predictions.
  - **Starting Time**: The specific time the gas was first used, adding granularity to the data.

### Prediction Page
- **Purpose**: This page provides an at-a-glance summary of current gas levels and predictive analytics powered by a Machine Learning model.
- **Metrics Displayed**:
  - **Remaining Gas Percentage**: A circular visual indicator showing the percentage of gas left.
  - **Remaining Days**: An estimate of how many days the remaining gas will last based on historical usage data.
  - **Gas Weight Finish Date**: A predicted date when the gas is expected to be fully depleted.

### Gas Weight Chart Page
- **Purpose**: The Gas Weight Chart Page visualizes daily gas weight measurements, allowing users to track usage trends over time and detect any abnormal consumption patterns.
- **Features**:
  - **Historical Data Visualization**: Daily gas weights are plotted to show trends, making it easier to understand consumption patterns.
  - **Easy-to-Read Chart**: A clear and interactive chart updates in real-time, with no need for reloading, using Firebase for seamless data access.

### Gas Leak Detection Page
- **Purpose**: This page monitors the gas weight data to detect sudden drops that could indicate a leak, offering an added layer of safety for the user.
- **Leak Detection and Alarm Control**:
  - **Automatic Leak Detection**: Alerts users of potential leaks if abnormal weight reductions are detected.
  - **Remote Alarm Control**: Users can activate or deactivate the alarm via the web application interface, providing remote control over safety measures.

## Tech Stack

- **Frontend**: Java (Android)
- **Backend**: Firebase Realtime Database
- **Real-Time Data Synchronization**: Data updates in real-time without needing manual refreshes, ensuring users always see current information.

## Usage

1. **User Input**: Enter user details on the User Input Page, including name, email, gas type, start date, and time to initialize tracking.
2. **Monitor Gas Predictions**: Check the Prediction Page for real-time updates on remaining gas, estimated days, and expected depletion date.
3. **Track Usage Trends**: Access the Gas Weight Chart Page to view gas consumption over time in a clear, interactive chart.
4. **Enable Leak Detection**: Monitor the Gas Leak Detection Page to receive alerts for potential leaks. Use the web application to control the alarm remotely.

## Machine Learning Model

The app employs a Machine Learning model to predict gas consumption patterns. This model uses user data and historical usage to forecast the remaining days and depletion date, helping users manage their gas supply efficiently.

## Documentation

### User Guide
- **Accessing Features**: Navigate through pages to view predictions, track usage, and detect leaks.
- **Viewing Data**: All data visualizations and predictions update in real-time.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For questions or support, please contact:

- **Name**: Sajith Eranda
- **Email**: [Your Email]

## Installation

To set up the Gas Weight Predictor Mobile App on your local machine:

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/gas-weight-predictor-mobile-app.git
