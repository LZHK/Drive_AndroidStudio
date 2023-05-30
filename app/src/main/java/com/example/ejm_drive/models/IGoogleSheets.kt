package com.example.ejm_drive.models

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface IGoogleSheets {
    @GET
    fun getPeople(@Url url: String): Call <String>

    @POST("exec")
    fun getStringRequestBody(@Body body: String): Call <String>
}