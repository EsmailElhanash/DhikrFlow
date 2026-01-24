package app.islammedia.azkar

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.esmailsasso.azkar.R

class CustomTitlesList(private val context: Context?, private val items: List<Int?>?) : BaseAdapter() {
    override fun getCount(): Int {
        return items!!.size
    }

    override fun getItem(position: Int): Any {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val arabic_font_1 = Typeface.createFromAsset(context!!.assets, "fonts/arabic_font_1.ttf")
        var v = convertView
        if (v == null) {
            val vi: LayoutInflater
            vi = LayoutInflater.from(context)
            v = vi.inflate(R.layout.custom_azkar_titles_adapter, parent, false)
        }
        val azkar_title = v.findViewById<TextView>(R.id.azkar_titles_text_view)
        azkar_title.setText(items!![position]!!)
        azkar_title.setTypeface(arabic_font_1)
        return v
    }
}