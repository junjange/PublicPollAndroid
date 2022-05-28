package com.junjange.myapplication.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.junjange.myapplication.databinding.ActivityMainBinding
import com.junjange.myapplication.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private val RC_SIGN_IN = 9001
    private var googleSignInClient: GoogleSignInClient? = null
    //var callbackManager : CallbackManager? = null
    private var statePlatForm : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
//        callbackManager = CallbackManager.Factory.create()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("370829546500-t8028m9e7dn1o9v4rggenem175jbdajb.apps.googleusercontent.com")
            .requestEmail()
            .build()

        binding.startPublicPollBt.setOnClickListener {
            val intent = Intent(this, LocalSignUpActivity::class.java)
            startActivity(intent)
        }

        binding.localSignInBt.setOnClickListener {
            val intent = Intent(this, LocalSignInActivity::class.java)
            startActivity(intent)
        }

        binding.googleSignInBt.setOnClickListener {
            statePlatForm = "google"
            googleSignInClient = GoogleSignIn.getClient(this, gso)
            val signInIntent = googleSignInClient!!.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

//        binding.facebookSignInBt.setOnClickListener {
//            statePlatForm = "facebook"
//            facebookLogin()
//        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (statePlatForm.equals("google")) {
            if (requestCode == RC_SIGN_IN) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)

                try {
                    val account = task.getResult(ApiException::class.java)!!
                    viewModel.firebaseAuthWithGoogle(account.idToken!!, this)
                } catch (e: ApiException) {

                }
            }
        }
//        } else if (statePlatForm.equals("facebook")) {
//            callbackManager?.onActivityResult(requestCode,resultCode,data)
//        }
    }

    /*
    * private fun facebookLogin(){
        LoginManager.getInstance()
            .logInWithReadPermissions(this, Arrays.asList("public_profile","email"))

        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult) {
                    // 로그인 성공시
                    firebaseAuthWithFacebook(result.accessToken)
                    // 파이어베이스로 로그인 데이터를 넘겨줌
                }

                override fun onCancel() {

                }

                override fun onError(error: FacebookException) {

                }
            })
    }
    private fun firebaseAuthWithFacebook(accessToken: AccessToken?) {
        var credential = FacebookAuthProvider.getCredential(accessToken?.token!!)

        auth.signInWithCredential(credential)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    // 아이디, 비밀번호 맞을 때

                }else{
                    // 틀렸을 때
                }
            }

    }*/

}