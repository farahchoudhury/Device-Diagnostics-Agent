# Device Diagnostics Agent

A lightweight Java diagnostics SDK for kiosks, embedded systems, arcade machines, and unattended applications.

## Overview

Device Diagnostics Agent is a personal project focused on building a production-style diagnostics and telemetry framework for Java applications.

The goal is to provide a lightweight, offline-first diagnostics layer that can be embedded into existing applications and continue operating reliably in environments where devices may run unattended for long periods of time.

The project draws inspiration from tools such as Crashlytics, Datadog agents, and device monitoring software commonly used in kiosk, arcade, and embedded system deployments.

## Current Progress

The project is currently under active development.

### Implemented

- Maven project structure
- Java 21 support
- Log4j2 + SLF4J integration
- Structured application logging
- Rolling log file configuration
- Diagnostics collection framework
- Crash reporting system
- Durable upload queue architecture
- Cloudflare R2 upload integration

### In Progress

- ZIP support bundle generation
- USB export workflows
- Retention policies
- Health monitoring
- SDK-style public API

## Design Goals

- Never crash the host application
- Offline-first operation
- Durable local storage
- Graceful failure handling
- Cross-platform support
- Modular architecture
- Production-oriented design

## Planned Features

- Support bundle ZIP exports
- USB diagnostics export
- Automatic retry uploads
- Health monitoring and metrics
- Queue statistics
- Upload throttling
- Configuration-driven deployment
- Plugin-based diagnostics collectors

## Technology Stack

- Java 21+
- Maven
- Log4j2
- SLF4J
- AWS SDK v2
- Cloudflare R2
- Jackson

## Project Status

This project is currently being developed as a learning exercise in systems design, observability tooling, and resilient application architecture.

The implementation is evolving incrementally as new subsystems are designed and tested.

Contributions, feedback, and suggestions are welcome.
