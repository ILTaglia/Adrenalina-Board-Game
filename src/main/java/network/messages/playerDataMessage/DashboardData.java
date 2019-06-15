package network.messages.playerDataMessage;

import model.Dashboard;

public class DashboardData extends InfoMessage {

    private Dashboard dashboard;

    public DashboardData(Dashboard dashboard){
        super("Selected map index is:"+dashboard.getMapType()+". Map printed:\n");
        this.dashboard=dashboard;
        this.content="DashboardData";
    }

    public Dashboard getDashboard(){
        return this.dashboard;
    }
}
