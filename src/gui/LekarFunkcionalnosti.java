package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import korisnici.KorisnikUtils;
import korisnici.Lekar;
import korisnici.Pol;
import korisnici.Sluzba;
import korisnici.Uloga;
import net.miginfocom.swing.MigLayout;

public class LekarFunkcionalnosti extends JFrame{
	
	private JLabel lblIme = new JLabel("Ime:");
	private JLabel lblPrezime = new JLabel("Prezime:");
	private JLabel lblJmbg = new JLabel("JMBG:");
	private JLabel lblPol = new JLabel("Pol:");
	private JLabel lblAdresa = new JLabel("Adresa:");
	private JLabel lblTelefon = new JLabel("Br. telefona:");
	private JLabel lblKorisnickoIme = new JLabel("Korisnicko ime:");
	private JLabel lblLozinka = new JLabel("Lozinka:");
	private JLabel lblPlata = new JLabel("Plata:");
	private JLabel lblSpec = new JLabel("Specijalizacija:");
	private JLabel lblSluzba = new JLabel("Sluzba:");

	private JTextField txtIme = new JTextField(10);
	private JTextField txtPrezime = new JTextField(10);
	private JTextField txtJmbg = new JTextField(10);
	private JComboBox<Pol> cmbPol = new JComboBox<Pol>(Pol.values());
	private JTextField txtAdresa = new JTextField(10);
	private JTextField txtTelefon = new JTextField(10);
	private JTextField txtKorisnickoIme = new JTextField(10);
	private JTextField txtLozinka = new JTextField(10);
	private JTextField txtPlata = new JTextField(10);
	private JTextField txtSpecijalizacija = new JTextField(10);
	private JComboBox<Sluzba> cmbSluzba = new JComboBox<Sluzba>(Sluzba.values());
	
	private Lekar lekar;
	private JButton btnOk = new JButton("Potvrdi");
	
