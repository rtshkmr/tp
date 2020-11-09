---
layout: page
title: Developer Guide
---
<div>
    <img style="-webkit-box-shadow: none; -moz-box-shadow: none; box-shadow: none;" src="images/TBM_BRAND.png" width="500px" height="500px">
</div>

## **Introduction**

Welcome! We are excited that you want to learn about **_TBM_**. **_TBM_** is a specialised contact management system targeted at business people with many international clients.
<br><br> 
This page contains step-by-step instructions on how to get started in contributing or getting yourself involved in this awesome project! However, before you get too excited, allow us to introduce you to the core concepts that underpins the heart of **_TBM_**.

### Travelling BusinessMan Design Concepts

1. Lightweight/Portable: **_TBM_** has a small footprint and runs fast on any platform.

1. Scalable: Software which does not deteriorate in performance with increasing data sizes.

1. Extensible: A community driven software development process, which encourages innovative inputs and contributions from our users. Features can be easily integrated into the application when needed.

1. Privacy: We do not collect, store or misappropriate our client's data.

1. CLI Optimised: Built by users for users. Our features in the application cater to users highly accustomed to the CLI.

These concepts are the foundations of **_TBM_**, furthermore we believe in the value of having users be active contributors to our project.
With that being said, this guide will help orient you eager developers quickly, to things like:

1. Software design choices of **_TBM_**.

1. Architecture of **_TBM_**.

1. Implementations for the array of features **_TBM_** offers.

--------------------------------------------------------------------------------------------------------------------

## Table of Contents

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide: [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<img src="images/ArchitectureDiagram.png" width="450" />

The ***Architecture Diagram*** given above explains the high-level design of the App. Given below is a quick overview of each component.

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/AY2021S1-CS2103T-F11-4/tp/tree/master/docs/diagrams) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

