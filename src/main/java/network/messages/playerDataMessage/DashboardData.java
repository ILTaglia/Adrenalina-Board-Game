package network.messages.playerDataMessage;

import model.Dashboard;

/**
 * Info message for dashboard data
 */
public class DashboardData extends InfoMessage {

    private Dashboard dashboard;

    public DashboardData(Dashboard dashboard){
        super("Dashboard is updated");
        this.dashboard=dashboard;
        this.content="DashboardData";
    }

    public Dashboard getDashboard(){
        return this.dashboard;
    }
}
