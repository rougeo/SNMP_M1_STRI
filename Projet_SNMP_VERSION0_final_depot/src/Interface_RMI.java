import java.rmi.RemoteException;




public interface Interface_RMI extends java.rmi.Remote{
	
	public String GetReq_NomMachine(String id) throws RemoteException;
	
public void set_NomMachine(String id, String nom) throws RemoteException;
	

}
