package sk.sksv.newsappcompose.domain.manager

/**
 * Securely provides the database passphrase for SQLCipher encryption.
 * Uses Android Keystore via EncryptedSharedPreferences - the passphrase
 * never exists as plain text in the source code or decompiled APK.
 */
interface SecurePassphraseManager {
    /**
     * Returns the database passphrase. On first call, generates a cryptographically
     * random 32-byte passphrase and stores it encrypted in Android Keystore.
     */
    fun getPassphrase(): ByteArray
}
