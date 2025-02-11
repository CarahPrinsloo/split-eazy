# Split Eazy

## Table of Contents
1. [Overview](#overview)
2. [Prerequisites](#prerequisites)
3. [Building the project](#building-the-project)
4. [Starting the Server](#starting-the-server)

## Overview

**Split Eazy** is an app that helps users easily split bills with friends.

## Prerequisites

Before you build and run the project, ensure you have the following installed:

- **Java 21**
- [**Maven 3.9.X**](https://maven.apache.org/)

### Java

If you are using sdkman, you can run `sdk env` to detect and set the version of Java required for this project.

### Database

You will need to create the Split Eazy database:

```postgresql
CREATE DATABASE split_eazy;
```

## Building the project

**To build the project and run tests, use:**

```bash
mvn clean package
```

This will:
- Compile the source code
- Run tests

## Starting the server

```bash
mvn spring-boot:run
```