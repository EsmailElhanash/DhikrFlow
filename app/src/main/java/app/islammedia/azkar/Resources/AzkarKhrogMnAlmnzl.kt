package app.islammedia.azkar.Resources

import app.islammedia.azkar.R
import com.esmailsasso.azkar.R
import java.util.*

class AzkarKhrogMnAlmnzl : AzkarInterface {
    override val azkar = arrayOf(R.string.kh_mn_mnzl_1, R.string.kh_mn_mnzl_2)
    private val repTimes = arrayOf(R.string.kh_mn_mnzl_1_rep, R.string.kh_mn_mnzl_2_rep)
    private val repTimes_int = arrayOf(1, 1)
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