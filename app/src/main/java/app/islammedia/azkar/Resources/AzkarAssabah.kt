package app.islammedia.azkar.Resources

import app.islammedia.azkar.R
import com.esmailsasso.azkar.R
import java.util.*

class AzkarAssabah : AzkarInterface {
    override val azkar = arrayOf(R.string.zekr_1, R.string.zekr_2, R.string.zekr_3, R.string.zekr_4, R.string.zekr_5, R.string.zekr_6, R.string.zekr_7, R.string.zekr_8, R.string.zekr_9, R.string.zekr_10, R.string.zekr_11, R.string.zekr_12, R.string.zekr_13, R.string.zekr_14, R.string.zekr_15, R.string.zekr_16, R.string.zekr_17, R.string.zekr_18, R.string.zekr_19, R.string.zekr_20, R.string.zekr_21, R.string.zekr_22, R.string.zekr_23, R.string.zekr_24)
    private val repTimes = arrayOf(R.string.rep_1, R.string.rep_2, R.string.rep_3, R.string.rep_4, R.string.rep_5, R.string.rep_6, R.string.rep_7, R.string.rep_8, R.string.rep_9, R.string.rep_10, R.string.rep_11, R.string.rep_12, R.string.rep_13, R.string.rep_14, R.string.rep_15, R.string.rep_16, R.string.rep_17, R.string.rep_18, R.string.rep_19, R.string.rep_20, R.string.rep_21, R.string.rep_22, R.string.rep_23, R.string.rep_24)
    private val repTimes_int = arrayOf(1, 1, 3, 1, 1, 1, 4, 1, 3, 7, 1, 1, 3, 3, 1, 1, 1, 100, 10, 100, 3, 1, 100, 10)
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