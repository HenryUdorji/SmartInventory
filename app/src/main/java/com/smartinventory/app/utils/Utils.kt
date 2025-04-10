package com.smartinventory.app.utils

import java.text.NumberFormat

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/10/2025
 */
object Utils {
    fun formatAmount(amount: Double): String {
        val formatter = NumberFormat.getNumberInstance()
        return formatter.format(amount)
    }
}