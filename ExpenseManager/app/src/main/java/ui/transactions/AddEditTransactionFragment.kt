class AddEditTransactionFragment : Fragment() {
    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TransactionsViewModel
    private var currentTransaction: Transaction? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TransactionsViewModel::class.java)

        setupCategoryDropdown()
        setupForm()
        setupSaveButton()
    }

    private fun setupCategoryDropdown() {
        val categories = resources.getStringArray(R.array.expense_categories).toList()
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, categories)
        binding.categoryInput.setAdapter(adapter)
    }

    private fun setupForm() {
        arguments?.getInt("transactionId")?.let { transactionId ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getTransactionById(transactionId)?.let { transaction ->
                    currentTransaction = transaction
                    binding.amountInput.setText(transaction.amount.toString())
                    binding.noteInput.setText(transaction.note)
                    binding.categoryInput.setText(transaction.category)
                    
                    if (transaction.type == "income") {
                        binding.incomeRadio.isChecked = true
                    } else {
                        binding.expenseRadio.isChecked = true
                    }
                }
            }
        }
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            val amountText = binding.amountInput.text.toString()
            if (amountText.isEmpty()) {
                binding.amountInput.error = "Amount is required"
                return@setOnClickListener
            }

            val amount = amountText.toDouble()
            val note = binding.noteInput.text.toString()
            val category = binding.categoryInput.text.toString()
            val type = if (binding.incomeRadio.isChecked) "income" else "expense"

            val transaction = currentTransaction?.copy(
                amount = amount,
                type = type,
                category = category,
                note = note,
                date = currentTransaction?.date ?: Date()
            ) ?: Transaction(
                amount = amount,
                type = type,
                category = category,
                note = note,
                date = Date()
            )

            viewLifecycleOwner.lifecycleScope.launch {
                if (currentTransaction == null) {
                    viewModel.insert(transaction)
                } else {
                    viewModel.update(transaction)
                }
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}