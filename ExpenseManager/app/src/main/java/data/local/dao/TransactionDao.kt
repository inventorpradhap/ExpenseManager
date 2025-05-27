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

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'expense' AND strftime('%m', date/1000, 'unixepoch') = :month")
    fun getMonthlyExpenses(month: String): LiveData<Double>
}