package com.example.menuapplication

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface EndPoint {
    @Multipart
    @POST("addimg")
    suspend fun addimg(@Part image:MultipartBody.Part ) : Response<String>
}