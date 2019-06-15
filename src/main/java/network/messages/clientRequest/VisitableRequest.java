package network.messages.clientRequest;

public interface VisitableRequest {

    void accept(VisitorRequest visitor);
}
