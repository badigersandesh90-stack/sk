package com.example

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.Product
import com.example.ui.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.ObsidianBlack
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.recaptcha.RecaptchaAppCheckProviderFactory

private const val TAG = "AppCheckInit"
private const val DEBUG_TOKEN_EXTRA = "FIREBASE_APPCHECK_DEBUG_TOKEN"
private const val DEBUG_STORE_PREFIX = "com.google.firebase.appcheck.debug.store."
private const val DEBUG_SECRET_KEY = "com.google.firebase.appcheck.debug.DEBUG_SECRET"
private const val RECAPTCHA_SITE_KEY_RES = "recaptcha_site_key"

fun configureAppCheck(context: Context, intent: Intent?) {
    try {
        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(context)
        }

        if (BuildConfig.DEBUG) {
            val debugToken = intent?.getStringExtra(DEBUG_TOKEN_EXTRA)
            if (!debugToken.isNullOrEmpty()) {
                val persistenceKey = FirebaseApp.getInstance().persistenceKey
                val prefs = context.getSharedPreferences(
                    "$DEBUG_STORE_PREFIX$persistenceKey",
                    Context.MODE_PRIVATE
                )
                prefs.edit().putString(DEBUG_SECRET_KEY, debugToken).commit()
                Log.d(TAG, "App Check debug secret configured successfully.")
            }

            FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance()
            )
            Log.d(TAG, "DebugAppCheckProviderFactory installed.")
        } else {
            val resId = context.resources.getIdentifier(RECAPTCHA_SITE_KEY_RES, "string", context.packageName)
            if (resId != 0) {
                val siteKey = context.getString(resId)
                if (siteKey.isNotBlank()) {
                    FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
                        RecaptchaAppCheckProviderFactory.getInstance(siteKey)
                    )
                }
            }
        }
    } catch (e: Exception) {
        Log.e(TAG, "Failed to initialize Firebase App Check", e)
    }
}

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // STEP 1: Configure App Check with launch intent before any UI or Firebase operations
        configureAppCheck(this, intent)

        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                val context = LocalContext.current
                val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
                val products by viewModel.filteredProducts.collectAsStateWithLifecycle()
                val allProducts by viewModel.rawProducts.collectAsStateWithLifecycle()
                val featuredProducts by viewModel.featuredProducts.collectAsStateWithLifecycle()
                val storeConfig by viewModel.storeConfig.collectAsStateWithLifecycle()
                val enquiries by viewModel.enquiries.collectAsStateWithLifecycle()
                val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
                val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
                val conditionFilter by viewModel.conditionFilter.collectAsStateWithLifecycle()
                val aiState by viewModel.aiState.collectAsStateWithLifecycle()

                var selectedProductForDetails by remember { mutableStateOf<Product?>(null) }

                // Handle Back button for non-home screens
                if (currentScreen != Screen.HOME) {
                    BackHandler {
                        viewModel.navigateTo(Screen.HOME)
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = ObsidianBlack,
                    topBar = {
                        SkTopAppBar(
                            currentScreen = currentScreen,
                            onNavigate = { viewModel.navigateTo(it) }
                        )
                    },
                    bottomBar = {
                        if (currentScreen != Screen.ADMIN) {
                            SkBottomNavBar(
                                currentScreen = currentScreen,
                                onNavigate = { viewModel.navigateTo(it) }
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (currentScreen) {
                            Screen.HOME -> HomeScreen(
                                featuredProducts = featuredProducts,
                                storeConfig = storeConfig,
                                onNavigate = { viewModel.navigateTo(it) },
                                onEnquireWhatsApp = { product ->
                                    val msg = "Hello SK COMPUTERS,\nI am interested in:\n${product.title}\nPrice: ${formatCurrency(product.price)} (${product.condition})\nWarranty: ${product.warranty}\n\nIs this currently available in the shop?"
                                    openWhatsApp(context, storeConfig.whatsappNumber, msg)
                                },
                                onViewProductDetails = { selectedProductForDetails = it }
                            )

                            Screen.PRODUCTS -> ProductsScreen(
                                products = products,
                                selectedCategory = selectedCategory,
                                searchQuery = searchQuery,
                                conditionFilter = conditionFilter,
                                onCategorySelected = { viewModel.setCategory(it) },
                                onSearchChanged = { viewModel.setSearch(it) },
                                onConditionChanged = { viewModel.setCondition(it) },
                                onEnquireWhatsApp = { product ->
                                    val msg = "Hello SK COMPUTERS,\nI am interested in:\n${product.title}\nPrice: ${formatCurrency(product.price)} (${product.condition})\nWarranty: ${product.warranty}\n\nIs this in stock?"
                                    openWhatsApp(context, storeConfig.whatsappNumber, msg)
                                },
                                onViewDetails = { selectedProductForDetails = it }
                            )

                            Screen.SERVICES -> ServicesScreen(
                                storeConfig = storeConfig,
                                onSubmitServiceBooking = { enquiry, onComplete ->
                                    viewModel.submitEnquiry(enquiry, onComplete)
                                }
                            )

                            Screen.AI_ADVISOR -> AiAdvisorScreen(
                                aiState = aiState,
                                storeConfig = storeConfig,
                                onAskAi = { viewModel.askAiAdvisor(it) },
                                onResetAi = { viewModel.clearAiState() }
                            )

                            Screen.CONTACT -> ContactScreen(
                                storeConfig = storeConfig,
                                onSubmitEnquiry = { enquiry, onComplete ->
                                    viewModel.submitEnquiry(enquiry, onComplete)
                                }
                            )

                            Screen.ADMIN -> AdminScreen(
                                products = allProducts,
                                enquiries = enquiries,
                                storeConfig = storeConfig,
                                onAddProduct = { viewModel.addProduct(it) },
                                onUpdateProduct = { viewModel.updateProduct(it) },
                                onDeleteProduct = { viewModel.deleteProduct(it) },
                                onUpdateEnquiryStatus = { enquiry, status -> viewModel.updateEnquiryStatus(enquiry, status) },
                                onDeleteEnquiry = { viewModel.deleteEnquiry(it) },
                                onUpdateStoreConfig = { viewModel.updateStoreConfig(it) },
                                onExitAdmin = { viewModel.navigateTo(Screen.HOME) }
                            )
                        }
                    }

                    // Product Details Dialog
                    selectedProductForDetails?.let { product ->
                        ProductDetailDialog(
                            product = product,
                            onDismiss = { selectedProductForDetails = null },
                            onEnquireWhatsApp = { prod ->
                                val msg = "Hello SK COMPUTERS,\nI am inquiring about:\n${prod.title}\nPrice: ${formatCurrency(prod.price)} (${prod.condition})\n\nPlease share availability and best purchase price."
                                openWhatsApp(context, storeConfig.whatsappNumber, msg)
                            }
                        )
                    }
                }
            }
        }
    }
}
