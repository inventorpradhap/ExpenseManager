package com.example.expenser;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
            String title = edtTitle.getText().toString();
            String category = edtCategory.getText().toString();
            double amount = Double.parseDouble(edtAmount.getText().toString());
            dbHelper.insertExpense(title, category, amount);
            finish();
        });
    }
}
