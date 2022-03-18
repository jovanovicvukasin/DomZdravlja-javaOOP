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
import net.miginfocom.swing.MigLayout;

public class LekarForma extends JFrame{
	private JToolBar mainToolBar = new JToolBar();
	private JTable tableLekari;
	private Lekar lekar;
	private JButton btnIzmeni,btnObrisi,btnDodaj;
	
	public LekarForma() {
		setTitle("Lekari");
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
		//add(mainToolBar, BorderLayout.NORTH);
		MigLayout layout = new MigLayout("wrap, top, left", "[]", "[]5[]");
		setLayout(layout);
		String[] header = new String[] { "Ime", "Prezime", "JMBG", "Pol", "Adresa", "Br. telefona", "Korisnicko ime",
				"Lozinka", "Uloga", "Plata", "Specijalizacija", "Sluzba" };
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Image img = kit.getImage("src/slike/slika.png");
		setIconImage(img);
		
		int brojac = KorisnikUtils.getLekari().size();
		Object[][] content = new Object[brojac][header.length];
		
		for(int i=0; i<KorisnikUtils.getLekari().size(); i++) {
			Lekar lek = KorisnikUtils.getLekari().get(i);
			content[i][0] = lek.getIme();
			content[i][1] = lek.getPrezime();
			content[i][2] = lek.getJmbg();
			content[i][3] = lek.getPol();
			content[i][4] = lek.getAdresa();
			content[i][5] = lek.getBrojTelefona();
			content[i][6] = lek.getKorisnickoIme();
			content[i][7] = lek.getLozinka();
			content[i][8] = lek.getUloga();
			content[i][9] = lek.getPlata();
			content[i][10] = lek.getSpecijalizacija();
			content[i][11] = lek.getSluzba();
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		tableLekari = new JTable(model);
		tableLekari.setRowSelectionAllowed(true);
		tableLekari.setColumnSelectionAllowed(false);
		tableLekari.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableLekari.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tableLekari);
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
				LekarFunkcionalnosti lf = new LekarFunkcionalnosti(lekar);
				lf.setVisible(true);
				dispose();
				
			}
		});
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tableLekari.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Selektujte red za izmenu.", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel) tableLekari.getModel();
					String korisnickoIme = model.getValueAt(red, 6).toString();
					Lekar lekar = KorisnikUtils.nadjiLekara(korisnickoIme);
					if(lekar != null) {
						LekarFunkcionalnosti lf = new LekarFunkcionalnosti(lekar);
						lf.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog lekara.", "Greska",
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
				int red = tableLekari.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Selektujte red za brisanje.", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					String korisnickoIme = tableLekari.getValueAt(red, 6).toString();
					Lekar l = KorisnikUtils.nadjiLekara(korisnickoIme);
					if(l != null) {
						int izabran = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Potvrdi brisanje lekara", JOptionPane.YES_NO_OPTION);
						if(izabran == JOptionPane.YES_OPTION) {
							KorisnikUtils.getLekari().remove(l);
							DefaultTableModel model = (DefaultTableModel) tableLekari.getModel();
							model.removeRow(red);
							KorisnikUtils.snimiLekare();
						}
					}
					
				}
				//dispose();
				
			}
		});

	}

}
