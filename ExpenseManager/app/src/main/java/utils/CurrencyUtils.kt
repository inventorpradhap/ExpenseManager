object CurrencyUtils {
    fun formatCurrency(amount: Double): String {
        return NumberFormat.getCurrencyInstance().format(amount)
    }

    fun parseCurrencyInput(input: String): Double {
        return try {
            input.replace("[^\\d.]".toRegex(), "").toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }
    }
}