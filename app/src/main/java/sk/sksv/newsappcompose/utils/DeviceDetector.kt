package sk.sksv.newsappcompose.utils

import android.os.Build

/**
 * Utility class to detect device manufacturer and model.
 * Used to apply device-specific SQLCipher configurations.
 */
object DeviceDetector {
    
    /**
     * Checks if the device is a Samsung device.
     * Samsung devices may have limited SQLCipher support and need
     * reduced key derivation iterations for better performance.
     */
    fun isSamsungDevice(): Boolean {
        val manufacturer = Build.MANUFACTURER.lowercase()
        val brand = Build.BRAND.lowercase()
        val model = Build.MODEL.lowercase()
        
        return manufacturer.contains("samsung") || 
               brand.contains("samsung") ||
               model.contains("samsung") ||
               model.contains("sumsang") // Handle typo variant
    }
    
    /**
     * Checks if the device is a OnePlus device.
     * OnePlus devices typically have better SQLCipher support
     * and can use default key derivation iterations.
     */
    fun isOnePlusDevice(): Boolean {
        val manufacturer = Build.MANUFACTURER.lowercase()
        val brand = Build.BRAND.lowercase()
        val model = Build.MODEL.lowercase()
        
        return manufacturer.contains("oneplus") || 
               brand.contains("oneplus") ||
               model.contains("oneplus") ||
               model.contains("one plus") // Handle space variant
    }
    
    /**
     * Gets the device manufacturer and model information for debugging.
     */
    fun getDeviceInfo(): String {
        return "Manufacturer: ${Build.MANUFACTURER}, Brand: ${Build.BRAND}, Model: ${Build.MODEL}"
    }
}
