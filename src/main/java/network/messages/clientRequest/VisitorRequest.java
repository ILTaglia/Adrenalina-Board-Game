package network.messages.clientRequest;

public interface VisitorRequest {
    void visit(ColorClientRequest messageRequest);

    void visit(ConnectionClientRequest messageRequest);

    void visit(MapClientRequest messageRequest);
}
