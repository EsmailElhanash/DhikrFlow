package app.islammedia.azkar.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import app.islammedia.azkar.CustomContentsList
import app.islammedia.azkar.MainActivity
import app.islammedia.azkar.R
import app.islammedia.azkar.Resources.AzkarClassesCollection
import com.esmailsasso.azkar.R

class AzkarContentsFragment : Fragment() {
    private var lv: ListView? = null
    var cl11: ConstraintLayout? = null
    var context: Context? = null
    var preferences: SharedPreferences? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = context!!.applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
    }

    fun updateAdapter(clickedAzkarNum: Int, useScreenHeight: Boolean, refresh: Boolean, section: Int) {
        val arabic_font_1 = Typeface.createFromAsset(context!!.assets, "fonts/arabic_font_1.ttf")
        val mToolbar = (context as AppCompatActivity?)!!.findViewById<Toolbar>(R.id.mToolbar)
        (context as AppCompatActivity?)!!.setSupportActionBar(mToolbar)
        val title = mToolbar.findViewById<TextView>(R.id.title)
        title.setTypeface(arabic_font_1)
        when (section) {
            0 -> when (clickedAzkarNum) {
                0 -> title.setText(R.string.Azkar_as_sabah)
                1 -> title.setText(R.string.Azkar_al_masaa)
            }
            1 -> {
                when (clickedAzkarNum) {
                    0 -> title.setText(R.string.after_salam_salah)
                }
                when (clickedAzkarNum) {
                    0 -> title.setText(R.string.Azkar_istiqath)
                    1 -> title.setText(R.string.Azkar_zahab_noum)
                }
            }
            2 -> when (clickedAzkarNum) {
                0 -> title.setText(R.string.Azkar_istiqath)
                1 -> title.setText(R.string.Azkar_zahab_noum)
            }
            3 -> when (clickedAzkarNum) {
                0 -> title.setText(R.string.kh_mn_mnzl)
                1 -> title.setText(R.string.dokh_mnzl)
                2 -> title.setText(R.string.zahab_msjed)
                3 -> title.setText(R.string.in_msjed)
                4 -> title.setText(R.string.out_msjed)
            }
        }
        lv!!.adapter = CustomContentsList(context, AzkarClassesCollection().getOne(clickedAzkarNum, section), cl11, useScreenHeight, clickedAzkarNum, refresh)
    }

    fun expandAdapter(section: Int) {
        updateAdapter(preferences!!.getInt("tileNum", 0), true, false, section)
    }

    fun collapseAdapter(section: Int) {
        updateAdapter(preferences!!.getInt("tileNum", 0), false, false, section)
    }

    fun refreshAdapter(section: Int) {
        updateAdapter(preferences!!.getInt("tileNum", 0), preferences!!.getBoolean("expanded", false), true, section)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_contents_azkar, container, false)
        cl11 = v.findViewById(R.id.constraintLayout11)
        lv = v.findViewById(R.id.azkar_contents_recycler_view)
        val customContentsList = CustomContentsList(context, AzkarClassesCollection().getOne(0, MainActivity.Companion.section), cl11, false, 0, false)
        lv.setAdapter(customContentsList)
        return v
    }

    companion object {
        fun newInstance(): AzkarContentsFragment {
            return AzkarContentsFragment()
        }
    }
}