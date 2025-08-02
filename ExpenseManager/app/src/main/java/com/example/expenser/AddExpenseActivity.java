package com.example.expenser;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddExpenseActivity extends AppCompatActivity {
    private EditText edtTitle, edtCategory, edtAmount;
    private ExpenseDatabaseHelper dbHelper;
    private int expenseId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        edtTitle = findViewById(R.id.edtTitle);
        edtCategory = findViewById(R.id.edtCategory);
        edtAmount = findViewById(R.id.edtAmount);
        dbHelper = new ExpenseDatabaseHelper(this);

        expenseId = getIntent().getIntExtra("expenseId", -1);

        if (expenseId != -1) {
            Expense expense = dbHelper.getExpenseById(expenseId);
            if (expense != null) {
                edtTitle.setText(expense.title);
                edtCategory.setText(expense.category);
                edtAmount.setText(String.valueOf(expense.amount));
            }
        }

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            String title = edtTitle.getText().toString().trim();
            String category = edtCategory.getText().toString().trim();
            String amountStr = edtAmount.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(this, "Please enter the title", Toast.LENGTH_SHORT).show();
                return;
            }
            if (category.isEmpty()) {
                Toast.makeText(this, "Please enter the category", Toast.LENGTH_SHORT).show();
                return;
            }
            if (amountStr.isEmpty()) {
                Toast.makeText(this, "Please enter the amount", Toast.LENGTH_SHORT).show();
                return;
            }
            double amount;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid amount entered", Toast.LENGTH_SHORT).show();
                return;
            }

            if (expenseId == -1) {
                dbHelper.insertExpense(title, category, amount);
                Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.updateExpense(expenseId, title, category, amount);
                Toast.makeText(this, "Expense updated", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }
}
