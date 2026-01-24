package app.islammedia.azkar

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import com.esmailsasso.azkar.R
import java.util.*

//CustomContentsListAdapter
class CustomContentsList(private val context: Context?, private val objects: List<Array<Int?>?>?, cl11: ConstraintLayout?, useScreenHeight: Boolean, azkarNum: Int, refresh: Boolean) : BaseAdapter() {
    private val counter: Array<Int>
    private val rep_times: Array<Int?>?
    private val isDone: BooleanArray
    private val cl11: ConstraintLayout?
    private val useScreenHeight = false
    private val counterNums: SharedPreferences
    private val refresh: Boolean
    private val azkarNum: Int
    val day: String
        get() = Calendar.getInstance()[Calendar.YEAR].toString() + "-" + Calendar.getInstance()[Calendar.MONTH].toString() + "-" + Calendar.getInstance()[Calendar.DAY_OF_MONTH].toString()

    override fun getCount(): Int {
        return objects!![0].length
    }

    override fun getItem(position: Int): Any {
        return null
    }

    fun getCounter(position: Int): Int {
        return counter[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v: View
        v = convertView
                ?: LayoutInflater.from(context).inflate(R.layout.custom_azkar_content_adapter, parent, false)
        val container = v.findViewById<RelativeLayout>(R.id.container)
        val container2 = v.findViewById<LinearLayout>(R.id.container2)
        if (useScreenHeight || context!!.getSharedPreferences("pref", Context.MODE_PRIVATE).getBoolean("expanded", false)) {
            v.layoutParams.height = Resources.getSystem().displayMetrics.heightPixels - dpToPx(56) - dpToPx(33)
            container2.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        val tickSounds = arrayOfNulls<MediaPlayer>(rep_times!![position]!!)
        val vibrator = context!!.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val arabic_font_1 = Typeface.createFromAsset(context.assets, "fonts/arabic_font_1.ttf")
        val done_img = v.findViewById<ImageView>(R.id.done_image)
        if (isDone[position]) {
            done_img.visibility = View.VISIBLE
            v.alpha = 0.3f
        } else {
            done_img.visibility = View.GONE
            v.alpha = 0.85f
        }
        val zekr = v.findViewById<TextView>(R.id.zekr)
        zekr.text = context.getString(objects!![0]!![position]!!)
        zekr.setTypeface(arabic_font_1)
        val times = v.findViewById<TextView>(R.id.times)
        times.text = context.getString(objects[1]!![position]!!)
        times.setTypeface(arabic_font_1)
        val counter_number = v.findViewById<TextView>(R.id.counter_number)
        if (!refresh) {
            if (azkarNum == 0) {
                //Log.i("reached?", "reached");
                counter[position] = counterNums.getInt(day + "sabah" + position.toString(), 0)
            } else if (azkarNum == 1) {
                counter[position] = counterNums.getInt(day + "masaa" + position.toString(), 0)
            }
        } else {
            counter[position] = 0
        }
        counter_number.text = counter[position].toString()
        counter_number.setTypeface(arabic_font_1)
        if (counter[position] == rep_times[position] && !isDone[position]) {
            isDone[position] = true
            done_img.visibility = View.VISIBLE
            v.alpha = 0.3f
        }
        val big_counter = cl11!!.findViewById<TextView>(R.id.big_counter)
        big_counter.setTypeface(arabic_font_1)
        big_counter.bringToFront()
        big_counter.text = ""
        val zekr_number = v.findViewById<TextView>(R.id.zekr_number)
        zekr_number.setText((position + 1 + "/" + rep_times.size).toString())
        zekr_number.setTypeface(arabic_font_1)
        val moreButton = v.findViewById<ImageButton>(R.id.moreButton)
        moreButton.setOnClickListener { view ->
            val popup = PopupMenu(context, view)
            popup.menuInflater.inflate(R.menu.more_menu, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.refresh_zekr) {
                    isDone[position] = false
                    counter[position] = 0
                    counter_number.text = counter[position].toString()
                    done_img.visibility = View.GONE
                    v.alpha = 0.85f
                }
                true
            }
            popup.show()
        }
        v.setOnClickListener { v ->
            big_counter.animate().cancel()
            if (counter[position] < rep_times[position]!!) {
                tickSounds[counter[position]] = MediaPlayer.create(context, R.raw.light)
                tickSounds[counter[position]].start()
                counter[position]++
                counter_number.text = counter[position].toString()
                if (azkarNum == 0) {
                    Log.i("dayTime", day + "sabah" + position.toString())
                    counterNums.edit().putInt(day + "sabah" + position.toString(), counter[position]).apply()
                } else if (azkarNum == 1) {
                    Log.i("dayTime", day + "masaa" + position.toString())
                    counterNums.edit().putInt(day + "masaa" + position.toString(), counter[position]).apply()
                }
            }
            big_counter.text = counter[position].toString()
            big_counter.animate()
                    .alpha(0f)
                    .scaleX(20f)
                    .scaleY(20f)
                    .setDuration(1000)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            big_counter.alpha = 1f
                            big_counter.scaleX = 1f
                            big_counter.scaleY = 1f
                            big_counter.text = ""
                        }
                    })
            if (counter[position] == rep_times[position] && !isDone[position]) {
                isDone[position] = true
                done_img.visibility = View.VISIBLE
                v.alpha = 0.3f
                (parent as ListView).smoothScrollToPositionFromTop(position + 1, 0, 500)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    //deprecated in API 26
                    vibrator?.vibrate(500)
                }
            }
            if (tickSounds[counter[position] - 1] != null) {
                tickSounds[counter[position] - 1]!!.setOnCompletionListener { mp -> mp.release() }
            }
        }
        return v
    }

    private fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    init {
        rep_times = objects!![2]
        counter = arrayOfNulls(rep_times!!.size)
        isDone = BooleanArray(rep_times.size)
        this.useScreenHeight = useScreenHeight
        for (i in rep_times.indices) {
            counter[i] = 0
            isDone[i] = false
        }
        this.cl11 = cl11
        this.refresh = refresh
        counterNums = context!!.getSharedPreferences("counterNums", Context.MODE_PRIVATE)
        this.azkarNum = azkarNum
        if (refresh) counterNums.edit().clear().apply()
    }
}