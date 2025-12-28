# Pedestrian and Bicyclist Safety Tool - Application

> Core application source code and resources

## 📁 Directory Structure

```
├── src/aProject/           # Java source files
│   ├── MIS_Project.java    # Main application class
│   ├── Login.java          # User authentication
│   ├── CreateDB.java       # Database initialization
│   ├── DataPoint.java      # Crash data model
│   ├── DataPointSet.java   # Data collection management
│   ├── Point.java          # Geographic marker class
│   ├── CaseType.java       # Accident type enum
│   └── UserLevelType.java  # User role enum
├── bin/                    # Compiled .class files
├── doc/                    # Generated JavaDoc
├── config/                 # WorldWind configuration
├── images/                 # Globe textures & icons
└── resources/              # Military standard symbols
```

## 🔧 Class Overview

### Core Classes

| Class | Description |
|-------|-------------|
| `MIS_Project` | Main JFrame application with GUI, event handling, and database operations |
| `Login` | Authentication panel that verifies credentials against MySQL |
| `CreateDB` | Static methods to create database schema and seed data |

### Data Model

| Class | Description |
|-------|-------------|
| `DataPoint` | Represents a single crash with case number, type, date, and location |
| `DataPointSet` | ArrayList wrapper with parsing, filtering, and plotting methods |
| `Point` | Extends WorldWind `BasicMarker` for geographic positioning |

### Enums

| Enum | Values |
|------|--------|
| `CaseType` | `BICYCLE`, `PEDESTRIAN` |
| `UserLevelType` | `USER`, `ADMIN` |

## 🗄️ Database Schema

### Table: `caseLocations`
| Column | Type | Description |
|--------|------|-------------|
| caseID | MEDIUMINT | Auto-increment primary key |
| caseNumber | CHAR(25) | Case identifier |
| caseType | CHAR(10) | BICYCLE or PEDESTRIAN |
| caseDate | DATE | Date of incident |
| caseLat | CHAR(10) | Latitude |
| caseLong | CHAR(10) | Longitude |
| caseElev | CHAR(10) | Elevation |

### Table: `login`
| Column | Type | Description |
|--------|------|-------------|
| userID | MEDIUMINT | Auto-increment primary key |
| userName | CHAR(10) | Username |
| password | CHAR(25) | Password |
| userType | CHAR(5) | USER or ADMIN |

## 📚 Dependencies

- NASA WorldWind Java SDK
- JDatePicker library
- MySQL Connector/J
- Custom `gbl` package (GridBagLayout helpers)

## 📖 JavaDoc

Pre-generated documentation is available in the `doc/` folder. Open `doc/index.html` in a browser.

---

### NASA WorldWind

This project uses NASA's WorldWind SDK for 3D globe visualization.

- **Official Page:** https://worldwind.arc.nasa.gov/java/
- **License:** [NASA Open Source Agreement v1.3](./NASA_Open_Source_Agreement_1.3.txt)


