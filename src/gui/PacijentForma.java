package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import korisnici.Knjizica;
import korisnici.KorisnikUtils;
import korisnici.Pacijent;
import net.miginfocom.swing.MigLayout;


public class PacijentForma extends JFrame {
	private JToolBar mainToolBar = new JToolBar();
	private JTable tablePacijenti;
	private Pacijent pacijent;
	private JButton btnIzmeni,btnObrisi,btnDodaj;
	
	
	public PacijentForma() {
		setTitle("Pacijenti");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(900, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		Container c = this.getContentPane();
		c.setBackground(Color.white);
		//KorisnikUtils.ucitavanjeKnjizica();
		initGUI();
	}
	
	private void initGUI() {
		MigLayout layout = new MigLayout("wrap, top, left", "[]", "[]5[]");
		setLayout(layout);
		String[] header = new String[] { "Ime", "Prezime", "JMBG", "Pol", "Adresa", "Br. telefona", "Korisnicko ime",
				"Lozinka", "Uloga", "Lekar", "Knjizica"};
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Image img = kit.getImage("src/slike/slika.png");
		setIconImage(img);
		
		int brojac = KorisnikUtils.getPacijenti().size();
		Object[][] content = new Object[brojac][header.length];
		
		for(int i=0; i<KorisnikUtils.getPacijenti().size(); i++) {
			Pacijent pac = KorisnikUtils.getPacijenti().get(i);
			content[i][0] = pac.getIme();
			content[i][1] = pac.getPrezime();
			content[i][2] = pac.getJmbg();
			content[i][3] = pac.getPol();
			content[i][4] = pac.getAdresa();
			content[i][5] = pac.getBrojTelefona();
			content[i][6] = pac.getKorisnickoIme();
			content[i][7] = pac.getLozinka();
			content[i][8] = pac.getUloga();
			content[i][9] = pac.getLekar();
			content[i][10] = pac.getKnjizica().getBroj();
			
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		tablePacijenti = new JTable(model);
		tablePacijenti.setRowSelectionAllowed(true);
		tablePacijenti.setColumnSelectionAllowed(false);
		tablePacijenti.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tablePacijenti.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tablePacijenti);
		scrollPane.setPreferredSize(new Dimension(900, 400));
		scrollPane.setMaximumSize(new Dimension(900, 400));
		add(scrollPane, BorderLayout.CENTER);
		btnIzmeni = new JButton("Izmeni");
		btnObrisi = new JButton("Obrisi");
		btnDodaj = new JButton("Dodaj");
		add(btnDodaj, "split3, bottom, right");
		add(btnIzmeni);
		add(btnObrisi);
		
		btnDodaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PacijentFunkcionalnosti pf = new PacijentFunkcionalnosti(pacijent);
				pf.setVisible(true);
				dispose();
				
			}
		});
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tablePacijenti.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Selektujte red za izmenu.", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel) tablePacijenti.getModel();
					String korisnickoIme = model.getValueAt(red, 6).toString();
					Pacijent pacijent = KorisnikUtils.nadjiPacijenta(korisnickoIme);
					if(pacijent != null) {
						PacijentFunkcionalnosti pf = new PacijentFunkcionalnosti(pacijent);
						pf.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog pacijenta.", "Greska",
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
				int red = tablePacijenti.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Selektujte red za brisanje.", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					String korisnickoIme = tablePacijenti.getValueAt(red, 6).toString();
					int broj = Integer.parseInt(tablePacijenti.getValueAt(red, 10).toString());
					Pacijent pacijent = KorisnikUtils.nadjiPacijenta(korisnickoIme);
					Knjizica knjizica = KorisnikUtils.nadjiKnjizicu(broj);
					if(pacijent!=null&&knjizica!=null) {
						int izabran = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Potvrdi brisanje pacijenta", JOptionPane.YES_NO_OPTION);
						if(izabran == JOptionPane.YES_OPTION) {
							KorisnikUtils.getPacijenti().remove(pacijent);
							KorisnikUtils.getKnjizice().remove(knjizica);
							DefaultTableModel model = (DefaultTableModel) tablePacijenti.getModel();
							model.removeRow(red);
							KorisnikUtils.snimiPacijente();
							KorisnikUtils.snimiKnjizicu();
						}
					}
				}
				
			}
		});
		
	}

}
