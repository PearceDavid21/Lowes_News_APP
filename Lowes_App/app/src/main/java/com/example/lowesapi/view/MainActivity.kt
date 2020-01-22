package com.example.lowesapi.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lowesapi.R
import com.example.lowesapi.databinding.ActivityMainBinding
import com.example.lowesapi.viewmodel.NewsListViewModel
import com.example.lowesapi.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

    lateinit var viewModelFactory: ViewModelFactory
    lateinit var newsListViewModel: NewsListViewModel

    private var binding: ActivityMainBinding? = null
    private var errorSnackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding!!.articleList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        newsListViewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsListViewModel::class.java)
        newsListViewModel.errorMessage.observe(this, Observer {errorMessage ->
            if (errorMessage != null) {
                showError(errorMessage)
            } else {
                hideError()
            }
        })
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(url: String) {/* Do something */
        val bundle = Bundle()
        bundle.putString("ARTICLE_URL", url)
        println("_xyz $url")
        // staRT ACTIVITY AND LOAD URL IN WEBVIEW
    }

    private fun hideError() {
        errorSnackBar?.dismiss()
    }

    private fun showError(errorMessage: Int) {
        errorSnackBar = Snackbar.make(binding!!.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackBar!!.setAction(R.string.retry, newsListViewModel.errorClickListener)
        errorSnackBar!!.show()
    }

    companion object {
        val QUERY = "USA"
    }
}
