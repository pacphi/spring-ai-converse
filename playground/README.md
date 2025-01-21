# Spring AI Converse &#x2AA7; Playground

Welcome to the playground sample application.

You will enable features by activating Spring profiles.

## How to run

### Elevenlabs

Set these environment variables

```bash
export ELEVENLABS_API_KEY=
```

> Add an appropriate value for each environment variable above.

Navigate to the `playground` directory and activate a Spring profile

```bash
cd playground
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=elevenlabs,dev
```

> Back in the terminal shell, press Ctrl+C to shutdown.

#### Available endpoints

```commandline
http POST :8080/api/elevenlabs/speak \                                                                                                                                                                                                                     ✔  10s    12:05:55   
  Content-Type:application/json \
  text="A modern-day warrior.  Mean, mean stride.  Today's Tom Sawyer.  Mean, mean pride."
```

### AssemblyAI

Set these environment variables

```bash
export ASSEMBLYAI_API_KEY=
```

> Add an appropriate value for each environment variable above.

Navigate to the `playground` directory and activate a Spring profile

```bash
cd playground
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=assemblyai,dev
```

> Back in the terminal shell, press Ctrl+C to shutdown.

#### Available endpoints

```commandline
http POST :8080/api/assemblyai/transcribe \ 
  Content-Type:application/octet-stream \
  @/path/to/your/audio/file
```

> Replace `/path/to/your/audio/file` above with a valid absolute path to your audio file
