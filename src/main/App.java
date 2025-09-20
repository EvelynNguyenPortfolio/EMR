package main;

import main.util.Database;

public class App {
    public static void main(String[] args) {
        Database db = null;

        try {
            db = new Database();
        } catch (RuntimeException e) {
            System.exit(1);
        }

        System.out.println("=== EMR System Initialized ===\n");
        if (db != null)
            db.close();
    }
}