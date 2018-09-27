package com.example.artyom.newarchitecturesample.ui.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ViewFlipper
import com.example.artyom.newarchitecturesample.R
import dagger.Module
import dagger.Provides
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.view.btnPay
import kotlinx.android.synthetic.main.fragment_main.view.btnPayAgain
import kotlinx.android.synthetic.main.fragment_main.view.btnRetry
import kotlinx.android.synthetic.main.fragment_main.view.mainViewFlipper
import kotlinx.android.synthetic.main.fragment_main.view.textViewErrorDescription
import javax.inject.Inject

@Module
class MainFragmentModule {

    @Provides
    fun provideView(mainFragment: MainFragment): MainFragmentView = mainFragment
}

class MainFragment : DaggerFragment(), MainFragmentView, View.OnClickListener {

    private var listener: OnFragmentInteractionListener? = null

    @Inject
    lateinit var presenter: MainFragmentPresenter
    lateinit var mainViewFlipper: ViewFlipper
    lateinit var btnPay: Button
    lateinit var btnRetry: Button
    lateinit var btnPayAgain: Button
    lateinit var textViewErrorDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(presenter)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        mainViewFlipper = view.mainViewFlipper
        btnPay = view.btnPay
        btnRetry = view.btnRetry
        btnPayAgain = view.btnPayAgain
        textViewErrorDescription = view.textViewErrorDescription

        btnPay.setOnClickListener(this)
        btnRetry.setOnClickListener(this)
        btnPayAgain.setOnClickListener(this)
        return view
    }

    override fun setState(state: MainFragmentState) {
        when (state) {
            is MainFragmentState.ERROR -> {
                mainViewFlipper.displayedChild = MAIN_VIEW_ERROR
                textViewErrorDescription.text = state.errorDescription
            }
            is MainFragmentState.INIT -> {
                mainViewFlipper.displayedChild = MAIN_VIEW_INIT
            }
            is MainFragmentState.PAY_IN_PROGRESS -> {
                mainViewFlipper.displayedChild = MAIN_VIEW_LOADING
            }
            is MainFragmentState.PAY_SUCCESS -> {
                mainViewFlipper.displayedChild = MAIN_VIEW_SUCCESS
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnPay -> presenter.onPayClicked()
            R.id.btnRetry -> presenter.onRetryClicked()
            R.id.btnPayAgain -> presenter.onPayClicked()
        }
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        const val MAIN_VIEW_INIT = 0
        const val MAIN_VIEW_LOADING = 1
        const val MAIN_VIEW_ERROR = 2
        const val MAIN_VIEW_SUCCESS = 3

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    //                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}




