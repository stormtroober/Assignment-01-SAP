package sap.ass01.layered.UI.Models;



public record EBikeViewModel (String id, double x, double y, int batteryLevel, String state){
    public EBikeViewModel updateBatteryLevel(int batteryLevel) {
        return new EBikeViewModel(id, x, y, batteryLevel, state);
    }
    public EBikeViewModel updateState(String state) {
        return new EBikeViewModel(id, x, y, batteryLevel, state);
    }

}