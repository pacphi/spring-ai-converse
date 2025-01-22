# Spring AI Converse

[![GA](https://img.shields.io/badge/Release-Alpha-darkred)](https://img.shields.io/badge/Release-Alpha-darkred) ![Github Action CI Workflow Status](https://github.com/pacphi/spring-ai-converse/actions/workflows/ci.yml/badge.svg) [![Known Vulnerabilities](https://snyk.io/test/github/pacphi/spring-ai-converse/badge.svg?style=plastic)](https://snyk.io/test/github/pacphi/spring-ai-converse)

Community Spring Boot and Spring AI building blocks for conversations between humans and artificial intelligence.

* [Background](#background)
* [Getting started](#getting-started)
* [Prerequisites](#prerequisites)
* How to
    * [Clone](#how-to-clone)
    * [Build](#how-to-build)
    * [Consume](#how-to-consume)
    * [Run](#how-to-run)

## Background

As a Spring Boot and Spring AI developer, I want
to consume libraries that make it convenient to add capabilities to my application(s)
as for the following

Use-cases:

* [x] Interfaces and abstract implementations for conversations (speech to text, inquiry, and text to speech)
* Support for speech to text using
  * [x] [Assembly AI](https://www.assemblyai.com/blog/speech-recognition-in-java/)
  * [x] [Google STT](https://cloud.google.com/speech-to-text/docs/transcribe-client-libraries#client-libraries-install-java)
  * [ ] [Picovoice Leopard](https://github.com/Picovoice/leopard?tab=readme-ov-file#java-demo)
  * [ ] [Picovoice Cheetah](https://github.com/Picovoice/cheetah?tab=readme-ov-file#java-demos)
* Support for text to speech using 
  * [x] [ElevenLabs API](https://elevenlabs.io/docs/api-reference/text-to-speech/convert)
  * [ ] [Picovoice Porcupine](https://github.com/Picovoice/porcupine?tab=readme-ov-file#java-demos)
  * [ ] [Parler TTS](https://huggingface.co/ecyht2/parler-tts-mini-v1-GGUF)
  * [ ] [Google TTS](https://cloud.google.com/text-to-speech/docs/create-audio-text-client-libraries#client-libraries-install-java)
* [ ] Sample converse implementations

## Getting started

Start with:

* A Github [account](https://github.com/signup)
* One or more of a(n)
  * AssemblyAI [account](https://www.assemblyai.com/dashboard/signup)
  * Eleven Labs [account](https://elevenlabs.io/app/sign-up)
  * Google Cloud [account](https://cloud.google.com/docs/get-started)
  * Picovoice [account](https://console.picovoice.ai/signup)
* An LLM provider
  * e.g., HuggingFace, Gemini, Ollama, or OpenAI

## Prerequisites

* Git CLI (2.43.0 or better)
* Github CLI (2.65.0 or better)
* httpie CLI (3.2.2 or better)
* Java SDK (21 or better)
* Maven (3.9.9 or better)
* an LLM provider account (if using public cloud or commercially hosted models)

## How to clone

with Git CLI

```bash
git clone https://github.com/pacphi/spring-ai-converse
```

with Github CLI

```bash
gh repo clone pacphi/spring-ai-converse
```

## How to build

Open a terminal shell, then execute:

```bash
cd spring-ai-converse
mvn clean install
```

## How to consume

If you want to incorporate any of the starters as dependencies in your own projects, you would:

### Add dependency

Maven

```maven
<dependency>
    <groupId>me.pacphi</groupId>
    <artifactId>spring-boot-elevenlabs-starter</artifactId>
    <version>{release-version}</version>
</dependency>
```

Gradle

```gradle
implementation 'me.pacphi:spring-boot-elevenlabs-starter:{release-version}'
```

> Replace occurrences of {release-version} above with a valid artifact release version number

### Add configuration

Following Spring Boot conventions, you would add a stanza like this to your:

application.properties

```properties
spring.elevenlabs.enabled=true
spring.elevenlabs.api-key=${ELEVENLABS_API_KEY:}
# Consult https://api.elevenlabs.io/v1/voices for a list of all the available voices
spring.elevenlabs.defaults.voiceId=Xb7hH8MSUJpSbSDYk0k2
```

application.yml

```yaml
spring:
  elevenlabs:
    enabled: true
    api-key: ${ELEVENLABS_API_KEY:}
    defaults:
      # Consult https://api.elevenlabs.io/v1/voices for a list of all the available voices
      voiceId: Xb7hH8MSUJpSbSDYk0k2
```

> Each dependency you add will require a similar stanza where you will typically: activate the capability, specify an API key (if required), and tune other associated configuration.

Consult the [playground](playground) module's configuration for alternative
`dependencies` and `configuration` that are available to add.

Configuration will be found in labeled `spring.config.activate.on-profile` sections of the [pom.xml](playground/pom.xml) file.

## How to run

There's a sample application in the [playground](playground) module.
