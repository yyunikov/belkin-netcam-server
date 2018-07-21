package com.yunikov.belkin

import com.yunikov.belkin.page.Pages
import okhttp3.Headers
import org.slf4j.Logger
import kotlin.properties.Delegates

class BelkinNetCam(ctx: Context,
                   private val cmdOptions: CmdOptions,
                   private val pages: Pages) {

    private val headers = mapOf (
        "AntiCSRF" to "AntiCSRF",
        "Host" to "netcam.belkin.com",
        "Origin" to "https://netcam.belkin.com",
        "Referer" to pages.loginPage.url(),
        "User-Agent" to "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36"
    )

    private var log: Logger by Delegates.notNull()

    init {
        log = ctx.log(this)
    }

    fun updateSession() {
        val loginPage = pages.loginPage

        loginPage.load()
        loginPage.authenticate(cmdOptions.username(), cmdOptions.password(), Headers.of(headers))

        val sessionCookie = loginPage.sessionCookie()
        if (sessionCookie.isNullOrEmpty()) {
            throw IllegalStateException("Empty session cookie after successful authentication")
        }

        log.info("Session cookie $sessionCookie")

        // TODO example of video stream url
        // https://netcam-webserver-eu.belkin.com/cxs/api/devices/292109/cam/liveStream?type%3Dvideo%2Fx-flv%26viewerSessionId%3D8c14a8de6ef5403931ed73f8baf7ab4c
    }
}