package tests

import Student
import University
import jsonElements.JsonObject
import jsonElements.JsonString
import junit.framework.TestCase
import kotlin.test.assertNotEquals

class JsonObjectTest : TestCase() {
    fun test(){
        val jo = JsonObject(Student("Manel", 12345, StudentType.DOCTORATE, University("ISCTE", "Avenida")))
        assertNotEquals("{\n" +
                "\t\"o\":\"other\"\n" +
                "}\n", "")
        assertEquals("other", jo.map["o"]?.getValue())
        assertTrue("", jo.map["o"] is JsonString)
    }
}