package com.example.roomdb.di

import android.app.Application
import androidx.room.Room
import com.example.roomdb.data.ConverterDatabase
import com.example.roomdb.data.ConverterRepository
import com.example.roomdb.data.ConverterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //for converterDataBase abstract class
    //for providing a database
    @Provides
    //for being a singleton object which will be created once and stored and used when needed
    @Singleton
    fun provideConvertorDataBase(app: Application): ConverterDatabase {
        return Room.databaseBuilder(
            app,
            ConverterDatabase::class.java,
            "converter_data_base"
        ).build()
    }


    //for converterRepository interface
    //because  converter data base is already in the hilt graph, we don't have to inject it. hilt library will do it itself.
    @Provides
    @Singleton
    fun provideConverterRepository(db: ConverterDatabase): ConverterRepository {
        return ConverterRepositoryImpl(db.converterDAO)
    }
}