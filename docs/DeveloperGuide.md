---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# RelayCoach Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter`, `TeamListPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` (athlete) and `Team` objects residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete an athlete).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml"/>


The `Model` component,

* stores the address book data i.e., all `Person` (athlete) objects (which are contained in a `UniquePersonList` object) and `Team` objects (which are contained in a `UniqueTeamList` object).
* stores the currently 'selected' `Person` (athlete) objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change. (
* also stores the currently 'selected' `Team` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Team>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components).
* toggles between displaying `Person`s and `Team`s in the UI when executing commands that affect either `Person`s or `Team`s (e.g. deleting teams display `Team`s, deleting athletes display `Person`s).

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` (athlete) needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml"/>

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

#### Cascading deletions on athlete removal

When an athlete is deleted via `DeleteCommand`, the system also deletes any team that includes that athlete. Rationale:
- Teams must always have **exactly 4** members; removing one would violate the invariant.
- The `Model` exposes `getTeamOfPerson(Person)` to locate the team, and `deleteTeam(Team)` is invoked after `deletePerson(Person)` if applicable.
- The `DeleteCommand` composes a combined success message for both deletions.

### Teams and Sessions

This project extends AB-3 with `Team` and `Session` domain entities and corresponding commands.

- Teams are groups of exactly 4 distinct athletes (`Person`).
- Sessions represent training events with a location and start/end datetimes, attached to teams.

Key model classes:
- `seedu.address.model.team.Team` (holds `TeamName`, members, sessions)
- `seedu.address.model.team.session.Session` (holds `Location`, `startDate`, `endDate`)
- `seedu.address.model.team.UniqueTeamList` (manages uniqueness and storage of teams)

Relevant CLI prefixes (see `CliSyntax`): `tn/` (team name), `i/` (index), `l/` (location), `sdt/` (start), `edt/` (end).

Implemented commands:
- `team tn/TEAM_NAME i/IDX IDX IDX IDX` — create a team of 4 athletes.
    - Constraints: exactly 4 distinct valid athlete indexes; members cannot already belong to another team; team name must be unique.
- `listteams` — list all teams.
- `deleteteam INDEX` — delete a team by displayed index in the team list.
- `addsession i/TEAM_INDEX sdt/YYYY-MM-DD HHmm edt/YYYY-MM-DD HHmm l/LOCATION` — add a session to a team.
    - Constraints: team index must be valid; `end` must not be before `start`; location and datetimes must satisfy format and validation rules.
- `deletesession i/TEAM_INDEX si/SESSION_INDEX` — delete a session from a team.
    - Notes: sessions are ordered by start datetime (`Session.SESSION_ORDER`) when interpreting `SESSION_INDEX`.

High-level logic and model interactions:
- Commands are parsed in `AddressBookParser` and delegated to specific parsers (`AddTeamCommandParser`, `AddSessionCommandParser`, etc.).
- `ModelManager` updates teams immutably: when adding or removing a session, it constructs a new `Team` instance with updated session sets and replaces the existing team in the address book.

Design considerations:
- Team immutability avoids side-effects when editing nested collections (sessions, members).
- Session indices are resolved against a deterministic ordering to ensure predictable deletion behavior.

### Listing Athletes and Teams

This project displays a list of athletes by default but will display either athletes or teams depending on the command being executed.
- Any athlete-related commands (e.g. `add`, `delete`, `find`) are implemented to display athletes by default after execution.
- Any team-related commands (e.g. `team`, `listteams`) are implemented to display teams by default after execution.
- Each team displays its members in alphabetical order and its sessions in chronological order based on start datetime.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th athlete in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new athlete. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the athlete was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the athlete being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:
School coaches that coach relay teams in different schools. They:
* have a need to manage a significant number of athletes and teams
* prefer desktop apps over other types
* can type fast
* prefer typing to mouse interactions
* are reasonably comfortable using CLI apps

**Value proposition**: 
Given that coaches generally have many athletes to keep track of, our product aims to help 
coaches manage athletes and teams from different schools faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                             | I want to …​                         | So that I can…​                                                         |
|----------|-----------------------------------------------------|--------------------------------------|-------------------------------------------------------------------------|
| `* * *`  | new user                                            | see usage instructions               | refer to instructions when I forget how to use the App                  |
| `* * *`  | user                                                | add a new athlete                    |                                                                         |
| `* * *`  | user                                                | delete a athlete                     | remove entries that I no longer need                                    |
| `* * *`  | user                                                | find an athlete by name              | locate details of athletes without having to go through the entire list |
| `* *`    | user with lots of athletes to keep track of         | find athletes by school, role or tag | locate details of athletes that I wish to find via these means          |
| `* *`    | user managing multiple teams                        | group 4 athletes by their teams      | keep track of who is in which team                                      |
| `* *`    | user                                                | find a team by name                  | locate details of teams without having to go through the entire list    |
| `* *`    | user                                                | delete a team                        | remove teams that I no longer need                                      |
| `*`      | user with multiple teams' training to keep track of | add a team's training sessions       | keep track of team's training sessions                                  |
| `*`      | user                                                | delete a team's training session     | remove unwanted sessions                                                |
| `* *`    | user                                                | list all teams with their sessions   | see who is in which team and when and where sessions are easiliy        |
| `*`      | user with many athletes in the address book         | sort athletes by name                | locate an athlete easily                                                |
| `*`      | user needing to keep track of athletes' progress    | record attendance for athletes       | monitor his / her progress in training                                  |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `RelayCoach` and the **Actor** is the `Coach`, unless specified otherwise)

**Use case: Add Athlete (Basic Info)**

**MSS**

    1. Coach chooses to add a new athlete.
    2. RelayCoach requests the athlete’s basic details.
    3. Coach provides the requested details.
    4. RelayCoach validates the details.
    5. RelayCoach saves the athlete’s record.
    6. RelayCoach confirms the addition and displays the stored information.
    Use case ends.

**Extensions**

    3a. RelayCoach detects missing or invalid details.
        3a1. RelayCoach requests correction of details.
        3a2. Coach updates information.
        Use case resumes from Step 4.

**Use case: Add Athlete (Additional Info)**

**MSS**

    1. Coach selects an athlete from the list.
    2. RelayCoach requests additional information (Role, Tags, Height, Weight).
    3. Coach provides the information.
    4. RelayCoach validates the information.
    5. RelayCoach updates the athlete’s record.
    6. RelayCoach confirms the update and displays the new information.
    Use case ends.

**Extensions**

    2a. Athlete Index not found.
        2a1. RelayCoach displays an error message.
        Use case ends.

    4a. Invalid data.
        4a1. RelayCoach requests correction of information.
        Use case resumes from Step 3.

**Use case: List Athletes**

**MSS**

	1. Coach requests to view all athletes.
	2. RelayCoach retrieves all stored athletes.
	3. RelayCoach displays the list with details (Name, School, Role, Tags, etc.).
    Use case ends.

**Extensions**

    2a. No athletes found.
        2a1. RelayCoach informs the coach that no athletes are stored.
        Use case ends.

**Use case: Find Athletes by Filter**

**MSS**

	1. Coach chooses to find athletes by specifying one or more filters (Name, School, Role, Tag).
	2. RelayCoach validates the filter input.
	3. RelayCoach searches the database.
	4. RelayCoach displays the matching athletes with details.
    Use case ends.

**Extensions**

    2a. Filter missing or invalid.
        2a1. RelayCoach prompts for correction.
        Use case ends.

    3a. No athletes found.
        3a1. RelayCoach informs the coach that no matching athletes were found.
        Use case ends.

**Use case: Delete Athlete**

**MSS**

	1. Coach selects an athlete to delete.
	2. RelayCoach verifies the selection.
	3. RelayCoach removes the athlete’s record.
	4. RelayCoach confirms the deletion.
    Use case ends.

**Extensions**

    2a. Selection invalid (e.g., index out of bounds).
        2a1. RelayCoach prompts for correction.
        Use case ends.

    3a. Athlete belongs to a team.
        3a1. RelayCoach deletes the team that includes the athlete.
        3a2. RelayCoach confirms both deletions in the success message.
        Use case ends.

**Use case: Group Athletes by Teams**

**MSS**

    1. Coach chooses to form a new relay team.
    2. RelayCoach requests the team name and athlete indexes.
    3. Coach provides the team name and 4 valid athlete indexes.
    4. RelayCoach validates the details.
    5. RelayCoach creates the new team with the specified athletes.
    6. RelayCoach confirms the team creation and displays the team details.
    Use case ends.

**Extensions**
    
    3a. Missing or invalid details provided.
        3a1. RelayCoach requests correction of team name or indexes.
        3a2. Coach updates information. 
        Use case resumes from Step 4.

    4a. Fewer/more than 4 indexes provided.
        4a1. RelayCoach rejects team creation and informs coach.
        Use case ends.

    4b. Invalid athlete index provided.
        4b1. RelayCoach notifies coach that the index does not match any athlete.
        Use case ends.

    4c. Duplicate athlete index detected. 
        4c1. RelayCoach rejects team creation due to duplicate members.
        Use case ends.

    4d. Athlete already belongs to another team.
        4d1. RelayCoach informs coach that the athlete is already in <existing_team>.
        Use case ends.

    4e. No athletes exist in the database.
        4e1. RelayCoach prompts coach to add athletes first.
        Use case ends.

**Use case: View Team Information**

**MSS**

    1. Coach chooses to view teams.
    2. RelayCoach requests an optional team name filter.
    3. Coach provides either no team name or a specific team name.
    4. RelayCoach retrieves the relevant team(s) and displays details.
    5. Use case ends.

**Extensions**

    3a. Invalid team name provided (non-alphabet characters).
        3a1. RelayCoach notifies coach of invalid team name.
        Use case ends.

    4a. No teams exist in the database.
        4a1. RelayCoach informs coach: “No teams found!”.
        Use case ends.

    4b. No teams match the provided team name.
        4b1. RelayCoach informs coach: “No teams found!”.
        Use case ends.

**Use case: Add Session to Team**

**MSS**

    1. Coach chooses to add a training session for a team.
    2. RelayCoach requests the team index, session date/time, and location.
    3. Coach provides the requested details.
    4. RelayCoach validates the input.
    5. RelayCoach adds the session to the specified team.
    6. RelayCoach confirms the addition and displays the updated team schedule.
    Use case ends.

**Extensions**

    3a. Missing team index.
        3a1. RelayCoach notifies coach of missing team index.
        Use case ends.
    
    3b. Invalid date/time format provided.
        3b1. RelayCoach requests correction using required format yyyy-MM-dd HHmm.
        Use case ends.
    
    3c. Missing location.
        3c1. RelayCoach notifies coach that location must be provided.
        Use case ends.
    
    4a. Invalid team index.
        4a1. RelayCoach informs coach the team does not exist.
        Use case ends.
    
    4b. Duplicate session detected (same team, same date/time).
        4b1. RelayCoach rejects scheduling and notifies coach.
        Use case ends.

**Use case: Delete Team**

**MSS**

	1. Coach chooses to delete a team.
    2. RelayCoach requests the team index.
    3. Coach provides the team index.
    4. RelayCoach validates the team index.
    5. RelayCoach deletes the team from the database.
    6. RelayCoach confirms deletion with a success message.
    Use case ends.

**Extensions**

    2a. Selection invalid (e.g., index out of bounds).
        2a1. RelayCoach prompts for correction.
        Use case ends.

**Use case: Add Students to Session**

**MSS**

    1. Coach chooses to add students to a session.
    2. RelayCoach requests session datetime, location, and student indexes.
    3. Coach provides the requested details.
    4. RelayCoach validates the input.
    5. RelayCoach adds the students to the specified session.
    6. RelayCoach confirms the addition and displays updated session details.
    Use case ends.

**Extensions**

    3a. Missing student index.
        3a1. RelayCoach notifies coach that at least one student index must be provided.
        Use case ends.
    
    3b. Invalid date/time format.
        3b1. RelayCoach requests correction using required format yyyy-MM-dd HHmm.
        Use case ends.

    3c. Missing location.
        3c1. RelayCoach notifies coach that location is required.
        Use case ends.
    
    4a. Invalid student index provided.
        4a1. RelayCoach informs coach that the student does not exist.
        Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 athletes without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. The command interface should allow coaches to perform common tasks while standing or in outdoor environments without requiring precise mouse movements.
5. The application should efficiently handle peak usage during competition seasons when coaches need to manage multiple teams and training sessions simultaneously.
6. New athlete attributes or team management features should be easily addable without major architectural changes.
7. The system should provide clear, actionable error messages when coaches input invalid team compositions or conflicting athlete assignments.
8. The system should validate critical data formats (dates, indexes, required fields) before executing commands.
9. The application should be able to run on standard laptops commonly used by coaches without requiring high-end hardware.
10. A coach should be able to learn the basic commands (add, list, delete) within 30 minutes of use.



*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Standard laptops**: laptops that run on Mid-range CPU(Intel Core i5 or AMD Ryzen 5) with maximum of 8GB Ram
* **CLI**: Command-Line Interface
* **GUI**: Graphical User Interface
* **MSS**: Main Success Scenario
* **Athletes**: Secondary school relay race Athletes
* **teams**: Teams are used to group Athletes. Each team can only have 4 Athletes.
* **sessions**: Sessions are added to Teams. Each Session has Start Date & Time, End Date & Time and Location.

--------------------------------------------------------------------------------------------------------------------


## **Appendix: Instructions for manual testing**

Below are step-by-step instructions to manually test the application. 
For each feature, prerequisites and expected outcomes are provided. 

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on; 
Testers are expected to do more *exploratory* testing.

</box>

---

### 1. Launch and Setup

As a coach, you begin by launching the application for the first time and configuring your workspace.

1. **Initial launch**
    1. Download the jar file and copy it into an empty folder.
    2. Double-click the jar file.<br>
       **Expected:** The GUI opens with a set of sample athletes. The window size may not be optimal.

2. **Saving window preferences**
    1. Resize the window to your preferred size and move it to a different location. Close the window.
    2. Re-launch the app by double-clicking the jar file.<br>
       **Expected:** The most recent window size and location are retained.

---

### 2. Adding Athletes

Now, you want to populate your database with athletes.

1. **Clear Current Sample Data**
    1. Test case: `clear`
      - **Expected:** All sample athletes/teams are removed so you can add your own data from a clean slate.

2. **Adding a new athlete**
    1. Test case: `add n/Alice Tan d/2003-10-10 p/98765432 e/alicet@example.com a/Alice Street, Block 123, #01-011 s/Singapore School r/Captain h/180 w/75 t/Kneeinjury`
        - **Expected:** Success message confirming Alice Tan is added. She appears in the athlete list.

