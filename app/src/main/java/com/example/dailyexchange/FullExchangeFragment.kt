package com.example.dailyexchange

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyexchange.databinding.FragmentFullExchangeBinding
import com.example.dailyexchange.network.ExchangeApi
import com.example.dailyexchange.network.ForeignExchange
import com.example.dailyexchange.network.SingleForeignExchange
import com.example.dailyexchange.viewmodels.ExchangeViewModel
import com.example.dailyexchange.viewmodels.ExchangeViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 * Use the [FullExchangeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FullExchangeFragment : Fragment() {

    private var _binding: FragmentFullExchangeBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: ExchangeAdapter

    //用來存所有貨幣、匯率、時間
    private lateinit var allExchanges:List<SingleForeignExchange>

    private val viewModel: ExchangeViewModel by activityViewModels {
        ExchangeViewModelFactory(
            (activity?.application as ExchangeApplication).database.exchangeDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFullExchangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    //  done use viewModels' insert fun to insert data into room, and also need to check dependency
    //  done GlobalScope.launch{allExchange} <- 先將API資料導入，然後再寫一個coroutine insert into database(可同步前面)，最後再透過coroutine方式將viewModel's getAll() insert into adapter
    //  done adapter改成接資料庫資料
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        GlobalScope.launch {
            // 獲取ForeignExchange並且轉成List<SingleForeignExchange>  然後賦值給 allExchanges 去丟給adapter
            allExchanges = getAllExchange()
            // 使用協程的方式等待 getAllExchange() 完成
            withContext(Dispatchers.Main) {
//                binding.recyclerView.adapter = ExchangeAdapter(allExchanges)
                for (i in allExchanges) {
                    viewModel.addExchange(i.currency_name, i.exrate, i.time)
                }
                adapter = ExchangeAdapter(viewModel.getAll())
                recyclerView.adapter = adapter
//                Log.d(tag, "allExchange: ${allExchanges[0].currency_name}")
            }
        }
    }

    //  done This fun must move to FullExchangeFragment
    private suspend fun getAllExchange(): List<SingleForeignExchange> = coroutineScope {
        val listResult: ForeignExchange = try {
            ExchangeApi.retrofitService.getAllExchange()
        } catch (e: Exception) {
            throw e
        }
        foreignExchangetoSingleForeignExchange(listResult)
    }
    // 先轉singleForeignExchange 再放用adapter 想不到方法只能手動新增!!
    private fun foreignExchangetoSingleForeignExchange(foreignExchange: ForeignExchange):List<SingleForeignExchange>{
        var result = mutableListOf<SingleForeignExchange>()
        var currency1:SingleForeignExchange=SingleForeignExchange("USDCNH",foreignExchange.USDCNH.exrate,foreignExchange.USDCNH.time)
        var currency2:SingleForeignExchange=SingleForeignExchange("USDFKP",foreignExchange.USDFKP.exrate,foreignExchange.USDFKP.time)
        var currency3:SingleForeignExchange=SingleForeignExchange("USDITL",foreignExchange.USDITL.exrate,foreignExchange.USDITL.time)
        var currency4 = SingleForeignExchange("USDLAK", foreignExchange.USDLAK.exrate, foreignExchange.USDLAK.time)
        var currency5 = SingleForeignExchange("USDZMW", foreignExchange.USDZMW.exrate, foreignExchange.USDZMW.time)
        var currency6 = SingleForeignExchange("USDAOA", foreignExchange.USDAOA.exrate, foreignExchange.USDAOA.time)
        var currency7 = SingleForeignExchange("USDTWD", foreignExchange.USDTWD.exrate, foreignExchange.USDTWD.time)
        result.add(currency1)
        result.add(currency2)
        result.add(currency3)
        result.add(currency4)
        result.add(currency5)
        result.add(currency6)
        result.add(currency7)
        return  result.toList()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}