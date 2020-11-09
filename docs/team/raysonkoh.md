---
layout: page
title: Rayson Koh's Project Portfolio Page
---

## Project: Travelling BusinessMan

Travelling BusinessMan is a desktop application that manages notes and contacts across multiple
countries and timezones for businessmen who travels a lot.

Given below are my contributions to the project.

* **New Feature**:
  * Implemented `country filter` command
  * Implemented `country note add` command
  * Implemented `country note view` command
  * Implemented `country note delete` command
  * Implemented `country note edit` command
  * Implemented `client suggest by/available` command

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=raysonkoh)

* **Project management**:
  * Brainstormed user stories for `v1.1`
  * Translated user stories to issues in Github issue tracker
  * Created bug issues relating to the country note entity
  * Reviewed PRs that are mainly related to the note entity

* **Enhancements to existing features**:
  * Updated existing `Saving Data` feature to include functionality to save Country Notes
  * Updated existing Display Panel to toggle between Client View and Country Note Panel based on user command
  * Included sample country notes as part of the existing sample data that will be loaded for a first-time user

* **Documentation**:
  * User Guide:
    * Added `Deleting clients`, `Saving Data` sections
    * Added `Country Note Edit` and `Country Note Delete` sections
    * Updated `Country Filter` and `Country Note Add` example usage to be more relevant in the context of TBM
    * Added `Glossary` section
  * Developer Guide:
    * Added a class diagram to show the relation between classes that are related to Country Note in the `Model` component
    * Added a sequence diagram to illustrate the sequence of method calls that occur when a user inputs `country note add`
    * Added an activity diagram to illustrate the toggling mechanism of the Display Panel to display either the Client View or the Country Note Panel.
    * Added the Non-Functional Requirements (NFR) section
    * Added the use case for `Saving Data` 
* **Community**:
  * PRs reviewed (with non-trivial review comments): (examples: [\#121](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/121), [\#125](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/125), [\#134](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/134), [\#150](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/150), [\#182](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/182), [\#235](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/235))
  * Contributed to forum discussions (examples: [\#125](https://github.com/nus-cs2103-AY2021S1/forum/issues/125), [\#161](https://github.com/nus-cs2103-AY2021S1/forum/issues/161))
  * Reported bugs in other team's product (examples: [Bugs found during Practical Exam Dry-Run](https://github.com/raysonkoh/ped/issues))

* **Tools**:
  * Gradle
  * Junit 5.0
