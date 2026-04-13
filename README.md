# AuraZone Automation — QA Internship Task | Zansphere

Selenium WebDriver automation for AuraZone e-commerce application.
Prepared by: Gospel Reehan X | April 2026

---

## Prerequisites

- Java JDK 11 or higher
- Maven 3.6+
- Google Chrome (latest)
- ChromeDriver (matching your Chrome version)

---

## Setup Instructions

1. Clone the repository
   git clone https://github.com/Gospelreehan/AurazoneAutomation.git

2. Open in Eclipse as a Maven project

3. Download ChromeDriver from https://chromedriver.chromium.org
   and place it in a known path (e.g. C:/drivers/chromedriver.exe)

4. Update the ChromeDriver path in each test class if needed

5. Install dependencies
   mvn clean install

---

## Running the Tests

Run all tests:
   mvn test

Run a specific test class:
   mvn test -Dtest=EndToEndPurchaseTest

Or run via TestNG:
   Right-click testng.xml → Run

---

## Test Classes

| Class | Test Cases | Description | Result |
|---|---|---|---|
| EndToEndPurchaseTest | 9 | Full purchase flow — Homepage to Order Confirmation (COD) | Pass |
| InvalidPriceFilterTest | 1 | Price filter validation — Min greater than Max | Fail (Bug confirmed) |
| ForgotPasswordNegativeTest | 2 | Empty email and invalid email format in forgot password | Fail (Bug confirmed) |
| LoginTest | 3 | Invalid password, unverified account, wrong credentials | Pass |

> Note: Tests marked Fail are intentional — they serve as automated proof of the reported bugs (BUG-005 and BUG-006). They will pass once the bugs are fixed.

---

## Key Features

- Explicit waits used throughout — no Thread.sleep()
- Screenshots captured automatically at every step (saved to /screenshots)
- All tests are independent and can be run individually
- Tests cover both positive (happy path) and negative (bug verification) scenarios

---

## Application Under Test

URL: https://test.aurazone.shop
Type: E-commerce — Premium Footwear

---

## Bug Report Document

Full bug report, test plan, and QA analysis available in the submission document.