**`Main`** has two classes called [`Main`](https://github.com/AY2021S1-CS2103T-F11-4/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2021S1-CS2103T-F11-4/tp/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

Each of the four components,

* defines its *API* in an `interface` with the same name as the Component.
* exposes its functionality using a concrete `{Component Name}Manager` class (which implements the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

![Class Diagram of the Logic Component](images/LogicClassDiagram.png)

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `client delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

The sections below give more details of each component.

### UI component

![Structure of the UI Component](images/UiClassDiagram.png)

**API** :
[`Ui.java`](https://github.com/AY2021S1-CS2103T-F11-4/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ClientListPanel`, `WidgetViewBox`, `StatusBarFooter`, etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2021S1-CS2103T-F11-4/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2021S1-CS2103T-F11-4/tp/tree/master/src/main/resources/view/MainWindow.fxml)
The `UI` component,

* Executes user commands using the `Logic` component.
* Listens for changes to `Model` data so that the UI can be updated with the modified data.

### Logic component

![Structure of the Logic Component](images/LogicClassDiagram.png)

**API** :
[`Logic.java`](https://github.com/AY2021S1-CS2103T-F11-4/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

1. `Logic` uses the `MainParser` class to parse the user command.
1. This results in a `Command` object which is executed by the `LogicManager`.
1. The command execution can affect the `Model` (e.g. adding a client).
1. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.
1. In addition, the `CommandResult` object can also instruct the `Ui` to perform certain actions, such as displaying help to the user.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `client delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

### Model component

![Structure of the Model Component](images/ModelClassDiagram.png)

**API** : [`Model.java`](https://github.com/AY2021S1-CS2103T-F11-4/tp/tree/master/src/main/java/seedu/address/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user’s preferences.
* stores the TbmManager data.
* exposes an unmodifiable `ObservableList<Client>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

#### Inner workings

![Design of the Client, Note, Tag, and Country Components](images/InnerWorkings.png)

The above class diagram shows the inner workings of the Note, Tag, and Country components, as well as their associations with the Client component.

### Storage component

![Structure of the Storage Component](images/StorageClassDiagram.png)

**API** : [`Storage.java`](https://github.com/AY2021S1-CS2103T-F11-4/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the TbmManager data in json format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Associating Notes and Tags

The proposed association mechanism between `Tag` and `Note` objects is facilitated by `TagNoteMap`.
It is stored internally within the `TbmManager` object. Additionally, it implements the following operations:

* `TagNoteMap#getTagsForNote()` — Returns an unmodifiable set of Tags associated to a particular Note.

* `TagNoteMap#updateTagsForNote()` — Returns an unmodifiable set of Notes associated to a particular Tag .

* `TagNoteMap#initTagNoteMapFromNotes()` —  Initialises the TagNoteMap from a set of Notes.

The `TagNoteMap#initTagNoteMapFromNotes()` is exposed in the `Model` interface as `Model#initialiseTagNoteMap()`.

Given below is an example usage scenario and how mapping mechanism behaves at each step.


`TagNoteMap`'s storage had two alternative implementations of our choosing: 

1. Create JSON adaptations of the association class which holds the relevant many-to-many relationship so that it could simply be loaded upon application start-up.

2. Programatically initialise a single `TagNoteMap` object at application start-up. 


Choosing between 1 and 2 has a trade-off between storage-file-size and some overhead operation for initialising at startup. We realised that the file size issue would be a bigger issue and our tests showed that initialising has no visiable impact on the User Experience hence we chose option 2.

The sequence diagram below highlights the key aspects of initialising `TagNoteMap` at Application Start-Up:

![Init TagNoteMap](images/TagNoteMapInitializationSequence.png) 




### Associating Notes and Countries

#### Implementation

The association between `Note` and `Country` is facilitated by `CountryNotesManager`.
Internally, `CountryNotesManager` stores a list of country notes using an instance of `javafx.collections.ObservableList<CountryNote>`.
By storing the list of country notes as an `ObservableList`, the UI would be able to track and immediately reflect any changes to the country notes list.

It implements the following operations:
* `CountryNotesManager#asUnmodifiableObservableList()` — Returns an unmodifiable `ObservableList<CountryNote>`.
* `CountryNotesManager#hasCountryNote(CountryNote countryNote)`  — Returns true if the given `countryNote` already exists in the internal `ObservableList<CountryNote>`.
* `CountryNotesManager#addCountryNote(CountryNote countryNote)`  — Adds the given `countryNote` to the internal `ObservableList<CountryNote>`.
* `CountryNotesManager#deleteCountryNote(CountryNote countryNote)`  — Deletes the given `countryNote` from the internal `ObservableList<CountryNote>`.

The following class diagram illustrates how the relevant classes in the `Model` component are related to `CountryNote`.

![Country Class Diagram](images/CountryClass.png)

Given below is a sequence diagram that shows how the `country note add` command works within the `Logic` component.

For brevity, the full command `country note add c/COUNTRY_CODE nt/NOTE_STRING` will be substituted by `country note add`.

Note that the `MainParser#parseCommand(userInput)` calls `CountryNoteAddCommandParser#parse(userInput)` which in turn parses the user input into a `CountryNote` object, and returns an instance of a `CountryNoteAddCommand` with the `CountryNote` instance passed in as an argument to the constructor of `CountryNoteAddCommand`.
Hence, `CountryNoteAddCommand` stores a `CountryNote` object. For brevity, the aforementioned sequence of method calls will be excluded from the following sequence diagram.

![Country Note Add Sequence Diagram](images/CountryNoteAddSeqDiag.png)

### Switching between displaying Country Note List Panel, the Client View, and the default view

#### Implementation

The mechanism to switch between displaying the Country Note List Panel, the Client View, and the default view is facilitated by the state of the `CommandResult` after executing the user command.

`CommandResult` implements the following operations that are relevant to the Display Panel: 
* `CommandResult#shouldDisplayClient()` — Returns true if the UI should display the client view, otherwise returns false.
* `CommandResult#shouldDisplayCountryNote()` — Returns true if the UI should display the country notes view, otherwise returns false.
* `CommandResult#shouldResetWidget()` — Returns true if the UI should reset the display panel to its default view, otherwise returns false.

The following activity diagram illustrates what happens to the Display Panel when the user inputs a command.  

![Switching Country Note Panel, Client View, and default view](images/CountryNotePanelClientViewActDiag.png)

### Suggesting clients

#### Implementation

The suggestion mechanism is facilitated by `filteredClients` and `sortedFilteredClients` in `ModelManager`. They are instances of `javafx.collections.transformation.FilteredList<Client>` and `javafx.collections.transformation.SortedList<Client>` respectively. The `SortedList` wraps around the `FilteredList`, which in turn wraps around the `ObservableList` of clients. Any change in the underlying `ObservableList` or in the `FilteredList` will propagate up to the `SortedList`. The two lists implement the following relevant operations:
* `FilteredList<Client>#setPredicate(Predicate<? super Client> p)` — Filters out any clients that do not match the predicate in the list.
* `SortedList<Client>#setComparator(Comparator<? super Client> p)` — Sets the comparator for the client list, which will automatically sort the clients in the `SortedList` using the comparator.

These operations are exposed in the `Model` interface as `Model#updateFilteredClientList()` and `Model#updateSortedFilteredClientList()` respectively.

The following activity diagram summarizes what happens when a user inputs a `client suggest` command.

![Suggest Activity Diagram](images/SuggestActivityDiagram.png)

Given below is an example usage scenario and how the suggestion mechanism behaves at each step.

Step 1: The user executes `client suggest by/contract` to list the suggested clients sorted by contract expiry dates. At this point, `filteredClients` is showing all clients.

![SuggestState0](images/SuggestState0.png)

Step 2: The `client suggest` command calls `Model#updateFilteredClientList()` with the contract expiry date predicate (which checks if a client has a contract expiry date). `Model` updates the `filteredClients` object with the contract expiry date predicate which filters out all clients without an existing contract expiry date.

![SuggestState1](images/SuggestState1.png)

Step 3: The `client suggest` command calls `Model#updateSortedFilteredClientList()` with the contract expiry date comparator (which sorts clients by earliest contract expiry date). `Model` updates the `sortedFilteredClients` object with the contract expiry date comparator which gives us clients in order of increasing contract expiry date.

![SuggestState2](images/SuggestState2.png)

Step 4: The change is then propagated to `Ui`, which updates the displayed clients in `ClientListPanel`.

Step 5: The user decides to execute the command `client list`, which resets the `filteredClients` objects to have all clients, and resets the `sortedFilteredClients` to the default sorting order, and this in turn resets the displayed clients in `ClientListPanel` as well.

The following sequence diagram shows how the suggest operation works:

![Suggest Sequence Diagram](images/SuggestSequenceDiagram.png)

### Command History

A command history greatly optimises the user's productivity by removing the need to repetitively type out similar commands.
Furthermore, it allows backwards viewing of previous commands given, which is similar to the CLI experience. 

However, the difference between the traditional CLI history and **_TBM_**'s CLI history is that **_TBM_**'s history only accepts valid commands. Invalid commands entered will not be included will not appear in the command history.
Furthermore, the modification of previous commands in the history do not override the current command as well. The current input being edited is stored in a separate variable as the user scrolls through the command history. This is proposed upgrade for the CLI history feature.

The implementation of command history is backed by a list of strings `history`. Upon the entering of a valid command, the command history, `CommandHistory`.

<img src="images/CommandHistoryActivity.png" style="width: 100%" alt="Command History Activity Diagram" />

Below is a step-by-step description of what CommandHistory does.

Step 1: User enters a series of valid commands. `CommandHistory` saves all the commands.

Step 2: User presses UP button.

Step 3: `CommandHistory` **tries** to shift the pointer upwards, and retrieves the command string at `history[pointer]`.

Step 4: User presses DOWN button.

Step 5: `CommandHistory` **tries** to shift the pointer downwards, and retrieves the command string at `history[pointer]`.

  - If it reaches the end of the history, the pointer in `CommandHistory` will be of value equal to the length of the history. And it will retrieve the current input being typed.
  
### \[Proposed\] Modification to command history

The current command history does not allow users to edit a previously entered command, and save it as teh current command being edited.

A further, and closer to CLI implementation would be to support this feature. For example: 

1. User enters a series of commands:

    1. `client view 1`
    1. `client view 2`
    1. `client note add 1 nt/Errands`

1. User presses up to a previous command (ii).

1. User modifies the command to `client view 5`.

1. User continues to navigate the history.

1. User returns to the command he is modifying, which is `client view 5`.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedTbmManager`. It extends `TbmManager` with an undo/redo history, stored internally as an `tbmManagerStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedTbmManager#commit()` — Saves the current TbmManager state in its history.
* `VersionedTbmManager#undo()` — Restores the previous TbmManager state from its history.
* `VersionedTbmManager#redo()` — Restores a previously undone TbmManager state from its history.

These operations are exposed in the `Model` interface as `Model#commitTbmManager()`, `Model#undoTbmManager()` and `Model#redoTbmManager()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedTbmManager` will be initialized with the initial TbmManager state, and the `currentStatePointer` pointing to that single TbmManager state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `client delete 5` command to delete the 5th client in `TbmManager`. The `client delete` command calls `Model#commitTbmManager()`, causing the modified state of the TbmManager after the `client delete 5` command executes to be saved in the `tbmManagerStateList`, and the `currentStatePointer` is shifted to the newly inserted TbmManager state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `client add n/David …​` to add a new client. The `client add` command also calls `Model#commitTbmManager()`, causing another modified TbmManager state to be saved into the `tbmManagerStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitTbmManager()`, so the TbmManager state will not be saved into the `tbmManagerStateList`.

</div>

Step 4. The user now decides that adding the client was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoTbmManager()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous TbmManager state, and restores the TbmManager to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial TbmManager state, then there are no previous TbmManager states to restore. The `undo` command uses `Model#canUndoTbmManager()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoTbmManager()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the TbmManager to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `tbmManagerStateList.size() - 1`, pointing to the latest TbmManager state, then there are no undone TbmManager states to restore. The `redo` command uses `Model#canRedoTbmManager()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `client list`. Commands that do not modify `TbmManager`, such as `client list`, will usually not call `Model#commitTbmManager()`, `Model#undoTbmManager()` or `Model#redoTbmManager()`. Thus, the `tbmManagerStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitTbmManager()`. Since the `currentStatePointer` is not pointing at the end of the `tbmManagerStateList`, all TbmManager states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `client add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

![CommitActivityDiagram](images/CommitActivityDiagram.png)

#### Design consideration:

##### Aspect: How undo/redo executes

* **Alternative 1 (current choice):** Saves the entire TbmManager.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `client delete`, just save the client being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.


### \[Proposed\] Find by Tags

The use of `TagNoteMap` as an association class to keep track of the many-to-many relationship between `Tag` and `Note` allows for easy addition of a "Find by Tags" feature. `TagNoteMap` has `tagToNotesMap` and `noteToTagsMap` which map a single entity to a collection of all the other associated objects of the other entity. 

Hence a valid command syntax of `find byTag/TAG_NAME` would be a minimal change to the parsing methods that already exist.

The complication lies in the GUI implementation to display the output of the find by tag. This is because the return values will be both country and client notes (which themselves are associated to countries and clients) so while we might reuse the Display Panel area, we would still a modification of cards to indicate the entity type (`Client` or `Country`). 



--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix A: Requirements**

### Product scope

**Target user profile**: Businesspeople who travel a lot

* has a need to manage a significant number of clients that span multiple timezones
* prefers desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Manages notes, client contact details and preferences across multiple countries and timezones


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                     | So that I can…​                                                        |
| -------- | --------------------------------- | ---------------------------------------- | ---------------------------------------------------------------------- |
| `* * *`  | potential user                    | see the App populated with sample data   | easily see how the App will look like when it is in use                |
| `* * *`  | user ready to start using the App | purge all current data                   | get rid of sample/experimental data I used to explore the app          |
| `* * *`  | new user                          | see usage instructions                   | refer to instructions when I forget how to use the App                 |
| `* * *`  | user                              | add contact details of business contacts |                                                                        |
| `* * *`  | user                              | add personal preferences of existing business contacts | keep track of them                                       |
| `* * *`  | user                              | add meeting notes after meeting with existing business contacts | keep track of my meetings                       |
| `* * *`  | user                              | add information on business deals and contract expiration dates | let **_TBM_** keep track of them                      |
| `* * *`  | user                              | filter business contacts by country      |                                                                        |
| `* * *`  | user                              | add cultural or relevant notes regarding a specific country | refer to these notes in the future                  |
| `* * *`  | user                              | view my previously added information regarding a country |                                                        |
| `* * *`  | user                              | see and search for all my clients        | easily find and view information on my clients without having to go through the entire list |
| `* * *`  | user                              | edit my clients' information           |                                                                        |
| `* * *`  | user                              | save the information I enter             |                                                                        |
| `* * *`  | user                              | transfer my data across different computers |                                                                     |
| `* * *`  | user                              | manage the priority of business aspects regarding my clients (e.g. expiring contracts) |                          |
| `* * *`  | user                              | receive suggestions on which clients to catch up on if I have not done so for quite long | maintain my relationship with my clients |
| `* * *`  | user                              | view who is likely to be free in other timezones currently | contact them and catch up with them                  |
| `* * *`  | user                              | delete a client                          | remove entries that I no longer need                                   |
| `* * *`  | user                              | note down and be reminded when my clients have their noteworthy moments (dates) | congratulate them in a timely manner |
| `* *`    | long-time user                    | archive/hide unused data                 | not be distracted by irrelevant data                                   |
| `* *`    | user                              | manage my flight bookings across countries |                                                                      |
| `* *`    | user                              | manage my hotel bookings in a particular country |                                                                |
| `* *`    | user                              | visualize my flight and hotel bookings on a timeline |                                                            |
| `* *`    | user                              | filter business contacts by country and state |                                                                   |
| `* *`    | user                              | undo and redo                                 | correct my mistakes easily                                        |
| `*`      | user                              | hide private contact details             | minimize chance of someone else seeing them by accident                |
| `*`      | user                              | find clients nearest to my current location   | find someone near me to meet                                      |
| `*`      | user                              | customize the GUI   | make it more intuitive to use or more visually appealing                                    |
| `*`      | proficient CLI user               | type out my emails straight from the application without moving to the browser | have a faster workflow           |

### Use cases

(For all use cases below, the **System** is the `**_TBM_**` and the **Actor** is the `user`, unless specified otherwise)

#### UC1 - Adding a Client

**MSS**

1. User meets secures a new business deal/client

2. User attempts to add the all associated parties and their information into **_TBM_**.

3. User successfully adds the all new information into **_TBM_**.
  
      Use case ends.

**Extensions**

* 2a. A party has a prior entry in **_TBM_**, which shows that User has a previous professional encounter with the party.

  * 2a1. User does not add the new party as it will duplicate entries in the **_TBM_**.
  
  * 2a2. User can choose to update/edit the client information instead.

      Use case ends.

#### UC2 - Finding Clients

**MSS**

1. User requests to find a client.

2. **_TBM_** shows a list of clients that match user's query.

    Use case ends.

**Extensions**

* 1a. The list of clients is empty.

  Use case ends.

#### UC3 - Saving data

**MSS**

1. User enters a valid command that alters data (E.g. <u>`adding a client (UC1)`</u>).

2. Modified data gets stored in the existing data file.

3. **_TBM_** shows a message indicating command has been executed successfully.

    Use case ends.

**Extensions**

* 2a. An error occurred while saving the modified data to the existing data file.

  * 2a1. **_TBM_** reloads the data from the existing data file.
  
  * 2a2. **_TBM_** shows an error message.
  
      Use case ends.

#### UC4 - Filtering by country

**MSS**

1. User inputs a country as filter.

2. **_TBM_** shows all clients belonging to that country.

    Use case ends.

**Extensions**

* 1a. Invalid country is given.

  * 1a1. **_TBM_** shows an error message.
  
      Use case ends.

#### UC5 - Clearing all entries from **_TBM_**

**MSS**

1. User requests to clear all entries.

2. **_TBM_** asks the user to confirm.

3. User confirms that they want to clear all entries.

4. **_TBM_** clears all entries.

    Use case ends.

**Extensions**

* 1a. There are no entries.

    Use case ends.

* 3a. User decides not to clear all entries.

    Use case ends.
  
#### UC6 - Obtaining client suggestions

**MSS**

1. User wants to view clients who are available for a quick call.

2. User passes this criteria via a command.

3. **_TBM_** filters the clients, only showing clients who are available.

    Use case ends.

**Extensions**

* 1a. User wishes to obtain suggestions based on a different criteria (E.g. clients whose contracts are expiring).
    
    * 1a1. User passes in a different criteria via the command.
    
    * 1a2. **_TBM_** filters and sorts the clients, showing those whose contracts are expiring first.
    
        Use case ends.

* 2a. User passes in an invalid criteria.

    * 2a1. **_TBM_** shows an error message.

        Use case ends.

#### UC7 - Noting down information about a client

**MSS**

1. User requests to add a note to a client.

2. **_TBM_** adds the note to the client specified.

3. **_TBM_** displays that note.

    Use case ends.

**Extensions**

* 1a. The note is not a valid note.

  * 1a1. **_TBM_** shows an error message.
      
      Use case ends.
  

### Non-Functional Requirements

1.  **_TBM_** should work on any _mainstream OS_ as long as it has Java 11 or above installed.
1.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
1.  **_TBM_** should be able to recover its previous stable state from the data file if it crashes.
1.  The data file should be stored locally in a human-editable text file.
1.  **_TBM_** should be used by a single user.
1.  **_TBM_** should be able to hold up to 1000 business contacts without a noticeable sluggishness in performance for typical usage.
1.  **_TBM_** should be able to hold up to 5000 total client notes without a noticeable sluggishness in performance for typical usage.
1.  **_TBM_** can handle at most 10000 business contacts and at most 50000 total client notes.
1.  **_TBM_** will only accept countries that are specified by the [ISO3166](https://en.wikipedia.org/wiki/List_of_ISO_3166_country_codes) specification.
1.  **_TBM_** will only accept the UTC offsets defined in this [list](https://www.timeanddate.com/time/current-number-time-zones.html).
1. **_TBM_** is not required to validate that the timezone of a business contact correctly matches his/her country.
1. **_TBM_** should retain all functionalities even when it is not connected to the internet.
1. The size of the **_TBM_** _JAR_ file should not exceed 100Mb.

### Glossary

* **Business Contact**: Synonymous with **Client**
* **Client**: Refers to a person whom the user is conducting his/her business with
* **UTC**: [Coordinated Universal Time](https://en.wikipedia.org/wiki/Coordinated_Universal_Time)
* **Mainstream OS**: Windows, Linux, Unix, macOS
* **_TBM_**: Initialism for Travelling BusinessMan

--------------------------------------------------------------------------------------------------------------------

## **Appendix B: Instructions for manual testing**

Given below are instructions to test the app manually. Additionally, testers can look through our [Testing Guide](Testing.md) to get started on automated testing.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.
</div>

### Launch

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample clients and notes.

### Deleting a client

1. Deleting a client while all clients are being shown

   1. Prerequisites: List all clients using the `client list` command. Multiple clients in the list.

   1. Test case: `client delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the results display.

   1. Test case: `client delete 0`<br>
      Expected: No client is deleted. Error details shown in the display.

   1. Other incorrect delete commands to try: `client delete`, `client delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.


--------------------------------------------------------------------------------------------------------------------

## **Appendix C: Effort**

With an estimate value of **100** to be the level of effort required to develop **AB3**, we would place the effort required to deliver the current version of ****_TBM_**** at **205**.

Major changes are changes that required a significant amount of effort (an estimate minimum of 68 man-hours per change) from the team to coordinate, plan, implement, review, and optimize, while minor changes are changes that required a moderate amount of effort (an estimated minimum of 20 man-hours per change).

### Major Changes

**1. Note Package**

To give users the ability to write notes capable of being handled in a generic manner for the entities of `Client` and `Country`, a `Note` class was created and a `CountryNote` class extended from it. Client notes could be stored easily by allowing clients to contain client-specific notes and country notes had to be managed by the `CountryNotesManager` controller class to handle the mapping between unique `Country` objects and their associated Notes. 

Unique`Tag` objects were implemented by having a `TagNoteMap` class that keeps track of the association between unique `Note` and `Tag` objects. Storage of this many-to-many relationship required effort in circumventing the no-DBMS constraint imposed by the module. We chose to programmatically generate the `TagNoteMap` object upon **_TBM_**'s start-up with the aim of reducing file-size of the JSON data files on the user's hard-drive.

**2. Additional Client Fields**

We refactored the codebase as well as the documentation from `Person` in AB3 to `Client` in **_TBM_** with additional fields to fit our target audience. Adding of simple tags was moved to a new note package that allowed more freedom and flexibility in adding data to clients. We added new fields in `Country`, `Timezone`, `ContractExpiryDate`, `LastModifiedInstant`. `Country` and `Timezone` required non-trivial validation and testing to verify their correctness. `Timezone`, `ContractExpiryDate` and `LastModifiedInstant` are necessary for suggestions. The `LastModifiedInstant` class serves as metadata that is not directly exposed to the user but is only updated upon any logical modifications to a client object.

**3. Client Suggestion**

A `ClientSuggestionType` class was created which acts as a controller class that generates the combined client predicate and comparator. For the three suggestion criteria, classes were created where necessary to encapsulate the filtering and sorting logic. The non-trivial implementation consisted of accounting for timezone differences, and the sorting and filtering of JavaFX's `ObservableList`. New classes like `Timezone`, `ContractExpiryDate`, and `LastModifiedInstant` were added to `Client` to facilitate prioritizing which clients to suggest to the user. 

**4. GUI**

In line with human-centric UX design, **_TBM_**'s GUI was changed to be visually appealing, intuitive, informative, smooth, and have a flexible layout. We made effort to ensure that the layout was consistent and eliminated weird behaviours resulting from limitations of JavaFX. One challenge was that the client cards in **AB3** had an undefined behaviour where they changed sizes when being clicked on, even though there is no expected response upon clicking. We fixed this by setting `ListViewCell` to `disabled`.

**5. Automated GUI Testing**

As recommended by the reference book for CS2103T, we adopted the TestFX library for automated GUI testing. We modified the GUI to listen to the state of the application to achieve a dynamic display. TestFX provided a good testing framework to automate our GUI unit tests. We simulated user interactions with TestFX for our application's system testing.

However, the implementation encountered many hiccups due to the limited amount of documentation available. Significant effort was taken to troubleshoot the implementation of the framework and to run it in headless mode, so that we could integrate it into our Github Continuous Integration workflow.

**6. Non-GUI Testing**
 
We made sure to write rigorous tests for every piece of code we added, expanded coverage, and tested for boundary cases. We also standardized and abstracted the testing code to make it more maintainable and readable. This, together with GUI testing, managed to increase code coverage from 72% (AB3) to 90%.

### Minor Changes

**1. Command Format**

The command format of **_TBM_** had to be modified from AB3 to account for client-related, client-note-related and country-note-related commands.

**2. Command Parsing**

Extending on the original parsing of AB3, we modified the command parsing to accommodate our command format, and further modularized code in `MainParser` to make the codebase easier to read and test.

**3. Command History**

Keeping in line with **_TBM_**'s time-conscious target users who prefer using CLIs, we implemented a command history to provide an authentic CLI experience and to increase user-friendliness.

**4. Code Clean-up**

Code clean up required a moderate amount of effort thanks to our decision to enforce clean coding practices from the get-go: each PR had to reach a minimum of 80% diff line coverage in terms of testing, had to adhere to the module's code quality guidelines in terms of variable naming and documentation, and had to go through extensive PR reviews. This spread the code clean-up task over the entire project instead of being an issue in its own regard. We also did an in-depth examination of the whole codebase at the end of v1.4 to standardize all JavaDocs and improve code quality.
