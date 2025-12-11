package ma.emsi.dhissiayman.projet.serverstreaming;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

public class MultiplicationServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051)
                .addService(new MultiplicationService())
                .build();
        System.out.println("Serveur gRPC démarré sur le port 50051...");
        server.start();
        server.awaitTermination();
    }
}