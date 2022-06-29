/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2022 LemonyPancakes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.lemonypancakes.bukkit.common.actionbarapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class ActionBarAPI extends JavaPlugin {

    private static ActionBar actionBar;

    public ActionBarAPI() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);

        try {
            final Class<?> clazz = Class.forName("me.lemonypancakes.bukkit.common.actionbarapi." + version + ".CraftActionBar");

            if (ActionBar.class.isAssignableFrom(clazz)) {
                ActionBarAPI.setActionBar((ActionBar) clazz.getConstructor().newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        new BukkitRunnable() {

            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    ActionBarAPI.sendMessage(player, "HELLO");
                });
            }
        }.runTaskTimer(this, 0L, 1L);
    }

    public static ActionBar getActionBar() {
        return actionBar;
    }

    public static void setActionBar(ActionBar actionBar) {
        if (ActionBarAPI.actionBar == null) {
            ActionBarAPI.actionBar = actionBar;
        }
    }

    public static void sendMessage(Player player, String message) {
        if (ActionBarAPI.actionBar != null) {
            actionBar.sendMessage(player, message);
        }
    }
}
