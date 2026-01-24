package app.islammedia.azkar.Resources

import app.islammedia.azkar.R
import com.esmailsasso.azkar.R
import java.util.*

class AzkarAlmasaa : AzkarInterface {
    override val azkar = arrayOf(R.string.masaa_zekr_1, R.string.masaa_zekr_1_5, R.string.masaa_zekr_2, R.string.masaa_zekr_3, R.string.masaa_zekr_4, R.string.masaa_zekr_5, R.string.masaa_zekr_6, R.string.masaa_zekr_7, R.string.masaa_zekr_8, R.string.masaa_zekr_9, R.string.masaa_zekr_10, R.string.masaa_zekr_11, R.string.masaa_zekr_12, R.string.masaa_zekr_13, R.string.masaa_zekr_14, R.string.masaa_zekr_15, R.string.masaa_zekr_16, R.string.masaa_zekr_17, R.string.masaa_zekr_18, R.string.masaa_zekr_19, R.string.masaa_zekr_20)
    private val repTimes = arrayOf(R.string.masaa_rep_1, R.string.masaa_rep_1_5, R.string.masaa_rep_2, R.string.masaa_rep_3, R.string.masaa_rep_4, R.string.masaa_rep_5, R.string.masaa_rep_6, R.string.masaa_rep_7, R.string.masaa_rep_8, R.string.masaa_rep_9, R.string.masaa_rep_10, R.string.masaa_rep_11, R.string.masaa_rep_12, R.string.masaa_rep_13, R.string.masaa_rep_14, R.string.masaa_rep_15, R.string.masaa_rep_16, R.string.masaa_rep_17, R.string.masaa_rep_18, R.string.masaa_rep_19, R.string.masaa_rep_20)
    private val repTimes_int = arrayOf(1, 1, 3, 1, 1, 1, 4, 1, 3, 7, 1, 1, 3, 3, 1, 1, 1, 100, 10, 3, 10)
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