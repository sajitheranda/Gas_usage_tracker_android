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
7. [License](#license)
8. [Contact](#contact)
9. [Installation](#installation)
10. [Contributing](#contributing)

## Overview

The **Gas Weight Predictor Mobile App** provides users with tools to track their gas usage, predict depletion dates, and detect potential gas leaks. Designed for Android, the app allows real-time tracking, updates, and leak detection directly from a mobile device, helping users manage gas consumption efficiently and safely.

## Features

### User Input Page
- Collects user details:
  - **Name**
  - **Email**
  - **Gas Type** (e.g., propane, natural gas)
  - **Starting Date**
  - **Starting Time**

### Prediction Page
- Displays:
  - **Remaining Gas Percentage** in a circular progress view.
  - **Remaining Days** until gas depletion.
  - **Predicted Finish Date** based on the ML model.

### Gas Weight Chart Page
- Shows a daily gas weight chart, allowing users to track gas usage trends over time.

### Gas Leak Detection Page
- Detects leaks by monitoring gas weight reduction without assumptions.
- Users can activate or deactivate the leak alarm through the web interface from any location.

## Tech Stack

- **Frontend**: Java (Android)
- **Backend**: Firebase Realtime Database
- **Real-Time Data Updates**: No need to reload the page to see different data.

## Usage

1. **Input User Details**: Enter name, email, gas type, starting date, and starting time on the User Input Page.
2. **View Predictions**: Check gas consumption predictions, including remaining gas percentage, remaining days, and the finish date on the Prediction Page.
3. **Track Usage**: View historical gas usage data on the Gas Weight Chart Page.
4. **Leak Detection**: Monitor for gas leaks and control the alarm from the Gas Leak Detection Page.

## Machine Learning Model

The app uses a Machine Learning model trained on historical gas consumption data to predict the depletion date based on real-time usage.

## Documentation

### User Guide
- **Navigating the App**: Open the app and enter the necessary details to start tracking.
- **Viewing Data**: Switch between pages to access predictions, charts, and leak detection.

### API Documentation
- **Data Handling**: Data is synchronized with Firebase in real time.
- **Alerts**: Leak alerts can be managed via the web interface and are connected to Firebase for real-time control.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any questions or feedback, please contact:

- **Name**: Sajith Eranda
- **Email**: [Your Email]

## Installation

To set up the Gas Weight Predictor Mobile App on your local machine:

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/gas-weight-predictor-mobile-app.git
