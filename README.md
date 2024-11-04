# Predictions Simulation Platform

### Project Overview
The **Predictions Simulation Platform** is designed as a meta-simulation system capable of assisting in the definition and execution of simulations for forecasting potential societal impacts of proposed laws or policy changes. This project was built as part of a Java course assignment at [The Academic College of Tel-Aviv Yaffo].

### Motivation
When the government considers proposing a new law or substantial policy change that could impact many citizens, it often consults research bodies to analyze the current situation and forecast the likely effects. The Predictions platform enables simulations that explore the impact of various parameters and conditions on societal factors, helping answer questions like, "What will happen if we raise taxes on sugary drinks?" or "How will establishing a new transportation route affect employment rates?"

### Project Structure
The project consists of three major parts, each adding new functionalities:
1. **Console-based Core System** - An initial, text-based interface to run basic simulations.
2. **Graphical Interface (JavaFX)** - A more interactive, visual approach with JavaFX for simulation controls.
3. **Remote Access (Server)** - An extension to enable remote access for multiple users to interact with the simulation in real-time.

In this project, there is a **client-server architecture**:
![image](https://github.com/user-attachments/assets/388a4754-b8e3-488f-a003-33db0e1714ed)

---

### Server Components

1. **Server - Manages Different Simulations for Users**
   - The server is the central hub that coordinates simulations and facilitates user interactions with the system.

   #### Administrator
   The **Administrator** (Admin) is the sole system manager with special access permissions. Admin capabilities include:
   - **Uploading Simulation Files**: The admin can upload multiple simulation configuration files (XML) to enrich the system with diverse simulations.
   - ![image](https://github.com/user-attachments/assets/5b213c88-fb5a-4e16-ab96-d5b91547cb9f)
   - ![image](https://github.com/user-attachments/assets/321b0545-c701-484b-b88d-349270e2e1a9)
   - **Managing Simulation Runs**: Grants users access to run specific simulations multiple times.
   - ![image](https://github.com/user-attachments/assets/4d1c532d-ff07-441d-a6da-fe3aa78bc2c0)
   - **Viewing All Runs**: The admin has access to monitor all active and past simulation runs and their results.
   - ![image](https://github.com/user-attachments/assets/91642e22-f32e-44dd-960f-db618e5f6d9e)
   - **Internal System Status**: Provides system-wide insights, showing which simulations are currently active and by whom.

   #### User
   **Regular Users** connect to the system to utilize its simulation features. At any moment, multiple users can connect and interact with the platform. User capabilities include:
   - **Viewing Available Simulations**: Users can browse through available simulations uploaded by the admin.
   - **Requesting Simulation Runs**: Users can request to run a specific simulation multiple times.
   - ![image](https://github.com/user-attachments/assets/93f1b724-df45-4b7c-b84e-409279246a5b)
   - **Executing Simulations**: Just like in the standalone application, users can initiate simulations via the remote server.
   - ![image](https://github.com/user-attachments/assets/02252653-e226-4638-83ed-bb3cce4e706e)
   - **Viewing Own Results**: Users can view the results of their own simulation runs (but not those of other users).

This structured design allows both administrators and users to interact with the platform smoothly, enabling robust and customizable simulations for diverse policy impact analyses.


### System Components

#### 1. World
The central component managing an entire simulation scenario. The World defines multiple entity types, each appearing in a defined quantity, simulating a population within a timeline-based environment. Entities interact with one another through statistical relationships.

#### 2. Entities
An **Entity** represents an individual within the simulation world and may contain specific properties that distinguish it from other entities. Each property includes:
- **Name** (unique identifier)
- **Type** (e.g., integer, real number, boolean, string)
- **Constraints** (optional value restrictions)

#### 3. Rules, Actions, and Functions
**Rules** govern entity interactions and modify certain properties based on defined conditions and probabilities. These rules can include simple property modifications or complex conditions, such as creating or removing entities.

#### 4. Simulation Workflow
Simulations progress through discrete time steps (ticks), where each rule is evaluated based on:
- **Tick frequency** (how often the rule should apply)
- **Probability** (likelihood of rule activation)

### Additional Features
The system supports various utilities, such as average property tracking over time, consistency checks, and error handling for common issues (e.g., missing entities or undefined properties).

### Ending Conditions
A simulation can be set to stop based on:
1. **Time-based**: Defined run duration
2. **Tick count**: Defined number of ticks
3. **User-directed**: User manually ends the simulation

## Used Concepts
### Java Servlets and Server-Engine Architecture
Java Servlets handle web requests and responses, while an Engine component performs core business logic, isolating complex processing from the servlets for modular, efficient interactions.

### DTOs and Data Transfer
DTOs (Data Transfer Objects) define client-server data exchange, with fields tailored to transmitted data, supporting clear, dependency-free serialization.

### Context Class
The Context Class stores shared server data, giving servlets unified access to configurations and Engine instances, reducing redundancy.

### Input Validation
A Validator checks inputs for null values, formatting, and constraints, ensuring data integrity before Engine processing.

### XML Serialization and Conversion
XML Serialization handles XML-based data transfer, with a Converter translating XML-generated classes to custom DTOs, aligning data for efficient use.

---
