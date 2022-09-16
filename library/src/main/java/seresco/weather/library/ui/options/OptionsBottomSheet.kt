package seresco.weather.library.ui.options

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_options.*
import seresco.weather.library.R
import seresco.weather.library.utils.MeteorologyType

class OptionsBottomSheet(optionsSheetCallback: OptionsSheetCallback, municipalityId: Int): BottomSheetDialogFragment() {

    private var dismissWithAnimation = false
    private var mOptionsSheetCallback = optionsSheetCallback
    private var mMunicipalityId = municipalityId

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_options, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    private fun setUpViews() {

    }

    private fun setUpListeners() {
        tv_humidity.setOnClickListener {
            Log.e("hey! zero","")
            mOptionsSheetCallback.onOptionClicked(MeteorologyType.HUMIDITY, mMunicipalityId)
            dismiss()
        }
        tv_precipitation.setOnClickListener {
            mOptionsSheetCallback.onOptionClicked(MeteorologyType.PRECIPITATION, mMunicipalityId)
            dismiss()
        }
        tv_today_climate.setOnClickListener {
            mOptionsSheetCallback.onOptionClicked(MeteorologyType.WEATHER_TODAY, mMunicipalityId)
            dismiss()
        }
        tv_tomorrow_climate.setOnClickListener {
            mOptionsSheetCallback.onOptionClicked(MeteorologyType.WEATHER_TOMORROW, mMunicipalityId)
            dismiss()
        }
        tv_weekly_climate.setOnClickListener {
            mOptionsSheetCallback.onOptionClicked(MeteorologyType.WEATHER_WEEKLY, mMunicipalityId)
            dismiss()
        }
        tv_wind.setOnClickListener {
            mOptionsSheetCallback.onOptionClicked(MeteorologyType.WIND, mMunicipalityId)
            dismiss()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dismissWithAnimation = arguments?.getBoolean(ARG_DISMISS_WITH_ANIMATION) ?: false
        (requireDialog() as BottomSheetDialog).dismissWithAnimation = dismissWithAnimation
    }

    companion object {
        const val TAG = "optionsBottomSheet"
        const val ARG_DISMISS_WITH_ANIMATION = "dismiss_with_animation"
        fun newInstance(dismissWithAnimation: Boolean, optionsSheetCallback: OptionsSheetCallback, municipalityId: Int): OptionsBottomSheet {
            val modalSimpleListSheet = OptionsBottomSheet(optionsSheetCallback, municipalityId)
            modalSimpleListSheet.arguments = bundleOf(ARG_DISMISS_WITH_ANIMATION to dismissWithAnimation)
            return modalSimpleListSheet
        }
    }

    interface OptionsSheetCallback {
        fun onOptionClicked(meteorologyType: MeteorologyType, municipalityId: Int)
    }
}