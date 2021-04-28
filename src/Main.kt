import jsonElements.JsonObject

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

fun main(){
    val v = JsonVisitor(Operation.SERIALIZE)
    val o = OtherOther("other")
    val jo =JsonObject("val", TestObject(true, ObjectTest("test",OtherObject("U",o))))
    jo.accept(v)
    v.setOperation(Operation.GETALLSTRINGS)
    jo.accept(v)
}

