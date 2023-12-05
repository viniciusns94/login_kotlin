package com.example.login_kotlin

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.login_kotlin.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    /**
     * lateinit -> inicia a variável posteriormente ou seja variável iniciada como null
     * var -> uma variável que pode ser alterada a qualquer momento
     * ActivityMainBinding -> classe de manipulação do Binding que é iniciada por padrão com o nome da xml sem "_"
     */
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this
        /**
         * inflando mBinding com o objeto da Activity layoutInflater
         */
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        /**
         * val é uma variável que não será mais alterada igual a variáveis "final" do java
         * mBinding.root a variável recebe o root de mBinding da respectiva classe
         */
        val mViewActivityMain = mBinding.root

        /** R.layout.activity_main é substituído no méotod setContentView(R.layout.activity_main) pelo view que manipula o binding da activity */
        setContentView(mViewActivityMain)

        /** Initialize Firebase Auth (criado a partir das ferramentas do firebaso no androidStudio) **/
        mAuth = Firebase.auth

        val mTextBotaoLogar = mBinding.signinbuttonActivitymainGoogle.getChildAt(0) as TextView
        mTextBotaoLogar.text = getString(R.string.text_botao_google)

        mBinding.buttonActivitymainEntrar.setOnClickListener {
            try {
                loginUsuarioESenha(
                    mBinding.edittexttextActivitymainUsuario.text.toString(),
                    mBinding.edittexttextActivitymainSenha.text.toString()
                )
            } catch (e: Exception) {
                Toast.makeText(mContext, "Por favor preencha todos os campos para o login", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loginUsuarioESenha(usuario: String, senha: String) {
        mAuth.signInWithEmailAndPassword(usuario, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    /** Sign in success, update UI with the signed-in user's information **/
                    Log.d(TAG, "signInWithCustomToken:success")
                    val user = mAuth.currentUser
                    limpaCampos()
                    Toast.makeText(mContext, "Autententicação efetuada com sucesso", Toast.LENGTH_SHORT).show()
                    val mIntent = Intent(this, PrincipalActivity::class.java).apply {
                        //putExtra(EXTRA_MESSAGE, message)
                    }
                    startActivity(mIntent)
//                            updateUI(user)
                } else {
                    /** If sign in fails, display a message to the user. **/
                    Log.w(TAG, "signInWithCustomToken:failure", task.exception)
                    Toast.makeText(mContext, "Erro de autententicação", Toast.LENGTH_SHORT).show()
//                            updateUI(null)
                }
            }
    }
// MINUTO 17:53 https://www.youtube.com/watch?v=_7YMA6awb7Y&list=PLHI7bDSQYkJjTcDfc5UNL2f6Cwd5-33Vr&index=4
    private fun limpaCampos() {
        mBinding.edittexttextActivitymainUsuario.setText("")
        mBinding.edittexttextActivitymainSenha.setText("")
    }

    public override fun onStart() {
        super.onStart()

        /** Check if user is signed in (non-null) and update UI accordingly.*/
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
    }
}