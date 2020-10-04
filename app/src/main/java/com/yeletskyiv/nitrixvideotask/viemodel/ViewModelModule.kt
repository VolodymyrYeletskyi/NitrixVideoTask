package com.yeletskyiv.nitrixvideotask.viemodel

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel {
        ListViewModel(get(), get())
    }
    viewModel {
        VideoViewModel(get())
    }
}