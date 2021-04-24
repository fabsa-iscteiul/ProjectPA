package jsonElements

import Visitor
import java.lang.Exception
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

class JsonObject(name:String, value: Any, private val count:Int =0) : JsonElement(name, value) {

    val map = mutableMapOf<String, JsonElement?>();
    var numberOfObjects : Int = 0

    init {
        if(value !is String && value !is Int && value !is Boolean && value !is Enum<*> && value !is Collection<*> && value !is Map<*,*>) {
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
        var s = if(count==0) "{\n" else "${super.getObjectName()}:{\n"
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
            val valueToType = it.call(super.getValue())
            if(valueToType != null)
                map[it.name] = mapTypeToJson(it,valueToType)
            else
                map[it.name]= null
        }
    }

    private fun mapTypeToJson(it: KProperty1<Any,*>, valueToType: Any) : JsonElement{
        lateinit var valueToAdd : JsonElement
        when(valueToType){
            is String -> valueToAdd = JsonString(it.name, valueToType)
            is Int -> valueToAdd = JsonInteger(it.name, valueToType)
            is Boolean -> valueToAdd = JsonBoolean(it.name, valueToType)
            is Collection<*> -> valueToAdd = JsonCollection(it.name, valueToType, true)
            is Enum<*> -> valueToAdd = JsonEnum(it.name, valueToType)
            else ->{
                        valueToAdd= JsonObject(it.name, valueToType, count+1)
                        numberOfObjects++
                    }
        }
        return valueToAdd

    }

    private fun mapTypeToJson(name:String, valueToType: Any) : JsonElement{
        lateinit var valueToAdd : JsonElement
        when(valueToType){
            is String -> valueToAdd = JsonString(name, valueToType)
            is Int -> valueToAdd = JsonInteger(name, valueToType)
            is Boolean -> valueToAdd = JsonBoolean(name, valueToType)
            is Collection<*> -> valueToAdd = JsonCollection(name, valueToType)
            is Enum<*> -> valueToAdd = JsonEnum(name, valueToType)
            else ->{
                        valueToAdd = JsonObject(name, valueToType, count + 1)
                        numberOfObjects++
                    }
        }
        return valueToAdd

    }

    override fun accept(v: Visitor){
        v.visit(this)
    }

}