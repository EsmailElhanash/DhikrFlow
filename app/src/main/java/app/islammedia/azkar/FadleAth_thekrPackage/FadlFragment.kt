package app.islammedia.azkar.FadleAth_thekrPackage

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import app.islammedia.azkar.R
import com.esmailsasso.azkar.R

class FadlFragment : Fragment() {
    var context: Context? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fadl_ath_thekr_layout, container, false)
        val tv = v.findViewById<TextView>(R.id.fadlTV)
        val arabic_font_1 = Typeface.createFromAsset(context!!.assets, "fonts/arabic_font_1.ttf")
        tv.setTypeface(arabic_font_1)
        return v
    }
}