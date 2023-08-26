package dev.youdroid.findit.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.youdroid.findit.di.AppModule.DataSourceModule
import dev.youdroid.findit.di.AppModule.RepositoryModule
import dev.youdroid.findit.domain.local.LocalSource
import dev.youdroid.findit.domain.local.LocalSourceImplementation
import dev.youdroid.findit.domain.repositories.QuizRepository
import dev.youdroid.findit.domain.repositories.QuizRepositoryImplementation


@Module(
    includes = [
        RepositoryModule::class,
        DataSourceModule::class,
        AppModule.Common::class
    ]
)
@InstallIn(SingletonComponent::class)
object AppModule {


    @Module
    @InstallIn(SingletonComponent::class)
    class Common {
        @Provides
        fun provideGson(): Gson {
            return GsonBuilder()
                .create()
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Binds
        fun provideQuizRepository(quizRepositoryImplementation: QuizRepositoryImplementation): QuizRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Binds
        fun provideLocalSource(localSourceImplementation: LocalSourceImplementation): LocalSource
    }

}