package app.islammedia.azkar

import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import app.islammedia.azkar.FadleAth_thekrPackage.FadlFragment
import app.islammedia.azkar.fragments.AboutFragment
import app.islammedia.azkar.fragments.AzkarContentsFragment
import app.islammedia.azkar.fragments.AzkarTitlesFragment
import app.islammedia.azkar.fragments.AzkarTitlesFragment.AzkarTitlesFragmentCommunication
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), AzkarTitlesFragmentCommunication {
    var azkarAdapter: AzkarAdapter? = null
    var mViewPager: ViewPager? = null
    var pageNum = 0
    var expanded = false
    var preferences: SharedPreferences? = null
    var menu: Menu? = null
    var mToolbar: Toolbar? = null
    var title: TextView? = null
    private var mDrawerLayout: DrawerLayout? = null
    var flag = 0
    var titlesTitle = "فضل الذكر"


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        if (pageNum == 0) {
            menuInflater.inflate(R.menu.options_menu_page_1, menu)
        } else if (pageNum == 1) {
            menuInflater.inflate(R.menu.options_menu, menu)
            if (getSharedPreferences("pref", MODE_PRIVATE).getBoolean("expanded", false)) {
                menu.findItem(R.id.action_collapse).isVisible = true
                menu.findItem(R.id.action_expand).isVisible = false
            } else {
                menu.findItem(R.id.action_expand).isVisible = true
                menu.findItem(R.id.action_collapse).isVisible = false
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_expand -> {
                (azkarAdapter!!.getItem(1) as AzkarContentsFragment).expandAdapter(section)
                expanded = true
                item.isVisible = false
                menu!!.findItem(R.id.action_collapse).isVisible = true
                preferences!!.edit().putBoolean("expanded", expanded).apply()
            }
            R.id.action_collapse -> {
                (azkarAdapter!!.getItem(1) as AzkarContentsFragment).collapseAdapter(section)
                expanded = false
                item.isVisible = false
                menu!!.findItem(R.id.action_expand).isVisible = true
                preferences!!.edit().putBoolean("expanded", expanded).apply()
            }
            android.R.id.home -> mDrawerLayout!!.openDrawer(GravityCompat.START)
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferences = applicationContext.getSharedPreferences("pref", MODE_PRIVATE)
        expanded = preferences.getBoolean("expanded", false)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        val arabic_font_1 = Typeface.createFromAsset(this.assets, "fonts/arabic_font_1.ttf")
        setTitle("")
        mToolbar = findViewById(R.id.mToolbar)
        setSupportActionBar(mToolbar)
        title = mToolbar.findViewById(R.id.title)
        title.setTypeface(arabic_font_1)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        val actionbar = supportActionBar!!
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu)
        val nav_view = findViewById<NavigationView>(R.id.nav_view)
        val appTitle = nav_view.getHeaderView(0).findViewById<TextView>(R.id.appTitle)
        appTitle.setTypeface(arabic_font_1)
        mViewPager = findViewById(R.id.pager)
        val m = nav_view.menu
        for (i in 0 until m.size()) {
            val mi = m.getItem(i)
            //the method we have create in activity
            applyFontToMenuItem(mi)
        }
        displayFadlThekr()
        mDrawerLayout.openDrawer(GravityCompat.START)
        title.setText(titlesTitle)
        mViewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageSelected(i: Int) {
                when (i) {
                    0 -> {
                        pageNum = 0
                        invalidateOptionsMenu()
                        title.setText(titlesTitle)
                    }
                    1 -> {
                        pageNum = 1
                        invalidateOptionsMenu()
                    }
                }
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })
        nav_view.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            mDrawerLayout.closeDrawers()
            when (menuItem.itemId) {
                R.id.fadl_ath_thekr -> displayFadlThekr()
                R.id.sabah_masaa -> {
                    section = 0
                    displayAzkarAssabahWalMasaa()
                }
                R.id.after_salah -> {
                    section = 1
                    displayAzkarAfterSalah()
                }
                R.id.sleeping -> {
                    section = 2
                    displayAzkarNoum()
                }
                R.id.in_out_door -> {
                    section = 3
                    displayAzkarInOut()
                }
                R.id.about -> displayAbout()
            }
            true
        }
    }

    override fun onTitleSelected(position: Int, section: Int) {
        if (azkarAdapter != null) {
            azkarAdapter!!.clearFragments()
            mViewPager!!.removeAllViews()
            mViewPager!!.adapter = null
        }
        val fs = arrayOfNulls<Fragment>(2)
        fs[0] = AzkarTitlesFragment.Companion.newInstance()
        fs[1] = AzkarContentsFragment.Companion.newInstance()
        val b = Bundle()
        b.putInt("section", section)
        fs[0].setArguments(b)
        azkarAdapter = AzkarAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        azkarAdapter!!.setFs(fs)
        mViewPager!!.adapter = azkarAdapter
        preferences!!.edit().putInt("tileNum", position).apply()
        (azkarAdapter!!.getItem(1) as AzkarContentsFragment).updateAdapter(position, false, false, section)
        mViewPager!!.currentItem = 1
    }

    private fun applyFontToMenuItem(mi: MenuItem) {
        val font = Typeface.createFromAsset(assets, "fonts/arabic_font_1.ttf")
        val mNewTitle = SpannableString(mi.title)
        mNewTitle.setSpan(CustomTypefaceSpan("", font), 0, mNewTitle.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        mi.title = mNewTitle
    }

    fun displayFadlThekr() {
        titlesTitle = "فضل الذكر"
        title!!.text = titlesTitle
        if (azkarAdapter != null) {
            azkarAdapter!!.clearFragments()
            mViewPager!!.removeAllViews()
            mViewPager!!.adapter = null
        }
        val fs = arrayOfNulls<Fragment>(1)
        fs[0] = FadlFragment()
        azkarAdapter = AzkarAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        azkarAdapter!!.setFs(fs)
        mViewPager!!.adapter = azkarAdapter
    }

    fun displayAzkarAssabahWalMasaa() {
        titlesTitle = "أذكار الصباح و المساء"
        title!!.text = titlesTitle
        if (azkarAdapter != null) {
            azkarAdapter!!.clearFragments()
            mViewPager!!.removeAllViews()
            mViewPager!!.adapter = null
        }
        flag = 0
        val fs = arrayOfNulls<Fragment>(1)
        val b = Bundle()
        b.putInt("section", section)
        fs[0] = AzkarTitlesFragment.Companion.newInstance()
        fs[0].setArguments(b)
        azkarAdapter = AzkarAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        azkarAdapter!!.setFs(fs)
        mViewPager!!.adapter = azkarAdapter
    }

    fun displayAzkarNoum() {
        titlesTitle = "أذكار النوم"
        title!!.text = titlesTitle
        if (azkarAdapter != null) {
            azkarAdapter!!.clearFragments()
            mViewPager!!.removeAllViews()
            mViewPager!!.adapter = null
        }
        flag = 0
        val fs = arrayOfNulls<Fragment>(1)
        fs[0] = AzkarTitlesFragment.Companion.newInstance()
        val b = Bundle()
        b.putInt("section", section)
        fs[0].setArguments(b)
        azkarAdapter = AzkarAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        azkarAdapter!!.setFs(fs)
        mViewPager!!.adapter = azkarAdapter
    }

    fun displayAzkarAfterSalah() {
        titlesTitle = "أذكار ختم الصلاة"
        title!!.text = titlesTitle
        if (azkarAdapter != null) {
            azkarAdapter!!.clearFragments()
            mViewPager!!.removeAllViews()
            mViewPager!!.adapter = null
        }
        flag = 0
        val fs = arrayOfNulls<Fragment>(1)
        fs[0] = AzkarTitlesFragment.Companion.newInstance()
        val b = Bundle()
        b.putInt("section", section)
        fs[0].setArguments(b)
        azkarAdapter = AzkarAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        azkarAdapter!!.setFs(fs)
        mViewPager!!.adapter = azkarAdapter
    }

    fun displayAzkarInOut() {
        titlesTitle = "أذكار دخول و خروج المسجد و المنزل"
        title!!.text = titlesTitle
        if (azkarAdapter != null) {
            azkarAdapter!!.clearFragments()
            mViewPager!!.removeAllViews()
            mViewPager!!.adapter = null
        }
        flag = 0
        val fs = arrayOfNulls<Fragment>(1)
        fs[0] = AzkarTitlesFragment.Companion.newInstance()
        val b = Bundle()
        b.putInt("section", section)
        fs[0].setArguments(b)
        azkarAdapter = AzkarAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        azkarAdapter!!.setFs(fs)
        mViewPager!!.adapter = azkarAdapter
    }

    fun displayAbout() {
        titlesTitle = "عن التطبيق"
        title!!.text = titlesTitle
        if (azkarAdapter != null) {
            azkarAdapter!!.clearFragments()
            mViewPager!!.removeAllViews()
            mViewPager!!.adapter = null
        }
        val fs = arrayOfNulls<Fragment>(1)
        fs[0] = AboutFragment()
        azkarAdapter = AzkarAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        azkarAdapter!!.setFs(fs)
        mViewPager!!.adapter = azkarAdapter
    }

    override fun onBackPressed() {
        if (mViewPager!!.currentItem == 1) {
            mViewPager!!.currentItem = 0
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        var section = 0
    }
}