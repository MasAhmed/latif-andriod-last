package com.latifapp.latif.ui.main.pets

import com.latifapp.latif.data.local.AppPrefsStorage
import com.latifapp.latif.network.repo.DataRepo
import com.latifapp.latif.ui.base.CategoriesViewModel
import javax.inject.Inject

class PetsViewModel @Inject constructor(appPrefsStorage: AppPrefsStorage, repo: DataRepo) :
    CategoriesViewModel(
        appPrefsStorage, repo
    ) {
}