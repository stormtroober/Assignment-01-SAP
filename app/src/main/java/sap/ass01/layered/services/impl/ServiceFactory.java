package sap.ass01.layered.services.impl;

import sap.ass01.layered.services.Services.AdminService;
import sap.ass01.layered.services.Services.LoginService;
import sap.ass01.layered.ui.plugin.PluginService;
import sap.ass01.layered.services.Services.UserService;

public class ServiceFactory {
    private static final ServiceImpl serviceImpl = new ServiceImpl();

    public static LoginService getLoginService() {
        return serviceImpl;
    }

    public static AdminService getAdminService() {
        return serviceImpl;
    }

    public static UserService getUserService() {
        return serviceImpl;
    }
}