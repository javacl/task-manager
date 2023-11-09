package sample.task.manager.core.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import sample.task.manager.BuildConfig
import sample.task.manager.core.api.DefaultIfNullFactory
import sample.task.manager.core.api.TLSSocketFactory
import sample.task.manager.core.db.AppDb
import sample.task.manager.core.preferences.PreferencesDataStore
import sample.task.manager.core.util.alarmManager.AppAlarmManager
import sample.task.manager.core.util.notification.AppNotificationManager
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(
        @ApplicationContext context: Context
    ): PreferencesDataStore = PreferencesDataStore(context)

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(DefaultIfNullFactory())
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .dispatcher(Dispatcher(Executors.newFixedThreadPool(20)).apply {
            maxRequests = 20
            maxRequestsPerHost = 20
        })
        .connectionPool(ConnectionPool(100, 30L, TimeUnit.SECONDS))
        .addInterceptor(httpLoggingInterceptor)
        .readTimeout(30L, TimeUnit.SECONDS)
        .writeTimeout(30L, TimeUnit.SECONDS)
        .connectTimeout(30L, TimeUnit.SECONDS)
        .sslSocketFactory(
            TLSSocketFactory(),
            TLSSocketFactory().trustManager
        )
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(
            MoshiConverterFactory.create(moshi)
        )
        .client(okHttpClient)
        .baseUrl("https://run.mocky.io/v3/")
        .build()

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDb {
        return Room
            .databaseBuilder(
                context,
                AppDb::class.java,
                BuildConfig.APPLICATION_ID + ".db"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideAppAlarmManager(
        @ApplicationContext context: Context
    ): AppAlarmManager = AppAlarmManager(context)

    @Singleton
    @Provides
    fun provideAppNotificationManager(
        @ApplicationContext context: Context
    ): AppNotificationManager = AppNotificationManager(context)

    @Singleton
    @Provides
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): WorkManager = WorkManager.getInstance(context)
}