---

### 3. Edit Athlete

After adding athletes, you may wish to update their details.

1. **Editing an athlete's information**
    1. Prerequisite: At least one athlete exists.
    2. Test case: `edit 1 r/Sprinter t/Captain`
        - **Expected:** Success message. Athlete at index 1 now has updated role and tag.

---

### 4. Find Athlete

As the list grows, you need to quickly find specific athletes.

1. **Finding athletes by name**
    1. Test case: `find n/Alice`
        - **Expected:** Only athletes with "Alice" in their name are listed.

2. **Filtering by tag or role**
    1. Test case: `find t/Sprinter`
        - **Expected:** Only athletes tagged as "Sprinter" are shown.

---

### 5. Add Team

With your athletes in place, you proceed to group them into teams.

1. Listing teams

   1. Test case: `listteams`  
      Expected: All teams are shown. If none exist, shows an empty team list section.

2. Creating a team of 4 athletes

   1. Prerequisites: At least 4 athletes listed via `list`.
   2. Test case: `team tn/Alpha i/1 2 3 4`  
      Expected: Success message. Team `Alpha` appears in teams list with 4 members.
   3. Test case: `team tn/Alpha i/1 2 3 4` (duplicate name)  
      Expected: Error about duplicate team name.
   4. Test case: `team tn/Beta i/1 2 3` (fewer than 4)  
      Expected: Error about team size must be exactly 4.
   5. Test case: `team tn/Gamma i/1 2 3 3` (duplicate index)  
      Expected: Error about requiring 4 distinct members.
   6. Test case: `team tn/Delta i/100 101 102 103` (invalid indexes)  
      Expected: Error about invalid athlete indexes.
   7. Test case: create a second team reusing a member already in a team  
      Expected: Error listing conflicting member indexes.

