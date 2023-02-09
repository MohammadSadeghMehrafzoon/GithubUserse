package ir.misterdeveloper.githubusers.util

import ir.misterdeveloper.githubusers.model.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiServiceSingleton {

    var apiService: ApiService? = null
        get() {

            if (field == null) {


                val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                apiService = retrofit.create(ApiService::class.java)


            }

            return field
        }
}