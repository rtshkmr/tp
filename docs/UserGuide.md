---
layout: page
title: User Guide
---
<!-- NTS: end every sentence with a period -->
<div>
    <div>
        <img src="images/TBM_BRAND.png" width="160px">
    </div>
    <p>
        <h3><i>Welcome to the Travelling BusinessMan User Guide!</i></h3>
    </p>
    <p>
        <strong><i>Travelling BusinessMan</i></strong>, <strong><i>TBM</i></strong>, is a specialised contact management system targeted at business people with many international clients.
    </p>
    <p>
        <strong><i>TBM</i></strong> sorts and organises your clients for you, streamlines your workflow, improves business efficiency and so much more. All this is at the convenience of the Command-Line Interface. <strong><i>TBM</i></strong> does more than just tracking your clients!<br>
    </p>
    <p>
        This user guide will be a starting point for you in getting oriented with how <strong><i>TBM</i></strong> works and how you can integrate it into your professional journey!
    </p>
</div>

   ![Client Notes](images/command-screenshots/client_view.png) 
   <p align="center"><i>Figure 1. TBM Application</i></p>

--------------------------------------------------------------------------------------------------------------------

## Table of Contents

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quickstart

<div markdown="block" class="alert alert-info">

**:information_source:** Please refer to the [Introduction to UI](#appendix-a---introduction-to-the-ui) if you're trying **_TBM_** out for the first time.
</div>

Before you start using **_TBM_**, 

1. Ensure you have Java 11.

1. Download the application file (.jar file) from [here](https://github.com/AY2021S1-CS2103T-F11-4/tp/releases).

1. Navigate to the directory that the application file was downloaded to. To start the application:

    1. Double click on the `TBM.jar` file and the application will start shortly.

    1. Alternatively, open any shell terminal in the current directory and run the command `java -jar TBM.jar`.

Now that you have started **_TBM_**,

1. Type any command in the command box and press Enter to execute it.

1. ***(Recommended for new users)*** The [`help`](#help-window) command will show a help page of some commonly
 used commands to fully utilise **_TBM_**.

   * **_Either_** press the **F1** key on your keyboard, **_or_** type the [`help`](#help-window) command in the command box and
    press Enter to execute it. e.g. typing [`help`](#help-window) and pressing Enter will open the help window.<br>

--------------------------------------------------------------------------------------------------------------------

## Features

The following section outlines the commands supported by **_TBM_**. A short description is given for each command along with the format that they should be executed with. Complex commands also come with examples to demonstrate the purpose that the command serve.

Certain commands require parameters and the summary of these parameters as well as their prefixes and constraints can be found in our [parameters summary table](#parameter-constraints-summary). **Note that parameter prefixes such as `t/` and `nt/` are special keywords and you should not use them for any other purposes except for indicating the start of a parameter.**

A summary of all commands can be found in our [command summary table](#command-summary)
<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `client add n/NAME`, `NAME` is a parameter which can be used as `client add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [ce/CONTRACT_EXPIRY_DATE]` can be used as `n/John Doe ce/2-3-2020`(both supplied) or as `n/John Doe`(optional parameter missing).

* Items in round brackets mean that you have to supply at least one of them.<br>
  e.g. `INDEX (n/NAME) (n/PHONE_NUMBER)` can be used as `1 n/John Doe` or `1 p/98899889` or `1 n/John Doe p/98899889`, but not as `1` (i.e. both items are not supplied).

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

</div>

### Viewing help: `help`

Shows commonly used commands for **_TBM_** in a [new help window](#help-window). Pressing Esc will close this window.

Format: `help`

### Viewing all saved clients: `client list`

Shows a list of all clients in **_TBM_**. If a client's data fields are too long, they may be cut off in the client card, but you can view them in full using the [`client view`](#viewing-a-client--client-view) command.
This command will also reset the left display panel to its default view. 

Format: `client list`

### Adding a client: `client add`

Adds a new client to **_TBM_**.

Format: `client add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS c/COUNTRY_CODE tz/TIMEZONE [ce/CONTRACT_EXPIRY_DATE]`

Please refer to [Appendix B](#appendix-b-international-standards-followed) to understand the International Standards that **_TBM_** follows for phone numbers and country codes.
Example:

* Command: `client add n/Katya p/98123456 e/katya@yahoo.com a/Vladivostok, Nevelskogo, bld. 15, appt. 256 c/RU tz/GMT+3 ce/2-3-2020`<br>

  Adds a new client with name **Katya**, phone number **98123456**, email **katya@yahoo.com**, address **Vladivostok, Nevelskogo, bld. 15, appt. 256**, country **Russia**, timezone **GMT+3**, contract expiry date **2 Mar 2020**.

### Viewing a client: `client view`

Views the client specified by the `INDEX` parameter.
<div markdown="block" class="alert alert-info">

**:information_source:** Please refer to [this](#ui-when-viewing-clients) if you need to be re-acquainted with the UI for client related commands
</div>

Format: `client view INDEX`

Example:

Given the current list of clients:
```
1. Katya
2. Alex
```
Command: `client view 2`

The information for the client at index 2 of the list will be shown. In the example above, Alex's information will be displayed.

### Finding clients: `client find`

Finds clients whose names contain any of the given keywords.

Format: `client find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`.
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`.
* Clients matching at least one keyword will be returned (i.e. OR search). e.g. `Hans Bo` will return `Hans Gruber` and `Bo Yang`.

Examples:

* Command: `client find katya`

   Finds all clients with names that matches **katya**.

* Command: `client find Katya Hans`

   Finds all clients whose names contain either **Katya** or **Hans**.

### Editing a client: `client edit`

Edits a client's information by their index in the list view. Only edits the fields that have been passed in as parameters.

Format: `client edit INDEX (n/NAME) (p/PHONE) (e/EMAIL) (a/ADDRESS) (c/COUNTRY_CODE) (tz/TIMEZONE) (ce/CONTRACT_EXPIRY)`

Examples:

* Command: `client edit 5 n/Katya`

    Only edits **name** to `Katya`, other fields remain the same.

* Command: `client edit 4 n/Alek p/34842097 e/dcsdcr@nus.edu.sg`

    Edits **name** to `Alek`, **phone number** to `34842097` and **email** to `dcsdcr@nus.edu.sg`, other fields remain the same.

* Command: `client edit 1 c/JP tz/GMT+7`

    Edits **country** to `Japan` and **timezone** to `GMT+7`, other fields remain the same.

Given the client below:

```
Client 1
--------
Name: Alex
Phone: 911
Country: Singapore
Timezone: GMT+8
...(other fields omitted)
```
Command: `client edit 1 c/JP tz/GMT+7`

Alex's **country** and **timezone** will be edited, and the following will be the result.

```
Client 1
--------
Name: Alex
Phone: 911
Country: Japan
Timezone: GMT+7
...(other fields omitted)
```

### Deleting a client: `client delete`

Deletes a client by their index in the list view.

Format: `client delete INDEX`

Example:

* Command: `client delete 5`

    Deletes the client at index 5 of the list panel.

### Saving data

Your data is automatically saved after every command you enter. The file is saved in the ubiquitous `.json` format, which
allows you to edit the file manually without even opening **_TBM_** up.

The location for this file is `./data/tbmManager.json` (indicated at the bottom left of the application), where the
 `.` represents the directory where you have saved `TBM.jar`. Here's a snippet of this editable text file:

```json5
{
  "clients" : [ {
    "name" : "Alex Yeoh",
    "phone" : "87438807",
    "email" : "alexyeoh@example.com",
    "address" : "Blk 30 Geylang Street 29, #06-40",
    "country" : "SG",
    "timezone" : "GMT+8",
    "contractExpiryDate" : "21-4-2022",
    "lastModifiedInstant" : "2020-01-01T00:00:00Z",
    "clientNotes" : [ {
      "contents" : "Wants to take his wife to Norway for New Years' Day",
      "countryCode" : "NULL_CC",
      "tags" : [ "romantic", "holidayPlans", "travel" ]
    } ]
  } ]
  . . .
}
```

<div markdown="block" class="alert alert-info">
**:information_source:** Tweaking this file should be done <strong><i>only</i></strong> if you're familiar with JSON
. If you're new to JSON, <a href="https://www.json.org/json-en.html">here's</a> a good place to learn more.
</div>

### Adding client notes: `client note add`

Adds a note to the client at the specified index in the list view. If a note with the exact same content and tags already exists, a "duplicate note" error will be displayed.

Format: `client note add CLIENT_INDEX nt/NOTE_STRING [t/TAG]...`

Examples:

* Command: `client note add 1 nt/wants meetings to be as short as possible (preferably 30 mins) t/pref`

    Adds a **note** with a **tag** to the **first** client in the list.

* Command: `client note add 2 nt/prefers emails to calls`

    Adds a **note** to the **second** client in the list.

* Command: `client note add 4 nt/need to slowly convince him to sign the contract t/meeting `

    Adds a **note** with a **tag** to the **fourth** client in the list.

### Deleting client notes: `client note delete`

Deletes a note of a client (denoted by a client's index) by the note's index.

Format: `client note delete CLIENT_INDEX NOTE_INDEX`

Examples:

Given a list of notes:

```
Client: 3
Notes:
1. Loves dogs [tag: pref]
2. Hates cats [tag: pref]
```

Command: `client note delete 3 2`

The above command deletes the note regarding "Hates cats". The resulting information will look like:

```
Client: 3
Notes:
1. Loves dogs [tag: pref]
```

### Editing client notes: `client note edit`

Edits a note of a client (denoted by that client's index) by the note's index. Supplying tags to the command will add the tags onto the current existing tags for that note.

Format: `client note edit CLIENT_INDEX NOTE_INDEX (nt/NOTE_STRING) (t/TAG)...`

Examples:

Given a list of notes:

```
Client: 3
Notes:
1. Loves dogs [tag: pref]
2. Hates cats [tag: pref]
3. Apprehensive of resigning contract [tag: meeting]
```

Command: `client note edit 3 2 nt/Loves cats t/important`

The original note containing "Hates cats" will be changed to "Loves cats" with an additional tag (on top of the existing one). The resulting list will look like:

```
Client: 3
Notes:
1. Loves dogs [tag: pref]
2. Loves cats [tag: pref, important]
3. Apprehensive of resigning contract [tag: meeting]
```

### Filtering clients by country: `country filter`

Filters the list of clients by the specified country.

Format: `country filter c/COUNTRY_CODE`

Examples:

* Command: `country filter c/SG`

    Filters by contacts in Singapore.

* Command: `country filter c/RU`

    Filters by contacts in Russia.

### Viewing notes for a country: `country note view`

<div markdown="block" class="alert alert-info">

**:information_source:** Please refer to [this](#ui-when-viewing-country) if you need to be re-acquainted with the UI for country note related commands
</div>

Views the list of country notes that are associated with the country corresponding to the country code supplied.
If no country code is given, all country notes in **_TBM_** will be shown.

Format: `country note view [c/COUNTRY_CODE]`

Examples:

* Command: `country note view`

    All country notes in **_TBM_** will be displayed.

* Command: `country note view c/SG`

    Only country notes for **Singapore** will be displayed.

### Adding notes for a country: `country note add`

Adds a note that will be associated with the country corresponding to the country code supplied.

Format: `country note add c/COUNTRY_CODE nt/NOTE_STRING [t/TAG]...`

Examples:

* Command: `country note add c/SG nt/has one of the lowest corporate taxes in the world t/tax`

    Adds a **note** for the country of **Singapore** with the following specifications: 
    - Content: **_has one of the lowest corporate taxes in the world_** 
    - Tag: **_tax_** 
    
* Command: `country note add c/CN nt/building good relations (guanxi) is important when conducting business here t/intercultural`

    Adds a **note** for the country of **China** with the following specifications: 
    - Content: **_building good relations (guanxi) is important when conducting business here_** 
    - Tag: **_intercultural_** 
    
* Command: `country note add c/IN nt/is world's fastest growing economy`

    Adds a **note** for the country of **India** with the following specifications:
    - Content: **_is world's fastest growing economy_** 
    - Tag: **_untagged_**
    
### Editing notes for a country: `country note edit`

Edits a note that is associated with a specific country at the given index based on the **current view** of the [list panel displaying country notes](#ui-when-viewing-country). Supplying tags to the command will add the tags onto the current existing tags for that note.

Due to ambiguity, editing of a country note when the country notes panel is not being displayed is not allowed, and an error message will be shown if this is attempted.

Format: `country note edit INDEX (nt/NOTE_STRING) (t/TAG)...`

Example:

Given the list of country notes in the country note list panel:

```
Japan
--------
1. A high-context country, hence building relationships is important for doing business [tag: intercultural]

Singapore
--------
2. Small domestic market as compared to rest of SEA countries [tag: marketsize]

Thailand
--------
3. Political unrest in the capital [tag: politics]
```

Command: `country note edit 2 nt/Rising middle class with increased purchasing power t/spending`

Edits the second country note in the country notes panel. In this case, it will be the note under **Singapore**. The resulting list will look like:

```
Japan
--------
1. A high-context country, hence building relationships is important for doing business [tag: intercultural]

Singapore
--------
2. Rising middle class with increased purchasing power [tag: marketsize, spending]

Thailand
--------
3. Political unrest in the capital [tag: politics]
```

### Deleting notes for a country: `country note delete`

Deletes a note that is associated with a specific country at the given index based on the **current view** of the [list panel displaying country notes](#ui-when-viewing-country).
Due to ambiguity, deleting of a country note when the country notes panel is not being displayed is not allowed, and an error message will be shown if this is attempted.

Format: `country note delete INDEX`

Example:

* Command: `country note delete 1`

    Deletes the country note at index 1 of the country notes list panel.

### Getting suggestions on clients: `suggest`

Obtains a list of clients based on the suggestion type(s) passed in. This command will also reset the left display panel to its default view.

Format: `suggest by/SUGGESTION_TYPE [by/SUGGESTION_TYPE]...`

Examples:

* Command: `suggest by/available`

    Obtains a list of clients where the time is 1800-2200 in the client's timezone (off work hours).

* Command: `suggest by/frequency`

    Obtains a list of clients based on the last time their details were edited in **_TBM_**. Clients who have not been contacted for a longer period will be the first in the list.

* `suggest by/contract`

    Obtains a list of clients based on their current contract details. Clients whose contracts are expiring will be shown first.

* `suggest by/contract by/available`

    Obtains a list of clients based on their current contract details but only clients who are available will be shown. Clients whose contracts are expiring will be shown first.

### Clearing all entries: `clear`

Deletes all existing information from the application. The end result will be an application with no client/note/country data. The left display panel will be reset to its default view.

Format: `clear`

### Exiting the program: `exit`

Exits the program.

Format: `exit`

--------------------------------------------------------------------------------------------------------------------

## Glossary

* **Business Contact**: Synonymous with **Client**
* **Client**: Refers to a person whom the user is conducting his/her business with
* **GMT**: [Greenwich Mean Time](https://en.wikipedia.org/wiki/Greenwich_Mean_Time)
* **Mainstream OS**: Windows, Linux, Unix, macOS
* **_TBM_**: Initialism for Travelling BusinessMan

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous **_TBM_** home folder.<br>

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**List all clients** | `list`
**Add client** | `client add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS c/COUNTRY_CODE tz/TIMEZONE [ce/CONTRACT_EXPIRY_DATE]` <br> e.g., `client add n/Katya p/98123456 e/katya@yahoo.com a/Vladivostok, Nevelskogo, bld. 15, appt. 256 c/RU tz/GMT+3 ce/22-12-2020`
**Edit client** | `client edit INDEX (n/NAME) (p/PHONE) (e/EMAIL) (a/ADDRESS) (c/COUNTRY_CODE) (tz/TIMEZONE) (ce/CONTRACT_EXPIRY_DATE)`<br> e.g.,`client edit 3 c/JP tz/GMT+7`
**View client** | `client view INDEX` <br> e.g., `client view 2`
**Find client** | `client find KEYWORD [MORE_KEYWORDS]`<br> e.g., `client find Hans`
**Delete client** | `client delete INDEX`<br> e.g., `client delete 3`
**Add client note** | `client note add CLIENT_INDEX nt/NOTE_STRING [t/TAG]...` <br> e.g., `client note add 4 t/meeting nt/need to slowly convince him to sign the contract`
**Delete client note** | `client note delete CLIENT_INDEX NOTE_INDEX` <br> e.g., `client note delete 3 2`
**Edit client note** | `client note edit CLIENT_INDEX NOTE_INDEX (nt/NOTE_STRING) (t/TAG)...` <br> e.g., `client note edit 3 2 nt/Loves cats t/pets`
**Filter by country** | `country filter c/COUNTRY_CODE` <br> e.g., `country filter c/SG`
**View country note** | `country note view [c/COUNTRY_CODE]` <br> e.g., `country note view c/SG`
**Add country note** | `country note add c/COUNTRY_CODE nt/NOTE_STRING [t/TAG]...` <br> e.g., `country note add c/SG nt/has one of the lowest coporate taxes in the world t/tax`
**Edit country note** | `country note edit INDEX (nt/NOTE_STRING) (t/TAG)...` <br> e.g., `country note edit 1 nt/has one of the lowest coporate taxes in the world t/tax`
**Delete country note** | `country note delete INDEX` <br> e.g., `country note delete 1`
**Get suggestions** | `suggest by/SUGGESTION_TYPE [by/SUGGESTION_TYPE]...` <br> e.g., `suggest by/available by/frequency`
**Clear** | `clear`
**Exit** | `exit`
**Help** | `help`

## Parameter constraints summary

Parameter | Prefix | Constraints, Examples
--------|------------------| ----------
**NAME** | `n/` | Names should only contain alphanumeric characters and spaces, and it should not be blank. <br> e.g. `n/John Doe`
**PHONE_NUMBER** | `p/` | Phone numbers should have at least 3 digits and at most 15 digits. <br> Please refer to [Appendix B](#appendix-b-international-standards-followed) for a comprehensive description. <br> e.g. `p/81867752` or `p/+65 81867752` 
**EMAIL** | `e/` | Emails should be of the format local-part@domain. <br> e.g. `e/katya@yahoo.com`
**ADDRESS** |`a/` | Addresses can take any values, and it should not be blank. <br> e.g. `Vladivostok, Nevelskogo, bld. 15, appt. 256`
**COUNTRY_CODE** | `c/` | A 2-letter country code that follows the ISO3166 specification <br> This [finding tool](https://www.countrycode.org/) can be used. <br> e.g. `c/SG` (Singapore) 
**TIMEZONE** | `tz/` | Timezone should be given in offsets relative to [Greenwich Mean Time](https://en.wikipedia.org/wiki/Greenwich_Mean_Time). <br> e.g. `tz/GMT+8`
**CONTRACT_EXPIRY_DATE** | `ce/` | Date should be given in the format "DD-MM-YYYY". <br> e.g. `ce/10-10-2020`
**NOTE_STRING** | `nt/` | Notes can take any values, and it should not be blank. <br> e.g. `nt/Likes cats`
**TAG** | `t/` | Tags names should be alphanumeric and have a maximum of 45 characters. <br> e.g. `t/important`
**SUGGESTION_TYPE** | `by/` | Suggestion types can only be either `available`, `contract` or `frequency`. <br> e.g. `by/available`
**INDEX** | - | Index is a number greater than 0 that is based on the numberings beside each client or note. <br> e.g. `1` would refer to the first client or note.

--------------------------------------------------------------------------------------------------------------------

## Appendix A - Introduction to the UI

### Start Page

The diagram below shows the key UI elements of **_TBM_** upon first start-up.

  ![Annotated UI Default Page](images/command-screenshots/UI_annotated.png)
  <p align="center"><i>Figure 2. Default View</i></p>

* **Command Box** allows you to input commands to interact with **_TBM_**.

* **Client List Panel** will always allow you to view all your clients' information at a go. It's scrollable so that you 
never have to worry about not being able to see all your important clients at a single glance!

* **Client Card** will show you brief information about a particular client. 

* **Result Display** will show you the output of executing your command.
    * It indicates successful commands.
    
    * On the off-chance there's a syntax issue, **_TBM_** will remind you of the correct syntax it expects, something like this:
 
     ![Result Display showing error message](images/command-screenshots/result_display_error_syntax.png)
     <p align="center"><i>Figure 3. Error Display</i></p>
     
* **Display Panel** shall render *Client Cards* or *Country Cards* when relevant commands are sent. The default view shows you your local time to the precise second, as well as your current location.
   
### UI when Viewing Clients

The diagram below is what you should see upon viewing a particular client (i.e. firing up the command `client view 1
`). It shows how to identify a particular client's `CLIENT_INDEX`, and an associated note's `CLIENT_NOTE_INDEX`.

  ![Annotated Client View UI Layout](images/command-screenshots/client_view_annotated.png)
  <p align="center"><i>Figure 4. Client View</i></p>

Here, the **Client Note Card** displaying the note that's tagged as `hobbies` is referred to by the `CLIENT_INDEX` of `1`.

Being able to identify these is key to adding, deleting and editing client notes as well as editing clients.

### UI when Viewing Country

Similarly, `COUNTRY_NOTE_INDEX` can be identified like so by referring to the index indicated on the **Country Note Card**:

  ![Annotated Country Note View UI Layout](images/command-screenshots/country_note_view_annotated.png)
  <p align="center"><i>Figure 5. Country Note View</i></p>

### Help Window

This is what to expect when you type in the `help` command or press `F1`: 

  ![help window](./images/command-screenshots/help_window.png)
  <p align="center"><i>Figure 6. Help Window</i></p>

<p align="center">
   <strong><i>{End of Appendix A, please return to the <a href="#table-of-contents">Table of Contents</a>}</i></strong> 
</p>

--------------------------------------------------------------------------------------------------------------------

## Appendix B: International Standards Followed 

### ISO Country Codes

**_TBM_** utilizes 2-letter country codes that follow the **_ISO3166_** specification. This [finding tool](https://www.countrycode.org/) can be used.

### Phone Numbers

**_TBM_**'s format for phone numbers is **_based_** on the [E.164 standard](https://en.wikipedia.org/wiki/E.164) as recommended by the _ITU Telecommunication Standardization Sector (ITU-T)_.
**_However_**, **_TBM_** doesn't allow for spaces in between phone number fields. For example, `+65 8186 7742` may follow this international standard, but **_TBM_ requires you to remove spacing within the phone number field** so the correct way to input it would be `+65 81867742`. 

Here are the alternative stylistic choices that you might make when writing out phone numbers, all of which are valid:
* Phone numbers can be grouped into `COUNTRY_CODE` and `NATIONAL_PHONE_NUMBER`. We shall refer to `NATIONAL_PHONE_NUMBER` simply as **_phone number field_** in this appendix.
    * Please note that the **_phone number field_** may only have a **maximum of 12 digits**, all without spaces. The digit count restriction is as per the "global services specification" of **E.164**.
    * `COUNTRY_CODE` field is optional and may be omitted by you. 
      * Adding country codes for the telephone numbers:
        * Country code numbers range from 1 to 3 digits and may be found from this [finding tool](https://www.countrycode.org/). You **must** prepend the country code with a `+` character.  
          * `+6581867742` is a valid phone number where `+65` is the country code for Singapore.
        * **If you wish to, you may also separate country code and phone number fields with a delimiter:** Valid delimiters include: the " " character, "-", and "."
          * `+65 81867742` with a " " character is also a valid phone number. 
          * `+65-81867742` with a "-" character is also a valid phone number.
          * `+65.81867742` with a "." character is also a valid phone number.
            
         These options have been provided because we know that such delimiting is a matter of _cultural preference_.
    


<p align="center">
   <strong><i>{End of Appendix B, please return to the <a href="#table-of-contents">Table of Contents</a>}</i></strong> 
</p>
--------------------------------------------------------------------------------------------------------------------

## Appendix C: Expected Command Behaviour Screenshots

We've added screenshots for you to refer to, to determine easily what the output should be upon issuing a particular command.  

## Dummy Command 

before ss and after ss 

each ss window  size to be 800x600px

link the command description above to this section so users can navigate to here easily 
link a return link back to the description as well



