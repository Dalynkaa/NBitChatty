package ru.brikster.chatty.chat.replacer.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.brikster.chatty.chat.replacer.InlineReplacer;

import java.util.Optional;

/**
 * Inline replacer that displays the player's current coordinates.
 * Format: X: 100 Y: 64 Z: -200
 * Color: Aqua, Bold
 * Requires permission: chatty.replacer.pos
 */
public final class PositionInlineReplacer implements InlineReplacer {

    @Override
    public @NotNull String getKey() {
        return "pos";
    }

    @Override
    public @NotNull Component replace(@NotNull Player player) {
        Location location = player.getLocation();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        return Component.text(String.format("X: %d Y: %d Z: %d", x, y, z))
                .color(NamedTextColor.AQUA)
                .decorate(TextDecoration.BOLD);
    }

    @Override
    public @NotNull Optional<String> getRequiredPermission() {
        return Optional.of("chatty.replacer.pos");
    }

}
