package app.islammedia.azkar.Resources

import app.islammedia.azkar.R
import com.esmailsasso.azkar.R
import java.util.*

class AzkarIstiqath : AzkarInterface {
    override val azkar = arrayOf(R.string.ist_1, R.string.ist_2, R.string.ist_3)
    private val repTimes = arrayOf(R.string.ist_1_rep, R.string.ist_2_rep, R.string.ist_3_rep)
    private val repTimes_int = arrayOf(1, 1, 1)
    override fun getZekr(num: Int): Int {
        return azkar[num]
    }

    override fun getRep(num: Int): Int {
        return repTimes[num]
    }

    override fun getreps(): Array<Int> {
        return repTimes
    }

    fun getreps_int(): Array<Int> {
        return repTimes_int
    }

    override val all: List<Array<Int>>
        get() = Arrays.asList(azkar, getreps(), getreps_int())
}