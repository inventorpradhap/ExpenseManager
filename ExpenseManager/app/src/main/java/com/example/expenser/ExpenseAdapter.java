package com.example.expenser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
    private final List<Expense> expenseList;

    public ExpenseAdapter(List<Expense> expenseList) { this.expenseList = expenseList; }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.txtTitle.setText(expense.title);
        holder.txtCategory.setText(expense.category);
        holder.txtAmount.setText("₹" + expense.amount);
    }

    @Override
    public int getItemCount() { return expenseList.size(); }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtCategory, txtAmount;
        ExpenseViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtAmount = itemView.findViewById(R.id.txtAmount);
        }
    }
}
