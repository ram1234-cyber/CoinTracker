package com.example.cointracker.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet.GONE
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.cointracker.R
import com.example.cointracker.adapter.TopLossGainPagerAdapter
import com.example.cointracker.adapter.TopMarketAdapter
import com.example.cointracker.apis.ApiUtilities
import com.example.cointracker.apis.Apiinterface
import com.example.cointracker.databinding.FragmentHomefragmentBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import androidx.constraintlayout.widget.ConstraintSet.VISIBLE as AndroidxConstraintlayoutWidgetConstraintSetVISIBLE


class homefragment : Fragment() {


    private lateinit var binding: FragmentHomefragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentHomefragmentBinding.inflate(layoutInflater)
        gettopcurrencyList()
        setTabLayout()
        return binding.root
    }

    private fun setTabLayout() {
        val adapter=TopLossGainPagerAdapter(this)
        binding.contentViewPager.adapter=adapter
        binding.contentViewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position==0){
                binding.topGainIndicator.visibility=VISIBLE
                binding.topLoseIndicator.visibility= View.GONE}
                else{
                    binding.topGainIndicator.visibility= View.GONE
                    binding.topLoseIndicator.visibility= VISIBLE
                }

            }
        })

        TabLayoutMediator(binding.tabLayout,binding.contentViewPager){
            tab,position->
            var title=if (position==0){
                "Top Gainer"
            }else{
                "Top Loser"
            }
            tab.text=title
        }.attach()
    }

    private fun gettopcurrencyList() {
        lifecycleScope.launch(Dispatchers.IO) {
           val res=ApiUtilities.getInstance().create(Apiinterface::class.java).getMarketdata()

            withContext(Dispatchers.Main){
                binding.topCurrencyRecyclerView.adapter=TopMarketAdapter(requireContext(),res.body()!!.data.cryptoCurrencyList)
            }

        }
    }


}