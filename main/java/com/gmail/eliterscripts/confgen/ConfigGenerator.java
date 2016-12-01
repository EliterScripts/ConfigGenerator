package com.gmail.eliterscripts.confgen;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.config.DefaultConfig;

import javax.xml.stream.events.Comment;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by eliter on 11/26/16.
 */
public class ConfigGenerator {

    private static final ConfigGenerator instance = new ConfigGenerator();


    private static ConfigurationLoader<CommentedConfigurationNode> loader;
    private final String nodeName = MainPluginFile.instance().container.getId();
    private CommentedConfigurationNode root;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path path;

    public ConfigGenerator(){
        loader = HoconConfigurationLoader.builder().setPath(path).build();
    }

    public static ConfigGenerator instance(){
        return instance;
    }

    static void parse(){
        instance().load();
        instance().setValues();
        instance().save();
    }

    private void load(){
        try {
            this.root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setValues(){
        CommentedConfigurationNode mode = root.getNode(nodeName, "mode");
        CommentedConfigurationNode bcast = root.getNode(nodeName, "broadcast-settings");
        CommentedConfigurationNode prefix = root.getNode(nodeName, "broadcast-settings", "prefix");
        CommentedConfigurationNode suffix = root.getNode(nodeName, "broadcast-settings", "suffix");
        CommentedConfigurationNode interval = root.getNode(nodeName, "broadcast-settings", "interval");
        CommentedConfigurationNode advanced = root.getNode(nodeName, "advanced");
        CommentedConfigurationNode check = root.getNode(nodeName, "advanced", "check");
        CommentedConfigurationNode version = root.getNode(nodeName, "advanced", "version");
        CommentedConfigurationNode alert_node = root.getNode(nodeName, "advanced", "alert_node");
        CommentedConfigurationNode install = root.getNode(nodeName, "advanced", "install");
        CommentedConfigurationNode install_delay = root.getNode(nodeName, "advanced", "install_delay");

        try {
            mode.setComment("This setting allows the plugin to be simply or comply configured.\n" +
                    "This can be either 'simple' or 'complex'.").setValue(
                        TypeToken.of(String.class), "simple"
                    );
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }

        try {
            bcast.setComment("These are the broadcast settings.").setValue(
                    TypeToken.of(CommentedConfigurationNode.class), loader.createEmptyNode()
            );
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }

        try{
            prefix.setComment("Whatever the prefix is set to, that message will be shown before every message.\n" +
                    "This setting supports colors and formatting, and must have the '' around it.").setValue(
                            TypeToken.of(String.class), "&2&l[&aAnnouncer&4Plus&2&l]&r"
                    );
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }

        try{
            suffix.setComment("Just like the prefix setting, but at the end of the message.\n" +
                    "This setting supports colors and formatting, and must have the '' around it.").setValue(
                    TypeToken.of(String.class), ""
            );
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }

        try{
            interval.setComment("This setting sets how long the interval is between messages.\n" +
                    "This setting must be positive, and include no decimals and include no quotes.").setValue(
                            TypeToken.of(Integer.class), 360
            );
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }

        try{
            advanced.setComment("These settings you don't really have to mess with.\n" +
                    "There...well...advanced.").setValue(
                    TypeToken.of(CommentedConfigurationNode.class), loader.createEmptyNode()
            );
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }

        try{
            version.setComment("This is the configuration version.\n" +
                    "This setting must be a string.").setValue(
                            TypeToken.of(String.class), "1.0"
            );
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
        try{
            check.setComment("This enables or disables the update checks.\n" +
                    "This must be a boolean value.").setValue(
                    TypeToken.of(Boolean.class), true
            );
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
        try{
            alert_node.setComment("This setting determines who gets alerted if there's an update available.\n" +
                    "This must be 'console-op', 'console', 'op', false, 'all', or a permission node " +
                    "(as a string).").setValue(
                        TypeToken.of(String.class), "console-op"
            );
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
        try{
            install.setComment("This setting determines whether or not to automatically install updates.\n" +
                    "This must be a boolean value.").setValue(
                    TypeToken.of(Boolean.class), true
            );
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }

        try{
            install_delay.setComment("This setting determines how much delay between notification of updates and its install\n" +
                    "This setting is in seconds, and must be either a positive integer or false.").setValue(
                        TypeToken.of(Integer.class), 360
            );
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }


    }

    private void save(){
        try {
            loader.save(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
