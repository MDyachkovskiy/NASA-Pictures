package com.test.application.picture_of_the_day.utils

import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.test.application.picture_of_the_day.R

class ChipGroupManager(
    private var chipGroup: ChipGroup?,
    private val onChipClicked: (Int) -> Unit
) {

    fun setChipListeners() {
        val chipIds = listOf(R.id.chip_today, R.id.chip_yesterday, R.id.chip_day_before_yesterday)
        chipIds.forEachIndexed { index, chipId ->
            val chip = chipGroup?.findViewById<Chip>(chipId)
            chip?.setOnClickListener { onChipClicked(index) }
        }
    }
    fun cleanup() {
        val chipIds = listOf(R.id.chip_today, R.id.chip_yesterday, R.id.chip_day_before_yesterday)
        chipIds.forEach { chipId ->
            val chip = chipGroup?.findViewById<Chip>(chipId)
            chip?.setOnClickListener(null)
        }
        chipGroup = null
    }

}