import gui.Gui
import jsonElements.JsonArray
import jsonElements.JsonBoolean
import jsonElements.JsonObject
import jsonElements.JsonString
import visitor.SerializeVisitor


enum class StudentType{
    BACHELOR,
    MASTER,
    DOCTORATE,

}

data class University(val uniName: String, val location: String)

data class Student(@Ignore val studentName: String, @Id("studentNumber") val number: Number, val studentType: StudentType, val university: University)


fun main(){
    val jObj = JsonObject(Student("Francisco", 82608, StudentType.MASTER, University("ISCTE", "Avenida das Forças Armadas")), "Student")
    val gui = Injector.create(Gui::class)
    gui.open(jObj)
}

