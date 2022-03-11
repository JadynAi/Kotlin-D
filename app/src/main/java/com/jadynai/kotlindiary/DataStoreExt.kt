package com.jadynai.kotlindiary

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder
import androidx.datastore.rxjava2.RxDataStore

/**
 *Jairett since 2022/3/7
 */
val Context.sDataStore: RxDataStore<Preferences>
    get() = RxPreferenceDataStoreBuilder(this,"test").build()