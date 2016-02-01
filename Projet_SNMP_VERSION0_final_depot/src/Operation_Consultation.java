
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

import javax.swing.JOptionPane;
import javax.swing.text.html.parser.Parser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.JSONWriter; 
import org.json.JSONStringer; 
import org.json.JSONException; 

public class Operation_Consultation extends UnicastRemoteObject implements Interface_RMI {
	
	Hashtable<String, Information_Agent> agents;
	
	
	public Operation_Consultation()  throws RemoteException {
		agents = new Hashtable <String, Information_Agent>();
	}
	
	
	public void creerAgent(String id, String nom ) {
		agents.put(id, new Information_Agent(nom));
	}
	
	public String GetReq_NomMachine (String id){
		Information_Agent inf = agents.get(id);
		return inf.getnom();
	}
	
	
	public void set_NomMachine(String id, String nom_new){
		Object obj;
		
		Information_Agent inf = agents.get(id);
		inf.setnom(nom_new);
		
		JSONParser parser = new JSONParser();
		
		try {
			obj = parser.parse(new FileReader("C:/Users/Sana/workspace_javaSTRI/"
					+ "Projet_SNMP_VERSION0_final_depot/src/ficher_donnee.json"));
			
			JSONObject jsonObject = (JSONObject) obj;
			jsonObject.put("NomMachine", nom_new);
			
			FileWriter fichier = new FileWriter("C:/Users/Sana/workspace_javaSTRI/"
					+ "Projet_SNMP_VERSION0_final_depot/src/ficher_donnee.json");
		       fichier.write (jsonObject.toString());
		fichier.close();
			
			
			
			System.out.println(	jsonObject); 
			
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	/////////////////////////////////////////////////////////
	
	class Information_Agent {
		
		private String nom;

		public Information_Agent(String nom) {
		//	this.adre_IP = adre_IP;
			this.nom=nom;
		}

		public String getnom() {
			return nom;
		}

		public void setnom( String nn) {
			 nom=nn;
		}
	}
	////////////////////////////////////
	
	
	
	

	
	}

	
	
	
	

