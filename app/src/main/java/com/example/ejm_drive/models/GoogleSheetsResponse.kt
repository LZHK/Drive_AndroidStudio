package com.example.ejm_drive.models

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class GoogleSheetsResponse {
    private var retrofit: Retrofit? = null

    fun getClientGetMethod(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

}