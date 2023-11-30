package com.example.login_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.login_kotlin.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class MainActivity : AppCompatActivity() {

    /**
     * lateinit -> inicia a variável posteriormente ou seja variável iniciada como null
     * var -> uma variável que pode ser alterada a qualquer momento
     * ActivityMainBinding -> classe de manipulação do Binding que é iniciada por padrão com o nome da xml sem "_"
     */
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * inflando mBinding com o objeto da Activity layoutInflater
         */
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        /**
         * val é uma variável que não será mais alterada igual a variáveis "final" do java
         * mBinding.root a variável recebe o root de mBinding da respectiva classe
         */
        val mViewActivityMain = mBinding.root

        /**
         * R.layout.activity_main é substituído no méotod setContentView(R.layout.activity_main) pelo view que manipula o binding da activity
         */
        setContentView(mViewActivityMain)

        val mTextBotaoLogar = mBinding.signinbuttonActivitymainGoogle.getChildAt(0) as TextView
        mTextBotaoLogar.text = getString(R.string.text_botao_google)
    }
}