3. Deleting a team

   1. Prerequisites: Teams listed via `listteams`, at least 1 team.
   2. Test case: `deleteteam 1`  
      Expected: First team is deleted, success message.
   3. Test case: `deleteteam i` where i is 0 or larger than the teams list size
      Expected: Error about invalid index.

4. Adding a session to a team

   1. Prerequisites: At least 1 team exists; note its index from `listteams`.
   2. Test case: `addsession i/1 sdt/2025-10-21 0700 edt/2025-10-21 0800 l/Track`  
      Expected: Success message showing session details added to the team.
   3. Test case: `addsession i/1 sdt/2025-10-21 0900 edt/2025-10-21 0800 l/Track`  
      Expected: Error about invalid datetime (end before start).
   4. Test case: `addsession i/999 sdt/2025-10-21 0700 edt/2025-10-21 0800 l/Track`  
      Expected: Error about invalid team index.
   5. Test case: `addsession i/1 sdt/2099-10-21 edt/2025-10-21 0800 l/Track`  
      Expected: Error about datetime format.

5. Deleting a session from a team

   1. Prerequisites: Team at index `1` has at least one session.
   2. Test case: `deletesession i/1 si/1`  
      Expected: Success message and session removed.
   3. Test case: `deletesession i/1 si/x`: 'where x is 0 or larger than the sessions list size 
      Expected: Error about invalid session index.

      