	public LekarFunkcionalnosti(Lekar lekar) {
		this.lekar = lekar;
		if(this.lekar == null) {
			setTitle("Dodavanje lekara");
		}else {
			setTitle("Izmena lekara: " + this.lekar.getKorisnickoIme());
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		initGUI();
		initActions();
		setResizable(false);
		setSize(400,400);
		//pack();
	}
	
	private void initGUI() {
		MigLayout layout = new MigLayout("wrap 2");
		setLayout(layout);
		
		if(this.lekar != null) {
			popunjavanjePolja();
		}
		
		cmbSluzba.removeItem(Sluzba.S_ZA_TEHNICKE_POSLOVE);
		cmbSluzba.removeItem(Sluzba.S_ZA_PRAVNE_EKONOMSKE_POSLOVE);
		
		add(lblIme);
		add(txtIme);
		add(lblPrezime);
		add(txtPrezime);
		add(lblJmbg);
		add(txtJmbg);
		add(lblPol);
		add(cmbPol);
		add(lblAdresa);
		add(txtAdresa);
		add(lblTelefon);
		add(txtTelefon);
		add(lblKorisnickoIme);
		add(txtKorisnickoIme);
		add(lblLozinka);
		add(txtLozinka);
		add(lblPlata);
		add(txtPlata);
		add(lblSpec);
		add(txtSpecijalizacija);
		add(lblSluzba);
		add(cmbSluzba);
		add(new JLabel());
		add(btnOk);
	}
	
	private void initActions() {
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(validacija() == true && validacija2() == true) {
					String ime = txtIme.getText().trim();
					String prezime = txtPrezime.getText().trim();
					long jmbg = Long.parseLong(txtJmbg.getText().trim());
					Pol pol = (Pol) cmbPol.getSelectedItem();
					String adresa = txtAdresa.getText().trim();
					long brojTelefona = Long.parseLong(txtTelefon.getText().trim());
					String korisnickoIme = txtKorisnickoIme.getText().trim();
					String lozinka = txtLozinka.getText().trim();
					Uloga uloga = Uloga.LEKAR;
					double plata = Double.parseDouble(txtPlata.getText().trim());
					String specijalizacija = txtSpecijalizacija.getText().trim();
					Sluzba sluzba = (Sluzba) cmbSluzba.getSelectedItem();
					if(lekar == null) {
						lekar = new Lekar(ime, prezime, jmbg, pol, adresa, brojTelefona, korisnickoIme, lozinka, uloga, plata, specijalizacija, sluzba);
						KorisnikUtils.getLekari().add(lekar);
					}else {
						lekar.setIme(ime);
						lekar.setPrezime(prezime);
						lekar.setJmbg(jmbg);
						lekar.setPol(pol);
						lekar.setAdresa(adresa);
						lekar.setBrojTelefona(brojTelefona);
						lekar.setKorisnickoIme(korisnickoIme);
						lekar.setLozinka(lozinka);
						lekar.setUloga(uloga);
						lekar.setPlata(plata);
						lekar.setSpecijalizacija(specijalizacija);
						lekar.setSluzba(sluzba);
						
					}
					KorisnikUtils.snimiLekare();
					LekarFunkcionalnosti.this.dispose();
					LekarFunkcionalnosti.this.setVisible(false);
				}
				
			}
		});
	}
	
	private void popunjavanjePolja() {
		txtIme.setText(this.lekar.getIme());
		txtPrezime.setText(this.lekar.getPrezime());
		txtJmbg.setText(String.valueOf(this.lekar.getJmbg()));
		cmbPol.setSelectedItem(this.lekar.getPol());
		txtAdresa.setText(this.lekar.getAdresa());
		txtTelefon.setText(String.valueOf(this.lekar.getBrojTelefona()));
		txtKorisnickoIme.setText(this.lekar.getKorisnickoIme());
		txtLozinka.setText(this.lekar.getLozinka());
		txtPlata.setText(String.valueOf(this.lekar.getPlata()));
		txtSpecijalizacija.setText(this.lekar.getSpecijalizacija());
		cmbSluzba.setSelectedItem(this.lekar.getSluzba());
	}
	
	private boolean validacija2() {
		boolean ok = true;
		String poruka = "Neispravan unos:\n";
		if(txtJmbg.getText().chars().allMatch( Character::isDigit )!=true){
			poruka += "Polje JMBG mora biti ceo broj!";
			ok = false;
		}
		if(txtTelefon.getText().chars().allMatch( Character::isDigit )!=true) {
			poruka += "Br. telefona podrzava brojeve!\n";
			ok = false;
		}
		if(txtPlata.getText().chars().allMatch( Character::isDigit )!=true){
			poruka += "Polje plata podrzava brojeve!\n";
			ok = false;
		}
		if(txtJmbg.getText().chars().count()!=13){
			poruka += "JMBG mora imati 13 cifara!\n";
			ok = false;
		}
		if (ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Greska", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
		
	}	
	
	private boolean validacija() {
		boolean ok = true;
		String poruka = "Sva polja moraju biti popunjena:\n";
		if (txtIme.getText().trim().equals("")) {
			poruka += "Ime;\n";
			ok = false;
		}
		if (txtPrezime.getText().trim().equals("")) {
			poruka += "Prezime;\n";
			ok = false;
		}
		if (txtKorisnickoIme.getText().trim().equals("")) {
			poruka += "Korisnicko ime;\n";
			ok = false;
		}
		
		if (txtLozinka.getText().trim().equals("")) {
			poruka += "Lozinka;\n";
			ok = false;
		}
		if (txtPlata.getText().trim().equals("")) {
			poruka += "Plata;\n";
			ok = false;
		}

		if (txtJmbg.getText().trim().equals("")) {
			poruka += "JMBG;\n";
			ok = false;
		}
		if (txtAdresa.getText().trim().equals("")) {
			poruka += "Adresa;\n";
			ok = false;
		}
		if (txtTelefon.getText().trim().equals("")) {
			poruka += "Broj telefona;\n";
			ok = false;
		}
		if (txtSpecijalizacija.getText().trim().equals("")) {
			poruka += "Specijalizacija\n";
			ok = false;
		}

		if (ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Greska", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}

}
