package org.matic.msclansaddons;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.matic.msclansaddons.commands.*;
import org.matic.msclansaddons.events.*;

import java.util.Objects;


public final class MsClansAddons extends JavaPlugin {

    private static Economy econ = null;
    private static MsClansAddons instance;
    @Override
    public void onEnable() {

        instance = this;

        System.out.println(ChatColor.translateAlternateColorCodes('&',"&7--------------------------"));
        System.out.println(ChatColor.translateAlternateColorCodes('&',"&3 __       __   ______     "));
        System.out.println(ChatColor.translateAlternateColorCodes('&',"&3|  \\     /  \\ /      \\ "));
        System.out.println(ChatColor.translateAlternateColorCodes('&',"&3| $$\\   /  $$|  $$$$$$\\ "));
        System.out.println(ChatColor.translateAlternateColorCodes('&',"&3| $$$\\ /  $$$| $$___\\$$ "));
        System.out.println(ChatColor.translateAlternateColorCodes('&',"&3| $$$$\\  $$$$ \\$$    \\ "));
        System.out.println(ChatColor.translateAlternateColorCodes('&',"&3| $$\\$$ $$ $$ _\\$$$$$$\\"));
        System.out.println(ChatColor.translateAlternateColorCodes('&',"&3| $$ \\$$$| $$|  \\__| $$ "));
        System.out.println(ChatColor.translateAlternateColorCodes('&',"&3| $$  \\$ | $$ \\$$    $$ "));
        System.out.println(ChatColor.translateAlternateColorCodes('&',"&3 \\$$      \\$$  \\$$$$$$ "));
        System.out.println(ChatColor.translateAlternateColorCodes('&',"&7--------------------------"));
        System.out.println(ChatColor.translateAlternateColorCodes('&',"&7registering events"));

        // Plugin startup logic

        Plugin clanPlugin = getServer().getPluginManager().getPlugin("Clans");

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (clanPlugin == null){
            getLogger().severe(String.format("[%s] - Disabled due to no Clan dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        PluginCommand clanCommand = clanPlugin.getServer().getPluginCommand("clan");
        System.out.println(ChatColor.translateAlternateColorCodes('&',"&aFound clans plugin"));
        if (clanCommand != null){
            clanCommand.setTabCompleter(new tabber());
            System.out.println(ChatColor.translateAlternateColorCodes('&',"&aAdd tab support for clans"));
        }
        else {
            System.out.println(ChatColor.translateAlternateColorCodes('&',"&cFailed To add tab support for clans"));
        }

        this.getServer().getPluginManager().registerEvents(new clanCreate(), this);
        this.getServer().getPluginManager().registerEvents(new clanStats(), this);

        //events
        this.getServer().getPluginManager().registerEvents(new joinEvent(), this);
        this.getServer().getPluginManager().registerEvents(new leaveEvent(), this);

        this.getServer().getPluginManager().registerEvents(new deathHandler(), this);
        this.getServer().getPluginManager().registerEvents(new phatomRemover(), this);

        this.getServer().getPluginManager().registerEvents(new betterSleep(), this);

        //commands
        this.getCommand("spawn").setExecutor(new spawn());
        this.getCommand("wild").setExecutor(new wild());
        this.getCommand("stats").setExecutor(new stats());

        System.out.println(ChatColor.translateAlternateColorCodes('&',"&7Loaded successfully"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MsClansAddons getInstance() {
        return instance;
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
