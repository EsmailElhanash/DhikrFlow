package app.islammedia.azkar.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import app.islammedia.azkar.R
import com.esmailsasso.azkar.R

class AboutFragment : Fragment() {
    var context: Context? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.about_activity, container, false)
        val tv = v.findViewById<TextView>(R.id.aboutTV)
        val arabic_font_1 = Typeface.createFromAsset(context!!.assets, "fonts/arabic_font_1.ttf")
        tv.setTypeface(arabic_font_1)
        val b = v.findViewById<Button>(R.id.download_book)
        b.setTypeface(arabic_font_1)
        b.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://bit.ly/2NjVeie"))) }
        return v
    }
}