package ru.brikster.chatty.chat.replacer;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.bukkit.Bukkit;
import ru.brikster.chatty.chat.replacer.impl.BalanceInlineReplacer;
import ru.brikster.chatty.chat.replacer.impl.ItemInHandInlineReplacer;
import ru.brikster.chatty.chat.replacer.impl.PositionInlineReplacer;

import javax.inject.Singleton;

/**
 * Guice module for registering inline replacers and related components.
 * This module sets up the dependency injection for the inline replacers system
 * and registers all built-in replacers with the processor.
 */
public final class ReplacersGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        // Bind the processor and transformer as singletons
        bind(InlineReplacersProcessor.class);
        bind(InlineReplacersComponentTransformer.class);
    }

    /**
     * Provides a configured InlineReplacersProcessor with all built-in replacers registered.
     *
     * @param processor the processor instance to configure
     * @return the configured processor
     */
    @Provides
    @Singleton
    public InlineReplacersProcessor provideConfiguredProcessor(InlineReplacersProcessor processor) {
        // Register built-in replacers
        processor.register(new PositionInlineReplacer());
        processor.register(new ItemInHandInlineReplacer());
        
        // Only register balance replacer if Vault is available
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            processor.register(new BalanceInlineReplacer());
        }

        return processor;
    }

}
