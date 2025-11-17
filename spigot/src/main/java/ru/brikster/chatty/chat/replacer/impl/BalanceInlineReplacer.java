package ru.brikster.chatty.chat.replacer.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;
import ru.brikster.chatty.chat.replacer.InlineReplacer;

import java.util.Optional;

/**
 * Inline replacer that displays the player's balance via Vault economy.
 * Color: Green
 * Requires permission: chatty.replacer.balance
 * Note: This replacer requires Vault and an economy plugin to be installed.
 */
public final class BalanceInlineReplacer implements InlineReplacer {

    private final Economy economy;

    public BalanceInlineReplacer() {
        this.economy = setupEconomy();
    }

    @Override
    public @NotNull String getKey() {
        return "balance";
    }

    @Override
    public @NotNull Component replace(@NotNull Player player) {
        if (economy == null) {
            return Component.text("Economy not available")
                    .color(NamedTextColor.RED);
        }

        double balance = economy.getBalance(player);
        String formattedBalance = economy.format(balance);

        return Component.text(formattedBalance)
                .color(NamedTextColor.GREEN);
    }

    @Override
    public @NotNull Optional<String> getRequiredPermission() {
        return Optional.of("chatty.replacer.balance");
    }

    /**
     * Sets up the Vault economy provider.
     *
     * @return the Economy instance, or null if Vault is not available
     */
    private Economy setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return null;
        }

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager()
                .getRegistration(Economy.class);
        if (rsp == null) {
            return null;
        }

        return rsp.getProvider();
    }

}
