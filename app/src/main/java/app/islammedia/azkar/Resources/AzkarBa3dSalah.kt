package app.islammedia.azkar.Resources

import app.islammedia.azkar.R
import com.esmailsasso.azkar.R
import java.util.*

class AzkarBa3dSalah : AzkarInterface {
    override val azkar = arrayOf(R.string.ba3d_salah_1, R.string.ba3d_salah_2, R.string.ba3d_salah_3, R.string.ba3d_salah_4, R.string.ba3d_salah_5, R.string.ba3d_salah_6, R.string.ba3d_salah_7, R.string.ba3d_salah_8, R.string.ba3d_salah_9, R.string.ba3d_salah_10, R.string.ba3d_salah_11)
    private val repTimes = arrayOf(R.string.ba3d_salah_rep_1, R.string.ba3d_salah_rep_2, R.string.ba3d_salah_rep_3, R.string.ba3d_salah_rep_4, R.string.ba3d_salah_rep_5, R.string.ba3d_salah_rep_6, R.string.ba3d_salah_rep_7, R.string.ba3d_salah_rep_8, R.string.ba3d_salah_rep_9, R.string.ba3d_salah_rep_10, R.string.ba3d_salah_rep_11)
    private val repTimes_int = arrayOf(3, 1, 1, 33, 33, 33, 1, 1, 1, 10, 1)
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