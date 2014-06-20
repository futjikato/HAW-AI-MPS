package de.haw.mps;

import de.haw.mps.api.ExternalApiManager;
import de.haw.mps.api.Hub;
import de.haw.mps.api.network.TcpServer;
import de.haw.mps.banking.messaging.MessageConsumer;
import de.haw.mps.banking.messaging.MessageService;
import de.haw.mps.fabrication.api.FabricationController;
import de.haw.mps.offer.api.OfferController;
import de.haw.mps.persistence.MpsSessionFactory;
import org.hibernate.Session;

import java.io.IOException;
import java.util.logging.Level;

public class Main {

  public static void main(String[] args) {
      // initialize db setup
      MpsSessionFactory.getcurrentSession();

      // start tcp server
      try {
          TcpServer server = new TcpServer(Integer.valueOf(args[0]));
          server.start();
      } catch (IOException e) {
          MpsLogger.getLogger().severe(e.getMessage());
      }

      // Add Controllers to Api
      Hub.getInstance().addObserver(new FabricationController());
      Hub.getInstance().addObserver(new OfferController());

      // initialize external api manager
      ExternalApiManager externalApiManager = ExternalApiManager.getInstance();

      // add
      MessageService.getInstance().addObserver(externalApiManager);

      // start banking message queue consumer
      try {
          MessageConsumer messageConsumer = new MessageConsumer();
          messageConsumer.start();
      } catch (IOException e) {
          MpsLogger.getLogger().log(Level.WARNING, "Message queue error.", e);
      }
  }

}
