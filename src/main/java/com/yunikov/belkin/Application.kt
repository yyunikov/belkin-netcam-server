package com.yunikov.belkin

import com.yunikov.belkin.page.LoginPage
import com.yunikov.belkin.page.Pages
import okhttp3.OkHttpClient
import org.apache.commons.cli.Options
import spark.kotlin.Http
import spark.kotlin.ignite


fun main(args: Array<String>) {
    val ctx = context()
    ctx.log(Context.APP_NAME).info("Context initialized. Starting server...")

    val options = CmdOptions(ctx, Options(), args)
    val belkinNetcam = belkinNetcam(ctx, options)

    val http: Http = ignite()
            .port(8080)

    Router(http, belkinNetcam)
            .route()
}

private fun context(): Context {
    val cookieJar = InMemoryCookieJar()
    return Context(
            Context.Http(
                    OkHttpClient.Builder()
                            .cookieJar(cookieJar)
                            .build(),
                    cookieJar
            )
    )
}

private fun belkinNetcam(context: Context, options: CmdOptions): BelkinNetCam {
    return BelkinNetCam(
            context,
            options,
            Pages(
                    LoginPage(context)
            )
    )
}