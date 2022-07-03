package com.example.cointracker.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.webkit.RenderProcessGoneDetail
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.cointracker.R
import com.example.cointracker.adapter.MarketAdapter
import com.example.cointracker.apis.ApiUtilities
import com.example.cointracker.apis.Apiinterface
import com.example.cointracker.databinding.FragmentTopLossGainBinding
import com.example.cointracker.models.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class TopLossGainFragment : Fragment() {



     lateinit var binding: FragmentTopLossGainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentTopLossGainBinding.inflate(layoutInflater)
        getMarketData()


        return binding.root
    }

    private fun getMarketData() {
        val positon=requireArguments().getInt("position")
        lifecycleScope.launch(Dispatchers.IO){
            val res=ApiUtilities.getInstance().create(Apiinterface::class.java).getMarketdata() //list available after this line
            //called
            if (res.body()!=null){
                withContext(Dispatchers.Main){
                    val dataitem=res.body()!!.data.cryptoCurrencyList
                    //for sorting the item
                    Collections.sort(dataitem){
                        o1,o2->(o2.quotes[0].percentChange24h.toInt().
                            compareTo(o1.quotes[0].percentChange24h.toInt()))
                    }

                    binding.spinKitView.visibility= GONE

                    val list=ArrayList<CryptoCurrency>()
                    if (positon==0){
                        list.clear()
                        for (i in 0..9){
                            list.add(dataitem[i])
                        }

                        binding.topGainLoseRecyclerView.adapter=MarketAdapter(requireContext(),list)
                    }
                    else{
                        list.clear()
                        for (i in 0..9){
                            list.add(dataitem[dataitem.size-1-i])
                        }
                        binding.topGainLoseRecyclerView.adapter=MarketAdapter(requireContext(),list)
                    }
                }
            }
        }
    }


}