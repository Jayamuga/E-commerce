# E-Commerce Website

## Overview
This is a full-stack e-commerce web application built with **Spring Boot (Java)** for the backend, **Html,css** for the frontend, and **PostgreSQL** for the database. The application allows users to browse products, manage a shopping cart, place orders, and process payments. Admin users can manage products, inventory, and orders.

---

## Features

### User Features
- Browse products with search and filter options.  
- Add items to shopping cart.  
- Place orders and checkout using Stripe or PayPal (test mode).  
- View order history.  

### Admin Features
- CRUD operations for products.  
- Manage inventory and stock levels.  
- View all orders and their statuses.  

### Authentication & Security
- JWT-based authentication.  
- Role-based access control (`ROLE_USER`, `ROLE_ADMIN`).  
- Secure handling of sensitive user data.  

---

## Deployment on AWS

This section explains how to deploy the **Spring Boot E-Commerce Application** on **AWS EC2** using an **S3 bucket** for file storage and transfer.

---

### **1. Prerequisites**
- AWS account with access to:
  - EC2 (Elastic Compute Cloud)
  - S3 (Simple Storage Service)
- Built JAR file (e.g., `ecommerce-app.jar`)
- EC2 instance (Amazon Linux or Ubuntu)
- SSH key pair (`.pem` file) for access

---

### **2. Upload JAR to S3**
1. Go to the **AWS S3 Console**.  
2. Create a new bucket (e.g.,this has to be unique `my-ecommerce-app-bucket`).  
3. Upload the `ecommerce-app.jar` file to the bucket.  
4. Generate a **pre-signed URL** for the file (right-click → "Share with a presigned URL").  
5. Copy this link — it will be used to download the JAR from EC2.

---

### **3. Connect to EC2 via SSH**
1. Open **Command Prompt / PowerShell**.  
2. Navigate to your `.pem` key file directory.  
3. Run the following SSH command (replace details as needed):

   ```bash
   ssh -i "C:\path\to\keypair.pem" ec2-user@ec2-18-219-32-119.us-east-2.compute.amazonaws.com

### **4. Download the JAR from S3**
Once connected to your EC2 instance via SSH, download your JAR file using the **pre-signed S3 link** you generated earlier:

```bash
wget "https://my-ecommerce-app-bucket.s3.amazonaws.com/ecommerce-app.jar?X-Amz-Algorithm=AWS4-HMAC-SHA256..."

"java -jar ecommerce-app.jar"
