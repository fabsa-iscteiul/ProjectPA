import jsonElements.JsonCollection
import jsonElements.JsonEnum
import jsonElements.JsonObject
import jsonElements.JsonString

class TestObject(val a : Boolean, val b: ObjectTest){}

class TestCollect(val list: MutableList<Int>) {}

class OtherOther(val o:String)

class OtherObject(val u : String, val oo: OtherOther, val enum: MyEnum = MyEnum.CENTIMETER){}

class ObjectTest(val t:String, val o:OtherObject){}

enum class MyEnum(private val unit:String, private val meters:Double){
    KILOMETER("km", 1000.0),
    MILE("miles", 1609.34),
    METER("meters", 1.0),
    INCH("inches", 0.0254),
    CENTIMETER("cm", 0.01),
    MILLIMETER("mm", 0.001);

}

fun main(){
    val v = JsonVisitor(Operation.SERIALIZE)
    //val jo = JsonObject("ola", TestCollect( mutableListOf(1,2,3,4)))
    val jo =JsonObject("val", TestObject(true, ObjectTest("test",OtherObject("U",OtherOther("other")))))
    jo.accept(v)
    v.setOperation(Operation.GETALLSTRINGS)
    jo.accept(v)
}

