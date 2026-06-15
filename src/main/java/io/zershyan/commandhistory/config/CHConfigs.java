package io.zershyan.commandhistory.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CHConfigs {
    public enum ConfigName {
        historyLimit("historyLimit"),
        onlyCommand("onlyCommand"),
        ;

        private final String name;
        ConfigName(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class Client {
        public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        public static final ForgeConfigSpec SPEC;

        public static final ForgeConfigSpec.ConfigValue<Integer> historyLimit;
        public static final ForgeConfigSpec.ConfigValue<Boolean> onlyCommand;

        static {
            BUILDER.push("General");
            historyLimit = BUILDER.comment("Command history limit.")
                    .defineInRange(ConfigName.historyLimit.name, 50, 0, 500);
            onlyCommand = BUILDER.comment("Only the commands are recorded.")
                    .define(ConfigName.onlyCommand.name, false);
            BUILDER.pop();
            SPEC = BUILDER.build();
        }
    }
}
