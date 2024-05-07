package com.example.dailyexchange

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import com.example.dailyexchange.databinding.FragmentExchangeCalculatorBinding
import com.example.dailyexchange.viewmodels.ExchangeViewModel
import com.example.dailyexchange.viewmodels.ExchangeViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [ExchangeCalculatorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExchangeCalculatorFragment : Fragment() {

    private var _binding: FragmentExchangeCalculatorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExchangeViewModel by activityViewModels {
        ExchangeViewModelFactory(
            (activity?.application as ExchangeApplication).database.exchangeDao()
        )
    }
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExchangeCalculatorBinding.inflate(inflater, container, false)

        spinner = binding.spinner
        val spinnerAdapter = ArrayAdapter.createFromResource(requireContext(),
            R.array.Exchange_Name_Array,
            android.R.layout.simple_spinner_item)

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                //利用viewModel處理資料
                viewModel.convertCurrencyName=selectedItem
                Log.d(tag,"selectedItem is $selectedItem")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case when nothing is selected
                viewModel.convertCurrencyName="USDCNH"
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calculateButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) { // 使用MainDispatcher
                val inputNumber = binding.textInputLayout.editText?.text.toString()
                val result = viewModel.calculatorRate(inputNumber.toFloat())
                binding.textView.text = "換算值：$result"
            }
        }
    }
}