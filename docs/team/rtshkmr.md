---
layout: page
title: Ritesh Kumar's Project Portfolio Page
---

## Project: Travelling BusinessMan

Travelling BusinessMan is a desktop application that manages notes and clients across multiple countries and timezones for businessmen who travel a lot and have many international clients. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 24 kLoC.

Given below are my contributions to the project.

* **New Features Implemented**:
  * `Client Notes`: Added [Create](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/134), Read, [Update](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/259), [Destroy](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/203) functionality for `Note` objects associated to clients along with required documentation and tests.  
  * `TagNoteMap`: Created unique `Tag` Objects and established the many-to-many relationship between `Tag` and `Note` objects to circumvent "no-DBMS constraint" imposed by the mod (Initial implementation in PR [#150](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/150)). 
  * Added storage for `Note` [#235](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/235), [#164](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/164).
<br/><br/>
* **Code contributed:** [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=rtshkmr&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **Project management:**
  * Managed project v1.1 to v1.4 on GitHub
  * Created Last-Sprint documentation PR with Templates and Descriptions to facilitate collaborative work between all members of the group (Pull Request[#351](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/351))
   
<br/><br/>

* **Enhancements to existing features**:
  * Improved Phone Number parsing using Custom Regex and adhering to international standards PR [#337](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/337)
  * Bug Fixes: 
    * Serialization/Deserialization inconsistencies for Client Notes storage [#320](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/320)
    * Inconsistent order when refreshing Notes after editing [#312](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/312)
    * Minor UI Interactions [#336](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/336)
    
<br/><br/>

* **Documentation**:
  * User Guide:
      * Updated Client Note features
      * Updated Help Instruction on command syntax
      * Contextualised UG to the Target User, added appendices and screenshots to wrap up v1.3 milestone submission [#276](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/276) 
        * Added `Appendix A: Introduction to the UI` for First-Time users of the application
        * Added `Appendix B: International Standards Followed` to clarify our syntax related decisions and added useful external links to aid users
  * Developer Guide:
      * Added Proposed Implementation for Finding by Tags to utilise the many-to-many relationship tracked by `TagNoteMap`
      * Added Description for Implementation of Associating Notes and Tags 
      * Added PUML Sequence diagram to explain how `TagNoteMap` is initialised
      
<br/><br/>
   
* **Community**: 
  * PRs reviewed, some with changes requested (noteworthy ones): [#118](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/118), [#149](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/149), [#161](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/161), [#185](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/185), [#352](https://github.com/AY2021S1-CS2103T-F11-4/tp/pull/352)
  * Added a tip on **JVM shutdown hook** on the module page [#85](https://github.com/nus-cs2103-AY2021S1/forum/issues/85)
  * Reported bugs in other teams' products from a module-coordinated bug bounty. [Issues created here](https://github.com/rtshkmr/ped/issues).
  
