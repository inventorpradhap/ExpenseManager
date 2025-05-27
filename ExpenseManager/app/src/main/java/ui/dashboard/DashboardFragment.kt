class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.monthlyBalance.observe(viewLifecycleOwner) { balance ->
            binding.balanceText.text = getString(R.string.balance_format, balance)
        }
        
        binding.addExpenseButton.setOnClickListener {
            findNavController().navigate(R.id.action_to_addTransactionFragment)
        }
    }
}