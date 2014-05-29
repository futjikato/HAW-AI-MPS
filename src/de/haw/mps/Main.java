package de.haw.mps;

import de.haw.mps.api.TcpServer;
import de.haw.mps.persistence.MpsSessionFactory;
import org.hibernate.Session;

import java.io.IOException;

public class Main {

  public static void main(String[] args) {
      Session factory = MpsSessionFactory.getcurrentSession();

      try {
          TcpServer server = new TcpServer(8089);
          server.start();
      } catch (IOException e) {
          MpsLogger.handleException(e);
      }
  }

}
