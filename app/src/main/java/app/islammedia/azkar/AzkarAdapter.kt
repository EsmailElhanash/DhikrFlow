package app.islammedia.azkar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class AzkarAdapter(var fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {
    private var fs: Array<Fragment>
    private var fragmentCount = 0
    override fun getItem(position: Int): Fragment {
        return fs[position]
    }

    override fun getCount(): Int {
        return fragmentCount
    }

    fun clearFragments() {
        when (fragmentCount) {
            1 -> {
                fm.beginTransaction().remove(fs[0]).commit()
                return
            }
            2 -> {
                fm.beginTransaction().remove(fs[0]).commit()
                fm.beginTransaction().remove(fs[1]).commit()
            }
        }
    }

    fun setFs(fs: Array<Fragment>) {
        fragmentCount = fs.size
        this.fs = fs
    }
}