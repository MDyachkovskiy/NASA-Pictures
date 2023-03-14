package com.gb_materialdesign.ui.main.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gb_materialdesign.adapters.ContactsListAdapter
import com.gb_materialdesign.databinding.FragmentContactsBinding
import com.gb_materialdesign.model.contacts.User

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ContactsViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(ContactsViewModel::class.java)
    }

    companion object {
        fun newInstance() = ContactsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getLiveData().observe(viewLifecycleOwner) { contacts ->
            initRV(contacts)
        }
        viewModel.getContacts()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initRV(contacts: List<User>){
        binding.contactsList?.let{
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = ContactsListAdapter(contacts)
        }
    }
}