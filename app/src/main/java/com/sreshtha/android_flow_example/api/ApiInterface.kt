package com.sreshtha.android_flow_example.api

import com.sreshtha.android_flow_example.api.response.LoginResponse
import com.sreshtha.android_flow_example.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    //user login
    @POST("base_url/login_url")
    suspend fun login(
        @Body userModel: User
    )     : Response<LoginResponse>

}