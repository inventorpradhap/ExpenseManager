class TransactionRepository(private val transactionDao: TransactionDao) {
    val allTransactions: LiveData<List<Transaction>> = transactionDao.getAllTransactions()

    suspend fun insert(transaction: Transaction) {
        transactionDao.insert(transaction)
    }

    suspend fun update(transaction: Transaction) {
        transactionDao.update(transaction)
    }

    suspend fun delete(transaction: Transaction) {
        transactionDao.delete(transaction)
    }

    fun getMonthlyExpenses(month: String): LiveData<Double> {
        return transactionDao.getMonthlyExpenses(month)
    }

    fun getMonthlyIncome(month: String): LiveData<Double> {
        return transactionDao.getMonthlyIncome(month)
    }

    fun getTransactionsByMonth(month: String, year: String): LiveData<List<Transaction>> {
        return transactionDao.getTransactionsByMonth(month, year)
    }
}