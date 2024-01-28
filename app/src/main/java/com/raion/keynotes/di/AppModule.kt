package com.raion.keynotes.di

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.room.Room
import com.raion.keynotes.data.NoteDAO
import com.raion.keynotes.data.NoteDatabase
import com.raion.keynotes.data.TokenDAO
import com.raion.keynotes.data.TokenDatabase
import com.raion.keynotes.model.TokenClass
import com.raion.keynotes.network.RaionAPI
import com.raion.keynotes.repository.RaionAPIRepository
import com.raion.keynotes.screen.RaionAPIViewModel
import com.raion.keynotes.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.toList
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRaionRepository(api: RaionAPI, tokenDAO: TokenDAO) = RaionAPIRepository(api, tokenDAO)

    @Singleton
    @Provides
    fun provideRaionAPI(): RaionAPI{
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RaionAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideNoteDAO(NoteDatabase: NoteDatabase): NoteDAO
        = NoteDatabase.NoteDAO()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NoteDatabase
        = Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "note_db")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideTokenDAO(TokenDatabase: TokenDatabase): TokenDAO
        = TokenDatabase.TokenDAO()

    @Singleton
    @Provides
    fun provideAppDatabase2(@ApplicationContext context: Context): TokenDatabase
        = Room.databaseBuilder(
            context,
            TokenDatabase::class.java,
            "token_db"
        ).fallbackToDestructiveMigration()
        .build()
}