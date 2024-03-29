/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package mirko

import com.kotlindiscord.kord.extensions.ExtensibleBot
import com.kotlindiscord.kord.extensions.utils.env
import dev.kord.common.entity.Snowflake
import mirko.extensions.HistoryExtension
import mirko.extensions.MirkoSearchExtension
import mirko.mongo.MongoManager

val SERVER_ID = Snowflake(
	env("SERVER_ID").toLong()  // Get the test server ID from the env vars or a .env file
)

private val TOKEN = env("TOKEN")   // Get the bot' token from the env vars or a .env file

suspend fun main() {
	MongoManager.connect(env("MONGO_COLLECTION_NAME"))
	val bot = ExtensibleBot(TOKEN) {
		chatCommands {
			defaultPrefix = "!"
			enabled = true

			prefix { default ->
				if (guildId == SERVER_ID) {
					// For the test server, we use ! as the command prefix
					"!"
				} else {
					// For other servers, we use the configured default prefix
					default
				}
			}
		}

		extensions {
			add(::HistoryExtension)
			add(::MirkoSearchExtension)
		}
	}

	bot.start()
}
