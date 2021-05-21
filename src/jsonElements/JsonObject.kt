package jsonElements

import Id
import Ignore
import visitor.Visitor
import java.lang.Exception
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

class JsonObject( value: Any,name:String="", private val count:Int =0) : JsonElement(value,name) {

    val map = mutableMapOf<String, JsonElement?>()
    private var numberOfObjects : Int = 0

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

    override fun serialize(): String {
        var s = if(count==0) "{\n" else "${super.name}:{\n"
        map.values.forEach{
            if(count == 0)
                s+="\t"+it!!.serialize()+",\n"
            else {
                for (i in 0..count)
                    s += "\t"
                s+=it!!.serialize()+",\n"
            }
        }
        s =s.removeSuffix(",\n")
        if(count == 0) {
            s+="\n"
            while(numberOfObjects > 0){
                for (i in 0 until numberOfObjects)
                    s += "\t"
                numberOfObjects--
                s += "}\n"
            }
            s += "}\n"
        }
        else if(count>=2) {
            s+="\n"
            for (i in 0 until count)
                s+="\t"
            s+="}"
        }
        return s
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
            else ->{
                        valueToAdd= JsonObject(valueToType,varName, count+1)
                        numberOfObjects++
                    }
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
        map.values.forEach {
            it?.accept(v)
        }
    }

    fun nElements() = map.size

}