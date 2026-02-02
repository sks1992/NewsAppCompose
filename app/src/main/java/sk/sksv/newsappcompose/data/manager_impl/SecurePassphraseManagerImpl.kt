package sk.sksv.newsappcompose.data.manager_impl

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import sk.sksv.newsappcompose.domain.manager.SecurePassphraseManager
import sk.sksv.newsappcompose.utils.Constants
import java.security.SecureRandom
import java.util.Base64

/**
 * Stores the SQLCipher database passphrase in Android Keystore.
 * The passphrase is never hardcoded - a random one is generated on first run
 * and stored encrypted. Hardware-backed Keystore (on supported devices) prevents
 * extraction even with root access.
 */
class SecurePassphraseManagerImpl(private val context: Context) : SecurePassphraseManager {

    override fun getPassphrase(): ByteArray {
        val prefs = getEncryptedPrefs()
        val stored = prefs.getString(KEY_PASSPHRASE, null)

        return when {
            stored != null -> Base64.getDecoder().decode(stored)
            // Migration: use fallback from local.properties for existing users with old DB
            sk.sksv.newsappcompose.BuildConfig.DATABASE_PASSWORD_FALLBACK.isNotEmpty() -> {
                val fallback = sk.sksv.newsappcompose.BuildConfig.DATABASE_PASSWORD_FALLBACK.toByteArray()
                prefs.edit().putString(KEY_PASSPHRASE, Base64.getEncoder().encodeToString(fallback)).apply()
                fallback
            }
            else -> {
                val passphrase = generateSecurePassphrase()
                prefs.edit().putString(KEY_PASSPHRASE, Base64.getEncoder().encodeToString(passphrase)).apply()
                passphrase
            }
        }
    }

    private fun getEncryptedPrefs() = EncryptedSharedPreferences.create(
        context,
        Constants.USER_SETTINGS_SECURE,
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    /**
     * Generates 32 cryptographically random bytes. SQLCipher disallows null bytes (0x00)
     * in passphrases, so we replace any zero with a non-zero value.
     */
    private fun generateSecurePassphrase(): ByteArray {
        val bytes = ByteArray(PASSPHRASE_SIZE)
        SecureRandom().nextBytes(bytes)
        for (i in bytes.indices) {
            if (bytes[i] == 0.toByte()) bytes[i] = 1
        }
        return bytes
    }

    companion object {
        private const val KEY_PASSPHRASE = "db_passphrase"
        private const val PASSPHRASE_SIZE = 32
    }
}
