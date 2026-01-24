package app.islammedia.azkar.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.fragment.app.Fragment
import app.islammedia.azkar.CustomTitlesList
import app.islammedia.azkar.R
import app.islammedia.azkar.Resources.AzkarTitlesNames
import com.esmailsasso.azkar.R

class AzkarTitlesFragment : Fragment() {
    var context: Context? = null
    var mCallback: AzkarTitlesFragmentCommunication? = null

    // Container Activity must implement this interface
    interface AzkarTitlesFragmentCommunication {
        fun onTitleSelected(position: Int, section: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
        mCallback = context as AzkarTitlesFragmentCommunication
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_titles_azkar, container, false)
        val lv = v.findViewById<ListView>(R.id.azkar_titles_list_view)
        val b = this.arguments
        lv.adapter = CustomTitlesList(context, AzkarTitlesNames().getNamesAsList(b!!.getInt("section")))
        lv.onItemClickListener = OnItemClickListener { adapterView, view, i, l -> mCallback!!.onTitleSelected(i, b.getInt("section")) }
        return v
    }

    companion object {
        fun newInstance(): AzkarTitlesFragment {
            return AzkarTitlesFragment()
        }
    }
}