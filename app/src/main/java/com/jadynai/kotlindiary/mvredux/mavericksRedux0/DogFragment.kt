package com.jadynai.kotlindiary.mvredux.mavericksRedux0

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.jadyn.ai.acrobat.recyclerview.AcrobatAdapter
import com.jadynai.kotlindiary.R
import com.jadynai.kotlindiary.mvredux.mavericksRedux0.data.Dog

/**
 *JadynAi since 2021/7/28
 */
class DogFragment : Fragment(R.layout.fragment_dog), MavericksView {

    private val dogViewModel: DogViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val action = DogState::dogs
    }

    override fun invalidate() = withState(dogViewModel) { state ->
        val recyclerView = view?.findViewById<RecyclerView>(R.id.dogsRecyclerView)
        val loading = view?.findViewById<RecyclerView>(R.id.loadingAnimation)
        val adoptBt = view?.findViewById<RecyclerView>(R.id.adoptButton)
        loading?.isVisible = state.dogs is Loading
        recyclerView?.adapter = AcrobatAdapter<Dog> {

        }
    }
}