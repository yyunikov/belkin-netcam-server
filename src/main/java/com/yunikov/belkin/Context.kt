package com.yunikov.belkin

import okhttp3.CookieJar
import okhttp3.OkHttpClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Context(private val httpContext: Context.Http) {

    companion object {
        const val APP_NAME = "belkin-netcam-server"
    }

    class Http(val httpClient: OkHttpClient, val cookieJar: CookieJar) {
        companion object {
            const val MEDIA_TYPE_APPLICATION_JSON = "application/json"
        }
    }

    fun httpClient(): OkHttpClient {
        return httpContext.httpClient
    }

    fun httpCookieJar(): InMemoryCookieJar {
        return httpContext.cookieJar as InMemoryCookieJar
    }

    fun log(any: Any): Logger {
        return LoggerFactory.getLogger(any.javaClass)
    }

    fun log(logger: String): Logger {
        return LoggerFactory.getLogger(logger)
    }
}