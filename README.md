The Dog - a free app for every dog lover!

..................................................

In this App you can find thousands of dogs of different breeds and colors. They are represented as a list of images and names, and you can open more information about particular dogs with their descriptions by clicking an item. You also have an opportunity to save concrete ones to your own list by clicking the "like" icon.

..................................................

Application Structure:

Main Fragment - a fragment with a list of dogs. Initially opened right after performing the Splash Screen. Uses RecyclerView and pull-to-refresh function. Items are being loaded from the Internet. Skeletons are shown while loading. Contains 'liked' floating button meaning navigation to Liked Dogs Fragment. Each item is clickable and has its image, name and 'like' button. Clicking a dog navigates you to the Detail Fragment. Clicking 'like' button means saving particular dog into the local database. Clicking the 'like' button already been clicked means deleting that dog from the local database;

Liked Dogs Fragment - a fragment with a list of dogs been liked, accessible from the main fragment by clicking 'liked' button. Contains 'back' button meaning navigation back to the Main Fragment. All liked dogs are saved into the local database and accordingly being loaded from the database while the fragment is loading. Clicking a dog navigates you to the Details Fragment;

Details Fragment - a fragment with detailed information about particular dog. Works offline. Being opened after clicking a dog from the Main Fragment or from the Liked Dogs Fragment. Contains a name, an image and a description of a chosen dog. Contains 'back' button meaning navigation back to the Main Fragment or Liked Dogs Fragment - depends on where this Details Fragment was opened from.

..................................................

Technologies being used:

Databases: SQLite;

Languages: Kotlin, XML;

Libraries: Kotlin Coroutines, LivaData, DataBinding, AndroidX, Navigation, JUnit, SplashScreen, SwipeRefreshLayout, Dagger2, JsonToKotlinClass, RxJava/RxJava2/RxAndroid/RxKotlin?,(reliant on ReactiveStreams, a standard for asynchronous stream processing with non-blocking backpressure) FlexboxLayout?,(similar capabilities to the CSS Flexible Box Layout Module)

..................................................

External libraries: ROOM, Retrofit, Lottie?,(supports solids, shape layers, masks, alpha mattes, trim paths, and dash patterns) Glide?,(to transform the image) ExpansionPanel?,(multiple expansion layouts) MyLittleCanvas?.(to facilitate work with canvas on Android)

..................................................

This app is using public API: https://thedogapi.com
