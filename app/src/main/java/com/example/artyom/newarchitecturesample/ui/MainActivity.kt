package com.example.artyom.newarchitecturesample.ui

import android.net.Uri
import android.os.Bundle
import com.example.artyom.newarchitecturesample.R
import com.example.artyom.newarchitecturesample.ui.fragment.MainFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity(),
    MainFragment.OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
