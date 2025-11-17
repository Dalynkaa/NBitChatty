package ru.brikster.chatty.chat.replacer;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.brikster.chatty.chat.component.context.SinglePlayerTransformContext;
import ru.brikster.chatty.chat.component.impl.PlaceholdersComponentTransformer;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Component transformer that applies inline replacers to chat messages.
 * This transformer integrates with the existing chat message processing pipeline
 * to replace inline placeholders like :pos:, :item:, :balance: with their
 * corresponding components.
 */
@Singleton
public final class InlineReplacersComponentTransformer implements PlaceholdersComponentTransformer {

    private final InlineReplacersProcessor processor;

    @Inject
    public InlineReplacersComponentTransformer(InlineReplacersProcessor processor) {
        this.processor = processor;
    }

    @Override
    public @NotNull Component transform(@NotNull Component formatComponent, @NotNull SinglePlayerTransformContext context) {
        // Only process if the player is online
        if (context.getPlayer() instanceof Player) {
            Player player = (Player) context.getPlayer();
            return processor.processMessage(formatComponent, player);
        }
        return formatComponent;
    }

}
