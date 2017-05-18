package org.lejos.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIMenu extends Remote {
    void runProgram(String var1) throws RemoteException;

    void debugProgram(String var1) throws RemoteException;

    void runSample(String var1) throws RemoteException;

    void stopProgram() throws RemoteException;

    boolean deleteFile(String var1) throws RemoteException;

    long getFileSize(String var1) throws RemoteException;

    String[] getProgramNames() throws RemoteException;

    String[] getSampleNames() throws RemoteException;

    boolean uploadFile(String var1, byte[] var2) throws RemoteException;

    byte[] fetchFile(String var1) throws RemoteException;

    String getSetting(String var1) throws RemoteException;

    void setSetting(String var1, String var2) throws RemoteException;

    void deleteAllPrograms() throws RemoteException;

    String getVersion() throws RemoteException;

    String getMenuVersion() throws RemoteException;

    String getName() throws RemoteException;

    void setName(String var1) throws RemoteException;

    void configureWifi(String var1, String var2) throws RemoteException;

    String getExecutingProgramName() throws RemoteException;

    void shutdown() throws RemoteException;

    void suspend() throws RemoteException;

    void resume() throws RemoteException;
}

