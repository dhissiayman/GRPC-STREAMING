package ma.emsi.dhissiayman.projet.clientstreaming;

import io.grpc.stub.StreamObserver;
import stub.SalutationGrpc;
import stub.SalutationOuterClass.SalutRequest;
import stub.SalutationOuterClass.SalutResponse;

public class MessageService extends SalutationGrpc.SalutationImplBase {
    @Override
    public StreamObserver<SalutRequest> message(StreamObserver<SalutResponse> responseObserver) {
        return new StreamObserver<SalutRequest>() {
            StringBuilder names = new StringBuilder();

            @Override
            public void onNext(SalutRequest request) {
                // Traite chaque message envoyé par le client
                names.append(request.getName()).append(", ");
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Erreur reçue : " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                // Envoie une réponse au client une fois que tous les messages sont reçus
                String responseMessage = "Salut à tous : " + names.toString();
                SalutResponse response = SalutResponse.newBuilder()
                        .setMessage(responseMessage)
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }
}