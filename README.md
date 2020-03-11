# Gradle Metamodel Plugin

[![Build Status](https://travis-ci.org/Scalified/gradle-metamodel-plugin.svg)](https://travis-ci.org/Scalified/gradle-metamodel-plugin)
[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v?label=Plugin&metadataUrl=https%3A%2F%2Fplugins.gradle.org%2Fm2%2Fcom%2Fscalified%2Fplugins%2Fgradle%2Fmetamodel%2Fcom.scalified.plugins.gradle.metamodel.gradle.plugin%2Fmaven-metadata.xml)](https://plugins.gradle.org/plugin/com.scalified.plugins.gradle.metamodel)

## Description

[Gradle Metamodel Plugin](https://plugins.gradle.org/plugin/com.scalified.plugins.gradle.metamodel) generates static metamodel classes

## Requirements

* [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Gradle 4](https://gradle.org/)

> For Gradle 5+ use the new [*Gradle Sourcegen Plugin*](https://github.com/Scalified/gradle-sourcegen-plugin)

## Changelog

[Changelog](CHANGELOG.md)

## Applying

Build script snippet for plugins DSL for Gradle 2.1 and later:

```gradle
plugins {
  id "com.scalified.plugins.gradle.metamodel" version "0.0.1"
}
```

Build script snippet for use in older Gradle versions or where dynamic configuration is required:

```gradle
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.scalified.plugins.gradle:metamodel:0.0.1"
  }
}

apply plugin: "com.scalified.plugins.gradle.metamodel"
```

## Usage

After applying the plugin, the following takes place:

1. JavaPlugin applied if not yet applied
2. New directories are created (if missing):
   * Generated source set directory for metamodel classes (the value of **metamodel.srcDir** property, which is **src/generated/java** by default)
3. Metamodel source set directory marked as generated source directory (IntelliJ IDEA only)
4. Gradle **metamodel** configuration added
5. Gradle **metamodel** source set added
6. Java source directories of **main** source set are extended with java source directories of **metamodel** source set
7. Gradle tasks added:
   * **metamodelClean** - cleans the content of metamodel source directory (does not remove the directory itself)
   * **metamodelCompile** - generates metamodel classes into metamodel source directory
8. Task dependencies added:
   * **clean** depends on **metamodelClean**
   * **compileJava** depends on **metamodelCompile**
9. Hibernate static metamodel generator dependency added to **metamodel** configuration

## Configuration

Currently the following configuration parameters supported (default values are shown):

```gradle
metamodel {
  srcDir = 'src/generated/java' // metamodel generated source directory
  hibernateVersion = '+' // hibernate static metamodel generator version
  processor = 'org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor' // metamodel processor
}
```

> You may also wish to add the metamodel source directory to **.gitignore** for exclusion

If you need more configuration options, you may <a href="mailto:info@scalified.com?subject=[Gradle Metamodel Plugin]: Proposals And Suggestions">send a request</a> with description

## License

```
MIT License

Copyright (c) 2018 Scalified

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## Scalified Links

* [Scalified](http://www.scalified.com)
* [Scalified Official Facebook Page](https://www.facebook.com/scalified)
* <a href="mailto:info@scalified.com?subject=[Gradle Metamodel Plugin]: Proposals And Suggestions">Scalified Support</a>
