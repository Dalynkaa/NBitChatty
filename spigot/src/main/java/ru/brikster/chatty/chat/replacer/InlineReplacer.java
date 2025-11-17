package ru.brikster.chatty.chat.replacer;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Interface for inline replacers that can be used in chat messages.
 * Inline replacers allow players to insert dynamic content into their messages
 * using a syntax like :pos:, :item:, :balance:, etc.
 */
public interface InlineReplacer {

    /**
     * Gets the key for this replacer.
     * The key is what players type between colons in their messages.
     * For example, if the key is "pos", players would type :pos: in their message.
     *
     * @return the replacer key (e.g., "pos", "item", "balance")
     */
    @NotNull
    String getKey();

    /**
     * Replaces the inline placeholder with the actual content for the given player.
     *
     * @param player the player whose message is being processed
     * @return the Component to replace the placeholder with
     */
    @NotNull
    Component replace(@NotNull Player player);

    /**
     * Gets the permission required to use this replacer.
     * If no permission is required, this method should return Optional.empty().
     *
     * @return an Optional containing the permission string, or empty if no permission is required
     */
    @NotNull
    default Optional<String> getRequiredPermission() {
        return Optional.empty();
    }

}
