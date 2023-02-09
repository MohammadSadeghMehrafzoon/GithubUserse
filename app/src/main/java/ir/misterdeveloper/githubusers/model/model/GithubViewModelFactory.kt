package ir.misterdeveloper.githubusers.model.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.misterdeveloper.githubusers.model.Repository.GithubRepository

class GithubViewModelFactory(private val githubRepository: GithubRepository) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GithubViewModel(githubRepository) as T
    }
}