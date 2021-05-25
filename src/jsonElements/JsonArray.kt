package jsonElements

import visitor.SerializeVisitor
import visitor.Visitor

class JsonArray(value: Any, name:String=""): JsonElement(value,name) {

    private val collection : MutableCollection<Any?> = mutableListOf()

    init {
        if(value is Collection<*>)
            initCollection(value)
    }

    override fun accept(v: Visitor) {
        v.visit(this)
        if(v is SerializeVisitor)
            v.numObj++
        collection.forEach {
            if(it is JsonElement)
                it?.accept(v)
            else if(v is SerializeVisitor) {
                if (v.stringToReturn.endsWith("[\n"))
                    v.stringToReturn += "${v.addTabs()}\t$it,"
                else
                    v.stringToReturn += "$it,"
            }
        }
        if(v is SerializeVisitor) {
            v.stringToReturn = v.stringToReturn.removeSuffix(",")
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
            if(it != null ) {
                if (it is String || it is Number || it is Boolean || it is Enum<*>)
                    collection.add(it)
                else
                    collection.add(mapTypeToJson(it))
            }
            else
                collection.add(null)
        }
    }


    private fun mapTypeToJson(valueToType: Any): JsonElement {
        return when (valueToType) {
            is Collection<*> -> JsonArray(valueToType)
            else -> JsonObject(valueToType)
        }
    }

    fun containsObject() : Boolean {
      collection.forEach {
          if(it is JsonElement)
              return true
      }
      return false
    }

    fun nElements() = collection.size
}