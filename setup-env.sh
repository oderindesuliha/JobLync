#!/bin/bash

# JobLync Environment Variables Setup Script
# Run this script to set up environment variables for the JobLync application

echo -e "\033[0;32mJobLync Environment Variables Setup\033[0m"
echo "=================================="
echo ""

# Database Configuration
echo -e "\033[1;33mDatabase Configuration:\033[0m"
read -p "Enter Database URL (default: jdbc:postgresql://localhost:5432/joblync): " DB_URL
export DB_URL=${DB_URL:-"jdbc:postgresql://localhost:5432/joblync"}

read -p "Enter Database Username (default: joblync): " DB_USERNAME
export DB_USERNAME=${DB_USERNAME:-"joblync"}

read -p "Enter Database Password (default: joblync): " DB_PASSWORD
export DB_PASSWORD=${DB_PASSWORD:-"joblync"}

echo ""
echo -e "\033[1;33mJWT Configuration:\033[0m"
read -p "Enter JWT Secret (press Enter to generate a secure one): " JWT_SECRET
if [ -z "$JWT_SECRET" ]; then
    # Generate a secure JWT secret
    JWT_SECRET=$(openssl rand -base64 64)
    echo "Generated secure JWT secret: $JWT_SECRET"
fi
export JWT_SECRET

read -p "Enter JWT Expiration in milliseconds (default: 86400000): " JWT_EXPIRATION
export JWT_EXPIRATION=${JWT_EXPIRATION:-"86400000"}

read -p "Enter JWT Algorithm (default: HS512): " JWT_ALGORITHM
export JWT_ALGORITHM=${JWT_ALGORITHM:-"HS512"}

read -p "Enter JWT Issuer (default: JobLync): " JWT_ISSUER
export JWT_ISSUER=${JWT_ISSUER:-"JobLync"}

read -p "Enter JWT Audience (default: JobLyncUsers): " JWT_AUDIENCE
export JWT_AUDIENCE=${JWT_AUDIENCE:-"JobLyncUsers"}

echo ""
echo -e "\033[1;33mEmail Configuration:\033[0m"
read -p "Enter Mail Host (default: smtp.gmail.com): " MAIL_HOST
export MAIL_HOST=${MAIL_HOST:-"smtp.gmail.com"}

read -p "Enter Mail Port (default: 587): " MAIL_PORT
export MAIL_PORT=${MAIL_PORT:-"587"}

read -p "Enter Mail Username (your email address): " MAIL_USERNAME
export MAIL_USERNAME

read -p "Enter Mail Password (app-specific password): " MAIL_PASSWORD
export MAIL_PASSWORD

echo ""
echo -e "\033[0;32mEnvironment variables have been set:\033[0m"
echo "DB_URL: $DB_URL"
echo "DB_USERNAME: $DB_USERNAME"
echo "JWT_SECRET: ${JWT_SECRET:0:10}..." # Show only first 10 characters for security
echo "MAIL_HOST: $MAIL_HOST"
echo "MAIL_USERNAME: $MAIL_USERNAME"
echo ""
echo -e "\033[0;32mYou can now run the application with: ./mvnw spring-boot:run\033[0m"