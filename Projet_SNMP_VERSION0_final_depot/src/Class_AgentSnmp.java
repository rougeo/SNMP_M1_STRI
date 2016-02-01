import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileReader;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Class_AgentSnmp {

	String nomMachine;
	

	public Class_AgentSnmp(String nomficher) {
		/* on va recuperer nom machine d apres ficher json
		 pour l instant on se limite a une information nom machine qu on peut la recuperer et la modifier */

		JSONParser parser = new JSONParser();

		try {

			Object obj = parser.parse(new FileReader(nomficher));

			JSONObject jsonObject = (JSONObject) obj;

			nomMachine = (String) jsonObject.get("NomMachine");

			System.out.println("nomMachine: " + nomMachine);

		} catch (Exception e) {
			e.printStackTrace();
		}

		Fenetre_Agent f = new Fenetre_Agent();
		f.setVisible(true);
	}

	
	public static void main(String args[]) {

		Class_AgentSnmp a = new Class_AgentSnmp(
				"C:/Users/Sana/workspace_javaSTRI/Projet_SNMP_VERSION0_final_depot/src/ficher_donnee.json");

	}
/////////////////
	public class Fenetre_Agent extends JFrame {
		String nomAgent;
		public Fenetre_Agent() {
			initialisation();
		}

		public void initialisation() {
			/*
			 * a chaque fois on lance l agent on l enregistre de nouveau cote
			 * registry et on lui attribut un nom qui sera sa refernce coté
			 * client
			 */

			this.setTitle("Bievenue sur L'interface de l agent");

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			JPanel panel = new JPanel(new GridLayout(0, 1));
			
			Border border = BorderFactory
					.createTitledBorder("Enregistrement de l agent :");
			
			panel.setBorder(border);

		//	Label label = new Label("Nom de L agent", Label.LEFT);
		//	JTextField testField1 = new JTextField(JTextField.RIGHT);

		//	panel.add(label);
		//	panel.add(testField1);
//
			JButton bouton1 = new JButton("Lancement d agent");
			
			bouton1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//nomAgent = testField1.getText();
					//if (nomAgent.equals("")) {
						//JOptionPane
							//	.showMessageDialog(null,
								//		"veuillez saisir le nom de l agent a enregistrer");
					//} else {
						try {
							nomAgent="sana";
							Operation_Consultation obj = new Operation_Consultation();
							LocateRegistry.createRegistry(1099);
							Naming.rebind(nomAgent, obj);
							System.out.println("Obj declarre" + nomAgent);

					

							String nom_mach = nomMachine;
						

							obj.creerAgent(nomAgent, nom_mach);

							JOptionPane.showMessageDialog(null, "Agent " + nomAgent
									+ "   est bien enregistré avec une reference = " + nomAgent
									+ " , nom machine= " + nom_mach);

							
						} catch (Exception e2) {
							System.out.println("class_Serv : "
									+ e2.getMessage());
							e2.printStackTrace();
							JOptionPane.showMessageDialog(null,
									"Probleme Enregistrement Agent");
						}
						System.out.println(nomAgent);
						
					}
				//}
			});
			panel.add(bouton1, "Center");

			Container contentPane = this.getContentPane();
			contentPane.add(panel, BorderLayout.CENTER);
			this.setSize(600, 300);
			this.setVisible(true);
				}
	}

}
