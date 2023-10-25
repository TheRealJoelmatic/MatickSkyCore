package org.matic.msclansaddons;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.matic.msclansaddons.commands.clanStats;
import org.matic.msclansaddons.events.clanCreate;
import org.matic.msclansaddons.events.deathHandler;


public final class MsClansAddons extends JavaPlugin {

    private static Economy econ = null;
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new deathHandler(), this);
        this.getServer().getPluginManager().registerEvents(new clanCreate(), this);
        this.getServer().getPluginManager().registerEvents(new clanStats(), this);

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
}
