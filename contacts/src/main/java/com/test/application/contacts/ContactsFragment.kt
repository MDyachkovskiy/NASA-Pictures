package com.test.application.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.gb_materialdesign.adapters.ContactsListAdapter
import com.gb_materialdesign.adapters.ItemTouchHelperCallback
import com.gb_materialdesign.databinding.FragmentContactsBinding
import com.gb_materialdesign.model.contacts.User

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

    private val callbackDragAndMove = DragAndMoveItem{user, fromPosition,toPosition ->
        viewModel.deleteContact(fromPosition)
        viewModel.addContact(user,toPosition)
        val newContacts = viewModel.getUpdatedContacts()
        adapter.setNewContactsAfterMove(newContacts,fromPosition, toPosition)
    }

    private lateinit var adapter: ContactsListAdapter

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
        adapter = ContactsListAdapter(contacts, callbackAdd, callbackRemove, callbackMove, callbackDragAndMove)
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.contactsList)
        binding.contactsList.let{
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }
}