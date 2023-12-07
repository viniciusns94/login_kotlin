package com.example.login_kotlin

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
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
            if (TextUtils.isEmpty(mBinding.edittexttextActivitymainUsuario.text)) {
                mBinding.edittexttextActivitymainUsuario.setError("Campo senha não pode ser vazio")
                Toast.makeText(mContext, "Por favor, preencha o campo de usuário", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(mBinding.edittexttextActivitymainSenha.text)) {
                mBinding.edittexttextActivitymainSenha.setError("Campo senha não pode ser vazio")
                Toast.makeText(mContext, "Por favor, preencha o campo de senha", Toast.LENGTH_SHORT).show()
            } else {
                loginUsuarioESenha(
                    mBinding.edittexttextActivitymainUsuario.text.toString(),
                    mBinding.edittexttextActivitymainSenha.text.toString()
                )
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
                    abrePrincipal()
                    finish()
//                            updateUI(user)
                } else {
                    /** If sign in fails, display a message to the user. **/
                    Log.w(TAG, "signInWithCustomToken:failure", task.exception)
                    Toast.makeText(mContext, "Erro de autententicação", Toast.LENGTH_SHORT).show()
//                            updateUI(null)
                }
            }
    }

    private fun abrePrincipal() {
        mBinding.edittexttextActivitymainUsuario.setText("")
        mBinding.edittexttextActivitymainSenha.setText("")

        Toast.makeText(mContext, "Autententicação efetuada com sucesso", Toast.LENGTH_SHORT).show()
        val mIntent = Intent(this, PrincipalActivity::class.java).apply {
            //putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(mIntent)
    }

    public override fun onStart() {
        super.onStart()

        /** Check if user is signed in (non-null) and update UI accordingly.*/
        val currentUser = mAuth.currentUser
        try {
            if (currentUser != null) {
                Toast.makeText(mContext, "Usuário" + currentUser.email + " logado", Toast.LENGTH_LONG).show()
                abrePrincipal()
            }
        } catch (_: Exception) {

        }
//        updateUI(currentUser)
    }
}