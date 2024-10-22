# QueueIt — A Virtual Line Management Platform

QueueIt is a web and mobile platform designed to revolutionize how people and businesses manage queues. Users can join, monitor, and manage queues virtually for both physical locations and online services, eliminating unnecessary waiting and enhancing customer satisfaction.

## Key Features:

### Virtual Queue Management:
- Businesses can create and manage queues, set custom rules (wait times, priorities), and integrate existing scheduling systems.

### Real-Time Queue Tracking:
- Users join queues, track their position, and receive real-time notifications when their turn is near.

### Queue Scheduling & Flexibility:
- Users can schedule queues in advance, swap spots, and access priority lanes for special needs.

### Universal Queue System for Events:
- Support pre-queues for tickets and merchandise at events with countdown notifications.

### Delivery & Service Queueing:
- Users can track package or food delivery status in real-time.

### Multiple Queue Participation:
- Join multiple queues simultaneously with time management suggestions.

### Analytics for Businesses:
- Businesses get insights on queue times, customer behavior, and peak hours for optimization.

### Contactless Check-In & QR Codes:
- Allow contactless check-in at physical locations via QR codes or geo-fenced areas.

### Custom Wait-Time Experiences:
- Provide entertainment or promotional content to users during their wait.

## Target Audience:
- Consumers waiting in lines for services (restaurants, clinics, deliveries).
- Businesses optimizing foot traffic and enhancing customer experience.
- Event Organizers streamlining ticket sales or VIP lines.
- Service Providers managing queues for tech support, remote healthcare, etc.

## Tech Stack:

### Frontend (Web):
- React.js

### Frontend (App):
- Kotlin

### Backend:
- Go

### Database:
- MySQL

### Notifications:
- Firebase Cloud Messaging

##Structure of App

```
├──   src
|        ├── common
|        |         ├── AsyncImageLoader.kt          // Handles asynchronous image loading for the app
|        |         ├── DataCard.kt                  // Displays queue data with images
|        |         └── SearchFeature.kt             // Search bar for queues, businesses, and users
|        |
|        ├── data
|        |       ├── MySQL.kt                     // Handles MySQL connection and setup
|        |       └── MySQLRepository.kt           // Repository to perform data operations with MySQL
|        |
|        ├── di      // dependency injection
|        |    └── AppModule                         // Provides dependencies for the app
|        |
|        ├── model      // Data structures for the app
|        |            ├── User.kt                   // User model (includes role: business or customer)
|        |            ├── Business.kt               // Business model for managing queue services
|        |            ├── Queue.kt                  // Queue model for managing individual queues
|        |            ├── Ticket.kt                 // Ticket model for user's position in the queue
|        |            └── Notification.kt           // Notification model for alerts and reminders
|        |
|        ├── navigation
|        |             ├── NavGraph.kt              // Main navigation graph
|        |             ├── AuthenticationGraph.kt   // Navigation related to authentication (login, register)
|        |             ├── MainAppGraph.kt          // Core app flow graph
|        |             ├── NavigationDrawer.kt      // Custom navigation drawer for the app
|        |             ├── CustomNavType            // Custom navigation types for advanced routes
|        |             └── Routes.kt                // Route constants for navigating across screens
|        |
|        ├── ui
|        |        ├── home   
|        |        |       ├── HomeScreen.kt         // Home screen showing active queues and upcoming events
|        |        |       └── HomeViewModel.kt      // ViewModel to manage the home screen state
|        |        |
|        |        ├── queue   
|        |        |        ├── QueueScreen.kt       // Screen for queue management and user participation
|        |        |        └── QueueViewModel.kt    // ViewModel for handling queue-related logic
|        |        |
|        |        ├── business   
|        |        |          ├── BusinessScreen.kt  // Screen for business queue management and analytics
|        |        |          └── BusinessViewModel.kt// ViewModel for business operations
|        |        |
|        |        ├── notifications   
|        |        |                 ├── NotificationsScreen.kt // Screen displaying notifications and reminders
|        |        |                 └── NotificationsViewModel.kt // Handles notification logic
|        |        |
|        |        ├── settings
|        |        |       ├── SettingsScreen.kt     // User and app settings screen
|        |        |       └── SettingsViewModel.kt  // ViewModel for settings-related data
|        |        |
|        |        ├── theme
|        |        |        ├── Color.kt             // App color scheme
|        |        |        ├── Theme.kt             // Global theme settings
|        |        |        └── Type.kt              // Typography settings
|        |
|        ├── util
|        |        └── Constants.kt                  // Contains constant values used throughout the app
|        ├── QueueItApp.kt                          // Main app entry point
|        ├── AppViewModel.kt                        // Global ViewModel for managing app-wide states
|        ├── QueueItApplication.kt                  // Application class
|        └── MainActivity.kt                        // Main Activity - Entry point for Android
```

