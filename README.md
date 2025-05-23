# Online Event Ticket Reservation System

A desktop JavaFX application to create, search, reserve, and track tickets for various events (Concerts, Theatre, Conferences, and more). This project uses object-oriented design principles and stores all data in local files.

---

## Features

### For Firms

* **Create New Events:** Set name, date, location, type, and multiple ticket categories (with prices and capacities).
* **View Reservations:** See all reservations for any event you created.
* **Delete Events:** Remove your events from the system.

### For Users

* **Search Events:** Filter by date, location, or event type.
* **Reserve Tickets:** Add tickets to a cart and confirm purchases. Available for both registered and guest users.
* **Track Reservations:** View all your past and current reservations (for registered users).
* **Unique Reservation ID:** Each booking generates a UUID for easy tracking.

### Data Persistence

* All data (users, events, reservations) is saved in `/data/` as plain text files for easy portability and inspection.

---

## Technologies

* **Java 21** (Maven project)
* **JavaFX** for GUI
* **Maven** for dependency and build management

---

## Project Structure

```
ticketreservationsystem/
├── pom.xml
├── data/
│   ├── events.txt
│   ├── reservations.txt
│   └── users.txt
└── src/
    └── main/
        ├── java/
        │   ├── module-info.java
        │   └── com/
        │       └── iyte/
        │           ├── gui/           # All JavaFX controllers and GUI logic
        │           └── ticketsystem/  # Business logic, models, persistence
        └── resources/
            └── com/
                └── iyte/
                    └── gui/           # FXML layout files
```

---

## How to Run

1. **Clone the repository:**

   ```sh
   git clone https://github.com/vardareg/TicketReservationSystem.git
   cd TicketReservationSystem
   ```

2. **Build and run with Maven:**

   ```sh
   mvn clean javafx:run
   ```

   > Requires JDK 21 and Maven installed.

3. **Data Files:**

   * Pre-populated with sample users, events, and reservations in the `/data` folder.

---

## Main Classes

* `com.iyte.gui.App` – Application entry point
* `com.iyte.gui.*Controller` – JavaFX UI logic
* `com.iyte.ticketsystem.TicketService` – Core business logic and persistence
* `com.iyte.ticketsystem.model.*` – Models: `Event`, `User`, `Reservation`, `TicketCategory`
* `com.iyte.ticketsystem.util.FileHelper` – File I/O for data persistence

---

## Screenshots

*Add screenshots of your main screens here (Login, Search, Reservation, etc.)*

---

## Example Usage Scenarios

1. **Firm adds a new event:** Enters all event details and available ticket types.
2. **User searches for a concert in Berlin:** Finds Jazz Festival, views ticket categories and prices.
3. **User books VIP ticket:** Adds to cart and confirms, receives reservation ID.
4. **Firm checks reservations:** Views all tickets sold for their events.

## License

*This project is for educational use in SEDS 504 (Spring 2025), İzmir Institute of Technology.*
