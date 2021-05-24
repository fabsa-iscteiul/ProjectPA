package jsonElements

import visitor.SerializeVisitor
import visitor.Visitor

class JsonArray(value: Any, name:String=""): JsonElement(value,name) {

    private val collection: MutableCollection<JsonElement?> = mutableListOf()

    init {
        if(value is Collection<*>)
            initCollection(value)
    }

    override fun accept(v: Visitor) {
        v.visit(this)
        if(v is SerializeVisitor)
            v.numObj++
        collection.forEach {
            it?.accept(v)
        }
        if(v is SerializeVisitor) {
            v.numObj--
            v.stringToReturn = v.stringToReturn.removeSuffix(",\n") +"\n"
            v.stringToReturn += "${v.addTabs()}]\n"
            v.numObj--
            v.stringToReturn += "${v.addTabs()}}"
            v.stringToReturn+=if(v.numObj == 0) "\n" else ",\n"
        }
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
            is Number -> JsonNumber(valueToType )
            is Boolean -> JsonBoolean(valueToType)
            is Collection<*> -> JsonArray(valueToType)
            is Enum<*> -> JsonEnum(valueToType)
            else -> JsonObject(valueToType)
        }
    }


    fun nElements() = collection.size
}