##Structure of Website

```
├──   src
|        ├── components
|        |         ├── AsyncImageLoader.js          // Component for asynchronous image loading
|        |         ├── QueueCard.js                 // Displays queue details and image in a card format
|        |         └── SearchBar.js                 // Search bar for searching queues, businesses, and users
|        |
|        ├── services
|        |       ├── ApiService.js                  // Service for making API requests to the backend
|        |       ├── MySQLService.js              // Handles MySQL integration for fetching data
|        |       └── NotificationService.js         // Manages notifications and alerts
|        |
|        ├── context    // Context API for global state management
|        |    ├── AppContext.js                     // Provides global state across the application
|        |    └── AuthContext.js                    // Authentication state and user session management
|        |
|        ├── models      // Data models for the web application
|        |            ├── User.js                   // User data model (includes customer or business roles)
|        |            ├── Business.js               // Model for business entities that create/manage queues
|        |            ├── Queue.js                  // Model for queue entities
|        |            ├── Ticket.js                 // Model for managing user positions in queues
|        |            └── Notification.js           // Model for handling notifications and reminders
|        |
|        ├── navigation
|        |             ├── NavBar.js                // Navigation bar component for website navigation
|        |             ├── AuthRoutes.js            // Handles routes related to authentication (login, sign-up)
|        |             ├── ProtectedRoutes.js       // Routes protected based on user authentication status
|        |             └── RouteConstants.js        // Defines route paths for easy navigation
|        |
|        ├── pages
|        |        ├── Home   
|        |        |       ├── HomePage.js           // Home page displaying active queues and business listings
|        |        |       ├── FeaturedBusinesses.js  // Section displaying featured businesses
|        |        |       └── HomePageStyles.css    // CSS for styling the home page
|        |        |
|        |        ├── Queue   
|        |        |        ├── QueuePage.js         // Page for users to join and track queues
|        |        |        ├── QueueDetails.js      // Detailed page for queue management and tracking
|        |        |        └── QueuePageStyles.css  // CSS for styling queue-related pages
|        |        |
|        |        ├── Business   
|        |        |          ├── BusinessDashboard.js// Dashboard for businesses to manage their queues
|        |        |          ├── Analytics.js       // Analytics section for businesses to track queue stats
|        |        |          └── BusinessDashboardStyles.css // Styling for business management dashboard
|        |        |
|        |        ├── Notifications   
|        |        |                 ├── NotificationsPage.js // Page displaying user notifications
|        |        |                 └── NotificationsStyles.css // CSS for notification page
|        |        |
|        |        ├── Settings
|        |        |       ├── SettingsPage.js       // Page for user profile and app settings
|        |        |       └── SettingsStyles.css    // CSS for styling the settings page
|        |        |
|        ├── assets   
|        |        ├── images                        // Contains all static images for the website
|        |        ├── fonts                         // Contains custom fonts used in the website
|        |        └── icons                         // Contains icons for buttons and UI elements
|        |
|        ├── styles
|        |        ├── Colors.css                    // Defines the website's color palette
|        |        ├── Theme.css                     // Global theme settings for the website
|        |        └── Typography.css                // Typography settings for font sizes and styles
|        |
|        ├── utils
|        |        ├── Constants.js                  // Stores constants used throughout the app (API URLs, etc.)
|        |        └── Helpers.js                    // Utility functions (date formatting, validation, etc.)
|        |
|        ├── App.js                                 // Main React component that renders the website
|        ├── AppProvider.js                         // Context provider wrapping the entire app
|        ├── index.js                               // Main entry point for the website
|        └── index.html                             // Root HTML file for the website
```
