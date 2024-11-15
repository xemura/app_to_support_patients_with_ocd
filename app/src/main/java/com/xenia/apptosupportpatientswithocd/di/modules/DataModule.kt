package com.xenia.apptosupportpatientswithocd.di.modules

import com.xenia.apptosupportpatientswithocd.data.repository.AuthRepositoryImpl
import com.xenia.apptosupportpatientswithocd.data.repository.HomeworkRepositoryImpl
import com.xenia.apptosupportpatientswithocd.data.repository.ModulesRepositoryImpl
import com.xenia.apptosupportpatientswithocd.data.repository.MoodRepositoryImpl
import com.xenia.apptosupportpatientswithocd.data.repository.ProfileRepositoryImpl
import com.xenia.apptosupportpatientswithocd.data.repository.ScriptsRepositoryImpl
import com.xenia.apptosupportpatientswithocd.di.ApplicationScope
import com.xenia.apptosupportpatientswithocd.domain.repository.AuthRepository
import com.xenia.apptosupportpatientswithocd.domain.repository.HomeworkRepository
import com.xenia.apptosupportpatientswithocd.domain.repository.ModulesRepository
import com.xenia.apptosupportpatientswithocd.domain.repository.MoodRepository
import com.xenia.apptosupportpatientswithocd.domain.repository.ProfileRepository
import com.xenia.apptosupportpatientswithocd.domain.repository.ScriptsRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @ApplicationScope
    @Binds
    fun bindHomeworkRepository(impl: HomeworkRepositoryImpl): HomeworkRepository

    @ApplicationScope
    @Binds
    fun bindModulesRepository(impl: ModulesRepositoryImpl): ModulesRepository

    @ApplicationScope
    @Binds
    fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @ApplicationScope
    @Binds
    fun bindMoodsRepository(impl: MoodRepositoryImpl): MoodRepository

    @ApplicationScope
    @Binds
    fun bindScriptsRepository(impl: ScriptsRepositoryImpl): ScriptsRepository
}