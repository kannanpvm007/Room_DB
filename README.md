 # sample  Room DB
 **@Embedded -room database**\
 **@TypeConverter - store Image**
 
if you faced this error **AndroidBlockGuardPolicy.onNetwork(StrictMode.java:1565)** ->
 
 ```kotlin 
    if (Build.VERSION.SDK_INT > 9) {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
 ```
 
 **data base Migration ->**
 ```kotlin
 @Database(entities = [UserEntity::class], version = 2, exportSchema = false,  )

  val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDb::class.java, "user_db")
                     .fallbackToDestructiveMigration()
                    .build()
 
 ```
 <center>
<p align="center">

 [<img alt="alt_text" width="40px" src="https://wallpaper.dog/large/971295.png" />](https://www.facebook.com/ramakrishnan.kannanpvm/) [<img alt="alt_text" width="40px" src="https://cdn-icons-png.flaticon.com/512/174/174857.png" />](https://www.linkedin.com/in/kannanpvm007/)
 
 </p> </center>
