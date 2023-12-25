package com.test.application.contacts.view

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.application.contacts.adapter.ContactsListAdapter
import com.test.application.contacts.adapter.ItemTouchHelperCallback
import com.test.application.contacts.databinding.FragmentContactsBinding
import com.test.application.core.domain.contacts.User
import com.test.application.core.view.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactsFragment : BaseFragment<List<User>, FragmentContactsBinding>(
    FragmentContactsBinding::inflate
){

    private val viewModel: ContactsViewModel by viewModel()

    private lateinit var adapter: ContactsListAdapter

    companion object {
        fun newInstance() = ContactsFragment()
    }

    override fun findProgressBar(): FrameLayout {
        return binding.progressBar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { appState ->
                    renderData(appState)
                }
            }
        }
        viewModel.getContacts()
    }

    override fun setupData(data: List<User>) {
        adapter = ContactsListAdapter()

        adapter.onItemAdd = {user, position ->
            viewModel.addContact(user, position)
            updateContacts()
        }

        adapter.onItemRemove = { position ->
            viewModel.deleteContact(position)
            updateContacts()
        }

        adapter.onItemMoved = {user, moveBy, position ->
            viewModel.moveContact(user, moveBy)
            updateContacts()
        }

        adapter.onItemDragAndMove = { user, fromPosition, toPosition ->
            viewModel.deleteContact(fromPosition)
            viewModel.addContact(user, toPosition)
            updateContacts()
        }

        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.contactsList)

        binding.contactsList.let{
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter
        }
    }

    private fun updateContacts() {
        val newContacts = viewModel.getUpdatedContacts()
        adapter.updateContacts(newContacts)
    }
}