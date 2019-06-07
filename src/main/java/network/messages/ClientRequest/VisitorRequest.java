package network.messages.ClientRequest;

public interface VisitorRequest {
    void visit(ColorClientRequest messageRequest);

    void visit(ConnectionClientRequest messageRequest);

    void visit(MapClientRequest messageRequest);
}
