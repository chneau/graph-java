# graph-java

A high-performance graph processing, Dijkstra shortest path, and topology simplification engine for Java 26+.

[![Java 26](https://img.shields.io/badge/Java-26-ED8B00?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

---

## ⚡ Features & Performance

- **Optimized Dijkstra Shortest Path**: $O((V + E) \log V)$ shortest-path search implemented with binary min-heap `PriorityQueue`.
- **Bidirectional Topology Reduction**: Iterative dead-end and degree-2 intermediate node contraction via `Simplify.graph(g)`.
- **GTFS Transit Feed Parsing**: High-speed streaming GTFS zip archive parser (`stops`, `trips`, `stop_times`, `calendar`) powered by modern Apache Commons CSV.
- **Java 26 Modern Architecture**: Built with Java 26 toolchains, `java.time` APIs, clean records/collections, and Gradle 9.7.1.
- **OpenHours & TimeTable Integration**: Seamless compatibility with [openhours-java](https://github.com/chneau/openhours-java) and [timetable-java](https://github.com/chneau/timetable-java).

---

## 🚀 Quick Start

### Installation

#### Option 1: Via JitPack (Recommended for public use — zero auth required)

##### Gradle (Groovy)

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.chneau:graph-java:v1.0.0'
}
```

##### Gradle (Kotlin DSL)

```kotlin
repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.chneau:graph-java:v1.0.0")
}
```

##### Maven (`pom.xml`)

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.chneau</groupId>
        <artifactId>graph-java</artifactId>
        <version>v1.0.0</version>
    </dependency>
</dependencies>
```

---

#### Option 2: Via GitHub Packages (`maven.pkg.github.com`)

##### Gradle (Groovy)

```groovy
repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/chneau/graph-java")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key") ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation 'chneau:graph:1.0.0'
}
```

---

### Usage Example

```java
import chneau.graph.Dijkstra;
import chneau.graph.Graph;
import chneau.graph.Simplify;

public class Main {
    public static void main(String[] args) {
        // 1. Build a graph
        Graph g = new Graph();
        g.addEdge(1, 2, 4);
        g.addEdge(1, 3, 2);
        g.addEdge(2, 4, 5);
        g.addEdge(3, 4, 1);
        g.addEdge(4, 5, 3);

        // 2. Compute shortest path via Dijkstra
        var result = Dijkstra.shortest(g, 1, 5);
        System.out.println("Distance: " + result.getDistance()); // 6
        System.out.println("Path: " + result.getPath());         // [1, 3, 4, 5]

        // 3. Simplify redundant degree-2 intermediate nodes
        Simplify.graph(g);
        System.out.println("Remaining vertices: " + g.vertices.keySet());
    }
}
```

---

## 📊 Benchmarks

Run benchmarks using the Gradle suite:

```bash
./gradlew bench -q
```

Typical performance on Java 26:

| Benchmark Task | Size / Operations | Latency |
| :--- | :--- | :--- |
| **Dijkstra Shortest Path** | 900-node grid graph (5,000 runs) | **~355 µs / op** |
| **Topology Reduction (`Simplify`)** | 20-node linear reduction (5,000 runs) | **~38.9 µs / op** |
| **Graph Construction** | 20-edge graph (10,000 runs) | **~5.5 µs / op** |

---

## 🛠️ Testing & Verification

Run tests with JUnit 5:

```bash
./gradlew test
```

## 📄 License

This project is licensed under the [MIT License](LICENSE).
