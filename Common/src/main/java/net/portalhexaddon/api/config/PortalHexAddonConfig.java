package net.portalhexaddon.api.config;

import net.portalhexaddon.PortalHexAddon;

import java.util.Collection;

/**
 * Platform-agnostic class for statically accessing current config values.
 * If any of the config types (common, client, server) are not needed in your mod,
 * feel free to remove anything related to them in this class and platform-specific config implementations.
 */
public class PortalHexAddonConfig {
    private static final CommonConfigAccess dummyCommon = new CommonConfigAccess() {
    };
    private static final ClientConfigAccess dummyClient = new ClientConfigAccess() {
    };
    private static final ServerConfigAccess dummyServer = new ServerConfigAccess() {
        @Override
        public int getCongratsCost() {
            throw new IllegalStateException("Attempted to access property of Dummy Config Object");
        }

        @Override
        public int getSignumCost() {
            throw new IllegalStateException("Attempted to access property of Dummy Config Object");
        }
    };
    private static CommonConfigAccess common = dummyCommon;
    private static ClientConfigAccess client = dummyClient;
    private static ServerConfigAccess server = dummyServer;

    public static CommonConfigAccess getCommon() {
        return common;
    }

    public static void setCommon(CommonConfigAccess common) {
        if (PortalHexAddonConfig.common != dummyCommon) {
            PortalHexAddon.LOGGER.warn("CommonConfigAccess was replaced! Old {} New {}", PortalHexAddonConfig.common.getClass().getName(), common.getClass().getName());
        }
        PortalHexAddonConfig.common = common;
    }

    public static ClientConfigAccess getClient() {
        return client;
    }

    public static void setClient(ClientConfigAccess client) {
        if (PortalHexAddonConfig.client != dummyClient) {
            PortalHexAddon.LOGGER.warn("ClientConfigAccess was replaced! Old {} New {}", PortalHexAddonConfig.client.getClass().getName(), client.getClass().getName());
        }
        PortalHexAddonConfig.client = client;
    }

    public static ServerConfigAccess getServer() {
        return server;
    }

    public static void setServer(ServerConfigAccess server) {

        if (PortalHexAddonConfig.server != dummyServer) {
            PortalHexAddon.LOGGER.warn("ServerConfigAccess was replaced! Old {} New {}", PortalHexAddonConfig.server.getClass().getName(), server.getClass().getName());
        }
        PortalHexAddonConfig.server = server;
    }

    public static int bound(int toBind, int lower, int upper) {
        return Math.min(Math.max(toBind, lower), upper);
    }

    public static double bound(double toBind, double lower, double upper) {
        return Math.min(Math.max(toBind, lower), upper);
    }

    public interface CommonConfigAccess {
    }

    public interface ClientConfigAccess {
    }

    public interface ServerConfigAccess {
        double DEF_MIN_COST = 0.0001;
        double DEF_MAX_COST = 10_000.0;
        double DEFAULT_CONGRATS_COST = 1.5;
        double DEFAULT_SIGNUM_COST = 1.0;

        int getCongratsCost();

        int getSignumCost();
    }
}
