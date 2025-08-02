package com.example.expenser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Expense expense);
        void onDeleteClick(Expense expense);
    }

    private List<Expense> expenseList;
    private OnItemClickListener listener;

    // Constructor
    public ExpenseAdapter(List<Expense> expenseList, OnItemClickListener listener) {
        this.expenseList = expenseList;
        this.listener = listener;
    }

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

        // Click listener for item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(expense);
        });

        // Long click listener for delete
        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) listener.onDeleteClick(expense);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    // Method to update list (for filtering)
    public void updateList(List<Expense> filteredList) {
        this.expenseList = filteredList;
        notifyDataSetChanged();
    }

    // ViewHolder class
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
