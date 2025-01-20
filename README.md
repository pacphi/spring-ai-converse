# Spring AI Converse

[![GA](https://img.shields.io/badge/Release-Alpha-darkred)](https://img.shields.io/badge/Release-Alpha-darkred) ![Github Action CI Workflow Status](https://github.com/pacphi/spring-ai-converse/actions/workflows/ci.yml/badge.svg) [![Known Vulnerabilities](https://snyk.io/test/github/pacphi/spring-ai-converse/badge.svg?style=plastic)](https://snyk.io/test/github/pacphi/spring-ai-converse)

Community Spring Boot and Spring AI building blocks for conversations between humans and artificial intelligence.

* [Background](#background)
* [Getting started](#getting-started)
* [Prerequisites](#prerequisites)
* How to
    * [Clone](#how-to-clone)
    * [Build](#how-to-build)
    * [Run](#how-to-run)
    * [Endpoints](#endpoints)

## Background

Use-cases:

* [x] Interfaces and abstract implementations for conversations (speech to text, inquiry, and text to speech)
* Support for speech to text using
  * [ ] to be identified open-source alternative LMM models/services
* Support for text to speech using 
  * [x] [ElevenLabs API](https://elevenlabs.io/docs/api-reference/text-to-speech/convert)
  * [ ] [Parler TTS](https://huggingface.co/ecyht2/parler-tts-mini-v1-GGUF)
* [ ] Sample converse implementations

## Getting started

Start with:

* A Github [account](https://github.com/signup)
* An Eleven Labs [account](https://elevenlabs.io/app/sign-in)
* An LLM provider
  * e.g., HuggingFace, Gemini, Ollama, or OpenAI

## Prerequisites

* Git CLI
* an LLM provider account (if using public cloud or commercially hosted models)
* Java SDK 21
* Maven 3.9.9

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

For more exotic build and packaging alternatives, refer to the guide [here](docs/BUILD.md).

## How to run

Set these environment variables

```bash
export ELEVENLABS_API_KEY=
```

> Add an appropriate value for each environment variable above.

Navigate the playground directory and activate a Spring profile

```bash
cd playground
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=elevenlabs,dev
```

> Back in the terminal shell, press Ctrl+C to shutdown.

## Endpoints

```commandline
http POST :8080/api/elevenlabs/speak \                                                                                                                                                                                                                     ✔  10s    12:05:55   
  Content-Type:application/json \
  text="A modern-day warrior.  Mean, mean stride.  Today's Tom Sawyer.  Mean, mean pride."
```


