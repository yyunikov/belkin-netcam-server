package com.yunikov.belkin

import org.apache.commons.cli.*
import kotlin.properties.Delegates

class CmdOptions(context: Context, options: Options, args: Array<String>) {

    companion object {
        private const val USERNAME = "username"
        private const val PASSWORD = "password"
    }

    private var cmd: CommandLine by Delegates.notNull()

    init {
        val username = Option("u", USERNAME, true, "Belkin NetCam username")
        username.isRequired = true
        options.addOption(username)

        val password = Option("p", PASSWORD, true, "Belkin NetCam password")
        password.isRequired = true
        options.addOption(password)

        val parser = DefaultParser()
        val formatter = HelpFormatter()

        try {
            cmd = parser.parse(options, args)
        } catch (e: ParseException) {
            context.log(this).error("Failed to parse command line arguments")
            formatter.printHelp(Context.APP_NAME, options)
            throw e
        }
    }

    internal fun username(): String {
        return cmd.getOptionValue(USERNAME)
    }

    internal fun password(): String {
        return cmd.getOptionValue(PASSWORD)
    }
}