package mirko.extensions

import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.coalescingDefaultingString
import com.kotlindiscord.kord.extensions.commands.converters.impl.defaultingString
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.chatCommand
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.utils.respond
import mirko.SERVER_ID
import mirko.youtube.YouTubeManager

class MirkoSearchExtension : Extension() {
	override val name: String = "mirkosearch"

	override suspend fun setup() {
		chatCommand(::SearchArgs) {
			name = "mirkosearch"
			description = "Frage Mirko Drotschmann nach einem Video von ihm."

			check { failIf(event.message.author == null) }

			action {
				val kord = this@MirkoSearchExtension.kord
				message.respond(YouTubeManager.searchVideo(arguments.searchkey))
			}
		}

		publicSlashCommand(::SearchSlashArgs) {
			name = "mirkosearch"
			description = "Frage Mirko Drotschmann nach einem Video von ihm."

			guild(SERVER_ID)

			action {
				val kord = this@MirkoSearchExtension.kord

				respond {
					content = YouTubeManager.searchVideo(arguments.searchkey)
				}
			}
		}
	}

	inner class SearchArgs : Arguments() {
		val searchkey by coalescingDefaultingString {
			name = "search key"

			defaultValue = "putin"
			description = "Das Wort nach dem auf Mirko's Kanal gesucht werden soll."
		}
	}

	inner class SearchSlashArgs : Arguments() {
		val searchkey by defaultingString {
			name = "searchkey"

			defaultValue = "putin"
			description = "Der Suchbegruff nach dem auf dem Kanal von Mirko gesucht werden soll."
		}
	}
}
