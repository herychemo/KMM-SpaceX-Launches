package com.grayraccoon.samples.spacexlaunches.androidApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.grayraccoon.samples.spacexlaunches.androidApp.adapter.LaunchesAdapter
import com.grayraccoon.samples.spacexlaunches.shared.SpaceXSdk
import com.grayraccoon.samples.spacexlaunches.shared.cache.DatabaseDriverFactory
import com.grayraccoon.samples.spacexlaunches.shared.entity.RocketLaunch
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val mainScope = MainScope()

    private val spaceXSdk: SpaceXSdk = SpaceXSdk(DatabaseDriverFactory(this))

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var launchesRecyclerView: RecyclerView
    private lateinit var progressBarView: FrameLayout

    private lateinit var launchesAdapter: LaunchesAdapter
    private val items: MutableList<RocketLaunch> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.app_name)

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_container)
        launchesRecyclerView = findViewById(R.id.launches_recycler_view)
        progressBarView = findViewById(R.id.progress_bar)


        launchesAdapter = LaunchesAdapter(items)
        launchesRecyclerView.apply {
            adapter = launchesAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            loadData(needReload = true)
        }
    }

    override fun onResume() {
        super.onResume()

        loadData(needReload = false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

    private fun loadData(needReload: Boolean = false) {
        progressBarView.visibility = View.VISIBLE
        mainScope.launch {
            kotlin.runCatching {
                spaceXSdk.getLaunches(forceReload = needReload)
            }.onSuccess {
                items.clear()
                items.addAll(it)
                launchesAdapter.notifyDataSetChanged()
            }.onFailure {
                Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
            progressBarView.visibility = View.GONE
        }
    }

}
