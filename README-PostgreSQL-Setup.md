# PostgreSQL Setup Guide for JobLync Application

This guide will help you set up PostgreSQL for the JobLync application.

## Prerequisites

- PostgreSQL installed on your system
- Basic knowledge of SQL commands
- Access to a terminal or command prompt

## Step 1: Install PostgreSQL

If you haven't installed PostgreSQL yet, follow these steps:

### Windows

1. Download the PostgreSQL installer from the [official website](https://www.postgresql.org/download/windows/)
2. Run the installer and follow the installation wizard
3. Remember the password you set for the postgres user during installation
4. Complete the installation with the default options

### macOS

Using Homebrew:
```bash
brew install postgresql
brew services start postgresql
```

### Linux (Ubuntu/Debian)

```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

## Step 2: Verify PostgreSQL Installation

Ensure PostgreSQL is running:

### Windows
```
# Check if PostgreSQL service is running
services.msc
# Look for "postgresql-x64-xx" service and make sure it's running
```

### macOS/Linux
```bash
sudo systemctl status postgresql
# or
pg_isready
```

## Step 3: Create Database and User

1. Connect to PostgreSQL as the postgres user:

   ### Windows
   ```
   # Open Command Prompt as Administrator
   psql -U postgres
   ```

   ### macOS/Linux
   ```bash
   sudo -u postgres psql
   ```

2. Create the database and user for JobLync:

   ```sql
   -- Create the database
   CREATE DATABASE joblync;
   
   -- Create the user with password
   CREATE USER joblync WITH PASSWORD 'joblync';
   
   -- Grant privileges to the user
   GRANT ALL PRIVILEGES ON DATABASE joblync TO joblync;
   
   -- Connect to the joblync database
   \c joblync
   
   -- Grant schema privileges
   GRANT ALL ON SCHEMA public TO joblync;
   
   -- Create test database for running tests
   CREATE DATABASE joblync_test;
   GRANT ALL PRIVILEGES ON DATABASE joblync_test TO joblync;
   \c joblync_test
   GRANT ALL ON SCHEMA public TO joblync;
   ```

3. Exit PostgreSQL:
   ```
   \q
   ```

## Step 4: Configure Application Properties

The JobLync application is already configured to connect to PostgreSQL with the following settings in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/joblync?connectTimeout=5&socketTimeout=30&tcpKeepAlive=true&ssl=false
spring.datasource.username=joblync
spring.datasource.password=joblync
spring.datasource.driver-class-name=org.postgresql.Driver
```

If you created the database and user with different names or passwords, update these settings accordingly.

## Step 5: Test the Connection

1. Start the JobLync application:
   ```bash
   # Navigate to the project directory
   cd path/to/JobLync
   
   # Run the application using Maven
   mvn spring-boot:run
   ```

2. Check the application logs for successful database connection messages.

3. If you encounter any errors:
   - Verify PostgreSQL is running
   - Check that the database and user exist with the correct permissions
   - Ensure the credentials in application.properties match what you created
   - Check firewall settings if connecting to a remote database

## Troubleshooting

### Connection Refused
- Ensure PostgreSQL is running
- Check if PostgreSQL is listening on the default port (5432)
- Verify firewall settings

### Authentication Failed
- Verify the username and password in application.properties
- Check PostgreSQL's pg_hba.conf file for authentication settings

### Database Does Not Exist
- Connect to PostgreSQL and create the database manually
- Check for typos in the database name

## Additional Resources

- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Spring Boot Database Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/data.html#data.sql.datasource)