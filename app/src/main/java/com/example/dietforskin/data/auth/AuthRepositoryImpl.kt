package com.example.dietforskin.data.auth

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(private val firebaseAuth: FirebaseAuth) : AuthRepository {

    override fun loginUser(email: String, password: String): Flow<Resource<AuthRepositoryImpl>> {
        return flow<Resource<AuthRepositoryImpl>> {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result)
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun registerUser(email: String, password: String): Flow<Resource<AuthRepositoryImpl>> {
        return flow<Resource<AuthRepositoryImpl>> {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Resource.Success(result)
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }
}