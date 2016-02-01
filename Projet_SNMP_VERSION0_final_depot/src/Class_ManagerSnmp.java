import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Class_ManagerSnmp {
	/*
	 * le manager demande le nom de l agent a contacter et sur quel reseau : la
	 * on a 3 cas : connection possible objet trouve connection non posible
	 * objet serveur non creer champs saisis vide
	 */

	Interface_RMI obj;
	String nom;
	 String res;
	public Class_ManagerSnmp() {

		Fenetre_Manager f = new Fenetre_Manager();
		f.setVisible(true);
	}

	// classe Manager
	public static void main(String args[]) {

		Class_ManagerSnmp c = new Class_ManagerSnmp();

	}
	////////////////////////////////////////////////////////////
	
	public class Fenetre_Manager extends JFrame {
		public Fenetre_Manager() {
			initialisation();
		}

		public void initialisation() {

			this.setTitle("Bievenue sur L'interface du Manager");

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel panel = new JPanel(new GridLayout(0, 1));
			Border border = BorderFactory
					.createTitledBorder("Saisir le Nom de l'agent à contacter :");
			panel.setBorder(border);

			Label label = new Label("Nom de L agent");
			JTextField testField1 = new JTextField("");

			panel.add(label);
			panel.add(testField1);
			
			Label label_res = new Label("Adresse Reseau");
			JTextField testField1_res = new JTextField("127.0.0.1");

			panel.add(label_res);
			panel.add(testField1_res);

			JButton bouton1 = new JButton("valider");
			bouton1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					nom = testField1.getText();
					  res =testField1_res.getText();
					if (nom.equals("") || (res.equals(""))) {
						JOptionPane
								.showMessageDialog(null,
										"veuillez saisir le nom de l agent a contacter");
					} else {
						try {
							obj = (Interface_RMI) Naming
									.lookup("rmi://"+res+"/" + nom);

							JOptionPane.showMessageDialog(null,
									"Connection Manager_Agent");
							setVisible(false);
							Choix_OP c = new Choix_OP();
							c.setVisible(true);

						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null,
									"Objet Serveur non trouvable");

						}
						//System.out.println(nom);
					}
				}
			});
			panel.add(bouton1, "Center");

			Container contentPane = this.getContentPane();
			contentPane.add(panel, BorderLayout.CENTER);
			this.setSize(600, 300);
			// this.setVisible(true);
		}
	}

	
	
	
	//////////////////////////////////////////////////////////////

	public class Choix_OP extends JFrame implements ActionListener,ItemListener {
		
		public Choix_OP() {
			init();
		}

		public void init() {

			this.setTitle("Choix Operations :");

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel panel = new JPanel(new GridLayout(0, 1));
			Border border = BorderFactory
					.createTitledBorder("Sélection du choix");
			panel.setBorder(border);
			ButtonGroup group = new ButtonGroup();

			JRadioButton radio1 = new JRadioButton("Recuperer nom machine");
			radio1.setActionCommand("Nom_recuperation");

			JRadioButton radio2 = new JRadioButton("Modification nom machine");
			radio2.setActionCommand("Nom_modification");

			group.add(radio1);
			panel.add(radio1);
			group.add(radio2);
			panel.add(radio2);
			

			radio1.addActionListener(this);
			radio2.addActionListener(this);

			radio1.addItemListener(this);
			radio2.addItemListener(this);
		

			radio1.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					System.out.println("Clic sur le bouton : "
							+ e.getActionCommand());
					try {
						JOptionPane.showMessageDialog(null,
								e.getActionCommand() + "de l agent " + nom
										+ ": " + obj.GetReq_NomMachine(nom));
					} catch (Exception e1) {
					}

				}
			});

			radio2.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					System.out.println("Clic sur le bouton : "
							+ e.getActionCommand());
					try {
						Fenetre_Manager_modifi f1 = new Fenetre_Manager_modifi(
								1);
						f1.setVisible(true);

						JOptionPane.showMessageDialog(null,
								e.getActionCommand() + "de l agent " + nom
										+ " ");
					} catch (Exception e1) {
					}

				}
			});


			Container contentPane = this.getContentPane();
			contentPane.add(panel, BorderLayout.CENTER);
			this.setSize(600, 300);
			this.setVisible(true);
		}



		@Override
		public void itemStateChanged(ItemEvent e) {
	
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}

	//////////////////////////////////////////////////////////
	
	public class Fenetre_Manager_modifi extends JFrame {
		String valeur_new;

		public Fenetre_Manager_modifi(int i) {
			initialisation_modif(i);
		}

		public void initialisation_modif(int i) {

			this.setTitle("Modification donnees Agents");

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel panel = new JPanel(new GridLayout(0, 1));
			Border border = BorderFactory
					.createTitledBorder("Saisir la nouvelle valeur :");
			panel.setBorder(border);

			Label label = new Label("la nouvelle valeur");
			JTextField testField1 = new JTextField("");

			panel.add(label);
			panel.add(testField1);

			JButton bouton1 = new JButton("valider");
			bouton1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					valeur_new = testField1.getText();
					if (valeur_new.equals("")) {
						JOptionPane
								.showMessageDialog(null,
										" champs vide:veuillez saisir la nouvelle valeur");
					} else {
						try {
							switch (i) {
							case 1:
								obj.set_NomMachine(nom, valeur_new);
								break;
							case 2:
								// / on a prevu ca car il se peut on ajoute d autre parametre a modifier 
								break;
							default:
								break;
							}

						} catch (RemoteException e1) {
							
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null,
								"nouvelle valeur enregistrer");

					}
					//System.out.println(nom);
				}
			});
			panel.add(bouton1, "Center");

			Container contentPane = this.getContentPane();
			contentPane.add(panel, BorderLayout.CENTER);
			this.setSize(600, 300);
			
		}
	}


}