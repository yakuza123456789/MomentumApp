package com.azamat.momentumapp.ui.auth

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.azamat.momentumapp.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class AuthFragment : Fragment() {

    private lateinit var userPhone: EditText
    private lateinit var enter: Button
    private lateinit var auth: FirebaseAuth




    private var phoneNumber = ""
    private var codeFromInet: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCallback()
        init(view)


    }

    private fun initCallback() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.e("fail", "onVerificationFailed " + p0.message)
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                codeFromInet = p0
                resendToken = p1
                var bundle = Bundle()
                bundle.putString("code", codeFromInet)
                findNavController().navigate(
                    R.id.action_authFragment_to_notificationFragment,
                    bundle
                )
            }
        }
    }

    private fun init(view: View) {
        auth = FirebaseAuth.getInstance()
        userPhone = view.findViewById(R.id.phoneAuth)
        enter = view.findViewById(R.id.enter)
        enter.setOnClickListener {

                requestOTP()
        }
    }

    private fun requestOTP() {
        phoneNumber = userPhone.text.toString()

        if (TextUtils.isEmpty(phoneNumber)) {
            userPhone.error = "Phone number is required"
            userPhone.requestFocus()
            return
        }
        Toast.makeText(requireContext(), phoneNumber, Toast.LENGTH_LONG).show()
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                // Activity (for callback binding)
            .setCallbacks(callbacks!!)          // OnVerificationStateChangedCallbacks
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)




    }
}
//
