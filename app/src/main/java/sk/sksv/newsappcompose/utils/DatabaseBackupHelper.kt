package sk.sksv.newsappcompose.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

object DatabaseBackupHelper {

    fun backupDatabase(context: Context) {
        val dbName = Constants.NEWS_DATABASE_NAME
        val dbPath = context.getDatabasePath(dbName).absolutePath
        val dbFile = File(dbPath)
        val walFile = File(dbPath + "-wal")
        val shmFile = File(dbPath + "-shm")

        if (!dbFile.exists()) {
            Log.e("Backup", "Database file not found: $dbPath")
            return
        }

        // Try to save to "Documents/newsDatabase" directory
        val backupDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "newsDatabase"
        )
        
        // Fallback to app-specific external storage if Documents is not accessible or writable (simplified check)
        val targetDir = if (!backupDir.mkdirs() && !backupDir.exists()) {
             File(context.getExternalFilesDir(null), "newsDatabase")
        } else {
            backupDir
        }

        if (!targetDir.exists()) {
            targetDir.mkdirs()
        }

        try {
            copyFile(dbFile, File(targetDir, dbName))
            if (walFile.exists()) copyFile(walFile, File(targetDir, "$dbName-wal"))
            if (shmFile.exists()) copyFile(shmFile, File(targetDir, "$dbName-shm"))
            Log.d("Backup", "Database backed up successfully to: ${targetDir.absolutePath}")
        } catch (e: IOException) {
            Log.e("Backup", "Error backing up database", e)
        }
    }

    private fun copyFile(source: File, destination: File) {
        FileInputStream(source).use { input ->
            FileOutputStream(destination).use { output ->
                input.copyTo(output)
            }
        }
    }

    fun restoreDatabase(context: Context): Boolean {
        val dbName = Constants.NEWS_DATABASE_NAME
        val dbPath = context.getDatabasePath(dbName).absolutePath
        val dbFile = File(dbPath)
        val walFile = File(dbPath + "-wal")
        val shmFile = File(dbPath + "-shm")

        // 1. Define Backup Source
        val backupDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "newsDatabase"
        )
        
        // Check primary backup (Documents) then fallback
        var sourceDir = backupDir
        if (!File(sourceDir, dbName).exists()) {
             sourceDir = File(context.getExternalFilesDir(null), "newsDatabase")
        }

        if (!File(sourceDir, dbName).exists()) {
            Log.e("Restore", "No backup found in Documents or App External Files")
            return false
        }

        // 2. Perform Restore (Overwrite current DB)
        try {
            val sourceDb = File(sourceDir, dbName)
            val sourceWal = File(sourceDir, "$dbName-wal")
            val sourceShm = File(sourceDir, "$dbName-shm")

            // Ensure destination directory exists (should exist if app ran, but good practice)
            dbFile.parentFile?.mkdirs()

            copyFile(sourceDb, dbFile)
            
            // Handle WAL and SHM
            if (sourceWal.exists()) copyFile(sourceWal, walFile) else walFile.delete()
            if (sourceShm.exists()) copyFile(sourceShm, shmFile) else shmFile.delete()

            Log.d("Restore", "Database restored successfully from: ${sourceDir.absolutePath}")
            return true
        } catch (e: IOException) {
            Log.e("Restore", "Error restoring database", e)
            return false
        }
    }
}
