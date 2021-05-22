import gui.Gui
import jsonElements.JsonBoolean
import jsonElements.JsonObject
import jsonElements.JsonString
import visitor.SerializeVisitor

class TestObject(val file1 : Boolean, val dir1: ObjectTest){}

class TestCollect(val list: MutableList<Any>) {}

class OtherOther(val file2:String)

class OtherObject(val file1 : String, val dir3: OtherOther,@Id("newID") val enum: MyEnum = MyEnum.CENTIMETER){}

class ObjectTest(val t:String, val dir2:OtherObject){}

enum class MyEnum(private val unit:String, private val meters:Double){
    KILOMETER("km", 1000.0),
    METER("meters", 1.0),
    CENTIMETER("cm", 0.01),
    MILLIMETER("mm", 0.001);

}

fun main(){
    val o = OtherOther("other")
    val jo =JsonObject( TestObject(true, ObjectTest("test",OtherObject("U",o))),"src")

    val gui = Injector.create(Gui::class)
    gui.open(jo)
}

