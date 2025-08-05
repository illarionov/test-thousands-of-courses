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
import androidx.navigation.fragment.findNavController
import com.example.thcourses.feature.auth.impl.R
import com.example.thcourses.feature.auth.impl.databinding.FragmentLoginBinding
import com.example.thcourses.feature.auth.presentation.login.ExternalUrl.OK_RU_URL
import com.example.thcourses.feature.auth.presentation.login.ExternalUrl.VK_COM_URL
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import com.example.thcourses.core.navigation.R as navR

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
        with(binding) {
            emailInput.doOnTextChanged { text, _, _, _ ->
                viewModel.onEmailChanged(text.toString())
            }

            passwordInput.doOnTextChanged { text, _, _, _ ->
                viewModel.onPasswordChanged(text.toString())
            }

            confirmPasswordInput.doOnTextChanged { text, _, _, _ ->
                viewModel.onConfirmPasswordChanged(text.toString())
            }

            registerButton.setOnClickListener {
                viewModel.login()
            }

            loginLink.setOnClickListener {
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
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.registerButton.isEnabled = !isLoading
            binding.registerButton.text = if (isLoading) "Загрузка..." else "Регистрация"
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Snackbar.make(requireView(), error, Snackbar.LENGTH_LONG).show()
        }

        viewModel.success.observe(viewLifecycleOwner) {
            findNavController().navigate(navR.id.nav_destination_root_home)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
