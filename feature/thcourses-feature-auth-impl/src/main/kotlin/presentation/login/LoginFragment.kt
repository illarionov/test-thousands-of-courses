package com.example.thcourses.feature.auth.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.thcourses.feature.auth.impl.R
import com.example.thcourses.feature.auth.impl.databinding.FragmentLoginBinding
import com.example.thcourses.feature.auth.presentation.login.ExternalUrl.OK_RU_URL
import com.example.thcourses.feature.auth.presentation.login.ExternalUrl.VK_COM_URL
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        with(binding.loginForm) {
            viewModel.onEmailChanged(emailInput.text.toString())
            emailInput.doOnTextChanged { text, _, _, _ ->
                viewModel.onEmailChanged(text.toString())
            }

            viewModel.onPasswordChanged(passwordInput.text.toString())
            passwordInput.doOnTextChanged { text, _, _, _ ->
                viewModel.onPasswordChanged(text.toString())
            }

            loginButton.setOnClickListener {
                viewModel.onLoginClicked()
            }

            registerLink.setOnClickListener {
                findNavController().navigate(R.id.action_login_to_registration)
            }

            vkButton.setOnClickListener {
                openExternalUrl(VK_COM_URL)
            }

            okButton.setOnClickListener {
                openExternalUrl(OK_RU_URL)
            }
        }
    }

    private fun openExternalUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            startActivity(intent)
        } catch (_: Exception) {
            Snackbar.make(
                requireView(),
                getString(R.string.error_can_not_open_url),
                Snackbar.LENGTH_SHORT,
            ).show()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isLoginButtonEnabled.collect {
                        binding.loginForm.loginButton.isEnabled = it
                    }
                }
                launch {
                    viewModel.events.collect(::handleEvent)
                }
            }
        }
    }

    private fun handleEvent(event: LoginViewModel.Label) {
        when (event) {
            LoginViewModel.Label.LoginSuccess -> {
                findNavController().navigate(R.id.action_auth_to_home)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
