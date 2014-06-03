package de.haw.mps.api;


import de.haw.mps.MpsLogger;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public final class SystemController extends ActionController {

    public enum SystemActions {
        PING() {
            @Override
            public Response process(final Request request) {
                String[] params = new String[]{String.valueOf(System.currentTimeMillis())};
                return ActionController.createResponse("pong", ResponseCode.OK, params);
            }
        },
        LOAD() {
            @Override
            public Response process(Request request) {
                try {
                    double load = ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
                    return ActionController.createResponse("load", ResponseCode.OK, new String[]{String.valueOf(load)});
                } catch (Exception e) {
                    String msg = "Unable to determine server load on this system.";
                    MpsLogger.getLogger().info(msg);
                    return ActionController.createResponse(ResponseCode.ERROR, new String[]{msg});
                }
            }
        },
        SYSINFO() {
            @Override
            public Response process(Request request) {
                try {
                    OperatingSystemMXBean systemMXBean = ManagementFactory.getOperatingSystemMXBean();

                    String processors = String.valueOf(systemMXBean.getAvailableProcessors());
                    String arch = systemMXBean.getArch();
                    String sysName = systemMXBean.getName();

                    String[] params = new String[]{arch, processors, sysName};
                    return ActionController.createResponse("info", ResponseCode.OK, params);
                } catch (Exception e) {
                    String msg = "Unable to server information on this system.";
                    MpsLogger.getLogger().info(msg);
                    return ActionController.createResponse(ResponseCode.ERROR, new String[]{msg});
                }
            }
        },

        REQCOUNT() {
            @Override
            public Response process(Request request) {
                int reqCount = request.getClient().getRequestCounter();
                request.getClient().resetRequestCounter();
                return ActionController.createResponse("reqcount", ResponseCode.OK, new String[]{String.valueOf(reqCount)});
            }
        };

        public abstract Response process(Request request);
    }

    @Override
    public boolean liableForAction(String action) {
        try {
            return (SystemActions.valueOf(action.toUpperCase()) != null);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void process(Request request) {
        SystemActions action = SystemActions.valueOf(request.requestedAction().toUpperCase());

        if(action == null) {
            throw new NullPointerException("Action liable but not found for processing.");
        }

        request.setResponse(action.process(request));
    }
}
