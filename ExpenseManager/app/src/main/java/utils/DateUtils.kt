object DateUtils {
    fun formatDate(date: Date): String {
        return SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(date)
    }

    fun formatMonthYear(date: Date): String {
        return SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(date)
    }

    fun getCurrentMonth(): String {
        return SimpleDateFormat("MM", Locale.getDefault()).format(Date())
    }

    fun getCurrentYear(): String {
        return SimpleDateFormat("yyyy", Locale.getDefault()).format(Date())
    }
}