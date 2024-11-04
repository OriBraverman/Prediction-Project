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

---
