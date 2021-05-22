package jsonElements

import Id
import Ignore
import visitor.SerializeVisitor
import visitor.Visitor
import java.lang.Exception
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

class JsonObject( value: Any,name:String="") : JsonElement(value,name) {

    val map = mutableMapOf<String, JsonElement?>()

    init {
        if(value !is String && value !is Number && value !is Boolean && value !is Enum<*> && value !is Collection<*> && value !is Map<*,*>) {
            val obj = value::class as KClass<Any>
            buildObject(obj)
        }
        else if(value is Map<*, *>){
            value.keys.forEach {
                val valueToAdd = value[it]
                if(valueToAdd != null)
                    map[""+it] = mapTypeToJson(""+it,valueToAdd)
                else
                    map[""+it] = null
            }
        }
        else
            throw Exception("Insert a valid value for the object")
    }

    private fun buildObject(obj : KClass<Any>){
        obj.declaredMemberProperties.forEach {
            if(!it.hasAnnotation<Ignore>()) {
                val valueToType = it.call(super.getValue())
                val varName = if(it.hasAnnotation<Id>()) it.findAnnotation<Id>()!!.newId else it.name
                if (valueToType != null)
                    map[varName] = mapTypeToJson(varName, valueToType)
                else
                    map[varName] = null
            }
        }
    }

    private fun mapTypeToJson(varName: String , valueToType: Any) : JsonElement{
        lateinit var valueToAdd : JsonElement
        when(valueToType){
            is String -> valueToAdd = JsonString(valueToType,varName)
            is Number -> valueToAdd = JsonNumber(valueToType,varName)
            is Boolean -> valueToAdd = JsonBoolean(valueToType,varName)
            is Collection<*> -> valueToAdd = JsonArray(valueToType,varName, true)
            is Enum<*> -> valueToAdd = JsonEnum(valueToType,varName)
            else -> valueToAdd= JsonObject(valueToType,varName)

        }
        return valueToAdd

    }

    fun getAllStrings():List<String>{
        val list = mutableListOf<String>()
        map.values.forEach {
            if(it is JsonString)
                list.add(it.getValue() as String)
            else if(it is JsonObject )
                list.addAll(it.getAllStrings())
            else if(it is JsonArray)
                list.addAll(it.getAllStrings())
        }
        return list
    }

    override fun accept(v: Visitor){
        v.visit(this)
        if(v is SerializeVisitor)
            v.numObj++
        map.values.forEach {
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

    fun nElements() = map.size

}