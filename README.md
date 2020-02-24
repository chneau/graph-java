[![Build Status](https://travis-ci.org/chneau/graph-java.svg?branch=master)](https://travis-ci.org/chneau/graph-java)

# graph-java
A graph with Graph Simplification, Dijkstra and OSM data loader - in java

## useful commands (self reminder)

```bash
./gradlew assemble # assemble project, can be helpfull with vscode `ctrl+shift+P-> Java: Update project configuration`
```
```bash
./gradlew build # run tests compilation and checks
```
```bash
./gradlew spotlessApply # format source code
```


## import this library in another gradle project

In settings.gradle:
```groovy
rootProject.name = 'vs'

sourceControl {
    gitRepository("https://github.com/chneau/openhours-java.git") {
        producesModule("chneau:openhours")
    }
    gitRepository("https://github.com/chneau/timetable-java.git") {
        producesModule("chneau:timetable")
    }
    gitRepository("https://github.com/chneau/graph-java.git") {
        producesModule("chneau:graph")
    }
}
```

In build.gradle:
```groovy
    implementation "chneau:openhours"
    implementation "chneau:timetable"
    implementation "chneau:graph"
```
(self note: the project doesnt need a `group 'chneau'`)

**IMPORTANT** if using vscode, git pull these projects and open them on the same workspace (yeah, reading the vscode problems tab was useful for once)

### misc

self reminder: this project is understood to be a library by Gradle thanks to `group 'chneau'` in build.gradle and `rootProject.name = 'graph'` in settings.gradle.

## GTFS

<https://en.m.wikipedia.org/wiki/File:GTFS_class_diagram.svg> <- contains useful diagram

## TODO

- [ ] Build the OpenHours list without using strings (need to expose lib API)
