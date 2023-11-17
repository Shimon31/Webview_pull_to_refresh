package com.sbs.cpaeth.tuneey

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sbs.cpaeth.tuneey.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.GREEN))

        initWebView()
        setupSwipeRefresh()
        setupReloadButton()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.webView.visibility = View.GONE
                binding.loadingBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.webView.visibility = View.VISIBLE
                binding.loadingBar.visibility = View.GONE
                binding.swipeRefreshLayout.isRefreshing = false // Stop refreshing
            }
        }

        if (isOnline()) {
            binding.webView.loadUrl("https://tuneey.com/")
        } else {
            showNoInternetViews()
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            if (isUserAtTopOfPage()) {
                binding.webView.reload()
                binding.loadingBar.visibility = View.GONE
            } else {
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun setupReloadButton() {
        binding.reload.setOnClickListener {
            if (isOnline()) {
                initWebView()
                binding.networkTV.visibility = View.GONE
                binding.NoInternet.visibility = View.GONE
                binding.reload.visibility = View.GONE
            } else {
                showNoInternetViews()
            }
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected ?: false
    }

    private fun isUserAtTopOfPage(): Boolean {
        return binding.webView.scrollY <= 0
    }

    private fun showNoInternetViews() {
        binding.networkTV.visibility = View.VISIBLE
        binding.NoInternet.visibility = View.VISIBLE
        binding.webView.visibility = View.GONE
        binding.reload.visibility = View.VISIBLE
        binding.loadingBar.visibility = View.GONE
    }
}

