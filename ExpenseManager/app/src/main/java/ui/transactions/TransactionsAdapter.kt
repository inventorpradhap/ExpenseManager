class TransactionsAdapter(
    private val onClick: (Transaction) -> Unit
) : ListAdapter<Transaction, TransactionsAdapter.TransactionViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            binding.apply {
                categoryName.text = transaction.category
                transactionNote.text = transaction.note ?: ""
                transactionDate.text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                    .format(transaction.date)
                
                val amountText = if (transaction.type == "expense") {
                    "-${transaction.amount}"
                } else {
                    "+${transaction.amount}"
                }
                transactionAmount.text = amountText
                transactionAmount.setTextColor(ContextCompat.getColor(itemView.context,
                    if (transaction.type == "expense") R.color.expense_red else R.color.income_green))

                itemView.setOnClickListener { onClick(transaction) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}