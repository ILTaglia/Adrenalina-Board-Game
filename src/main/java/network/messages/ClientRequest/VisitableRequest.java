package network.messages.ClientRequest;

public interface VisitableRequest {

    void accept(VisitorRequest visitor);
}
