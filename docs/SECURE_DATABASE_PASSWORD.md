# Secure Database Password Documentation

This document describes how the database passphrase is secured in the News App using Android Keystore and EncryptedSharedPreferences.

---

## Overview

The app uses **SQLCipher** to encrypt the Room database. Previously, the passphrase was hardcoded in source code, which meant:
- It was visible in version control
- It could be extracted from decompiled APKs
- The same password was used for all users

The current implementation generates a **unique, random passphrase per installation** and stores it using **Android Keystore**—a hardware-backed, tamper-resistant secure storage.

---

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                     Secure Passphrase Flow                       │
└─────────────────────────────────────────────────────────────────┘

  First Launch                    Subsequent Launches
       │                                  │
       ▼                                  ▼
┌──────────────┐                   ┌──────────────────┐
│ No stored    │                   │ Passphrase in    │
│ passphrase   │                   │ EncryptedSP      │
└──────┬───────┘                   └────────┬─────────┘
       │                                    │
       ▼                                    │
┌──────────────────┐                        │
│ Generate 32-byte │                        │
│ random passphrase│                        │
└────────┬─────────┘                        │
         │                                  │
         ▼                                  ▼
┌──────────────────────────────────────────────┐
│     EncryptedSharedPreferences               │
│     (Backed by Android Keystore)             │
│     - Key never leaves device                │
│     - Hardware-backed on supported devices   │
└────────────────────┬─────────────────────────┘
                     │
                     ▼
         ┌───────────────────────┐
         │  SQLCipher Database   │
         │  (News Room DB)       │
         └───────────────────────┘
```

---

## Step-by-Step Implementation

### Step 1: Add the Security Library

In `gradle/libs.versions.toml`:
```toml
securityCrypto = "1.1.0-alpha06"

androidx-security-crypto = { module = "androidx.security:security-crypto", version.ref = "securityCrypto" }
```

In `app/build.gradle.kts`:
```kotlin
implementation(libs.androidx.security.crypto)
```

### Step 2: Create the Secure Passphrase Manager

**Interface** (`domain/manager/SecurePassphraseManager.kt`):
- Defines `getPassphrase(): ByteArray` to provide the passphrase on demand

**Implementation** (`data/manager_impl/SecurePassphraseManagerImpl.kt`):
1. On first call: generates 32 cryptographically random bytes (no null bytes for SQLCipher compatibility)
2. Stores the passphrase in EncryptedSharedPreferences (Base64-encoded)
3. On subsequent calls: retrieves and decodes the stored passphrase
4. Uses Android Keystore via `MasterKey.Builder` with AES256_GCM

### Step 3: Integrate with Dependency Injection

In `di/AppModule.kt`:
- Provide `SecurePassphraseManager` implementation
- Inject it into `provideNewsDatabase()`
- Replace `Constants.DATABASE_PASSWORD.toByteArray()` with `securePassphraseManager.getPassphrase()`

### Step 4: Remove Hardcoded Credentials

- Remove `DATABASE_PASSWORD` from `Constants.kt`
- Remove any `Log` statements that print the passphrase
- Never commit secrets to version control

---

## Migration for Existing Users

If you previously shipped the app with a hardcoded password (e.g., `"12345"`), existing users will have databases encrypted with that password. To migrate without losing data:

### One-Time Migration Setup

1. Add to `local.properties` (this file is in `.gitignore`—do not commit):
   ```properties
   DATABASE_PASSWORD_FALLBACK=12345
   ```

2. Build and ship one release with this fallback enabled.

3. On app launch, when no passphrase exists in secure storage:
   - The app uses the fallback from BuildConfig
   - Stores it in EncryptedSharedPreferences (now encrypted)
   - Database opens successfully with existing data

4. In the next release, remove `DATABASE_PASSWORD_FALLBACK` from `local.properties`.

5. Existing users already have the passphrase in secure storage—no action needed. New installs get a fresh random passphrase.

---

## Security Best Practices Checklist

| Practice | Status |
|----------|--------|
| No hardcoded passwords in source | ✓ |
| No logging of credentials | ✓ |
| Per-device unique passphrase | ✓ |
| Hardware-backed keystore (where supported) | ✓ |
| Passphrase never leaves app sandbox | ✓ |
| Migration path for existing users | ✓ |

---

## Security Considerations

1. **Android Keystore**: On devices with hardware support (most modern devices), keys are stored in a secure enclave and cannot be extracted—even with root access.

2. **Decompilation**: The passphrase is never in your source code, so it won't appear in decompiled APKs. Only the encrypted blob is stored.

3. **ProGuard/R8**: For release builds, consider enabling `isMinifyEnabled = true` to obfuscate code and make reverse engineering harder.

4. **Backup**: If the user backs up the app, the EncryptedSharedPreferences data may be included. Use `android:allowBackup="false"` for sensitive apps, or configure backup rules to exclude secure preferences.

---

## File Reference

| File | Purpose |
|------|---------|
| `domain/manager/SecurePassphraseManager.kt` | Interface for passphrase retrieval |
| `data/manager_impl/SecurePassphraseManagerImpl.kt` | Implementation using EncryptedSharedPreferences |
| `di/AppModule.kt` | Provides SecurePassphraseManager and injects into NewsDatabase |
| `utils/Constants.kt` | Contains `USER_SETTINGS_SECURE` for encrypted prefs file name |

---

## Troubleshooting

**"GeneralSecurityException" or "UnrecoverableKeyException"**
- Can occur after app reinstall or Keystore reset (e.g., factory reset)
- User will need to clear app data—database will be recreated with new passphrase
- Consider adding error handling to show a user-friendly message

**Migration fallback not working**
- Ensure `DATABASE_PASSWORD_FALLBACK` is in `local.properties` (project root)
- Ensure `buildConfig = true` in `buildFeatures` in `app/build.gradle.kts`
- Rebuild the project after adding the property
  