import jsonElements.JsonObject
import visitor.GetVisitor
import visitor.SerializeVisitor
import visitor.Type

class TestObject(val a : Boolean, val b: ObjectTest){}

class TestCollect(val list: MutableList<Any>) {}

class OtherOther(val o:String)

class OtherObject(val u : String, val oo: OtherOther,@Id("newID") val enum: MyEnum = MyEnum.CENTIMETER){}

class ObjectTest(val t:String, val o:OtherObject){}

enum class MyEnum(private val unit:String, private val meters:Double){
    KILOMETER("km", 1000.0),
    METER("meters", 1.0),
    CENTIMETER("cm", 0.01),
    MILLIMETER("mm", 0.001);

}
data class Date(val year: Int, val month: Int, val day: Int)

fun main(){
    val serializeVisitor = SerializeVisitor()
    val d = Date(2020,1, 20)
    val o = OtherOther("other")
    val jo =JsonObject( TestObject(true, ObjectTest("test",OtherObject("U",o))))
    jo.accept(serializeVisitor)
    val getVisitor = GetVisitor(Type.STRING)
    jo.accept(getVisitor)
    println(getVisitor.list)
}

