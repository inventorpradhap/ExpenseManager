@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: Int): Transaction?

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'expense' AND strftime('%m', date/1000, 'unixepoch') = :month")
    fun getMonthlyExpenses(month: String): LiveData<Double>

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'income' AND strftime('%m', date/1000, 'unixepoch') = :month")
    fun getMonthlyIncome(month: String): LiveData<Double>

    @Query("SELECT * FROM transactions WHERE strftime('%m', date/1000, 'unixepoch') = :month AND strftime('%Y', date/1000, 'unixepoch') = :year")
    fun getTransactionsByMonth(month: String, year: String): LiveData<List<Transaction>>
}