package inc.evil.playground

class SomeService(val anotherService: AnotherService) {

    fun performAction(): String = anotherService.performOtherAction()
    fun performComplexAction(value: Int = 0): String {
        var performOtherAction = anotherService.performOtherAction()
        repeat(value) { performOtherAction += performOtherAction }
        return performOtherAction
    }
    fun getSomeData() : Person = Person("Mike", 26)
    fun getSomeListData() : List<Person> = listOf(Person("Mike", 26))
}