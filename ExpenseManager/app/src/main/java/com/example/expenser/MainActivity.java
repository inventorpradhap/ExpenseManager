package com.example.expenser;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExpenseAdapter.OnItemClickListener {

    private ExpenseAdapter expenseAdapter;
    private ExpenseDatabaseHelper dbHelper;
    private ArrayList<Expense> expenseList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new ExpenseDatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerExpenses);
        edtSearch = findViewById(R.id.edtSearch);

        expenseAdapter = new ExpenseAdapter(expenseList, this);
        recyclerView.setAdapter(expenseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btnAddExpense).setOnClickListener(v ->
            startActivity(new Intent(MainActivity.this, AddExpenseActivity.class))
        );

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { filterExpenses(s.toString()); }
            @Override public void afterTextChanged(Editable s) { }
        });

        loadExpenses();
    }

    private void loadExpenses() {
        expenseList.clear();
        Cursor cursor = dbHelper.getAllExpenses();
        while (cursor.moveToNext()) {
            expenseList.add(new Expense(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("title")),
                cursor.getString(cursor.getColumnIndexOrThrow("category")),
                cursor.getDouble(cursor.getColumnIndexOrThrow("amount"))
            ));
        }
        cursor.close();
        expenseAdapter.notifyDataSetChanged();
    }

    private void filterExpenses(String query) {
        ArrayList<Expense> filteredList = new ArrayList<>();
        for (Expense exp : expenseList) {
            if (exp.title.toLowerCase().contains(query.toLowerCase()) ||
                exp.category.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(exp);
            }
        }
        expenseAdapter.updateList(filteredList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadExpenses();
    }

    @Override
    public void onItemClick(Expense expense) {
        Intent intent = new Intent(this, AddExpenseActivity.class);
        intent.putExtra("expenseId", expense.id);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Expense expense) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Expense")
               .setMessage("Are you sure you want to delete this expense?")
               .setPositiveButton("Delete", (dialog, which) -> {
                   dbHelper.deleteExpense(expense.id);
                   Toast.makeText(this, "Expense deleted", Toast.LENGTH_SHORT).show();
                   loadExpenses();
               })
               .setNegativeButton("Cancel", null)
               .show();
    }
}
