# Spring AI Converse &#x2AA7; Playground

Welcome to the playground sample application.

You will enable features by activating Spring profiles.

## How to run

### AssemblyAI

Prerequisites

* an AssemblyAI [account](https://www.assemblyai.com/dashboard/signup)

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

### Elevenlabs

Prerequisites

* an Eleven Labs [account](https://elevenlabs.io/app/sign-up)

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

### Google &#x2AA7; Speech-to-text

Prerequisites

* a Google Cloud [account](https://cloud.google.com/docs/get-started)
  * to create a new account, visit this [link](https://accounts.google.com/signup/v2/webcreateaccount) and click `Create Account`
* gcloud CLI (502.0.0 or better)

Authenticate

```bash
gcloud config set project <GOOGLE_PROJECT_ID> \
  && gcloud auth application-default login
```

> Replace <GOOGLE_PROJECT_ID> above with a valid identifier of a Google project

Enable the API (if not already enabled)

Visit https://console.developers.google.com/apis/api/speech.googleapis.com/overview and click the `Enable` button.

Set these environment variables

```bash
# Required
export GOOGLE_PROJECT_ID=
# Optional
export GOOGLE_STT_LANGUAGE_CODE=
export GOOGLE_STT_MODEL
```

> Add an appropriate value for each environment variable above.

Navigate to the `playground` directory and activate a Spring profile

```bash
cd playground
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=google,dev
```

> Back in the terminal shell, press Ctrl+C to shutdown.

#### Available endpoints

```commandline
http POST :8080/api/google/transcribe \ 
  Content-Type:application/octet-stream \
  @/path/to/your/audio/file
```

> Replace `/path/to/your/audio/file` above with a valid absolute path to your audio file

### Google &#x2AA7; Text-to-speech

Prerequisites

* a Google Cloud [account](https://cloud.google.com/docs/get-started)
  * to create a new account, visit this [link](https://accounts.google.com/signup/v2/webcreateaccount) and click `Create Account`
* gcloud CLI (502.0.0 or better)

Authenticate

```bash
gcloud config set project <GOOGLE_PROJECT_ID> \
  && gcloud auth application-default login
```

> Replace <GOOGLE_PROJECT_ID> above with a valid identifier of a Google project

Enable the API (if not already enabled)

Visit https://console.developers.google.com/apis/api/texttospeech.googleapis.com/overview and click the `Enable` button.

Set these environment variables

```bash
# Optional
export GOOGLE_TTS_LANGUAGE_CODE=
export GOOGLE_TTS_VOICE_GENDER=
export GOOGLE_TTS_AUDIO_ENCODING=
```

> Add an appropriate value for each environment variable above.

Navigate to the `playground` directory and activate a Spring profile

```bash
cd playground
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=google,dev
```

> Back in the terminal shell, press Ctrl+C to shutdown.

#### Available endpoints

```commandline
http POST :8080/api/google/speak \
  Content-Type:application/json \
  text="Say you can't sleep, baby, I know.  That's that me espresso."
```

### Picovoice &#x2AA7; Cheetah &#x2AA7; Speech-to-text

Prerequisites

* a Picovice [account](https://console.picovoice.ai/signup)

Set these environment variables

```bash
# Required
export PICOVOICE_ACCESS_KEY=
# Optional
export PICOVOICE_MODEL_PATH=
export PICOVOICE_ENABLE_AUTO_PUNCTUATION=
```

> Add an appropriate value for each environment variable above.

Navigate to the `playground` directory

```bash
cd playground
```

Execute the following script to seed the `model` files

```bash
files=(
   "cheetah_params.pv"
   "cheetah_params_de.pv" 
   "cheetah_params_es.pv"
   "cheetah_params_fr.pv"
   "cheetah_params_it.pv"
   "cheetah_params_pt.pv"
)

for file in "${files[@]}"; do
   curl -O "https://raw.githubusercontent.com/Picovoice/cheetah/master/lib/common/$file"
done

mv *.pv src/main/resources
```

> If you want to override the default model, remember to set an absolute path for the `PICOVOICE_MODEL_PATH` environment variable

Repackage and run activating a Spring profile

```bash
mvn package
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=picovoice,dev
```

> Back in the terminal shell, press Ctrl+C to shutdown.

#### Available endpoints

```commandline
http POST :8080/api/picovoice/transcribe \ 
  Content-Type:application/octet-stream \
  @/path/to/your/audio/file
```

> Replace `/path/to/your/audio/file` above with a valid absolute path to your audio file

Note: you are limited to providing .au, .aiff, or .wav files with a 16kHz sample rate.
 