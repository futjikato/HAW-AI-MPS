package de.haw.mps;

import de.haw.mps.persistence.MpsSessionFactory;
import org.hibernate.SessionFactory;

public class Main {

  public static void main(String[] args) {
      SessionFactory factory = MpsSessionFactory.createSession();
  }

}
