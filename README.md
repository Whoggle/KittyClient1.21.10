# KittyClient
A client-side Fabric mod for Hypixel Skyblock adding fun and useful features for dungeons and beyond!

## Features

### Dungeons
- Automatic party session tracking — reads the scoreboard at the start of each run to track teammates and their classes
- Waypoint system with colored block outlines that trigger automatically based on dungeon events
- Requeue system with downtime request tracking
- Party leader tracking

### Party Commands
All party commands are toggleable via `/kc` or `/kittyclient`
| Command | Description |
|---------|-------------|
| `!8ball` | Ask the 8-ball a question |
| `!ref` | Get a random reference |
| `!ping` | Pong! |
| `!coords` | Request Coordinates from others with the mod |
| `!w` | Start warp timer |
| `!c` | Cancel warp timer |
| `!fw` | Force warp |
| `!rq` | Toggle requeue |
| `!dt` | Request downtime |

### Imgur Preview
Hover over specially formatted Imgur png inserts in party chat to preview images directly in game!
An example of a message that would trigger this would be "Party > [MVP+] PrinceKittyMeow: I[mpaCz9b]"
This would create a hoverable link in chat to show the png at: https://i.imgur.com/mpaCz9b.png

To set this up:
1. Upload a PNG file to imgur
2. Right click it and click on "Open Image in New Tab"
3. Copy the characters between ".com/" and ".png"
4. Add "I[1234567]" to an autoparty message (replace 1234567 with your data) or just send it as a party message

## Commands
- `/kc` or `/kittyclient` — Open the party commands toggle GUI
- `/kc clearwaypoints` — Clear all active waypoints
- `/kc imgursize <size>` — Change the Imgur preview size
- `/kc ping` - Play a cat noise!

## Requirements
- Minecraft 1.21.10
- Fabric Loader >= 0.18.4
- Fabric API

## Installation
1. Install [Fabric Loader](https://fabricmc.net/use/installer/)
2. Download [Fabric API](https://modrinth.com/mod/fabric-api)
3. Place both `fabric-api` and `kittyclient` jars in your `.minecraft/mods` folder

## Building
```bash
./gradlew build
```
Output jar will be in `build/libs/`

## License
CC0-1.0
