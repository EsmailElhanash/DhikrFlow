package app.islammedia.azkar.Resources

interface AzkarInterface {
    fun getZekr(num: Int): Int
    fun getRep(num: Int): Int
    val azkar: Array<Int>
    fun getreps(): Array<Int>
    val all: List<Array<Int>>
}