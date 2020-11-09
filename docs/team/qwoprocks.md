---
layout: page
title: Lim Ming Chong's Project Portfolio Page
---

## Project: Travelling BusinessMan

Travelling BusinessMan is a desktop application that manages notes and clients across multiple countries and timezones for businessmen who travel a lot and have many international clients. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 24 kLoC.

Given below are my contributions to the project.

* **New features**: 
  * Updated help window display and contents (Pull requests [\#117](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/117), [\#317](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/317))
  * Added option to close help window using the Esc key (Pull request [\#133](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/133))
  * Created the `NoteCard` UI component to view client and country notes in a user-friendly way and implemented a better GUI display for `country note view` and `country note view c/COUNTRY` (Pull request [\#247](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/247))
  * Co-authored pull request [\#197](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/197) to implement GUI testing and integrate into GitHub Continuous Integration workflow
    * Credits: Address Book 4's [codebase](https://github.com/se-edu/addressbook-level4) was referenced  
  * Added `ContractExpiryDate` field to `Client` (Pull request [\#191](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/191))
  * `client suggest by/contract` command
  * Implemented sorting for client list for `SuggestCommand` purposes (Pull request [\#221](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/221))
  * Helped with designing the TBM logo
<br/><br/>
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=qwoprocks&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&zFR=false&tabAuthor=qwoprocks&tabRepo=AY2021S1-CS2103T-F11-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code)

* **Project management**:
  * Managed releases `v1.1` - `v1.4` (4 releases) on GitHub
  * Created walking skeleton for `v1.2` (Pull request [\#115](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/115))
<br/><br/>
* **Enhancements to existing features**:
  * Refactored all instances of `person` to `client` (Pull request [\#116](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/116))
  * Updated checkstyle to better enforce code quality (Pull requests [\#128](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/128), [\#216](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/216) (Co-authored))
  * Implemented defensive truncation of the window size and position read from user preferences to ensure the application renders on screen properly even if `preferences.json` is edited (Pull request [\#352](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/352))
  * Consistently increased test coverage in pull requests (Pull requests [\#191](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/191), [\#197](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/197) (Co-authored), [\#203](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/203) (Co-authored), [\#206](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/206), [\#258](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/258), [\#271](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/271), [\#274](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/274), [\#355](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/355))
  * Checked and polished code quality for the `logic` package (Pull request [\#362](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/362))
  * Cleaned up and standardized test code (Pull request [\#271](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/271))
<br/><br/>
* **Documentation**:
  * User Guide:
    * Added documentation for the features `country note`, `clear`, and `exit` [\#31](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/31)
    * Defined a new syntax for commands with multiple parameters that require at least one parameter to be supplied [\#257](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/257)
    * Added screenshots and descriptions under `Appendix C: Expected Command Behaviour Screenshots` for the commands `help`, `client list`, and `client find` [\#351](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/351)
    * Updated the FAQ [\#351](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/351)
  * Developer Guide:
    * Added the list of user stories and labelled their priorities [\#38](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/38)
    * Added the use case for `Clearing all entries from TBM` [\#53](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/53)
    * Added the use case for `Noting down information about a client` [\#351](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/351)
    * Co-authored implementation section with UML diagrams for `client suggest` [\#204](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/204) [\#351](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/351)
    * Updated UML diagrams for sections `Model component`, `Inner workings`, and `Storage component` [\#351](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/351)
    * Updated activity diagram under implementation section of `Switching between displaying Country Note List Panel, the Client View, and the default view` [\#351](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/351)
<br/><br/>
* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#51](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/51), [\#121](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/121), [\#131](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/131), [\#134](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/134), [\#137](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/137), [\#144](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/144), [\#146](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/146), [\#150](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/150), [\#164](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/164), [\#198](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/198), [\#199](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/199), [\#223](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/223), [\#244](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/244), [\#252](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/252), [\#256](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/256), [\#259](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/259), [\#266](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/266), [\#268](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/268), [\#337](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/337)
  * Contributed to forum discussions (examples: [\#36](https://github.com/nus-cs2103-AY2021S1/forum/issues/36), [\#62](https://github.com/nus-cs2103-AY2021S1/forum/issues/62), [\#71](https://github.com/nus-cs2103-AY2021S1/forum/issues/71), [\#110](https://github.com/nus-cs2103-AY2021S1/forum/issues/110), [\#126](https://github.com/nus-cs2103-AY2021S1/forum/issues/126), [\#132](https://github.com/nus-cs2103-AY2021S1/forum/issues/132), [\#145](https://github.com/nus-cs2103-AY2021S1/forum/issues/145), [\#146](https://github.com/nus-cs2103-AY2021S1/forum/issues/146), [\#169](https://github.com/nus-cs2103-AY2021S1/forum/issues/169), [\#239](https://github.com/nus-cs2103-AY2021S1/forum/issues/239), [\#295](https://github.com/nus-cs2103-AY2021S1/forum/issues/295), [\#296](https://github.com/nus-cs2103-AY2021S1/forum/issues/296), [\#316](https://github.com/nus-cs2103-AY2021S1/forum/issues/316), [\#336](https://github.com/nus-cs2103-AY2021S1/forum/issues/336))
  * Gave suggestions for other teams in the class (example: [\#309](https://github.com/nus-cs2103-AY2021S1/forum/issues/309))
  * Reported bugs in other teams' products:
    * [25 issues reported during the Practical Exam Dry-Run](https://github.com/qwoprocks/ped/issues)
<br/><br/>
* **Tools**:
  * Added a [GitHub action](https://github.com/cloudbees/plantuml-github-action) to automatically generates and commits images of UML diagrams when .puml files are changed (Pull requests [\#135](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/135), [\#141](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/141))
  * Helped to integrate [TestFX](https://github.com/TestFX/TestFX) for GUI testing into the team's GitHub Continuous Integration workflow (Pull request [\#197](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/197) (Co-authored)) 
