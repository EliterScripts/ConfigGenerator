package com.gmail.eliterscripts.confgen;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

@Plugin(
        id = "confgen",
        name = "ConfGen"
)
public class MainPluginFile {

    private static final MainPluginFile instance = new MainPluginFile();

    @Inject
    private Logger logger;

    @Inject
    public PluginContainer container;

    public MainPluginFile(){

    }

    public static MainPluginFile instance(){
        return instance;
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        this.logger.info("starting Configuration Generator...");
        ConfigGenerator.parse();
    }


    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event, @Getter("getTargetEntity") Player player) {
        player.sendMessage(Text.of(TextColors.AQUA, TextStyles.BOLD, "Configuration Generator is enabled on this server."));
    }

}
