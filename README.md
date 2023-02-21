# Stand Strategist App

Stand Strategist is a desktop app used by the Stand Strategists to record textual and subjective data during
competitions. The app is written in Kotlin using [Compose Desktop](https://www.jetbrains.com/lp/compose-mpp/) and runs
on Windows, macOS and Linux. To install the app, get the binaries from the latest GitHub Actions run or build the app
from source.

## Build

First, clone the repo.

Then, in the repo directory, run:

```bash
./gradlew packageDistributionForCurrentOS
```

This should place a binary somewhere in the `build/compose/binaries` directory, which you can install or
distribute.

> Note: You can only build a binary for the OS you're currently on. For example, if you're on macOS, you cannot build a
> Windows executable, you can only build for macOS.

## Develop

Get [IntelliJ IDEA](https://www.jetbrains.com/idea/download/). Android Studio may work, but it is not recommended.

Then open the project in IntelliJ IDEA. You should see a run configuration called 'Desktop App', which will run the app
without installing it.

From here, you can make changes and rerun the app to see them.

## Storage Locations

Stand Strategist has a storage folder containing all the data and settings.

On Windows, this folder is located at `C:\User\username\AppData\Local\Stand Strategist\`.

On macOS, this folder is located at `/Users/username/.stand-strategist/`.

On Linux, this folder is located at `/home/username/.stand-strategist/`.

> Note: On macOS and Linux, you may need enable showing hidden files to see the storage folder in your file explorer. On
> Windows, you can search for `%AppData%` to find the folder.
