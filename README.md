# The Away Guide

## Application Setup
1. Clone this repository
2. Launch Android Studio and open the project.
3. Go to local.properties and paste the line `MAPS_API_KEY=<INSERT GOOGLE MAPS API KEY HERE>`. Either sign up for your own or contact me for mine.
4. Launch an android emulator of your choice (must have Google Services and be API 23+)
5. Launch the application on your device.

## Assumptions Made
1. The device is over API 23 and has Google Services installed

## Functional requirements
- Show a list of football grounds (MVP) as this is part of the core functionality and is necessary to allow the user to select the football ground they want to look at.
- Include a map with a pinpoint of the football stadium’s location. This is so that users can easily locate the stadium.
- Add the functionality to group by, filter and search football grounds. This will make it easier for the user to find their football team.
- Include a description about each football ground listed (MVP). This is part of the core functionality and will give the user a nonvisual way of developing the stadiums narrative.
- Include a list of restaurants located near each football ground listed. This will assist users when they arrive at the ground as they will likely need to eat lunch/dinner.
- Include a list of pubs located near each football ground listed (MVP). A lot of football supporters enjoy drinking before/after the football match and will likely want to find pubs close by. 
- Include a list of hotels located near each football ground listed. For those football fans that travel a long distance – they may not want to travel back home the same day and will therefore require a nearby hotel.
- Include a list of car parking located near each football ground listed. Supporters will be travelling to these football matches, and many will use their cars – and will need to park nearby to the stadium.
- Include a map with a pinpoint of each of the restaurants, pubs and hotels listed. This is so that the user can easily find the attraction they want to find.
- Include the ability to add reviews to football grounds and display other users’ reviews. This is so that the user can get an understanding on how good the attraction is. This will increase user trust of an attraction, as a study found with KRC Research and Weber Shandwick stated that 65% of consumers “have been inspired enough by a favorable consumer review to buy a CE product that they weren’t considering” (2013).
- Have certain restaurants/pubs/hotels promoted in their lists. This is to give the application can get an income.
- Allow the user to select favourite grounds which appear on the home page. This is for ease of use and to allow the user to find their football ground easier.
- Show the users recently viewed football grounds on the home page. This is for ease of use and to allow the user to find their football ground easier.
- Each football club will be shown with their associated logo (MVP). This is to make finding their football club easier.
- At least one picture of the ground will be provided (MVP) - to give the user an easier user experience and give images so that the user can recognise the football ground once arrived. 
- Pictures of the local pubs will be shown (MVP) – to give the user an easier user experience and give images so that the user can recognise the football ground once arrived and also increase the customers engagement – as proven by Sabate et Al. (2014) in a study of Spanish travel agencies Facebook pages, where pictures had a positive impact on post popularity.
- Pictures of the local hotels/restaurants will be shown to give the user an easier user experience and give images so that the user can recognise the football ground once arrived.
- The app will support Android API 21 and all higher versions – so that the majority of devices can access the application (94.1%+ of devices) (Rahman, 2020).
- Include a dark theme option. This will make the app more power efficient, more visible to users with low vision or sensitive to bright light and easier for people to use in a low light environment (Dark theme  |  Android Developers, 2021).

## Non-Functional requirements
- Each page to load within 5 seconds – so that users don’t get frustrated and quit the application. This is important as a case study found that with Google, a 500-millisecond slowdown equalled a 25% decrease in searches (Everts, 2016 Chapter 2).
- The applications services must all maintain high uptimes. This is vital to keep customers using my application as a survey found that 82% of customers have stopped using a service due to a poor customer experience (Mansfield, 2021) and would also result in lost revenue – as showcased by Amazon in 2013, each minute of downtime - $117,882 of sales volume was lost (Everts, 2016 Chapter 2).
- The application should run without any unhandled errors and crashes
- The app must be user-friendly and easy to use.
- The application must follow a consistent green theme to match the branding.

