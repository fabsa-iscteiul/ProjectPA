package jsonElements

import visitor.Visitor

class JsonArray(value: Any, name:String="", private val insideObject:Boolean = false): JsonElement(value,name) {

    private val collection: MutableCollection<JsonElement?> = mutableListOf()

    init {
        if(value is Collection<*>)
            initCollection(value)
    }

    override fun accept(v: Visitor) {
        v.visit(this)
    }

    override fun serialize(): String {
        var stringToReturn =if(insideObject)"${super.name}:[" else "["
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
            else if(it is JsonArray)
                list.addAll(it.getAllStrings())
        }
        return list
    }

    private fun mapTypeToJson(valueToType: Any): JsonElement {
        return when (valueToType) {
            is String -> JsonString( valueToType)
            is Number -> JsonNumber(valueToType)
            is Boolean -> JsonBoolean(valueToType)
            is Collection<*> -> JsonArray(valueToType)
            is Enum<*> -> JsonEnum(valueToType)
            else -> JsonObject(valueToType)
        }
    }
}