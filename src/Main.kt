import gui.Gui
import jsonElements.JsonArray
import jsonElements.JsonBoolean
import jsonElements.JsonObject
import jsonElements.JsonString
import visitor.SerializeVisitor

class TestObject(val file1 : Boolean, val dir1: ObjectTest){}

class OtherOther(val file2:String)

class OtherObject(val file1 : String, val dir3: OtherOther,@Id("newID") val enum: MyEnum = MyEnum.CENTIMETER){}

class ObjectTest(val t:String, val dir2:OtherObject){}

enum class MyEnum(private val unit:String, private val meters:Double){
    KILOMETER("km", 1000.0),
    METER("meters", 1.0),
    CENTIMETER("cm", 0.01),
    MILLIMETER("mm", 0.001);

}

class TestCollect(val list : MutableCollection<Any>,val list1 : MutableCollection<Any>,val list2 : String)

fun main(){
    val o = OtherOther("other")
    val jo =JsonObject( TestObject(true, ObjectTest("test",OtherObject("U",o))))

    val js = JsonString("asd", "name")
    val ja = JsonArray(mutableListOf(1,2,3), "s")
    val joo = JsonObject(TestCollect(mutableListOf(o,o,o),mutableListOf(o,o,o),"llll"), "src")
    val gui = Injector.create(Gui::class)
    gui.open(jo)
}

