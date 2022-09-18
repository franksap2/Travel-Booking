package com.franksap2.travelbooking.core.ui.extensions

import android.icu.text.NumberFormat

fun Float.formatCurrency(): String = NumberFormat.getCurrencyInstance().format(this)