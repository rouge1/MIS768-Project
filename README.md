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
| **Database Integration** | MySQL storage for persistent crash data |
| **CSV Import** | Load crash data from CSV files |
| **Simplified Access** | Single admin user account for full access |

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
| Graphics | JOGL OpenGL bindings |
| Date Picker | JDatePicker |
| Layout | GridBagLayout |

---

## Getting Started

### Prerequisites

- Java JDK 8 or higher
- MySQL Server
- MySQL Connector/J (included in lib/)
- NVIDIA GPU (recommended for 3D rendering)
- X11 display server

### Database Setup

The application uses MySQL for storing crash data:

**Database Credentials:**
- **Host:** `localhost:3306`
- **Database:** `aProjectDB`
- **Username:** `mis768`
- **Password:** `Password@123`

**Database Structure:**

**`caseLocations` Table:**
```sql
CREATE TABLE caseLocations (
    caseID MEDIUMINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    caseNumber CHAR(25),
    caseType CHAR(10) NOT NULL,        -- 'PEDESTRIAN' or 'BICYCLE'
    caseDate DATE NOT NULL,
    caseLat CHAR(10) NOT NULL,         -- Latitude
    caseLong CHAR(10) NOT NULL,        -- Longitude
    caseElev CHAR(10) NOT NULL         -- Elevation
);
```

**`login` Table:**
```sql
CREATE TABLE login (
    userID MEDIUMINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    userName CHAR(10) NOT NULL,
    password CHAR(25) NOT NULL,
    userType CHAR(5) NOT NULL           -- 'ADMIN' or 'USER'
);
```

The application automatically creates these tables on first run via `CreateDB.java`. The login table is created empty - authentication is handled via hardcoded credentials.

### Running the Application

1. Clone this repository
2. Open in Eclipse or your preferred Java IDE
3. Run `MIS_Project.java` as a Java Application
4. Login with credentials:
   - **Username:** `test`
   - **Password:** `test`
   - **Note:** This is the only user account configured in the system

**Note:** For optimal 3D rendering performance, run with NVIDIA GPU acceleration:
```bash
__NV_PRIME_RENDER_OFFLOAD=1 __GLX_VENDOR_LIBRARY_NAME=nvidia java -cp "bin:lib/*" aProject.MIS_Project
```

### CSV Data Format

The application expects CSV files with the following format:

```
caseNumber,caseType,date,longitude,latitude,elevation
1,PEDESTRIAN,01/15/2024,-115.1398,36.1699,620.0
2,BICYCLE,02/20/2024,-115.1728,36.1147,610.5
```

---

## Usage

1. **Login** - Enter `test`/`test` to access the application
2. **File > Open** - Load a CSV file with crash data
3. **View Globe** - Crash locations appear as colored markers
4. **Click Markers** - View details in the left panel
5. **Filter Data** - Use date pickers and checkboxes on the right
6. **Database Upload** - Save crash data to MySQL database for persistence

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