6. Creating a team of 4 athletes
    1. Prerequisite: At least 4 athletes listed via `list`.
    2. Test case: `team tn/Alpha i/1 2 3 4`
        - **Expected:** Success message. Team "Alpha" appears in the teams list with 4 members.
---

### 6. Adding sessions

You want to schedule training sessions for your teams.

1. **Adding a session to a team**
    1. Prerequisite: At least one team exists; note its index from `listteams`.
    2. Test case: `addsession i/1 sdt/2025-10-21 0700 edt/2025-10-21 0800 l/Track`
        - **Expected:** Success message showing session details added to the team.

---

### 7. Deleting sessions

Sometimes, you need to remove a session from a team's schedule.

1. **Deleting a session from a team**
    1. Prerequisite: Team at index `1` has at least one session.
    2. Test case: `deletesession i/1 si/1`
        - **Expected:** Success message and session is removed.

---

### 8. Delete Team

You may need to remove teams as circumstances change.

1. **Listing teams**
    1. Test case: `listteams`
        - **Expected:** All teams are shown. If none exist, shows an empty team list section.

2. **Deleting a team**
    1. Prerequisite: At least one team exists (from `listteams`).
    2. Test case: `deleteteam 1`
        - **Expected:** First team is deleted, success message.

---

### 9. Deleting Athletes and Cascading Effects

