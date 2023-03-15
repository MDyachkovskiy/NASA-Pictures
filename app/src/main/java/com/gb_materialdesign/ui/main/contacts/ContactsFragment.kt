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

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ContactsViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(ContactsViewModel::class.java)
    }

    private val callbackAdd = AddItem {user, position ->
        viewModel.addContact(user, position)
        val newContacts = viewModel.getUpdatedContacts()
        adapter.setNewContactsListAfterAdd(newContacts,position)
    }

    private val callbackRemove = RemoveItem {
        viewModel.deleteContact(it)
        val newContacts = viewModel.getUpdatedContacts()
        adapter.setNewContactsListAfterRemove(newContacts,it)
    }

    private val callbackMove = MoveItem{user, moveBy, position ->
        when(moveBy){
            1 -> {
                viewModel.moveContact(user,moveBy)
                val newContacts = viewModel.getUpdatedContacts()
                adapter.setNewContactsAfterMoveUp(newContacts,position)
            }
            -1 -> {
                viewModel.moveContact(user,moveBy)
                val newContacts = viewModel.getUpdatedContacts()
                adapter.setNewContactsAfterMoveDown(newContacts,position)
            }
        }
    }

    lateinit var adapter: ContactsListAdapter

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
            adapter = ContactsListAdapter(contacts, callbackAdd, callbackRemove, callbackMove)
            initRV()
        }
        viewModel.getContacts()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initRV(){
        binding.contactsList.let{
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }
}