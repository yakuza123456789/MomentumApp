package com.azamat.momentumapp.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.azamat.momentumapp.R
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import java.util.concurrent.TimeUnit


class NotificationFragment : Fragment() {

    private lateinit var code: EditText
    private lateinit var codeBtn: Button
    private lateinit var timer: TextView
    var codeFromInet: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        codeFromInet = arguments?.getString("code")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        code = view.findViewById(R.id.editCode)
        codeBtn = view.findViewById(R.id.btnAccept)
        timer = view.findViewById(R.id.timeCounter)

        timerStart()

        codeBtn.setOnClickListener {
            val code = code.text.trim().toString()
            if (code.length < 5) {
                Toast.makeText(requireContext(), "Неверный код", Toast.LENGTH_LONG).show()
            }
            else
                verifyPhoneNumberWithCode(codeFromInet, code)
        }


    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        if (TextUtils.isEmpty(code)) {
            return
        }
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)

    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance(FirebaseApp.initializeApp(requireContext())!!)
            .signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_notificationFragment_to_firstFragment)
                }
            }
    }

    private fun timerStart() {
        object : CountDownTimer(60000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millis: Long) {
                var seconds = (millis / 1000).toInt()
                val minutes = seconds / 60
                seconds %= 30
                timer.text = "0" + String.format("%d:%02d", minutes, seconds) + " sec"
            }

            override fun onFinish() {
                timer.text = ""
            }
        }.start()
    }

}