package app.islammedia.azkar.Resources

import java.util.*

class AzkarClassesCollection {
    private fun getCollection(section: Int): List<List<Array<Int?>?>?>? {
        when (section) {
            0 -> return Arrays.asList(AzkarAssabah().all, AzkarAlmasaa().all)
            1 -> return Arrays.asList(AzkarIstiqath().all, AzkarNoum().all)
            2 -> return Arrays.asList(AzkarKhrogMnAlmnzl().all, AzkarDo5olAlmnzl().all, AzkarZahabLelMasjed().all, AzkarDo5olMasjed().all, AzkarAl5orojMenAlmasjed().all)
        }
        return null
    }

    fun getOne(which: Int, section: Int): List<Array<Int?>?>? {
        return getCollection(section)!![which]
    }
}