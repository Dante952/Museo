package com.example.museo.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
interface ApiService {

    @GET("api/pinturas")
    suspend fun getPinturas(): List<Pintura>

    @POST("api/pinturas")
    suspend fun addPintura(@Body pintura: Pintura): Call<Pintura>
}
object RetrofitClient {
    private const val BASE_URL = "https://us-central1-pinturas-8bf11.cloudfunctions.net/app/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
data class Pintura(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val autor: String,
    val imagenURL: String,
    val audioURL: String
)
