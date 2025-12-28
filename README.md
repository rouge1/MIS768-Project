# Pedestrian and Bicyclist Safety Tool

> A Java desktop application for crash data visualization and analysis using NASA WorldWind

[![Java](https://img.shields.io/badge/Java-Swing-orange)](https://www.java.com/)
[![WorldWind](https://img.shields.io/badge/NASA-WorldWind-blue)](https://worldwind.arc.nasa.gov/java/)
[![MySQL](https://img.shields.io/badge/Database-MySQL-green)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-NASA%20Open%20Source%201.3-lightgrey)](./NASA_Open_Source_Agreement_1.3.txt)

**MIS 768 Course Project @ UNLV**

---

## Overview

This tool provides an interactive solution for crash data point mapping. State and local agencies can use this software to identify "hot spot" areas where pedestrian and bicycle crashes are more likely to occur. Once these locations are identified, targeted initiatives and strategies can be implemented to increase safety.

### Background & Business Case

Bicyclists and pedestrians are vulnerable road users that are often overrepresented in fatal or incapacitating injury crash statistics. While passenger car fatalities have shown sharp declines in the last decade, pedestrian and bike fatalities have remained relatively constant.

Although DOT agencies have extensive road safety audit programs based on crash-to-traffic ratios, they do not specifically target locations with high bike/pedestrian crashes since these counts are not regularly recorded.

---

## Features

| Feature | Description |
|---------|-------------|
| **3D Globe Visualization** | Interactive NASA WorldWind globe with crash location markers |
| **Color-Coded Markers** | Red = Pedestrian crashes, Green = Bicycle crashes |
| **Date Filtering** | Filter crash data by date range |
| **Type Filtering** | Toggle pedestrian/cyclist visibility |
| **Database Integration** | MySQL storage for persistent data |
| **Role-Based Access** | USER (read-only) vs ADMIN (full CRUD) |
| **CSV Import** | Load crash data from CSV files |

---

## Project Structure

```
├── src/aProject/           # Main source code
│   ├── MIS_Project.java    # Main application & GUI
│   ├── Login.java          # Authentication screen
│   ├── CreateDB.java       # Database setup
│   ├── DataPoint.java      # Crash data model
│   ├── DataPointSet.java   # Data collection & filtering
│   ├── Point.java          # Geographic marker (WorldWind)
│   ├── CaseType.java       # Enum: BICYCLE, PEDESTRIAN
│   └── UserLevelType.java  # Enum: USER, ADMIN
├── lib/                    # JAR dependencies (WorldWind, JOGL, etc.)
├── doc/                    # JavaDoc documentation
└── resources/              # Military symbols & assets
```

---

## Technology Stack

| Component | Technology |
|-----------|------------|
| Language | Java (Swing GUI) |
| 3D Globe | [NASA WorldWind SDK](https://worldwind.arc.nasa.gov/java/) |
| Database | MySQL |
| Date Picker | JDatePicker |
| Layout | GridBagLayout |

---

## Getting Started

### Prerequisites

- Java JDK 8 or higher
- MySQL Server
- Eclipse IDE (recommended)

### Database Setup

The application automatically creates the database on first run:

1. Ensure MySQL is running on `localhost:3306`
2. Default credentials: `root` / (no password)
3. Database `aProjectDB` with tables `caseLocations` and `login` will be created

### Running the Application

1. Clone this repository
2. Open in Eclipse or your preferred Java IDE
3. Run `MIS_Project.java` as a Java Application
4. Login with default credentials:
   - **Admin:** Username: `Andrea`, Password: `test`

### CSV Data Format

The application expects CSV files with the following format:

```
caseNumber,caseType,date,longitude,latitude,elevation
1,PEDESTRIAN,01/15/2024,-115.1398,36.1699,620.0
2,BICYCLE,02/20/2024,-115.1728,36.1147,610.5
```

---

## Usage

1. **Login** - Enter credentials to access the application
2. **File > Open** - Load a CSV file with crash data
3. **View Globe** - Crash locations appear as colored markers
4. **Click Markers** - View details in the left panel
5. **Filter Data** - Use date pickers and checkboxes on the right
6. **Admin Only** - Edit/delete records, upload to database

---

## Authors

**Group #2** - MIS 768 Course Project

---

## License

This project uses the NASA WorldWind SDK under the [NASA Open Source Agreement v1.3](./NASA_Open_Source_Agreement_1.3.txt).

---

## Resources

- [NASA WorldWind Official Page](https://worldwind.arc.nasa.gov/java/)
- [WorldWind GitHub](https://github.com/NASAWorldWind/WorldWindJava)

> If you are attempting to use WorldWind in an RCP Plugin, visit the [world-wind-java-sdk-rcp](https://github.com/unofficial-nasa/world-wind-java-sdk-rcp) project.
