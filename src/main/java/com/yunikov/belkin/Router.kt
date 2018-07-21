package com.yunikov.belkin

import spark.kotlin.Http

class Router(private val http: Http,
             private val belkinNetCam: BelkinNetCam) {

    fun route() {
        http.get("/video") {
            belkinNetCam.updateSession()

            // http stream example https://stackoverflow.com/a/33464711/1889928
            "VIDEO STREAM"
        }
    }
}