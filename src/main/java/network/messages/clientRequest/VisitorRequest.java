package network.messages.clientRequest;

import network.messages.ConnectionClientRequest;

public interface VisitorRequest {
    void visit(ColorClientRequest messageRequest);

    void visit(ConnectionClientRequest messageRequest);

    void visit(MapClientRequest messageRequest);
}
