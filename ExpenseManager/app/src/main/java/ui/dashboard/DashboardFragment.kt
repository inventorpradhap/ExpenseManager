class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DashboardViewModel
    private lateinit var adapter: TransactionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        adapter = TransactionsAdapter { transaction ->
            // Handle transaction click
        }

        binding.recentTransactionsRecyclerView.adapter = adapter
        binding.recentTransactionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.monthlyBalance.observe(viewLifecycleOwner) { balance ->
            binding.balanceText.text = formatCurrency(balance)
        }

        viewModel.recentTransactions.observe(viewLifecycleOwner) { transactions ->
            adapter.submitList(transactions.take(5))
        }

        binding.seeAllTransactions.setOnClickListener {
            findNavController().navigate(R.id.action_to_transactionsFragment)
        }
    }

    private fun formatCurrency(amount: Double): String {
        return NumberFormat.getCurrencyInstance().format(amount)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}