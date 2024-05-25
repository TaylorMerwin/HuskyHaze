# HuskyHaze

### Team 3 Members:

- Taylor Merwin
- William Hay
- Minh Vu
- Keegan Sanders

  

[Check out our Github repo](https://github.com/TaylorMerwin/HuskyHaze)

# Description

HuskyHaze is a multipurpose application that helps UW students stay informed on both weather and news that is relevant to them. It is designed to empower UW students with timely information.

Our mission is to provide students with easy access to both weather forecasts and relevant news updates, all within one convenient platform.

## Technology

In order to build HuskyHaze, we have made use of multiple web and mobile technologies.

This includes using Kotlin and XML to build the application from the ground up.

Additionally, we make use of multiple powerful and freely available APIs to retrieve the latest information. These are from [https://openweathermap.org/](https://openweathermap.org/) and [https://newsapi.org/](https://newsapi.org/)

We bring these together through the use of libraries such as Retrofit for handling HTTP requests, and Picasso for handling images.

Finally, we include the use of a MySQL database and backend with PHP for handling user data and account information.

# Setup Instructions:

Step 0:

If you previously installed HuskyHaze, there may be an issue with how maps are displayed.

Please do the following:

-   Open Settings in Android Emulator or device.
    
-   Select ‘Apps’.
    
-   Select ‘HuskyHaze’.
    
-   Select ‘Storage & Cache’.
    
-   Select ‘Clear Cache’ then ‘Clear Storage’.
    
-   Uninstall HuskyHaze.
    
Step 1: Open APK in android studio or install HuskyHaze APK on local device

Step 2: Run HuskyHaze

# Activities

Our application has several features that are organized by different activities:

## Login Activity

Allows the user to log into the HuskyHaze application using an email and password. Includes two text input fields and a login button. Additionally offers a link to the Register activity for new users.

The application offers different toast notifications if there is a problem with signing in such as missing an email or password or using an email address that is not in the database.

The HTTP request used for login is POST

`https://students.washington.edu/tmerwin/login.php`

With the body being structured as

`{"email":"email@test.com", "password":"password"}`

## Register Activity

Allows the user to register a new account for use in HuskyHaze. The layout is similar to the Login Activity with the addition of a field for the user’s name. Includes three text input fields and a button to register an account. Additionally offers a link to the Sign in Activity for users who already have an account. Offers simple input validation to ensure accounts can only be created with a valid email address and adequate password. Duplicate email addresses are not allowed.

The HTTP request used for Register is POST

`https://students.washington.edu/tmerwin/register_user.php`

With the body being structured as

`{"email":"email@test.com", "name":"Name", "password":"password"}`

  

## Main Activity

Displays a welcome message to the user that lists the current time of day, the current temperature, and the weather in Tacoma.

  

There is an icon that represents the current weather.

There are three buttons that go to the Weather, News, and About Us Activities.

In the top corner is an icon that goes to the Settings activity.

  

## Weather Forecast Activity  
  
Displays the weather forecast for today and the next week.

This includes the minimum and maximum temperatures as well as the overall weather for the day.

  
This was done by making use of the recyclerView to generate the layout for each day in the forecast.

  

Moving forward this will have better icons and themes to match the rest of the application.

  

## Settings Activity

  

The settings activity allows the user to make changes to how the application looks and behaves. At this stage it allows users to toggle between light and dark themes. Future implementations that are not currently functional will be an “edit profile” feature, and other togglable features that will be determined.

  

## News Activity  
  

Things that are implemented for the news activity include previews of articles that display the title and author, along with an image if given. Clicking on the articles activates an intent that takes the user to a web view within the application using the article URL. Category buttons can be used to update the news activity with a new set of articles that apply to the selected category. The search bar also retrieves all articles with the keyword the user searches for.

  

## Maps Activity

  

Maps Activity includes a Google map that will first show the UWT location. Users can change the location by searching the coordinates of the location (i.e. 14,108 for Vietnam). Users can click on the maps to choose the new location where they want to see the weather. If the user says yes, it will go back to the main activity, if the user says no it stays on the map until the user says yes.

  

## About Us Activity

Displays information about the application and the team behind it.

# Sprint 2 Features and Bug Fixes

Since sprint 1, we have been able to implement the following features and bug fixes:

-   Fixed the bug where clicking the back button in the settings did not act as intended. After discussing with the professor, we removed the back button on the settings, along with not moving forward with adding back buttons to activities as the emulators and phones have a built-in back button feature.
    
-   Fixed the bug where clicking on the “About Us” button in the settings would cause multiple instances of a pop-up message.
    
-   Added icon for our application.
    
-   Category buttons and search bar work in the News Activity, along with a new web view that will open up an article the user wants to read within the application.
    
-   Added user authentication with fully working user registration and login
    
-   Added stored user preferences
    

  

The features we have not implemented or incorporated are:

-   Showing news for UWT students - The NewsAPI we were using can display by location, however, there wasn’t a consistent way to display news using this API that correlated to only UWT.
    
-   Home button to navigate back to the home screen - As previously mentioned, we decided not to move forward with adding back buttons to each activity as the professor mentioned that phones nowadays (and the emulator) have built-in buttons to go back.
 
