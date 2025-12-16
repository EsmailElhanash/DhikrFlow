package com.esmailsasso.azkaralsalah

object Data {
    private const val AD_PLACEHOLDER = 1919 // Replace with any unique integer not used by your actual resources

    val mThumbIds = arrayOf(
        R.drawable.sebha,
        R.drawable.sabah,
        AD_PLACEHOLDER, // Placeholder for ad at position 2
        R.drawable.masa2,
        R.drawable.reeh,
        AD_PLACEHOLDER, // Placeholder for ad at position 5
        R.drawable.duaa_istikhara,
        R.drawable.duaa_sa3b,
        AD_PLACEHOLDER, // Placeholder for ad at position 8
        R.drawable.duaa_safar,
        R.drawable.rokia,
        AD_PLACEHOLDER, // Placeholder for ad at position 11
        R.drawable.matar,
        R.drawable.nawm,
        AD_PLACEHOLDER, // Placeholder for ad at position 14
    )

    val mStrings = arrayOf(
        R.string.AzkarAlsalah,
        R.string.AzkarAlsabah,
        AD_PLACEHOLDER, // Placeholder for ad at position 2
        R.string.AzkarAlmasa2,
        R.string.reeh,
        AD_PLACEHOLDER, // Placeholder for ad at position 5
        R.string.Istikhara,
        R.string.sa3b,
        AD_PLACEHOLDER, // Placeholder for ad at position 8
        R.string.safar,
        R.string.rokia,
        AD_PLACEHOLDER, // Placeholder for ad at position 11
        R.string.matar,
        R.string.nawm,
        AD_PLACEHOLDER, // Placeholder for ad at position 14
    )


    fun getTitleFromNumber(i: Int): String {
        return when (i) {
            0 -> "أذكار ختم الصلاة"
            1 -> "أذكار الصباح"
            2 -> "أذكار المساء"
            3 -> "دعاء الريح"
            4 -> "دعاء الإستخارة"
            5 -> "دعاء استصعاب أمر"
            6 -> "دعاء السفر"
            7 -> "الرقية الشرعية"
            8 -> "دعاء المطر"
            9 -> "أذكار النوم"
            else -> ""
        }
    }
}