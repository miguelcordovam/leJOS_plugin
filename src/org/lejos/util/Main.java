package org.lejos.util;

import lejos.remote.ev3.RMIMenu;

import java.io.IOException;
import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Enumeration;

import static java.lang.System.out;
import static org.lejos.util.Discover.isReachable;

public class Main {

   /* public static void main(String[] args) {

        try {

            BrickInfo[] bricks = Discover.discover("10.0.1.1");
//            RMIMenu rmiMenu = (RMIMenu) Naming.lookup("//" + "10.0.1.1" + "/RemoteMenu");

            System.out.println(bricks.length);
            RMIMenu rmiMenu = (RMIMenu) Naming.lookup("//" + bricks[0].getIPAddress() + "/RemoteMenu");

            rmiMenu.runProgram("untitled");


        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    public static void main(String[] args) {

        System.out.println(isReachable("10.0.1.19", 22 ,2000));


    }


}
