package de.haw.mps;

import de.haw.mps.persistence.MpsSessionFactory;
import org.hibernate.Session;

public class Main {

  public static void main(String[] args) {
      Session factory = MpsSessionFactory.getcurrentSession();
  }

}
