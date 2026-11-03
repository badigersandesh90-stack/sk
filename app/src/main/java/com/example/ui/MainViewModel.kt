package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.AiResponse
import com.example.ai.AiService
import com.example.data.AppDatabase
import com.example.data.Enquiry
import com.example.data.Product
import com.example.data.SampleData
import com.example.data.StoreConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface AiUiState {
    data object Idle : AiUiState
    data object Thinking : AiUiState
    data class Success(val response: AiResponse) : AiUiState
    data class Error(val message: String) : AiUiState
}

enum class Screen(val title: String) {
    HOME("Home"),
    PRODUCTS("Products"),
    SERVICES("Services"),
    AI_ADVISOR("AI Tech Advisor"),
    CONTACT("Contact Us"),
    ADMIN("Admin Dashboard")
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application, viewModelScope)
    private val productDao = db.productDao()
    private val enquiryDao = db.enquiryDao()
    private val storeConfigDao = db.storeConfigDao()
    private val aiService = AiService()

    val currentScreen = MutableStateFlow(Screen.HOME)

    val selectedCategory = MutableStateFlow("All")
    val searchQuery = MutableStateFlow("")
    val conditionFilter = MutableStateFlow("All") // "All", "Brand New", "Refurbished"

    val rawProducts: StateFlow<List<Product>> = productDao.getAllProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val storeConfig: StateFlow<StoreConfig> = storeConfigDao.getConfig()
        .combine(MutableStateFlow(SampleData.getDefaultStoreConfig())) { config, fallback ->
            config ?: fallback
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SampleData.getDefaultStoreConfig())

    val enquiries: StateFlow<List<Enquiry>> = enquiryDao.getAllEnquiries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered products list
    val filteredProducts: StateFlow<List<Product>> = combine(
        rawProducts,
        selectedCategory,
        searchQuery,
        conditionFilter
    ) { products, category, query, condition ->
        products.filter { product ->
            val matchesCategory = if (category == "All") true else product.category == category
            val matchesCondition = when (condition) {
                "Brand New" -> product.condition.contains("New", ignoreCase = true)
                "Refurbished" -> product.condition.contains("Refurbished", ignoreCase = true) || product.condition.contains("Pre-Owned", ignoreCase = true)
                else -> true
            }
            val matchesQuery = if (query.isBlank()) true else {
                product.title.contains(query, ignoreCase = true) ||
                product.specifications.contains(query, ignoreCase = true) ||
                product.category.contains(query, ignoreCase = true)
            }
            matchesCategory && matchesCondition && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val featuredProducts: StateFlow<List<Product>> = rawProducts.combine(MutableStateFlow(Unit)) { products, _ ->
        products.filter { it.isFeatured }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // AI Advisor State
    private val _aiState = MutableStateFlow<AiUiState>(AiUiState.Idle)
    val aiState: StateFlow<AiUiState> = _aiState.asStateFlow()

    init {
        // Ensure database is populated if empty
        viewModelScope.launch {
            if (productDao.getCount() == 0) {
                productDao.insertAll(SampleData.getInitialProducts())
                storeConfigDao.insertOrUpdate(SampleData.getDefaultStoreConfig())
            }
        }
    }

    fun navigateTo(screen: Screen) {
        currentScreen.value = screen
    }

    fun setCategory(category: String) {
        selectedCategory.value = category
    }

    fun setSearch(query: String) {
        searchQuery.value = query
    }

    fun setCondition(condition: String) {
        conditionFilter.value = condition
    }

    fun askAiAdvisor(query: String) {
        if (query.isBlank()) return
        viewModelScope.launch {
            _aiState.value = AiUiState.Thinking
            try {
                val currentConfig = storeConfig.value
                val context = "${currentConfig.storeName}, Address: ${currentConfig.address}, Hours: ${currentConfig.businessHoursWeekday}"
                val response = aiService.consultTechExpert(query, context)
                _aiState.value = AiUiState.Success(response)
            } catch (e: Exception) {
                _aiState.value = AiUiState.Error(e.localizedMessage ?: "Failed to get technical guidance.")
            }
        }
    }

    fun clearAiState() {
        _aiState.value = AiUiState.Idle
    }

    // Product CRUD operations (Admin Features)
    fun addProduct(product: Product) {
        viewModelScope.launch {
            productDao.insertProduct(product)
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            productDao.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            productDao.deleteProduct(product)
        }
    }

    // Enquiry & Service Request
    fun submitEnquiry(enquiry: Enquiry, onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            enquiryDao.insertEnquiry(enquiry)
            onComplete()
        }
    }

    fun updateEnquiryStatus(enquiry: Enquiry, newStatus: String) {
        viewModelScope.launch {
            enquiryDao.updateEnquiry(enquiry.copy(status = newStatus))
        }
    }

    fun deleteEnquiry(enquiry: Enquiry) {
        viewModelScope.launch {
            enquiryDao.deleteEnquiry(enquiry)
        }
    }

    // Store settings update (Admin Features)
    fun updateStoreConfig(config: StoreConfig) {
        viewModelScope.launch {
            storeConfigDao.insertOrUpdate(config)
        }
    }
}
