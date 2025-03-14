package com.monzo.headlines.common

import com.monzo.headlines.ArticlesViewModel
import com.monzo.headlines.data.ArticlesRepository
import com.monzo.headlines.data.GuardianService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Module that creates dependencies manually
 */
object AppModule {
    /**
     * If key doesn't work obtain new one in https://bonobo.capi.gutools.co.uk/register/developer
     */
    private const val GUARDIAN_API_KEY = "1e51dc7e-7a4d-4874-ae32-6efc9412a6a6"
    private const val BASE_URL = "https://content.guardianapis.com"

    fun articlesViewModel(): ArticlesViewModel = ArticlesViewModel(
        ArticlesRepository(createGuardianService())
    )

    private fun createGuardianService(): GuardianService {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(createOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GuardianService::class.java)
    }

    private fun createOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(createAuthInterceptor())
        .addInterceptor(
            HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        )
        .build()

    private fun createAuthInterceptor() = Interceptor { chain ->
        val original = chain.request()
        val hb = original.headers.newBuilder()
        hb.add("api-key", GUARDIAN_API_KEY)
        chain.proceed(original.newBuilder().headers(hb.build()).build())
    }
}
