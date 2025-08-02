package com.example.expenser;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ExpenseAdapter expenseAdapter;
    private ExpenseDatabaseHelper dbHelper;
    private ArrayList<Expense> expenseList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnAdd = findViewById(R.id.btnAddExpense);
        recyclerView = findViewById(R.id.recyclerExpenses);

        dbHelper = new ExpenseDatabaseHelper(this);
        loadExpenses();

        expenseAdapter = new ExpenseAdapter(expenseList);
        recyclerView.setAdapter(expenseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(v -> startActivity(new Intent(this, AddExpenseActivity.class)));
    }

    private void loadExpenses() {
        expenseList.clear();
        Cursor cursor = dbHelper.getAllExpenses();
        while (cursor.moveToNext()) {
            expenseList.add(new Expense(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getDouble(3)
            ));
        }
        cursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadExpenses();
        expenseAdapter.notifyDataSetChanged();
    }
}
