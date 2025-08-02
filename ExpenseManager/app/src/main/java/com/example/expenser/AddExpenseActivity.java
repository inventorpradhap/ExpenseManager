package com.example.expenser;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddExpenseActivity extends AppCompatActivity {
    private EditText edtTitle, edtCategory, edtAmount;
    private ExpenseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        edtTitle = findViewById(R.id.edtTitle);
        edtCategory = findViewById(R.id.edtCategory);
        edtAmount = findViewById(R.id.edtAmount);
        dbHelper = new ExpenseDatabaseHelper(this);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            String title = edtTitle.getText().toString().trim();
            String category = edtCategory.getText().toString().trim();
            String amountStr = edtAmount.getText().toString().trim();

            // Validate inputs are not empty
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
            // Validate amount is a valid double
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid amount entered", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insert the expense into the database
            dbHelper.insertExpense(title, category, amount);

            // Optionally notify user
            Toast.makeText(this, "Expense saved", Toast.LENGTH_SHORT).show();

            // Close this activity and return to previous screen
            finish();
        });
    }
}
