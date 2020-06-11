package com.jpp.mpcredits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.jpp.mp.common.coroutines.MPViewModel
import com.jpp.mp.common.extensions.addAllMapping
import com.jpp.mp.common.navigation.Destination
import com.jpp.mpdomain.CastCharacter
import com.jpp.mpdomain.Credits
import com.jpp.mpdomain.CrewMember
import com.jpp.mpdomain.usecase.GetCreditsUseCase
import com.jpp.mpdomain.usecase.Try
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * [MPViewModel] that supports the credits section. The VM retrieves
 * the data from the underlying layers and maps the business
 * data to UI data, producing a [CreditsViewState] that represents the configuration of the view
 * at any given moment.
 *
 * This VM is language aware, meaning that when the user changes the language of the device, the
 * VM is notified about such event and executes a refresh of both: the data stored by the application
 * and the view state being shown to the user.
 */
class CreditsViewModel @Inject constructor(
    private val getCreditsUseCase: GetCreditsUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : MPViewModel() {

    private val _viewState = MediatorLiveData<CreditsViewState>()
    val viewState: LiveData<CreditsViewState>  = _viewState

    private lateinit var currentParam: CreditsInitParam

    /**
     * Called on VM initialization. The View (Fragment) should call this method to
     * indicate that it is ready to start rendering. When the method is called, the VM
     * internally verifies the state of the application and updates the view state based
     * on it.
     */
    fun onInit(param: CreditsInitParam) {
        currentParam = param
        updateCurrentDestination(Destination.ReachedDestination(currentParam.movieTitle))
        fetchMovieCredits()
    }

    /**
     * Called when an item is selected in the list of credit persons.
     */
    fun onCreditItemSelected(personItem: CreditPerson) {
        with(personItem) {
            navigateTo(
                Destination.MPPerson(
                    personId = id.toString(),
                    personImageUrl = profilePath,
                    personName = subTitle
                )
            )
        }
    }

    /**
     * When called, this method will push the loading view state and will fetch the credits
     * of the movie being shown.
     */
    private fun fetchMovieCredits() {
        _viewState.value = CreditsViewState.showLoading()
        viewModelScope.launch {
            val result = withContext(ioDispatcher) {
                getCreditsUseCase.execute(currentParam.movieId)
            }

            when (result) {
                is Try.Success -> processCreditsResponse(result.value)
                is Try.Failure -> processFailure(result.cause)
            }
        }
    }

    private fun processFailure(failure: Try.FailureCause) {
        _viewState.value = when (failure) {
            is Try.FailureCause.NoConnectivity -> _viewState.value?.showNoConnectivityError { fetchMovieCredits() }
            else -> _viewState.value?.showUnknownError { fetchMovieCredits() }
        }
    }

    private fun processCreditsResponse(credits: Credits) {
        if (credits.cast.isEmpty() && credits.crew.isEmpty()) {
            _viewState.value = _viewState.value?.showNoCreditsAvailable()
            return
        }

        val creditPersonItems = credits.cast
            .map { castCharacter -> castCharacter.mapToCreditPerson() }
            .toMutableList()
            .addAllMapping { credits.crew.map { crewMember -> crewMember.mapToCreditPerson() } }

        _viewState.value = _viewState.value?.showCredits(creditPersonItems)
    }

    private fun CastCharacter.mapToCreditPerson(): CreditPerson {
        return CreditPerson(
            id = id,
            profilePath = profile_path ?: "empty",
            title = character,
            subTitle = name
        )
    }

    private fun CrewMember.mapToCreditPerson(): CreditPerson {
        return CreditPerson(
            id = id,
            profilePath = profile_path ?: "empty",
            title = name,
            subTitle = department
        )
    }
}
