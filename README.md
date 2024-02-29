The Dog - a free app for every dog lover!

..................................................

In this App you can find thousands of dogs of different breeds and colors. They are represented as a list of images and names, and you can open more information about particular dogs with their descriptions by clicking an item. You also have an opportunity to save concrete ones to your own list by clicking the "like" icon. And you can also find any specific breed you want.

..................................................

Application Structure:

Dogs Fragment - a fragment with a list of dogs. Initially opened right after performing the Splash Screen and also is accessible from menu bar. Uses RecyclerView and pull-to-refresh function. Items are being loaded from the Internet. "Corgi-dog" is running while loading and stops when loaded. Each item is clickable and has its image, name and 'like' or 'dislike' button. Clicking a dog navigates you to Details Fragment of a chosen item. Clicking 'like' button means saving particular dog into the local database. Clicking the 'dislike' button means deleting that dog from the local database;

Search Fragment - a fragment where you can find a concrete dog breed and is accessible from menu bar. You should input a breed name fully. Found item is clickable and also navigates you to Details Fragment of that item.

Liked Dogs Fragment - a fragment with a list of dogs been liked and is accessible from menu bar. All liked dogs are saved into the local database and accordingly being loaded from the database while the fragment is loading. Clicking a dog navigates you to the Details Fragment of that dog. You can delete a dog from the database by swiping it to the left, and also click "Undo" to immedialty return it back to the database;

Details Fragment - a fragment with detailed information about particular dog. Works offline. Being opened after clicking a dog from Dogs Fragment, Search Fragment or Liked Dogs Fragment. Contains a name, an image and a description of a chosen dog. Also contains 'back' button meaning navigation back to the Dogs Fragment, Search Fragment or Liked Dogs Fragment - depends on where this Details Fragment was opened from.

..................................................

Technologies being used:

Databases: SQLite;

Languages: Kotlin, Java, XML;

Libraries: Kotlin Coroutines, LivaData, DataBinding, AndroidX, Navigation, SwipeRefreshLayout, Recycler View, JsonToKotlinClass, ROOM

..................................................

External libraries: Retrofit, Glide, Lottie, Hilt

..................................................

This app is using public API: https://thedogapi.com
