package com.example.familycoin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.familycoin.FamilyCoinApplication
import com.example.familycoin.repository.RepositoryApi
import com.example.familycoin.model.FilmModel
import com.example.familycoin.model.User

class FilmViewModel(private var repository: RepositoryApi) : ViewModel() {

    suspend fun getFilms(): List<FilmModel> {
        return repository.getFilms()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T { // Get the Application object from extras

                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return FilmViewModel(
                    (application as FamilyCoinApplication).appContainer.repositoryApi,
                ) as T
            }
        }
    }
}
