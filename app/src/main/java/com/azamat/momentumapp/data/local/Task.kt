package com.azamat.momentumapp.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val priorityId: Int? =null,
    var isChecked: Boolean = false,
    var id: String? = null,
    val title: String? = null,
    val description: String? = null
): Parcelable