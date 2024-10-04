#### Software Architecture and Platforms - a.y. 2024-2025
 
# Assignment #01 - 20241004 

v0.9.0-20241004

- **Description:** Consider the "EBike application" demo included in this repo. Currently it is implemented as a BBoM, without following any specific software architecture. We want to: 

   - Create a new "EBike application" prototype, based on a client-server (frontend, backend) architecture, with the backend designed using a layered and micro-kernel software architecture styles. The prototype: 
     -  should allow remote users and administrators to interact with the system;  
     -  should upport some level of data persistency and should make it possible: to change the persistent model and technology (e.g. MySQL, MongoDB, etc) without changing anything in the other layers;  
     -  should allow to change or add front-ends (presentation layer) without changing other layers (such as the business logic layer); 
     -  should allow to dynamically extend  the set of functionalities available to users [*].
     
     [*] this point will be refined after the lecture on Micro-Kernel architectural style.

       
  - Identify a set of fitness functions useful to assess the structural properties of the architecture, to be checked using the proper tools (e.g. ArchUnit).      
  - Write a report including:  
     - the description of the architecture structure using C&C view(s);  
     - the description of a core set of quality attribute and the quality attribute scenarios, driving the design of the architecture;
     - the description of the set of fiftness functions used to assess the quality of the architecture, and the result of some  validation carried on the system.

    
- **Deliverable**:  a zipped folder ``Assignment-01-<Surname>`` including a maven-based or gradle-based project, with sources and the report in PDF. The deliverable can be submitted using a link on the course web site.

- **Deadline:** October 18, 2024 - 9:00 AM
 
