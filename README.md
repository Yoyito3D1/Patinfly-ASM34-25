# 🚲 Patinfly ASM34

**Patinfly** is a modern mobile application developed as part of the Android Systems and Multimedia (ASM) course — Practical Work 1 (P1).  
The app allows users to log in, explore available electric bikes, view pricing plans, and access their profile securely.

Developed by:  
👨‍💻 **Jon Jordi Salvador Pérez**  
👨‍💻 **Joel Teodoro Gómez**

---

## ✨ Features

- 🔐 **Login with persistent session** using `DataStore`
- 📥 Load user, bike and pricing data from local `.json` files
- 🧭 Navigation between screens using Jetpack Compose Navigation
- 🚲 Bike listing with details and filtering
- 💶 System pricing plans UI
- 👤 Profile screen showing user information
- 📲 Splash screen with custom branding
- 🌙 Support for dark mode
- 📦 Clean MVVM architecture
- 🧪 Debug logging to track flow and data
- 🧼 Fully resource-managed strings, images, dimensions

---

## 🛠 Tech Stack

- **Kotlin** (100% Jetpack Compose)
- **Jetpack Compose** for UI
- **DataStore** for session persistence
- **Gson** for parsing local JSON
- **MVVM** architecture
- **Modular layers**: `domain`, `data`, `presentation`
- **Navigation Component** Compose version

---

## ✅ Functional Flow

### 1. **SplashScreen**
- Displays branding and a "Get Started" button
- Navigates to login

### 2. **LoginScreen**
- Validates credentials from `user.json`
- Stores session using `DataStore`
- Redirects to `MainScreen` if login is successful

### 3. **MainScreen**
- Presents options to:
  - View Profile
  - See Bike Listings
  - Explore Pricing Plans

### 4. **BikeListScreen**
- Loads and filters bikes from `bike.json`
- Only shows bikes where `isActive == true && isDeleted == false`
- Custom cards with image, battery info, and “View Details”

### 5. **BikeDetailScreen**
- Opens after tapping a bike
- Displays bike name, battery, distance, and type
- Option to return to the list

### 6. **PricingScreen**
- Loads data from `system_pricing_plans.json`
- Shows detailed information on unlock fee, per-minute and per-kilometer pricing
- Includes a return button to `MainScreen`

### 7. **ProfileScreen**
- Displays user name, email, creation date, last connection
- Includes profile picture and logout button
- Logout clears session and returns to login screen

---

## 📁 Data Sources Used

| File                        | Purpose                        |
|-----------------------------|--------------------------------|
| `user.json`                 | Simulated user database        |
| `bike.json`                 | Bike data with type & status   |
| `system_pricing_plans.json`| Contains pricing plan info     |

---

## ✅ Bonus Features & Enhancements

- ✅ **Persistent Login**: Auto-loads saved user on app start
- ✅ **Logout**: Proper session clearing and back stack reset
- ✅ **Dialog confirmation on logout**
- ✅ **Responsive UI** across devices
- ✅ **All strings externalized to `strings.xml`**
- ✅ **Custom styling with Material 3**
- ✅ **Rounded image loading in profile & splash**

---

## 📌 Requirements Covered

✔️ Full implementation of Lab0 → Lab4  
✔️ MVVM + Repositories + DataSources  
✔️ User login required (no anonymous access)  
✔️ Proper navigation & activity flow  
✔️ Fully resource-managed app (strings, dimensions, images)  
✔️ `asm_local.properties` contains `asm_group=ASM34`  
✔️ Functional logout  
✔️ GSON and DataStore used appropriately

---

## 🔒 Repository Guidelines

- ✅ Private GitHub repository: `Patinfly-ASM34-25`
- 👥 Both members added as collaborators
- 👨‍🏫 Instructor `TomasGiS` added as collaborator
- 📦 Project zipped and submitted without binaries

---

## 🗓 Deadline

📅 Submission due: **April 22, 2025**
