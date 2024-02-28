package mirko.facts

import mirko.mongo.MongoManager
import org.bson.Document

object FactManager {

	fun getRandomFact(): String {
		val maxFacts = MongoManager.getCollectionSize()
		val factNumber = (1..maxFacts).random().toString()

		val searchDocument = Document(factNumber, factNumber)
		val data = MongoManager.getDocument(searchDocument)

		if (data != null) {
			return data.getString("Fact")
		}
		return "No fact found"
	}
}
