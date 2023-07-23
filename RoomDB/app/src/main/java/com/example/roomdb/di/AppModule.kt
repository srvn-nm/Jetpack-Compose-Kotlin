package com.example.roomdb.di

import android.app.Application
import androidx.room.Room
import com.example.roomdb.data.ConverterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //for providing a database
    @Provides
    //for being a singleton object which will be created once and stored and used when needed
    @Singleton
    fun provideConvertorDataBase(app: Application):ConverterDatabase{
        return Room.databaseBuilder(
            app,
            ConverterDatabase::class.java,
            "converter_data_base"
        ).build()
    }

}