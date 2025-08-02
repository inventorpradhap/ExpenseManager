package com.example.expenser;

public class Expense {
    public int id;
    public String title, category;
    public double amount;

    public Expense(int id, String title, String category, double amount) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.amount = amount;
    }
}
