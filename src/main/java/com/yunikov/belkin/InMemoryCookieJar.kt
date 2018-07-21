package com.yunikov.belkin

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class InMemoryCookieJar: CookieJar {

    private var cookiesMap = mutableMapOf<HttpUrl, List<Cookie>>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookiesMap[url] = cookies
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return if (!cookiesMap.isEmpty() && cookiesMap.containsKey(url)) cookiesMap[url]!! else emptyList()
    }

    fun find(url: HttpUrl, cookieName: String): Cookie? {
        return cookiesMap[url]?.first { cookie -> cookie.name() == cookieName }
    }
}