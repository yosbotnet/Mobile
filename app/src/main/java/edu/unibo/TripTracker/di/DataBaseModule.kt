package edu.unibo.tracker.di

import edu.unibo.tracker.Database.FitnessTrackerDatabase
import dagger.hilt.InstallIn

import android.content.Context
import androidx.room.Room
import edu.unibo.tracker.Database.WorkoutDAO
import edu.unibo.tracker.Database.TrackDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.unibo.tracker.Database.UserDAO
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideUserDao(fitnessTrackerDatabase: FitnessTrackerDatabase): UserDAO {
        return fitnessTrackerDatabase.userDao()
    }

    @Provides
    fun provideWorkoutDao(fitnessTrackerDatabase: FitnessTrackerDatabase): WorkoutDAO {

        return fitnessTrackerDatabase.workoutDao()
    }
    @Provides
    fun provideTrackDao(fitnessTrackerDatabase: FitnessTrackerDatabase): TrackDAO {

        return fitnessTrackerDatabase.trackDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): FitnessTrackerDatabase {
        return Room.databaseBuilder(
            appContext as BaseApplication,
            FitnessTrackerDatabase::class.java,
            "fitness_tracker_db"
        ).fallbackToDestructiveMigration().build()
    }
}