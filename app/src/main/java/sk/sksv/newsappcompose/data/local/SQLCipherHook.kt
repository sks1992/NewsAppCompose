package sk.sksv.newsappcompose.data.local

import android.util.Log
import net.zetetic.database.sqlcipher.SQLiteConnection
import net.zetetic.database.sqlcipher.SQLiteDatabaseHook
import sk.sksv.newsappcompose.utils.DeviceDetector

/**
 * SQLCipher database hook that applies device-specific configurations.
 * 
 * For Samsung devices with limited SQLCipher support, this hook sets
 * kdf_iter = 4000 to reduce key derivation iterations and improve query performance.
 * 
 * For OnePlus devices, it uses the default SQLCipher configuration.
 */
class SQLCipherHook : SQLiteDatabaseHook {
    
    companion object {
        private const val TAG = "SQLCipherHook"
        // Reduced iterations for Samsung devices to improve performance
        private const val SAMSUNG_KDF_ITER = 4000
    }
    
    override fun preKey(connection: SQLiteConnection) {
        // Called before the encryption key is set
        // No action needed here
    }
    
    override fun postKey(connection: SQLiteConnection) {
        // Called after the encryption key is set
        // Apply device-specific PRAGMA settings here
        
        try {
            if (DeviceDetector.isSamsungDevice()) {
                // Set reduced key derivation iterations for Samsung devices
                // SQLiteConnection.execute(sql, bindArgs, cancellationSignal)
                connection.execute("PRAGMA kdf_iter = $SAMSUNG_KDF_ITER;", null, null)
                Log.d(TAG, "Applied Samsung device configuration: kdf_iter = $SAMSUNG_KDF_ITER")
                Log.d(TAG, "Device info: ${DeviceDetector.getDeviceInfo()}")
            } else {
                // OnePlus devices use default SQLCipher configuration
                // No need to set kdf_iter explicitly (uses default ~64000)
                Log.d(TAG, "Using default SQLCipher configuration for device")
                Log.d(TAG, "Device info: ${DeviceDetector.getDeviceInfo()}")
            }
            
            // Optional: Disable cipher memory security for better performance on constrained devices
            // This can provide 2-3x performance improvement but slightly reduces security
            // Uncomment if needed:
            // connection.execute("PRAGMA cipher_memory_security = OFF;", null, null)
            
        } catch (e: Exception) {
            Log.e(TAG, "Error applying SQLCipher hook configuration", e)
            // Don't throw - allow database to continue with default settings
        }
    }
}
