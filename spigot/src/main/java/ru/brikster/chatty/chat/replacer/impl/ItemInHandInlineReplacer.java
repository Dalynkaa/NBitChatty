package ru.brikster.chatty.chat.replacer.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.brikster.chatty.chat.replacer.InlineReplacer;

import java.util.Optional;

/**
 * Inline replacer that displays the item currently in the player's main hand.
 * Color: Gold
 * Requires permission: chatty.replacer.item
 */
public final class ItemInHandInlineReplacer implements InlineReplacer {

    @Override
    public @NotNull String getKey() {
        return "item";
    }

    @Override
    public @NotNull Component replace(@NotNull Player player) {
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        
        if (itemInHand == null || itemInHand.getType().isAir()) {
            return Component.text("Empty Hand")
                    .color(NamedTextColor.GOLD);
        }

        // Get the item name, using custom name if present
        Component itemName;
        if (itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasDisplayName()) {
            itemName = Component.text(itemInHand.getItemMeta().getDisplayName());
        } else {
            // Use the material name as fallback
            String materialName = itemInHand.getType().name()
                    .replace("_", " ")
                    .toLowerCase();
            // Capitalize first letter of each word
            String[] words = materialName.split(" ");
            StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < words.length; i++) {
                if (i > 0) formatted.append(" ");
                if (!words[i].isEmpty()) {
                    formatted.append(Character.toUpperCase(words[i].charAt(0)));
                    if (words[i].length() > 1) {
                        formatted.append(words[i].substring(1));
                    }
                }
            }
            itemName = Component.text(formatted.toString());
        }

        // Add count if more than 1
        if (itemInHand.getAmount() > 1) {
            itemName = itemName.append(Component.text(" x" + itemInHand.getAmount()));
        }

        return itemName.color(NamedTextColor.GOLD);
    }

    @Override
    public @NotNull Optional<String> getRequiredPermission() {
        return Optional.of("chatty.replacer.item");
    }

}
