# 📱 Patinfly: Telematics Data Services 

## 📋 Project Description
Patinfly is an innovative company in the mobility sector, updating its Android app using **Jetpack Compose**.  
The app focuses on corporate bike rentals and aims to transform mobility at a global scale.  
Tarragona is the main development and promotion center for the app.  

This practice (P2) focuses on **data persistence** and **query services** using a **WS API Rest** hosted at `https://api.patinfly.dev`.  

---

## 🚀 Main Features
- Integration of **BikeDetailActivity** (`BikeDetailViewModel`, `BikeDetailUseCase`).  
- Integration of **BikeRentDetailActivity** (`BikeRentDetailViewModel`, `BikeRentDetailUseCase`).  
- Data flow logic for queries:
  1. Check if data exists in the local database via the DAO.  
  2. If present, return stored values.  
  3. If not, query the WS API Rest.  
  4. If found in API, save to local database.  
  5. If not found in any source, return `null`, empty list, or `false`.

---

## 🛠️ Data Persistence
- **Entities (DTOs):**
  - `UserDTO` → represents `user` table  
  - `BikeDTO` → represents `bike` table  
  - `SystemPricingPlanDTO` → represents `systempricingplan` table  
- **Data Access Objects (DAOs)** using Room for SQLite persistence:  
  - `UserDatasource`  
  - `BikeDatasource`  
  - `SystemPricingPlanDatasource`  
- **Repository logic:**  
  - First queries the DAO (local database).  
  - Falls back to API if data is missing.  
  - Saves API results to database if retrieved.  

---

## 🌐 WS API Rest Endpoints
- **Login (get token):** `https://api.patinfly.dev/api/login` (email and password in header)  
- **User rentals:** `https://api.patinfly.dev/api/rent` (token in header)  
- **All bikes:** `https://api.patinfly.dev/api/vehicle` (token in header)  
- **Single bike:** `https://api.patinfly.dev/api/vehicle/{uuid}` (token + bike UUID)  
- **Reserve bike:** `https://api.patinfly.dev/api/vehicle/reserve/{uuid}` (token + bike UUID)  
- **Release bike:** `https://api.patinfly.dev/api/vehicle/release/{uuid}` (token + bike UUID)  
- **Start rental:** `https://api.patinfly.dev/api/rent/start/{uuid}` (token + bike UUID)  
- **Stop rental:** `https://api.patinfly.dev/api/rent/stop/{uuid}` (token + bike UUID)  

> Note: The SystemPricingPlan data is only handled locally (DAO) as the API does not provide it.

---

## 🔧 Usage
1. Implement **BikeDetailActivity** and **BikeRentDetailActivity** with ViewModels and UseCases.  
2. Use **Retrofit** to query the WS API Rest for User and Bike data.  
3. Persist retrieved data using **Room DAOs**.  
4. Handle data flow as described: check local DB → query API → store in DB if found → return results.  
5. Ensure reserved bikes block other users from renting until released.  

---

## 📂 Requirements
- Android Studio with Jetpack Compose support.  
- Retrofit library for API requests.  
- Room for local SQLite persistence.  
- Project must be private on GitHub with collaborators added.  
- Do **not** publish credentials in the repository.  

---

## 📅 Deadlines
- **Project submission:** Sunday, June 1, 2025  
- **Project defense interview:** Wednesday, June 4, 2025  

---

## 🎯 Objective
Implement persistent data storage and API integration for a mobile bike rental application, ensuring correct synchronization and reservation logic for users and bikes.