Occasionally, an athlete leaves. You remove them and observe how the system updates related teams.

1. **Deleting an athlete while all athletes are shown**
    1. Prerequisite: List all athletes using the `list` command. Multiple athletes are present.
    2. Test case: `delete 1`
        - **Expected:** First athlete is deleted from the list. If the athlete belonged to a team, that team is also deleted. Success message lists all deletions. Timestamp in the status bar is updated.

---

### 10. Saving and Reloading Data

You want to ensure your data persists between sessions.

1. **Saving and restoring data**
    1. Add or modify athletes, teams, or sessions.
    2. Close the application.
    3. Re-launch the application.
        - **Expected:** All changes made in the previous session are preserved.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**

### Difficulty level: Medium - High

**Technical Challenges faced**

1. Extended person model to support additional attributes:
    1. Adding new attributes to the existing `Person` model in AB3 required careful modification of multiple classes
       to ensure data consistency and sufficient validation.
2. Advanced search functionality:
    1. Implementing multi-criteria search (by name, school, role, tags...)
       required complex parsing and filtering logic, which was more challenging than simple single-criterion searches in AB3.
2. Complex entity relationship management:
    1. Whilst AB3 only deals with one entity type, RelayCoach manages multiple interconnected entities (Athletes, Teams, Sessions),
       necessitating careful design to maintain data integrity and consistency.
    2. Ensured data integrity during deletions: Deleting an athlete required cascading deletions of associated teams,
       which added complexity to the delete operations.

### Effort distribution

Estimated manhours spent: 120 hours

**High-Complexity Tasks (40% of effort)**
1. Designing the relational architecture between athletes, teams, and sessions
2. Implementing complex validation rules for team composition
3. Ensuring data integrity across interconnected entities

**Medium-Complexity Tasks (35% of effort)**
1. Extending existing AB3 components without breaking functionality
2. Implementing new field types with custom validation
3. Creating new command parsers and logic

**Routine Implementation Tasks (25% of effort)**
1. UI component updates
2. Additional test cases
3. Documentation updates

### Technical Achievements
1. Successfully extended AB3's Person model while maintaining all existing functionality
2. Implemented complex business rules for team formation (exactly 4 unique athletes)
3. Created a relational system between athletes, teams, and training sessions
4. Built advanced search that outperforms AB3's basic find functionality
5. Maintained data integrity across multiple interconnected entities

## **Planned Enhancements**

### **1. Show team information for each athlete**

**Description:**  
Enhance the person list panel to include details about the team each athlete belongs to.
Coaches can better see which team the athlete belongs to at a glance.

### **2. Show attendance for each athlete**

**Description:**
Implement attendance feature to track session attendance for each athlete.
Coaches can mark or unmark session attendance for each athlete.
Provide summary statistics such as attendance rate and missed sessions in the person list panel.
Coaches can better see which athlete is committed or not.

### **3. Auto deletion of sessions after the end date & time has passed**

**Description:**  
Sessions whose end date & time has passed will be deleted automatically.
This will maintain a clutter-free and up-to-date session list for the teams.