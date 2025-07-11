package edu.unibo.tracker.profile

import android.content.Context
import android.net.Uri
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileImageManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val PROFILE_IMAGE_DIR = "profile_images"
        private const val PROFILE_IMAGE_NAME = "profile_image.jpg"
    }

    private val profileImageDir: File by lazy {
        File(context.filesDir, PROFILE_IMAGE_DIR).apply {
            if (!exists()) mkdirs()
        }
    }

    private val profileImageFile: File by lazy {
        File(profileImageDir, PROFILE_IMAGE_NAME)
    }

    /**
     * Save image from URI to internal storage
     */
    fun saveProfileImage(imageUri: Uri): Boolean {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
            inputStream?.use { input ->
                FileOutputStream(profileImageFile).use { output ->
                    input.copyTo(output)
                }
            }
            Log.d("ProfileImageManager", "Profile image saved successfully")
            true
        } catch (e: Exception) {
            Log.e("ProfileImageManager", "Error saving profile image: ${e.message}")
            false
        }
    }

    /**
     * Get saved profile image URI
     */
    fun getProfileImageUri(): Uri? {
        return if (profileImageFile.exists()) {
            Uri.fromFile(profileImageFile)
        } else {
            null
        }
    }

    /**
     * Check if profile image exists
     */
    fun hasProfileImage(): Boolean {
        return profileImageFile.exists()
    }

    /**
     * Delete saved profile image
     */
    fun deleteProfileImage(): Boolean {
        return try {
            if (profileImageFile.exists()) {
                profileImageFile.delete()
            } else {
                true
            }
        } catch (e: Exception) {
            Log.e("ProfileImageManager", "Error deleting profile image: ${e.message}")
            false
        }
    }

    /**
     * Get profile image file path
     */
    fun getProfileImagePath(): String? {
        return if (profileImageFile.exists()) {
            profileImageFile.absolutePath
        } else {
            null
        }
    }
}