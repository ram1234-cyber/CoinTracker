package com.example.cointracker.apis

import com.example.cointracker.models.MarketModel
import retrofit2.Response
import retrofit2.http.GET

interface Apiinterface {

    @GET("data-api/v3/cryptocurrency/listing?start=1&limit=500")
    suspend  fun getMarketdata():Response<MarketModel>


}