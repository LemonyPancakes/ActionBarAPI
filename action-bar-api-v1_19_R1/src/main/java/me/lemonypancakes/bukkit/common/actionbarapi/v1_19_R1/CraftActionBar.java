/*
 * ActionBarAPI - Action bar API for Bukkit and forks of Bukkit.
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
package me.lemonypancakes.bukkit.common.actionbarapi.v1_19_R1;

import me.lemonypancakes.bukkit.common.actionbarapi.ActionBar;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CraftActionBar implements ActionBar {

    @Override
    public void sendMessage(Player player, String message) {
        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        ClientboundSystemChatPacket clientboundSystemChatPacket;

        try {
            Class<?> clazz = Class.forName(ClientboundSystemChatPacket.class.getName());
            Constructor<?> constructor = clazz.getConstructor(IChatBaseComponent.class, int.class);

            clientboundSystemChatPacket = (ClientboundSystemChatPacket) constructor.newInstance(iChatBaseComponent, 2);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            clientboundSystemChatPacket = new ClientboundSystemChatPacket(iChatBaseComponent, true);
        }
        ((CraftPlayer) player).getHandle().b.a(clientboundSystemChatPacket);
    }
}
