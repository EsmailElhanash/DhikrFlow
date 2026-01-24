package app.islammedia.azkar.Resources

import app.islammedia.azkar.R
import com.esmailsasso.azkar.R
import java.util.*

class AzkarTitlesNames {
    private val names = arrayOf(arrayOf(R.string.Azkar_as_sabah, R.string.Azkar_al_masaa), arrayOf(R.string.Azkar_istiqath, R.string.Azkar_zahab_noum), arrayOf(R.string.kh_mn_mnzl, R.string.dokh_mnzl, R.string.zahab_msjed, R.string.in_msjed, R.string.out_msjed))
    fun getNames(i: Int): Array<Int> {
        return names[i]
    }

    fun getNameWithIndex(index: Int, i: Int): Int {
        return names[i][index]
    }

    fun getNamesAsList(i: Int): List<Int> {
        return Arrays.asList(*names[i])
    }
}