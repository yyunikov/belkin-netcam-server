package com.yunikov.belkin.page

import com.yunikov.belkin.Context
import okhttp3.*
import org.slf4j.Logger
import kotlin.properties.Delegates

class LoginPage(private val ctx: Context) : Page {

    companion object {
        const val AUTH_URL = "https://netcam.belkin.com/cxs/api/auth/login"
        const val AUTH_EU_URL = "https://netcam-webserver-eu.belkin.com/cxs/api/auth/login"
        const val URL = "https://netcam.belkin.com/app/c/login.html"

        const val SESSION_COOKIE = "SeedonkSession"
    }

    private var log: Logger by Delegates.notNull()

    init {
        log = ctx.log(this)
    }

    override fun url() = URL

    override fun load() {
        ctx.httpClient()
                .newCall(
                        Request.Builder()
                                .url(URL).build()
                ).execute()
    }

    fun authenticate(username: String, password: String, headers: Headers) {
        authRequest(AUTH_URL, username, password, headers)
        authRequest(AUTH_EU_URL, username, password, headers)
    }

    fun sessionCookie() = ctx.httpCookieJar().find(HttpUrl.parse(AUTH_EU_URL)!!, SESSION_COOKIE)?.value()

    private fun authRequest(url: String, username: String, password: String, headers: Headers) {
        val response = ctx.httpClient()
                .newCall(
                        Request.Builder()
                                .url(url)
                                .post(
                                        RequestBody.create(
                                                MediaType.get(Context.Http.MEDIA_TYPE_APPLICATION_JSON),
                                                "{\"username\":$username, \"password\":$password}"
                                        )
                                )
                                .headers(headers)
                                .build()
                ).execute()

        if (!response.isSuccessful) {
            log.error("Failed to authenticate to $url. Status code: ${response.code()}, Body: ${response.body()}")
        } else {
            log.info("Authentication to $url successful")
        }
    }
}