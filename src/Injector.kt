import java.io.File
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.isAccessible

class Injector {

    companion object{
        private val map = mutableMapOf<String, String>()
        init{
            val scanner = Scanner(File("di.properties"))

            while (scanner.hasNextLine()) {
                val line = scanner.nextLine()
                val parts = line.split("=")
                // Only the first one counts if it has multiple ones
                if(map[parts[0]] == "Gui.plugin") {
                    map[parts[0]] = if (parts[1].contains(","))
                        parts[1].split(",")[0]
                    else parts[1]
                }
                else map[parts[0]] =  parts[1]

            }

            scanner.close()
        }
        fun <T:Any> create(clazz: KClass<T>): T {
            val objectToReturn = clazz.createInstance()
            clazz.declaredMemberProperties.forEach {
                val key = clazz.simpleName+"."+it.name
                if(it.hasAnnotation<Inject>()){
                    it.isAccessible = true
                    var c: KClass<*> = if(map[key] != null)
                                            Class.forName(map[key]).kotlin
                                        else
                                            Class.forName("plugins.DefaultPlugin").kotlin
                    val obj = c.createInstance()
                    (it as KMutableProperty<*>).setter.call(objectToReturn, obj)
                }
                else if(it.hasAnnotation<InjectAdd>()){
                    it.isAccessible=true
                    val types = map[key]?.split(",")
                    types?.forEach{ type ->
                        val c: KClass<*> = Class.forName(type).kotlin
                        (it.call(objectToReturn) as MutableCollection<Any>).add(c.createInstance())
                    }
                }
            }
            return objectToReturn
        }
    }
}