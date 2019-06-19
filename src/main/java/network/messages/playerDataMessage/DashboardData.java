package network.messages.playerDataMessage;

import model.Dashboard;

public class DashboardData extends InfoMessage {

    private Dashboard dashboard;

    public DashboardData(Dashboard dashboard){
        super("Selected map index is:"+dashboard.getMapType()+". Map printed:\n TODO: il primo di questi messaggi viene mandato dopo la fill, bisognerà mandarne uno per ogni modifica fatta alla dashboard");
        this.dashboard=dashboard;
        this.content="DashboardData";
    }

    public Dashboard getDashboard(){
        return this.dashboard;
    }
}
