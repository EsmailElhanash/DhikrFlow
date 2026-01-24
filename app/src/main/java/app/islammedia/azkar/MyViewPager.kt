package app.islammedia.azkar

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.duolingo.open.rtlviewpager.RtlViewPager

class MyViewPager(context: Context, attrs: AttributeSet?) : RtlViewPager(context, attrs) {
    private var is_paging_enabled = false
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (is_paging_enabled) {
            super.onTouchEvent(event)
        } else false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (is_paging_enabled) {
            super.onInterceptTouchEvent(event)
        } else false
    }

    fun setPagingEnabled(enabled: Boolean) {
        is_paging_enabled = enabled
    }
}