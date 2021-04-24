package jsonElements

import Visitor

class JsonCollection(name:String, value: Any, val insideObject:Boolean = false): JsonElement(name,value) {

    private val collection: MutableCollection<JsonElement?> = mutableListOf()

    init {
        if(value is Collection<*>)
            initCollection(value)
    }

    override fun accept(v: Visitor) {

    }

    override fun serialize(): String {
        var stringToReturn =if(insideObject)"${super.getObjectName()}:[" else "["
        collection.forEach {
            stringToReturn+= it?.serialize()+","
        }
        stringToReturn=stringToReturn.removeSuffix(",")
        return "$stringToReturn]"
    }

    private fun initCollection(col: Collection<*>){
        col.forEach {
            if(it != null )
                collection.add(mapTypeToJson(it))
            else
                collection.add(null)
        }

    }

    private fun mapTypeToJson(valueToType: Any): JsonElement {
        return when (valueToType) {
            is String -> JsonString("", valueToType)
            is Int -> JsonInteger("", valueToType)
            is Boolean -> JsonBoolean("", valueToType)
            is Collection<*> -> JsonCollection("", valueToType)
            is Enum<*> -> JsonEnum("", valueToType)
            else -> JsonObject("", valueToType)
        }
    }
}