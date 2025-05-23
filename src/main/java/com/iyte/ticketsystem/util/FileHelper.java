package com.iyte.ticketsystem.util;

import com.iyte.ticketsystem.model.*;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class FileHelper {
    private static final Path DATA = Path.of("data");
    private static final Path USERS = DATA.resolve("users.txt");
    private static final Path EVENTS = DATA.resolve("events.txt");
    private static final Path RES = DATA.resolve("reservations.txt");

    public static void loadAll(Map<String, User> users, List<Event> events) {
        try {
            Files.createDirectories(DATA);
        } catch (IOException ignored) {
        }
        loadUsers(users);
        loadEvents(events);
        loadReservations(users);
    }

    private static void loadUsers(Map<String, User> users) {
        if (!Files.exists(USERS))
            return;
        try (BufferedReader br = Files.newBufferedReader(USERS)) {
            br.lines().forEach(name -> users.put(name, new User(name)));
        } catch (IOException ignored) {
        }
    }

    private static void loadEvents(List<Event> events) {
        Path f = Path.of("data", "events.txt");
        if (!Files.exists(f))
            return;

        try (BufferedReader br = Files.newBufferedReader(f)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank())
                    continue; // skip empty
                String[] parts = line.split("\\|", 5);
                if (parts.length < 4) { // need at least name|date|loc|type
                    System.err.println("Skipping bad event line: " + line);
                    continue;
                }

                String name = parts[0];
                LocalDate date = LocalDate.parse(parts[1]);
                String location = parts[2];
                String type = parts[3];
                Event e = new Event(name, date, location, type);

                if (parts.length == 5 && !parts[4].isBlank()) {
                    for (String catSpec : parts[4].split(",")) {
                        String[] cp = catSpec.split(":");
                        if (cp.length < 3)
                            continue; // skip malformed category
                        e.addCategory(new TicketCategory(
                                cp[0],
                                Double.parseDouble(cp[1]),
                                Integer.parseInt(cp[2])));
                    }
                }
                events.add(e);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void loadReservations(Map<String, User> users) {
        if (!Files.exists(RES))
            return;
        try (BufferedReader br = Files.newBufferedReader(RES)) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] p = l.split("\\|");
                // only handle the new 6‐field lines: id|user|event|category|qty|price
                if (p.length != 6)
                    continue;

                String user = p[1];
                String event = p[2];
                String category = p[3];
                int qty = Integer.parseInt(p[4]);
                double price = Double.parseDouble(p[5]);

                // dummy event/type because we aren’t storing those in the reservation file
                Event dummy = new Event(event, LocalDate.now(), "", "");
                TicketCategory cat = new TicketCategory(category, price / qty, 0);
                Reservation r = new Reservation(user, dummy, cat, qty);
                users.computeIfAbsent(user, User::new).addReservation(r);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void saveAll(Map<String, User> users, List<Event> events) {
        saveUsers(users);
        saveEvents(events);
        saveReservations(users);
    }

    private static void saveUsers(Map<String, User> users) {
        try (BufferedWriter bw = Files.newBufferedWriter(USERS)) {
            for (String u : users.keySet())
                bw.write(u + "\n");
        } catch (IOException ignored) {
        }
    }

    public static void saveEvents(List<Event> events) {
        try (BufferedWriter bw = Files.newBufferedWriter(EVENTS)) {
            for (Event e : events) {
                // Build the "Cat:Price:Available" segments
                String cats = e.getCategories().stream()
                        .map(cat -> String.join(":",
                                cat.getCategoryName(), // <-- correct getter
                                String.valueOf(cat.getPrice()), // <-- correct getter
                                String.valueOf(cat.getAvailable()) // <-- correct getter
                        ))
                        .collect(Collectors.joining(","));

                // Now write ONE line per event
                String line = String.join("|",
                        e.getName(),
                        e.getDate().toString(),
                        e.getLocation(),
                        e.getType(),
                        cats);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void saveReservations(Map<String, User> users) {
        try (BufferedWriter bw = Files.newBufferedWriter(RES)) {
            for (User u : users.values())
                for (Reservation r : u.getReservations())
                    bw.write(r.getReservationId() + "|" + r.getUsername() + "|" + r.getEventName() + "|"
                            + r.getCategoryName() + "|" + r.getQuantity() + "|" + r.getPrice() + "\n");
        } catch (IOException ignored) {
        }
    }
}