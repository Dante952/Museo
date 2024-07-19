package com.example.museo.viewModel

class PaintingRepository {
    private val api = RetrofitInstance.api

    fun getPaintings() = api.getPaintings()
}