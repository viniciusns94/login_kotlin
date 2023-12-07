package com.example.login_kotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.login_kotlin.databinding.ActivityPrincipalBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class PrincipalActivity : AppCompatActivity() {

    private lateinit var mContext: Context
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mBinding: ActivityPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this
        /**
         * inflando mBinding com o objeto da Activity layoutInflater
         */
        mBinding = ActivityPrincipalBinding.inflate(layoutInflater)

        /**
         * val é uma variável que não será mais alterada igual a variáveis "final" do java
         * mBinding.root a variável recebe o root de mBinding da respectiva classe
         */
        val mPrincipalActivity = mBinding.root

        /** Initialize Firebase Auth (criado a partir das ferramentas do firebaso no androidStudio) **/
        mAuth = Firebase.auth

        /** R.layout.activity_main é substituído no méotod setContentView(R.layout.activity_main) pelo view que manipula o binding da activity */
        setContentView(mPrincipalActivity)

        mBinding.activityprincipalButtonSair.setOnClickListener {
            mAuth.signOut()
            val mIntent = Intent(this, MainActivity::class.java).apply {}
            startActivity(mIntent)
            finish()
        }
    }
}