package com.example.leboncoinalbumlibrary.di.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class UserAgentInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Check if request has image/png
        val isImageRequest = originalRequest.url.toString().endsWith("png")

        Log.d("TEST", "Original request ${originalRequest.url}")

        return if (isImageRequest) {
            Log.d("TEST", "got in ${originalRequest.url}")
            // Add User-Agent header for image requests
            val newRequest = originalRequest.newBuilder()
                .header("User-Agent", "LeBonCoin-Album-App")
                .build()
            chain.proceed(newRequest)
        } else {
            // Proceed with the original request for non-image requests
            chain.proceed(originalRequest)
        }
    }
}