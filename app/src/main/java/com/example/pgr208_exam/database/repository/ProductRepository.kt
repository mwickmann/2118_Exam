package com.example.pgr208_exam.database.repository

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.pgr208_exam.database.AppDatabase
import com.example.pgr208_exam.database.entities.Cart
import com.example.pgr208_exam.database.entities.Favorite
import com.example.pgr208_exam.database.entities.Product
import com.example.pgr208_exam.database.services.ProductService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.pgr208_exam.database.entities.OrderHistory

object ProductRepository {

    private val _httpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    private val _retrofit =
        Retrofit.Builder()
            .client(_httpClient)
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private lateinit var _appDatabase: AppDatabase

    private val _productService = _retrofit.create(ProductService::class.java)
    private val _favoriteDao by lazy { _appDatabase.favoriteDao() }
    private val _cartDao by lazy { _appDatabase.cartDao() }
    private val _productDao by lazy { _appDatabase.productDao() }
    private val _orderHistoryDao by lazy { _appDatabase.orderHistoryDao() }


    fun initiateDatabase(context: Context): AppDatabase {
        _appDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "fake-store"
        ).fallbackToDestructiveMigration().build()
        return _appDatabase
    }


    suspend fun getProducts(): List<Product> {
        try {
            val response = _productService.getAllProducts()

            if (response.isSuccessful) {
                val products = response.body() ?: emptyList()

                // gjør at APIet blir lagt inn i databasen
                _productDao.insertAll(products)

                // gjør at det blir returnert også i offline-modus!
                return _productDao.getAllProducts()
            } else {
                throw Exception("getProducts response failed.")
            }
        } catch (e: Exception) {
            Log.e("getProducts", "failed to load from API", e)
            // Returner fra databasen uansett feil
            return _productDao.getAllProducts()
        }
    }
    // Kategorier - også i offline modus
    suspend fun getProductsByCategory(category: String): List<Product> {
        return try {
            val response = _productService.getProductsByCategory(category)
            if (response.isSuccessful) {
                val products = response.body() ?: emptyList()
                _productDao.insertAll(products)
                products
            } else {
                throw Exception("getProductsByCategory response failed: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            // Hvis det oppstår en feil under nettverkskallet
            Log.e("getProductsByCategory", "failed to load from API", e)
            _productDao.getProductsByCategory(category)
        }
    }

    suspend fun getProductById(productId: Int): Product? {
        return _productDao.getProductById(productId)
    }

    // favoritt - funksjoner

    suspend fun getFavorites(): List<Favorite> {
        return _favoriteDao.getFavorites()
    }

    suspend fun addFavorite(favorite: Favorite) {
        _favoriteDao.insertFavorite(favorite)
    }

    suspend fun removeFavorite(favorite: Favorite) {
        _favoriteDao.removeFavorite(favorite)
    }

    suspend fun getProductsByIds(idList: List<Int?>): List<Product> {
        return _productDao.getProductsByIds(idList)
    }

   // CART funksjonene
    suspend fun getCarts(): List<Cart> {
        return _cartDao.getCarts()
    }

    suspend fun addCart(cart: Cart) {
        Log.d("ProductRepository", "Adding cart: $cart")
        _cartDao.insertCart(cart)
    }

    suspend fun removeCart(cart: Cart){
        Log.d("ProductRepository", "Removing cart: $cart")
        _cartDao.removeCart(cart)
    }


    // OrderHistory funksjoner
    suspend fun getOrders(): List<OrderHistory> {
        return _orderHistoryDao.getOrders()
    }
    suspend fun addOrder(order: OrderHistory){
        Log.d("ProductRepository", "Adding order: $order")
        _orderHistoryDao.insertOrder(order)
    }
    suspend fun getOrderById(orderId: Int): OrderHistory? {
        return _orderHistoryDao.getOrderById(orderId)
    }

    suspend fun getOrdersByIds(idList: List<Int>): List<OrderHistory> {
        return _orderHistoryDao.getOrdersByIds(idList)
    }

}

