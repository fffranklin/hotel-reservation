# Hotel Reservation Application
Project developed using **[Java SE 22](https://www.oracle.com/java/technologies/java-se-glance.html)** for the **[Udacity Java Fundamentals Course](https://www.udacity.com/course/java-fundamentals--cd0282)**. 
This Hotel Reservation Application was developed to demonstrate _Java Fundamentals_ applied in practice based on designing classes using _Object-Oriented Programming (OOP)_, organising and processing data with _collections_, and using _common Java types_.

## Application Main Components

The major components of this application consists of the following:

- **CLI for the User Interface ->** _Command Line Interface_ (or _CLI_) for the user interface. Java will monitor the _CLI_ for user input, so the user can enter commands to search for available rooms, book rooms, and so on.
- **Java code ->** The second main component is the Java code, which is the app's business logic.
- **Java collections ->** Java collections for in-memory storage of the data needed for the app, such as the users' names, room availability, and so on.

## Application Architecture

The app are separated into the following layers:

1. **User interface (UI) ->** the ui includes a _main menu_ for the users who want to book a room, and an _admin menu_ for administrative functions.
2. **Resources ->** to act as the Application Programming Interface (API) to the UI.
3. **Services ->** to communicate with the resources, and each other, to build the business logic necessary to provide feedback to the UI.
4. **Data models ->** to represent the domain that are used within the system (e.g., rooms, reservations, and customers).

## Application Requirements

The required features for the Hotel Reservation App are the following:

### User Scenarios

The application provides four user scenarios:

- **Creating a customer account ->** the user needs to first create a customer account before they can create a reservation.

- **Searching for rooms ->** the app should allow the user to search for available rooms based on provided checkin and checkout dates. If the application has available rooms for the specified date range, a list of the corresponding rooms will be displayed to the user for choosing.

- **Booking a room ->** once the user has chosen a room, the app will allow them to book the room and create a reservation.

- **Viewing reservations ->** After booking a room, the app allows customers to view a list of all their reservations.

### Admin Scenarios

The application provides four administrative scenarios:

- **Displaying all customers accounts.**
- **Viewing all of the rooms in the hotel.**
- **Viewing all of the hotel reservations.**
- **Adding a room to the hotel application.**

### Reserving a Room Requirements

The specifics for the customers to reserve a room are the following:

- **Avoid conflicting reservations ->** a single room may only be reserved by a single customer per check-in and check-out date range.
- **Search for recommended rooms ->** if there are no available rooms for the customer's date range, a search will be performed that displays recommended rooms on alternative dates. The recommended room search will add seven days to the original check-in and check-out dates to see if the hotel has any availabilities and then display the recommended rooms/dates to the customer.

### Room Requirements

- **Room cost ->** Rooms will contain a price per night. When displaying rooms, paid rooms will display the price per night and free rooms will display "Free" or have a $0 price.
- **Unique room numbers ->** each room will have a unique room number, meaning that no two rooms can have the same room number.
- **Room type ->** Rooms can be either single occupant or double occupant (Enumeration: SINGLE, DOUBLE).

### Customer Requirements

The application will have customer accounts. Each account has:

- **A unique email for the customer ->** RegEx is used to check that the email is in the correct format (_i.e., name@domain.com_).
- **A first name and last name**.

### Error Requirements

The hotel reservation application handles all exceptions gracefully (user inputs included), meaning:

- **No crashing ->** the application does not crash based on user input.
- **No unhandled exceptions ->** the app has try and catch blocks that are used to capture exceptions and provide useful information to the user. There are no unhandled exceptions.
  
