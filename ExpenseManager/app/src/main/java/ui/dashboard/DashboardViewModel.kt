class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TransactionRepository
    val monthlyBalance: LiveData<Double>
    val recentTransactions: LiveData<List<Transaction>>

    private val currentMonth = SimpleDateFormat("MM", Locale.getDefault()).format(Date())
    private val currentYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date())

    init {
        val dao = AppDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(dao)
        
        val monthlyIncome = repository.getMonthlyIncome(currentMonth)
        val monthlyExpense = repository.getMonthlyExpenses(currentMonth)
        
        monthlyBalance = MediatorLiveData<Double>().apply {
            addSource(monthlyIncome) { income ->
                val expense = monthlyExpense.value ?: 0.0
                value = income - expense
            }
            addSource(monthlyExpense) { expense ->
                val income = monthlyIncome.value ?: 0.0
                value = income - expense
            }
        }
        
        recentTransactions = repository.getTransactionsByMonth(currentMonth, currentYear)
    }
}