## Data Requirements
- A list of football teams with included description and location (MVP) – this will be coded into a cloud database using Firebase and is needed to provide the basic functionality of the app. I will also store some of this data locally using RoomDB (not part of MVP). The descriptions will be copied from Wikipedia or a similar site in this edition of the application.
- A list of restaurants located near each football ground – this will be retrieved from Google Maps API and is needed as it is a useful feature that users can find local restaurants. I will also be running a real-time database on Firebase which will store promoted restaurants
- A list of hotels located near each football ground this will be retrieved from Google Maps API and is needed as it is a useful feature that users can find local hotels. I will also be running a real-time database on Firebase which will store promoted hotels
- A list of pubs located near each football ground (MVP) this will be retrieved from Google Maps API and is needed as it is a useful feature that users can find local pubs. I will also be running a real-time database on Firebase which will store promoted pubs
- A list of car parks located near each football ground this will be retrieved from Google Maps API and is needed as it is a useful feature that users can find local car parks
- A list of the user’s favourite football grounds. This will be stored locally in RoomDB and will be used for ease of access in the application.
- A list of the user’s recently viewed football grounds. This will be stored locally in RoomDB and will be used for ease of access in the application.

## Frameworks, Libraries and Languages Used
- Kotlin - https://kotlinlang.org/ - At Google I/O 2019, Android announced they will be increasingly Kotlin-first. Using Kotlin is fully interoperable with Java and can help remove the amount of boiler plate code, and includes great features such Coroutines which are described as lightweight threads. 67%  of professional developers who use Kotlin say Kotlin has increased their productivity and it was found that Android apps that contain Kotlin code are 20% less likely to crash https://developer.android.com/kotlin/first#use.  
- Jetpack Compose - https://developer.android.com/jetpack/compose - Jetpack Compose is the future of Android UI development. This is a toolkit for building native UI. Instead of using XML's, in Compose you can create interfaces using Kotlin. By using a declarative language there are so many advantages - there is alot less code to write - meaning less bugs, higher productivity and it being easier to maintain. It contains features that make integrating Material Design (Googles UI Guidelines) and Dark Theme so much easier, and they have introduced a new Object type called State which when its value gets updated will automatically update the UI.
- Firebase - https://firebase.google.com/ - Mobile backend as a service is becoming increasingly popular in the app space with Firebase being Google's own. This allows for me to create high-quality applications on the cloud fast and for a good price. By using Firebase I can use additional functionalities I would have likely missed having coded my own backend (ie. Crashlytics, Analytics, Realtime Database, Caching), which made my app more functional - and gave me more time to spend on the frontend of my app.
- NoSQL - My main reason for using NoSQL is because Firebase Realtime Database required NoSQL. By using Firebase Realtime Database, it allowed me to update my applications information in realtime, and by using NoSQL allowed for my dataset to be flexible and perform well in terms of throughput.
- Maps SDK for Android - https://developers.google.com/maps/documentation/android-sdk/overview - This is a necessity for using Google Maps features in my application.
- Coil - https://github.com/coil-kt/coil - An image loading library for Android that uses Kotlin Coroutines. This makes loading an image from a URL super easy and fast. The alternatives to Coil would be Picasso or Glide - but I chose to go with Coil as it is built well with Kotlin and its support for Kotlin Coroutines.
- JUnit - https://junit.org/junit4/ - JUnit 4 is the standard Unit testing library for Android and is commonly used within the documentation of Android Developers. https://developer.android.com/studio/test
- MockK - https://mockk.io/ - This is the library I'm going to be using for mocking. This is similar to it's alternative Mockito - however, the reason I and alot of other applications are starting to switch to MockK is its built in language features with Kotlin which make certain mocking easier.
- Hilt - https://developer.android.com/training/dependency-injection/hilt-android - This is my library choice for doing dependency injection. This helps to reduce the boilerplate code compared to manual dependency injection. This is built ontop of dagger - another popular dependency injection in the Android space - but Hilt abstracts even more and makes it easier to inject classes.
- Retrofit - https://square.github.io/retrofit/ - This is a typesafe REST client for Android which allows me to interact with API's and send network requests using OkHttp. This was used in my app to retrieve information from the Google Maps API. The other alternative I could have used was Volley - however, this is a networking library that is starting to become outdated and Retrofit allows me to do more. Retrofit also allows me to use Caching which helps in my apps offline mode.
- Ktlint - https://github.com/pinterest/ktlint - A checkstyle and formatter for my code ensuring I conform to common code guidelines and keeping my code clean.