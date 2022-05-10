package com.jadynai.kotlindiary.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jadynai.kotlindiary.R

/**
 *Jairett since 2022/5/5
 */
class GroupFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_group, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment = ViewTextViewSaveInstanceStateFragment()
        val beginTransaction = childFragmentManager.beginTransaction()
        beginTransaction.add(R.id.group_layout, fragment)
        beginTransaction.commit()
    }
}