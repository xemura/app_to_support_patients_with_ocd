package com.xenia.apptosupportpatientswithocd.data.repository

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xenia.apptosupportpatientswithocd.domain.entity.UserModel
import com.xenia.apptosupportpatientswithocd.domain.repository.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStoreDatabase: FirebaseFirestore
) : ProfileRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val currentUserUID = firebaseAuth.currentUser?.uid
    private var user: UserModel = UserModel("Not found", false, "Not found")

    private val userInfo = flow {
        val userDeferred = coroutineScope.async {
            val value = fireStoreDatabase.collection("$currentUserUID")
                .document("userInfo")
                .get()
                .await()

            //Log.d("TAG", "value is done")
            val notificationEnable = value?.data?.getValue("notificationEnable").toString() != "false"

            //Log.d("TAG", "get user")
            user = UserModel(
                name = value?.data?.getValue("name").toString(),
                notificationEnable = notificationEnable,
                notificationTime = value?.data?.getValue("notificationTime").toString()
            )

            user
        }

        emit(userDeferred.await())
    }

    override fun getUserInfo(): Flow<UserModel> = userInfo
    override fun updateUserInfo(
        name: String,
        notificationEnable: Boolean,
        notificationTime: String
    ) {
        fireStoreDatabase.collection("$currentUserUID")
            .document("userInfo")
            .set(UserModel(name, notificationEnable, notificationTime))
            .addOnSuccessListener {
                Log.d("TAG", "SUCCESS")
            }
            .addOnFailureListener {
                Log.d("TAG", "FAIL")
            }
    }
}