package app.islammedia.azkar.Resources

import app.islammedia.azkar.R
import com.esmailsasso.azkar.R
import java.util.*

class AzkarNoum : AzkarInterface {
    override val azkar = arrayOf(R.string.noum_1, R.string.noum_2, R.string.noum_3, R.string.noum_4, R.string.noum_5, R.string.noum_6, R.string.noum_7, R.string.noum_8, R.string.noum_9, R.string.noum_10, R.string.noum_11, R.string.noum_12, R.string.noum_13, R.string.noum_14, R.string.noum_15)
    private val repTimes = arrayOf(R.string.noum_1_rep, R.string.noum_2_rep, R.string.noum_3_rep, R.string.noum_4_rep, R.string.noum_5_rep, R.string.noum_6_rep, R.string.noum_7_rep, R.string.noum_8_rep, R.string.noum_9_rep, R.string.noum_10_rep, R.string.noum_11_rep, R.string.noum_12_rep, R.string.noum_13_rep, R.string.noum_14_rep, R.string.noum_15_rep)
    private val repTimes_int = arrayOf(3, 1, 1, 1, 1, 3, 33, 33, 34, 1, 1, 1, 1, 1, 1)
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