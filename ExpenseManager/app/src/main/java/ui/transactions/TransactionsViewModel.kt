class TransactionsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TransactionRepository
    val allTransactions: LiveData<List<Transaction>>
    val monthlyExpenses: LiveData<Double>
    val monthlyIncome: LiveData<Double>

    private val currentMonth = SimpleDateFormat("MM", Locale.getDefault()).format(Date())
    private val currentYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date())

    init {
        val dao = AppDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(dao)
        allTransactions = repository.allTransactions
        monthlyExpenses = repository.getMonthlyExpenses(currentMonth)
        monthlyIncome = repository.getMonthlyIncome(currentMonth)
    }

    fun insert(transaction: Transaction) = viewModelScope.launch {
        repository.insert(transaction)
    }

    fun update(transaction: Transaction) = viewModelScope.launch {
        repository.update(transaction)
    }

    fun delete(transaction: Transaction) = viewModelScope.launch {
        repository.delete(transaction)
    }

    fun getMonthlyTransactions(month: String, year: String): LiveData<List<Transaction>> {
        return repository.getTransactionsByMonth(month, year)
    }
}