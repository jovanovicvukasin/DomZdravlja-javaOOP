package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import korisnici.KorisnikUtils;
import korisnici.Lekar;
import korisnici.MedicinskaSestra;
import net.miginfocom.swing.MigLayout;

public class MSForma extends JFrame {
	private JToolBar mainToolBar = new JToolBar();
	private JTable tableMS;
	private MedicinskaSestra medicinskaSestra;
	private JButton btnIzmeni,btnObrisi,btnDodaj;
	
	public MSForma() {
		setTitle("Medicinske sestre");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(900, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		Container c = this.getContentPane();
		c.setBackground(Color.white);
		initGUI();
		
	}
	
	private void initGUI() {
		MigLayout layout = new MigLayout("wrap, top, left", "[]", "[]5[]");
		setLayout(layout);
		String[] header = new String[] { "Ime", "Prezime", "JMBG", "Pol", "Adresa", "Br. telefona", "Korisnicko ime",
				"Lozinka", "Uloga", "Plata", "Sluzba" };
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Image img = kit.getImage("src/slike/slika.png");
		setIconImage(img);
		
		int brojac = KorisnikUtils.getMedicinskeSestre().size();
		Object[][] content = new Object[brojac][header.length];
		
		for(int i=0; i<KorisnikUtils.getMedicinskeSestre().size(); i++) {
			MedicinskaSestra ms = KorisnikUtils.getMedicinskeSestre().get(i);
			content[i][0] = ms.getIme();
			content[i][1] = ms.getPrezime();
			content[i][2] = ms.getJmbg();
			content[i][3] = ms.getPol();
			content[i][4] = ms.getAdresa();
			content[i][5] = ms.getBrojTelefona();
			content[i][6] = ms.getKorisnickoIme();
			content[i][7] = ms.getLozinka();
			content[i][8] = ms.getUloga();
			content[i][9] = ms.getPlata();
			content[i][10] = ms.getSluzba();
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		tableMS = new JTable(model);
		tableMS.setRowSelectionAllowed(true);
		tableMS.setColumnSelectionAllowed(false);
		tableMS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableMS.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tableMS);
		scrollPane.setPreferredSize(new Dimension(900, 400));
		scrollPane.setMaximumSize(new Dimension(900, 400));
		add(scrollPane, BorderLayout.CENTER);
		btnIzmeni = new JButton("Izmeni");
		btnObrisi = new JButton("Obrisi");
		btnDodaj = new JButton("Dodaj");
		add(btnDodaj, "split 3, bottom, right");
		add(btnIzmeni);
		add(btnObrisi);
		
		btnDodaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MSFunkcionalnosti msf = new MSFunkcionalnosti(medicinskaSestra);
				msf.setVisible(true);
				dispose();
			}
		});
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tableMS.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Selektujte red za izmenu.", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel) tableMS.getModel();
					String korisnickoIme = model.getValueAt(red, 6).toString();
					MedicinskaSestra medicinskaSestra = KorisnikUtils.nadjiMS(korisnickoIme);
					if(medicinskaSestra != null) {
						MSFunkcionalnosti msf = new MSFunkcionalnosti(medicinskaSestra);
						msf.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranu med. sestru.", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				//dispose();
			}
		});
		
		btnObrisi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tableMS.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Selektujte red za brisanje.", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					String korisnickoIme = tableMS.getValueAt(red, 6).toString();
					MedicinskaSestra ms = KorisnikUtils.nadjiMS(korisnickoIme);
					if(ms != null) {
						int izabrana = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Potvrdi brisanje med. sestre", JOptionPane.YES_NO_OPTION);
						if(izabrana == JOptionPane.YES_OPTION) {
							KorisnikUtils.getMedicinskeSestre().remove(ms);
							DefaultTableModel model = (DefaultTableModel) tableMS.getModel();
							model.removeRow(red);
							KorisnikUtils.snimiMedicinskeSestre();
						}
					}
					
				}
				
			}
		});
	}

}
