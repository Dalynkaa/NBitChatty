# Chatty (Bukkit plugin)

[![GitHub release (latest by date)](https://img.shields.io/github/v/release/Brikster/Chatty)](https://github.com/Brikster/Chatty/releases/latest)
[![GitHub All Releases](https://img.shields.io/github/downloads/Brikster/Chatty/total)](https://github.com/Brikster/Chatty/releases)
[![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/Brikster/Chatty)](https://github.com/Brikster/Chatty/archive/master.zip)
[![JitPack](https://jitpack.io/v/Brikster/Chatty.svg)](https://jitpack.io/#Brikster/Chatty)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/815bf25f21da4c81b9e26bd1159df072)](https://www.codacy.com/gh/Brikster/Chatty/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Brikster/Chatty&amp;utm_campaign=Badge_Grade)

> Latest release of Chatty (v2.*) is deprecated and won't be updated. 
> Currently we're developing **Chatty v3**, and it is **WORK IN PROGRESS**. Current branch contains a code of it. 
> You can download latest build in "Actions" (see "Artifacts" section).

Chatty is the modern chat management system for Bukkit-compatible servers. It's based on-top of Kyori's Adventure library, 
that makes it so powerful and stable.

**Key features**:
- Chat channels ("local" and "global" by default)
- Private messaging
- Moderation (CAPS, advertisements, swears)
- Notifications (chat, action bar and title)
- "Vanilla" messages configuring (join/quit/death)
- MiniMessage both legacy (&) styling format
- Inline replacers (`:pos:`, `:item:`, `:balance:`) for dynamic content in chat messages

## Building

Chatty uses Gradle to handle dependencies & building. You need JDK 11 or higher to compile Chatty.

### Compiling from source

```shell script
git clone https://github.com/Brikster/Chatty.git
cd Chatty/
./gradlew build
```

Output jar will be placed into `/build/libs` directory.

## Inline Replacers

Chatty v3 includes a modular inline replacers system that allows players to insert dynamic content into their chat messages using special syntax.

### Usage

Players can use inline replacers in their messages by typing `:key:` where `key` is the replacer identifier. For example:
- `"[G] Player: My position is :pos:"` → displays coordinates with aqua bold text
- `"[G] Player: I'm holding :item:"` → displays the item in hand with gold text
- `"[G] Player: My balance: :balance:"` → displays balance with green text (requires Vault)

### Built-in Replacers

| Key | Description | Permission | Color | Requirements |
|-----|-------------|------------|-------|--------------|
| `:pos:` | Player's coordinates (X: Y: Z:) | `chatty.replacer.pos` | Aqua, Bold | None |
| `:item:` | Item in main hand | `chatty.replacer.item` | Gold | None |
| `:balance:` | Player's balance | `chatty.replacer.balance` | Green | Vault plugin |

### Permissions

Players need the corresponding permission to use each replacer:
- `chatty.replacer.pos` - Allows using `:pos:`
- `chatty.replacer.item` - Allows using `:item:`
- `chatty.replacer.balance` - Allows using `:balance:`

If a player doesn't have permission, the replacer text will remain unchanged in their message.

### Extensibility

The inline replacers system is modular and can be extended programmatically. Developers can:
1. Implement the `InlineReplacer` interface
2. Register custom replacers with `InlineReplacersProcessor`
3. Define custom permissions and styling
