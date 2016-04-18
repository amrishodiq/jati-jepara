# Jati Jepara
Library to save Android logs into file, to be used in conjunction with Timber.

When this project started, Timber is (in my opinion) the best logger for Android. Sometime, we need the log to be written in files. Jake Warthon frees us to implement our extension of DebugTree. So then, I wrote this simple extension.

## Purpose
1. Enable Timber user to save logs to file easily
2. Make the log files rotated, so one log file is no bigger than 600 kilobytes
3. Send the log to server (not implemented yet)
 
## Usage
I'm preparing the usage document, I hope it's just as easy (or better easier) than Timber usage itself.
Meanwhile, please see how app module implemented.

## License

> Copyright 2016 Amri Shodiq
> 
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
> 
>    http://www.apache.org/licenses/LICENSE-2.0
> 
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.
