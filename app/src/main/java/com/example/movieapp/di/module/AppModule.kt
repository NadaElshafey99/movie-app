package com.example.movieapp.di.module

import com.example.movieapp.data.remote.remote_data_source.IRemoteDataSource
import com.example.movieapp.data.remote.remote_data_source.RemoteDataSourceImp
import com.example.movieapp.repositories.movie_repository.IMovieRepository
import com.example.movieapp.repositories.movie_repository.MovieRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(
        remoteDataSource: RemoteDataSourceImp
    ): IRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieRepositoryImp: MovieRepositoryImp
    ): IMovieRepository
}