
# 🛍️ ShopEase - E-commerce Demo App
![Home Screen](https://github.com/user-attachments/assets/2a2d6a46-1eee-41f9-9188-e39bc77e979a)
A modern mock E-commerce Android app built using **Kotlin**, **Jetpack Compose**, **Retrofit**, and**Navigation Component**. ShopEase demonstrates core features of a shopping app such as product listing, product details, cart management, and checkout—powered by a mock API.

##  Features

- 🛒 Product listing from remote API (FakeStore API)
- 🔍 Filter and sort functionality
- 📦 Detailed product view
- 🧺 Add to cart with quantity control
- 💰 Checkout screen with total calculation
- 🔄 Navigation between screens using Navigation Component
- 🌙 Jetpack Compose for fully modern UI
- 📶 Retrofit for network calls

## 🧱 Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** MVVM + Repository Pattern
- **Navigation:** Navigation Component
- **Networking:** Retrofit + Gson
- **Image Loading:** Coil
- **State Management:** ViewModel + LiveData /StateFlow
- **Mock API:** [https://fakestoreapi.com](https://fakestoreapi.com)
- **Dependency Injection** : Hilt
- **Local Storage** : Room or DataStore (for cart persistence)

Screenshot_product.png


## 🔧 Project Structure
/ShopEase
│
├── data
│   ├── model
│   ├── api
│   └── repository
│
├── domain (optional layer)
│   └── usecases
│
├── ui
│   ├── screens
│   │   ├── product_list
│   │   ├── product_detail
│   │   ├── cart
│   │   └── checkout
│   ├── components
│   └── theme
│
├── utils
│
└── MainActivity.kt

**Core Feature**
1. Product Listing Page
   Fetch data from API

Display in grid view using LazyVerticalGrid

Add filter/sort dropdowns

Search bar (optional)

2. Product Detail Page
   Show product image, description, price, rating

“Add to Cart” button

3. Cart Page
   List added items with quantity control

Total price calculation

Remove item from cart

4. Checkout Page
   Static confirmation screen

Show summary of cart items

Fake "Place Order" button
