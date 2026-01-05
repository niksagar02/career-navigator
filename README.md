# Name - Nikhil Sagar
# Course - Advanced Programming Techniques CS622
# project




# Career Navigator – Spring Boot Job Recommendation Web App

Career Navigator is a full-stack web application built with **Spring Boot**, **Thymeleaf**, **Java**, **HTML**, **CSS**, and **AJAX**.  
The application allows users to find real-time job recommendations by connecting to the **Adzuna Jobs API (US dataset)** and supports a smooth "Load More" pagination without page refresh.

This project was built as part of the **Advanced Programming Techniques (APT)** course and demonstrates concepts such as external API integration, server-side rendering, AJAX-based asynchronous loading, and clean MVC architecture.

---

## 🚀 Features

### 🔍 Job Search
Users can enter:
- Skill / Job keyword  
- Preferred location  
- Experience (optional)

The backend fetches real job results from Adzuna.

---

### 📄 Paginated Results (No Page Reload)
- Page 1 is rendered using Thymeleaf  
- Page 2, 3, … are fetched dynamically using **AJAX → JSON → HTML injection**  
- Smooth UX without reloading the page  
- Automatically shows a message when the API has no more jobs to return  

---

### 🧭 Modern UI (Glassmorphism + Particle Background)
- Beautiful animated particle background  
- Glassmorphism card design  
- Fade-in animation  
- Apply button linking directly to external job application pages  

---

### 📦 Robust Backend Logic
- Safe URL encoding  
- Pagination supported via dynamic API URL (`search/{page}`)  
- Graceful handling of missing fields  
- Exception-safe API calls  
- Session-based job tracking  

---

## 🛠️ Tech Stack

### **Backend**
- Java 17  
- Spring Boot 3  
- Spring MVC  
- RestTemplate  
- Jackson JSON Parser  

### **Frontend**
- HTML / Thymeleaf  
- CSS (Glassmorphism, Fade Animations)  
- JavaScript (Fetch API + Dynamic DOM Injection)  
- particles.js background  

### **Build Tool**
- Gradle (run via `./gradlew bootRun`)  

### **External API**
- Adzuna Jobs API (US)

---

## 📁 Project Structure

src/main/java/com/example/api/in/web
│
├── controller
│ └── JobController.java
│
├── service
│ └── JobRecommendationService.java
│
├── model
│ └── JobRecommendation.java
│
└── ApiInWebApplication.java

src/main/resources
│
├── templates
│ └── index.html
│
├── static/css
│ └── style.css
│
└── application.properties



---

## 📘 Architecture Overview (MVC + AJAX)

### 1️⃣ **User submits search** → `/recommend`
- Controller fetches Page 1  
- Stores jobs in session  
- Returns `index.html` with first results  

### 2️⃣ **User clicks "Load More"** → AJAX → `/load-more`
- Controller returns JSON for page 2, 3, 4…  
- JavaScript appends results dynamically  
- No UI refresh required  

### 3️⃣ **Service Layer**
- Builds API URL  
- Calls Adzuna  
- Parses JSON into `JobRecommendation` objects  
- Handles pagination  

---

## 🌐 API Details – Adzuna Jobs API

Example API call:

https://api.adzuna.com/v1/api/jobs/us/search/1

?app_id=YOUR_ID
&app_key=YOUR_KEY
&results_per_page=4
&what=java
&where=boston


Your application:
- Encodes query parameters safely  
- Uses page numbers dynamically  
- Extracts fields from JSON:
  - title  
  - company.display_name  
  - location.display_name  
  - salary_min / salary_max  
  - description  
  - redirect_url  

---

## ▶️ How to Run the Project

### **Option 1: IntelliJ IDEA**
1. Open project folder  
2. Ensure Java 17 SDK is selected  
3. Run `ApiInWebApplication.main()`  
4. Open browser at:  
http://localhost:8080


---

### **Option 2: Gradle (Terminal)**
./gradlew bootRun


---

## 📌 Key Files Explained

### **JobController.java**
Handles:
- `/recommend` (first page)
- `/load-more` (AJAX pagination)
- Session job list
- View rendering  

### **JobRecommendationService.java**
- Calls Adzuna API  
- Parses JSON response  
- Returns JobRecommendation objects  

### **JobRecommendation.java**
- Model class for job attributes (title, company, salary, description, apply URL)  

### **index.html**
- Main UI  
- Form submission  
- Thymeleaf rendering  
- AJAX Load More logic  

### **style.css**
- All frontend design elements  
- Gradient background, glass UI, animations  

---

## 📉 No Jobs Found Handling

If page 1 has zero jobs:
- A friendly message appears.

If "Load More" reaches the end:
- Button replaced with message:  
  **“Yes, you have reached the limit.”**

---

## 🧪 Testing

`ApiInWebApplicationTests.java`
- Basic Spring Boot context load test  
- Auto-generated, no additional logic needed  

---

## 🗂️ Grading Notes (Required by Instructor)

API credentials used during development are included in:
