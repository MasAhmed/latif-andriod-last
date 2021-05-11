package com.latifapp.latif.ui.main.services

import com.latifapp.latif.data.local.AppPrefsStorage
import com.latifapp.latif.network.repo.DataRepo
import com.latifapp.latif.ui.base.CategoriesViewModel
import javax.inject.Inject

class ServiceViewModel @Inject constructor(repo: DataRepo, appPrefsStorage: AppPrefsStorage) :
    CategoriesViewModel(appPrefsStorage,repo) {




}