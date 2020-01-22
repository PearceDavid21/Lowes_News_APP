package com.example.lowesapi.view

import android.os.Bundle
import android.util.LongSparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lowesapi.R
import com.example.lowesapi.databinding.ActivityMainBinding
import com.example.lowesapi.injection.MVVMApplication
import com.example.lowesapi.injection.component.ConfigPersistentComponent
import com.example.lowesapi.injection.component.DaggerConfigPersistentComponent
import com.example.lowesapi.injection.module.ActivityModule
import com.example.lowesapi.viewmodel.NewsListViewModel
import com.example.lowesapi.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var newsListViewModel: NewsListViewModel

    private var binding: ActivityMainBinding? = null
    private var errorSnackBar: Snackbar? = null

    var activityId: Long = 0
    val NEXT_ID = AtomicLong(0)
    val componentsArray = LongSparseArray<ConfigPersistentComponent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding!!.articleList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        inject(savedInstanceState)
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

    fun inject(savedInstanceState: Bundle?) {
        val configPersistentComponent: ConfigPersistentComponent
        if (componentsArray.get(activityId) == null) {
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                .appComponent(MVVMApplication[applicationContext].component)
                .build()
            componentsArray.put(activityId, configPersistentComponent)
        } else {
            configPersistentComponent = componentsArray.get(activityId)
        }

        val activityComponent = configPersistentComponent.activityComponent(ActivityModule(this))
        activityComponent.inject(this)

        newsListViewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsListViewModel::class.java)
        newsListViewModel.errorMessage.observe(this, Observer {errorMessage ->
            if (errorMessage != null) {
                showError(errorMessage)
            } else {
                hideError()
            }
        })
    }

    companion object {
        val QUERY = "USA"
    }
}
