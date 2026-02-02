package sk.sksv.newsappcompose.data.manager_impl

import sk.sksv.newsappcompose.domain.manager.SecurePassphraseManager

/**
 * Simple passphrase manager that returns a constant password for all devices.
 * This allows databases to be copied between devices and opened with the same password.
 * 
 * ⚠️ SECURITY NOTE: This approach uses a shared password across all devices.
 * While less secure than per-device passwords, it enables database portability.
 * 
 * For better security, consider:
 * - Using ProGuard/R8 to obfuscate the password
 * - Deriving password from app signature or package name
 * - Using a more complex password
 */
class SimplePassphraseManagerImpl : SecurePassphraseManager {
    
    override fun getPassphrase(): ByteArray {
        // Return constant password that works on all devices
        return DATABASE_PASSWORD.toByteArray(Charsets.UTF_8)
    }
    
    companion object {
        /**
         * Database password - same for all devices.
         * This allows copying database files between devices.
         * 
         * ⚠️ Change this to a stronger password in production!
         */
        private const val DATABASE_PASSWORD = "12345"
    }
}
