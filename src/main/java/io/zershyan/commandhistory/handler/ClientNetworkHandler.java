package io.zershyan.commandhistory.handler;

import io.zershyan.commandhistory.CommandHistory;
import io.zershyan.commandhistory.config.CHConfigs;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = CommandHistory.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientNetworkHandler {
    @SubscribeEvent
    public static void playerLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        try {
            Path history = Paths.get("command_history.txt");
            if (!Files.exists(history)) Files.createFile(history);
            try(Stream<String> lines = Files.lines(history)) {
                List<String> strings = lines.toList();
                if(CHConfigs.Client.onlyCommand.get()) {
                    strings = strings.stream().filter(s -> s.startsWith("/")).toList();
                }
                if(strings.isEmpty()) return;
                Integer limit = CHConfigs.Client.historyLimit.get();
                List<String> result = new ArrayList<>(strings);
                int size = strings.size();
                if(size > limit) result = strings.subList(size - limit, size);
                Minecraft instance = Minecraft.getInstance();
                result.forEach(instance.gui.getChat()::addRecentChat);
            }
        } catch (Exception e) {
            CommandHistory.LOGGER.error(e.getMessage());
        }
    }

    @SubscribeEvent
    public static void playerLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        if(event.getPlayer() == null) return;
        try {
            Path history = Paths.get("command_history.txt");
            if (!Files.exists(history)) Files.createFile(history);
            Integer limit = CHConfigs.Client.historyLimit.get();
            Minecraft instance = Minecraft.getInstance();
            List<String> recentChat = instance.gui.getChat().getRecentChat();
            if(CHConfigs.Client.onlyCommand.get()) {
                recentChat = recentChat.stream().filter(s -> s.startsWith("/")).toList();
            }
            List<String> result = new ArrayList<>(recentChat);
            int size = recentChat.size();
            if(size > limit) result = recentChat.subList(size - limit, size);
            Files.write(history, result, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            CommandHistory.LOGGER.error(e.getMessage());
        }
    }
}
