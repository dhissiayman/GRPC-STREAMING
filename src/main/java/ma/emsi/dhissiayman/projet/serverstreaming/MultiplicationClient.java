package ma.emsi.dhissiayman.projet.serverstreaming;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import stub.MultiplicationGrpc;
import stub.MultiplicationOuterClass.MultiplicationRequest;
import stub.MultiplicationOuterClass.MultiplicationResponse;

public class MultiplicationClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        MultiplicationGrpc.MultiplicationStub stub = MultiplicationGrpc.newStub(channel);

        MultiplicationRequest request = MultiplicationRequest.newBuilder()
                .setNombre(5)
                .setLimite(10)
                .build();

        stub.getMultiplicationTable(request, new StreamObserver<MultiplicationResponse>() {
            @Override
            public void onNext(MultiplicationResponse response) {
                System.out.println(response.getResultat());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Erreur : " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Streaming terminé.");
            }
        });

        // Garder le client en vie pour recevoir les réponses
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channel.shutdown();
    }
}