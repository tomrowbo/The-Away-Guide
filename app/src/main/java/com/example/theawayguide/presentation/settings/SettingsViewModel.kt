package com.example.theawayguide.presentation.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.theawayguide.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject
constructor(
    private val sharedPreferences: SharedPreferences?,
    private val context: Context // Mitigated warning in IDE
) : ViewModel() {

    var uiState: MutableState<SettingsUiState> = mutableStateOf(SettingsUiState())
    var attractionDistanceScope: Int = context.resources.getInteger(R.integer.default_attraction_distance)

    init {
        getScreenInfo()
    }

    private fun getScreenInfo() {
        attractionDistanceScope = sharedPreferences?.getInt(context.getString(R.string.attraction_distance_radius_key), context.resources.getInteger(R.integer.default_attraction_distance)) ?: context.resources.getInteger(R.integer.default_attraction_distance)
        uiState.value = uiState.value.copy().apply {
            radiusInKm = (attractionDistanceScope.toDouble() / 1000)
            sliderProportion = getSliderProportion(attractionDistanceScope)
        }
    }

    private fun getSliderProportion(attractionScopeInMetres: Int): Float {
        return (attractionScopeInMetres - context.resources.getInteger(R.integer.min_attraction_distance)).toFloat() / (context.resources.getInteger(R.integer.max_attraction_distance) - context.resources.getInteger(R.integer.min_attraction_distance)).toFloat()
    }

    fun onSliderChanged(newSliderProportion: Float) {
        attractionDistanceScope = calculateNewAttractionScope(newSliderProportion)
        saveNewDistance(attractionDistanceScope)
        uiState.value = uiState.value.copy().apply {
            radiusInKm = (attractionDistanceScope.toDouble() / 1000)
            sliderProportion = newSliderProportion
        }
    }

    private fun saveNewDistance(attractionDistanceScope: Int) {
        if (sharedPreferences != null) {
            with(sharedPreferences.edit()) {
                putInt(context.getString(R.string.attraction_distance_radius_key), attractionDistanceScope)
                apply()
            }
        }
    }

    private fun calculateNewAttractionScope(percentageAsDecimal: Float): Int {
        val percentage = (percentageAsDecimal * 10).toInt()
        return ((percentage * (context.resources.getInteger(R.integer.max_attraction_distance) - context.resources.getInteger(R.integer.min_attraction_distance))) / 10 + context.resources.getInteger(R.integer.min_attraction_distance))
    }
}
