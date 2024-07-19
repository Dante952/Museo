package com.example.museo.viewModel

import com.example.museo.model.Painting
import retrofit2.Call
import retrofit2.http.GET

interface OdooApiService {
    @GET("paintings")
    fun getPaintings(): Call<List<Painting>>
}
