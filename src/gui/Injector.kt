package gui

import java.io.File
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.isAccessible


@Target(AnnotationTarget.PROPERTY)
annotation class Inject

@Target(AnnotationTarget.PROPERTY)
annotation class InjectAdd

class Injector {

    companion object{
        private val map = mutableMapOf<String, String>()
        init{
            val scanner = Scanner(File("di.properties"))

            while (scanner.hasNextLine()) {
                val line = scanner.nextLine()
                val parts = line.split("=")
                map[parts[0]] = parts[1]
            }

            scanner.close()
        }
        fun <T:Any> create(clazz: KClass<T>): T {
            val objectToReturn = clazz.createInstance()
            clazz.declaredMemberProperties.forEach {
                val key = clazz.simpleName+"."+it.name
                if(it.hasAnnotation<Inject>()){
                    it.isAccessible = true
                    val c: KClass<*> = Class.forName(map[key]).kotlin
                    val obj = c.createInstance()
                    (it as KMutableProperty<*>).setter.call(objectToReturn, obj)
                }
                else if(it.hasAnnotation<InjectAdd>()){
                    it.isAccessible=true
                    val types = map[key]!!.split(",")
                    types.forEach{ type ->
                        val c: KClass<*> = Class.forName(type).kotlin
                        (it.call(objectToReturn) as MutableCollection<Any>).add(c.createInstance())
                    }
                }
            }
            return objectToReturn
        }
    }
}