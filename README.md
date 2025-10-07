# City-Simulation: Urban Agent-Based Simulation

**Project for the Object-Oriented Programming (OOP) course**, developed in **Java** following the **MVC architectural pattern**.

---

## Core Concept

This project is an **agent-based simulation** for observing the dynamics of an urban ecosystem over time.  
The goal is to model city behavior, focusing on traffic, the relationship between people and businesses, and zoning.

The platform models the city’s dynamic elements, including:

- **Environment:** The city, composed of streets and businesses.  
- **Agents:** People, each with goals who make decisions.  
- **Infrastructure:** Businesses providing jobs and public transportation lines used by agents.  

---

## Software Architecture (MVC)

The application was designed using the **Model-View-Controller (MVC)** pattern, which divides an application into three interconnected components:

- **Model:** Manages application data and business logic. Maintains state and enforces rules independently of the interface.  
- **View:** Presents data to the user, showing the Model's state and providing the graphical interface for interaction.  
- **Controller:** Receives user input from the View and translates it into actions for the Model. Acts as an intermediary between the two.  

---

## Design Patterns Used

The software design incorporates several patterns to improve **flexibility** and **maintainability**:

- **Factory Method:** Provides an interface to create objects in a superclass while allowing subclasses to alter the type of objects created.  
- **Strategy:** Defines a family of algorithms, encapsulates them, and makes them interchangeable. Allows objects to change behavior independently of clients.  
- **Observer:** The subject maintains a list of observers and automatically notifies them of any state changes.  
- **Decorator:** Dynamically adds functionality to objects without altering the behavior of other objects of the same class.  

---

## Technologies and Key Features

- **Language:** Java — high-level, object-oriented, class-based.  
- **GUI:** (specify library used, e.g., JavaFX or Swing)  
- **Lambda Expressions:** Enable concise, anonymous functions and treating functions as method arguments.  
- **Streams API:** Provides a functional-style interface for processing sequences of elements, allowing declarative and compact data handling.  
- **Optional:** Represents the presence or absence of a value, enforcing explicit handling and preventing `NullPointerException`.  

---

## Contact

## Contatti
* Alessio Bifulco: `alessio.bifulco@studio.unibo.it`
