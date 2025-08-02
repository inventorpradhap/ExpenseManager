package com.example.expenser;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
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

        loadExpenses();

        findViewById(R.id.btnAddExpense).setOnClickListener(v ->
            startActivity(new Intent(MainActivity.this, AddExpenseActivity.class))
        );

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { filterExpenses(s.toString()); }
            @Override public void afterTextChanged(Editable s) { }
        });
    }

    private void loadExpenses() {
        expenseList.clear();
        Cursor cursor = dbHelper.getAllExpenses();
        while (cursor.moveToNext()) {
            expenseList.add(new Expense(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("title")),
                cursor.getString(cursor.getColumnIndex("category")),
                cursor.getDouble(cursor.getColumnIndex("amount"))
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
        // Handle edit - launch add/edit activity with expense id
        Intent intent = new Intent(this, AddExpenseActivity.class);
        intent.putExtra("expense_id", expense.id);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Expense expense) {
        dbHelper.deleteExpense(expense.id);
        Toast.makeText(this, "Expense deleted", Toast.LENGTH_SHORT).show();
        loadExpenses();
    }

    // Add menu option for Chart and Export
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_charts) {
            startActivity(new Intent(this, ChartActivity.class));
            return true;
        } else if (item.getItemId() == R.id.menu_export) {
            exportData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exportData() {
        // Implement export CSV logic here or trigger a method in dbHelper
        Toast.makeText(this, "Export feature coming soon!", Toast.LENGTH_SHORT).show();
    }
}
