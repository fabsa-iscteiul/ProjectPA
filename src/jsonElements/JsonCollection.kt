package jsonElements

import Visitor

class JsonCollection(name:String, value: Any, private val insideObject:Boolean = false): JsonElement(name,value) {

    val collection: MutableCollection<JsonElement?> = mutableListOf()

    init {
        if(value is Collection<*>)
            initCollection(value)
    }

    override fun accept(v: Visitor) {
        v.visit(this)
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

    fun getAllStrings():List<String>{
        val list = mutableListOf<String>()
        collection.forEach {
            if(it is JsonString)
                list.add(it.getValue() as String)
            else if(it is JsonObject )
                list.addAll(it.getAllStrings())
            else if(it is JsonCollection)
                list.addAll(it.getAllStrings())
        }
        return list
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