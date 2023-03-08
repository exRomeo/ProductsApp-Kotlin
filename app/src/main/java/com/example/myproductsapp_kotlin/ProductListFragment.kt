package com.example.myproductsapp_kotlin

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.myproductsapp_kotlin.databinding.FragmentProductListBinding
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream

const val TAG = "TAG"

class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding
    lateinit var adapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniRecyclerView()
        val workManager = WorkManager.getInstance(this.requireContext())
        val request =
            OneTimeWorkRequestBuilder<ProductsWorker>().addTag(Constants.PRODUCTS_WORKER).build()
        workManager.enqueue(request)
        workManager.getWorkInfosByTagLiveData(Constants.PRODUCTS_WORKER)
            .observe(this.requireActivity()) { workInfo ->
                val myInfo = workInfo.find { it.id == request.id }
                when (myInfo?.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        Log.i(TAG, "work info: success ")
                        val uri = myInfo.outputData.getString(Constants.FILE_NAME)
                        setList(retrieveList())
                    }
                    WorkInfo.State.RUNNING -> {
                        Log.i(TAG, "work info: Processing ")
                    }
                    else -> {
                        Log.i(TAG, "when's else block ?!")
                    }
                }
            }
    }


    fun setList(list: List<Product>) {
        adapter.submitList(list)
    }

    private fun iniRecyclerView() {
        adapter =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                ProductsAdapter() { product: Product ->
                    startActivity(
                        Intent(
                            this.context, SingleProductActivity::class.java
                        ).putExtra("product", product)
                    )
                }
            } else {
                ProductsAdapter { product: Product ->
                    (this.activity as ProductListActivity).product = product
                }
            }
        binding.productsList.layoutManager = LinearLayoutManager(this.requireContext())
        binding.productsList.adapter = adapter

    }

    private fun retrieveList(): List<Product> {
        val fis = FileInputStream(File(this.requireContext().cacheDir, "products"))
        val ois = ObjectInputStream(fis)
        return ois.readObject() as MutableList<Product>
    }
}