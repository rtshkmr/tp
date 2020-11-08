---
layout: page
title: Testing guide
---

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Running tests

There are three ways to run tests.

:bulb: Running test using method 3 is more reliable, and it does not choke up your computer's graphical interface.

* **Method 1: Using IntelliJ JUnit test runner** (Not recommended)
  * To run all tests, right-click on the `src/test/java` folder and choose `Run 'All Tests'`
  * To run a subset of tests, you can right-click on a test package,
    test class, or a test and choose `Run 'ABC'`
* **Method 2: Using Gradle**
  * Open a console and run the command `gradlew clean test` (Mac/Linux: `./gradlew clean test`)
* **Method 3: Using Gradle (headless)**
  * Open a console and run command `gradlew clean headless test` (Mac/Linux: `./gradlew clean headless test`)

<div markdown="span" class="alert alert-secondary">:link: **Link**: Read [this Gradle Tutorial from the se-edu/guides](https://se-education.org/guides/tutorials/gradle.html) to learn more about using Gradle.
</div>

--------------------------------------------------------------------------------------------------------------------

## Types of tests

This project has three types of tests:

1. ##### GUI Tests
    These are tests which simulates user interaction with the application.

    a. *Unit tests* that test the individual GUI components. These are in the `seedu.address.ui` package.
    
    b. *System test* within `seedu.address.ui` package, the `MainWindowTest.java` simulates user interaction with the full **_TBM_** GUI.

1. ##### Non-GUI Tests
    These are tests not involving the GUI of **_TBM_**. They include,<br>
    a. *Unit tests* targeting the lowest level methods/classes.<br>

      e.g  `seedu.address.commons.StringUtilTest`

    b. *Integration tests* that are checking the integration of multiple code units (those code units are assumed to be working).<br>

      e.g. `seedu.address.storage.StorageManagerTest`

    c. Hybrids of unit and integration tests. These test are checking multiple code units as well as how they are connected together.<br>

      e.g. `seedu.address.logic.LogicManagerTest`
