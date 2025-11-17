package ru.brikster.chatty.chat.replacer;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Processor for inline replacers in chat messages.
 * This class manages registration of inline replacers and processes messages
 * to replace inline placeholders with their corresponding components.
 */
@Singleton
public final class InlineReplacersProcessor {

    /**
     * Pattern to match inline replacers in the format :key:
     * Matches lowercase letters, numbers, and underscores
     */
    private static final Pattern INLINE_REPLACER_PATTERN = Pattern.compile(":([a-z0-9_]+):");

    private final Map<String, InlineReplacer> replacers = new HashMap<>();

    @Inject
    public InlineReplacersProcessor() {
    }

    /**
     * Registers an inline replacer.
     *
     * @param replacer the replacer to register
     */
    public void register(@NotNull InlineReplacer replacer) {
        replacers.put(replacer.getKey(), replacer);
    }

    /**
     * Unregisters an inline replacer by its key.
     *
     * @param key the key of the replacer to unregister
     */
    public void unregister(@NotNull String key) {
        replacers.remove(key);
    }

    /**
     * Processes a message component and replaces all inline replacers with their corresponding components.
     * 
     * Note: The message component is expected to have an ending space to preserve styles properly.
     * This is handled by the calling code.
     *
     * @param message the message component to process
     * @param player the player whose message is being processed
     * @return the processed component with replacers applied
     */
    @NotNull
    public Component processMessage(@NotNull Component message, @NotNull Player player) {
        return ru.brikster.chatty.util.AdventureUtil.replaceWithEndingSpace(
                message,
                INLINE_REPLACER_PATTERN,
                matchedString -> {
                    String key = matchedString.substring(1, matchedString.length() - 1); // Remove surrounding colons
                    InlineReplacer replacer = replacers.get(key);
                    
                    if (replacer == null) {
                        return null; // Don't replace if replacer not found
                    }
                    
                    // Check if player has permission to use this replacer
                    if (replacer.getRequiredPermission().isPresent()) {
                        if (!player.hasPermission(replacer.getRequiredPermission().get())) {
                            return null; // Don't replace if player doesn't have permission
                        }
                    }
                    
                    // Replace the placeholder with the component, adding ending space for style preservation
                    Component replacement = replacer.replace(player);
                    return replacement.append(Component.text(" "));
                },
                matchedString -> {
                    // For click events and other text-only contexts, just return null to keep original
                    return null;
                }
        );
    }

    /**
     * Gets a set of all available replacer keys.
     *
     * @return set of replacer keys
     */
    @NotNull
    public Set<String> getAvailableReplacers() {
        return Set.copyOf(replacers.keySet());
    }

}
