# Feedbooks Driver  [![Build Status](https://travis-ci.com/lrusso96/feedbooks-driver.svg?token=uoNxtXYBDHpqERGMiZA8&branch=master)](https://travis-ci.com/lrusso96/feedbooks-driver) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/94908fbbc65842d1aaca4ee4a31a896e)](https://app.codacy.com/app/russo.1699981/feedbooks-driver?utm_source=github.com&utm_medium=referral&utm_content=lrusso96/feedbooks-driver&utm_campaign=Badge_Grade_Settings) [![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

Feedbooks driver is Java Libary to obtain infos from Feedbooks.

## Getting started

### Prerequisites
This application is written with JDK8 in mind. If you don't have a Java Development Kit installed you can download it from [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

### Compile from sources
- `git clone` or download this repo.
- Open a terminal in the directory where the sources are stored.
- Execute `mvn install -DskipTests` . The .jar file will be in the target folder.

### Add to your project

You can easily add to your existing project through Maven or Gradle.

**Maven**

1) Add the JitPack repository
```
<repositories>
	<repository>
	    <id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
```
2) Add the dependency
```
<dependency>
    <groupId>com.github.lrusso96</groupId>
    <artifactId>feedbooks-driver</artifactId>
    <version>0.0.1</version>
</dependency>
```

**Gradle**

1) Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
		maven { url 'https://jitpack.io' }
	}
}
```
2) Add the dependency
```
dependencies {
    implementation 'com.github.lrusso96:feedbooks-driver
}
```


### Examples
```
Feedbooks feedbooks = new Feedbooks(null, 10);
feedbooks.search("Carroll");

 ```

 ## Dependencies
 - [Square OkHttp](https://github.com/square/okhttp)
 - [JUnit](https://github.com/junit-team/junit4)
 - [Jsoup](https://github.com/jhy/jsoup)
 - [org/Json](https://github.com/stleary/JSON-java)
 - [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/)
