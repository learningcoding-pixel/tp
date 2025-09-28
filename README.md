[![Java CI](https://github.com/AY2526S1-CS2103-F13-1/tp/actions/workflows/gradle.yml/badge.svg)](https://github.com/AY2526S1-CS2103-F13-1/tp/actions/workflows/gradle.yml)

[![codecov](https://codecov.io/github/AY2526S1-CS2103-F13-1/tp/graph/badge.svg?token=AMY6SV7YLJ)](https://codecov.io/github/AY2526S1-CS2103-F13-1/tp)

![Ui](docs/images/Ui.png)

# Relay Coach🏃‍➡️

## ❓About
```
Relay Coach App Functionalities:
* 📝 Add Athlete's Information
* 🔄 Update Athlete's with Relay Information
* 👁️ View Athletes
* 🔍 Filter Athletes by Name, School, Role and Tag
* 🗑️ Delete Athletes

* ⭕ Group athelets by teams
* 👁️ View Teams
* 💪 Add Team's training session
* 🗑️ Delete Teams
* 🙋 Add Attendance
* 📅 Add students to session
```

## 📝 Add Athlete's Information
```
Format: add n/ <Name> s/ <School> d/ <DOB>
Example: add n/ Justin s/ NUS d/ 2003-10-10
```

## 🔄 Update Athlete's with Relay Information
```
Format: addinfo <AthleteIndex> r/ <Role> t/ <Tag> h/ <Height> w/ <Weight>
Example: addinfo 1 r/ Captain t/ Knee injury a/ 95 h/ 180 w/ 75 
```

## 👁️ View Athletes
```
Format: list
Example: list
```

## 🔍 Filter Athletes by Name, School, Role and Tag
```
Format:  filter <information category>/<information>
Example by Name: filter n/Tom 
Example by School: filter s/Kent Ridge Secondary School
Example by Role: filter r/Captain
Example by Tag: filter t/Sprainankle
```

## 🗑️ Delete Athletes
```
Format: deletestudent <AthleteIndex>
Example: deletestudent 2
```

## ⭕ Group Athletes by teams
```
Format: team n/ <team_name> index/ <INDEX_A> <INDEX_B> <INDEX_C> <INDEX_D>
Example: team name/ StarTeam index/ 3 5 7 8
```
## 👁️ View Teams
```
Format: viewteams [n/ <team_name>]
Example to view all teams: viewteams
Example to view specific team: viewteams n/ StarTeam
```

## 💪 Add Team's training session
```
Format: addsession index/ <team_index> /datetime <datetime> location/ <location>
Example: addsession index/ 1  /datetime 2025-09-21 1700 location/ TrackField
```

## 🗑️ Delete Teams
```
Format: deleteteam <TeamIndex>
Example:  deleteteam 1
```

## 🙋 Add Attendance
```
Format: attend <student_index>
Example: attend 1 2 4
```

## 📅 Add students to session
```
Format: addstudents /datetime <datetime> location/ <location> index/ <INDEX_A> [<INDEX_B>] [<INDEX_C>]
Example: addstudents /datetime 2025-09-21 1700 location/ TrackField index/ 1 2 3

```

## 💼 Acknowledgment
```
This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
```




