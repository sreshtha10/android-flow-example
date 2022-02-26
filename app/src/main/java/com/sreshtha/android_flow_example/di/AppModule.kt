package com.sreshtha.android_flow_example.di

import androidx.viewbinding.BuildConfig
import com.sreshtha.android_flow_example.api.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun providesApi():ApiInterface{
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder().also {
                    val logging = HttpLoggingInterceptor()
                    if(BuildConfig.DEBUG){
                        logging.level = HttpLoggingInterceptor.Level.BODY
                    }
                    it.addInterceptor(logging)
                }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://base_url.com")
            .build()
            .create(ApiInterface::class.java)
    }
}