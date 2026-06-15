package io.zershyan.commandhistory;

import com.mojang.logging.LogUtils;
import io.zershyan.commandhistory.config.CHConfigs;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(CommandHistory.MODID)
public class CommandHistory {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "commandhistory";

    public CommandHistory() {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, CHConfigs.Client.SPEC);
    }
}
