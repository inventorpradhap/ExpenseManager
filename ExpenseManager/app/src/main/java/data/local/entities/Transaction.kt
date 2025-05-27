@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val type: String, // "income" or "expense"
    val category: String,
    val note: String?,
    val date: Date
)