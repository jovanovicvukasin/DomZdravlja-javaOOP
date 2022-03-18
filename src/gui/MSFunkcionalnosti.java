package gui;

import static java.lang.Character.isDigit;
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
import korisnici.MedicinskaSestra;
import korisnici.Pol;
import korisnici.Sluzba;
import korisnici.Uloga;
import net.miginfocom.swing.MigLayout;

public class MSFunkcionalnosti extends JFrame{

	private JLabel lblIme = new JLabel("Ime:");
	private JLabel lblPrezime = new JLabel("Prezime:");
	private JLabel lblJmbg = new JLabel("JMBG:");
	private JLabel lblPol = new JLabel("Pol:");
	private JLabel lblAdresa = new JLabel("Adresa:");
	private JLabel lblTelefon = new JLabel("Br. telefona:");
	private JLabel lblKorisnickoIme = new JLabel("Korisnicko ime:");
	private JLabel lblLozinka = new JLabel("Lozinka:");
	private JLabel lblPlata = new JLabel("Plata:");
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
	private JComboBox<Sluzba> cmbSluzba = new JComboBox<Sluzba>(Sluzba.values());
	
	private MedicinskaSestra medicinskaSestra;
	private JButton btnOk = new JButton("Potvrdi");
	
	public MSFunkcionalnosti(MedicinskaSestra medicinskaSestra) {
		this.medicinskaSestra = medicinskaSestra;
		if(this.medicinskaSestra == null) {
			setTitle("Dodavanje medicinske sestre");
		}else {
			setTitle("Izmena medicinske sestre: " + this.medicinskaSestra.getKorisnickoIme());
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		initGUI();
		initActions();
		setResizable(false);
		setSize(400,400);
	}
	
	private void initGUI() {
		MigLayout layout = new MigLayout("wrap 2");
		setLayout(layout);
		
		if(this.medicinskaSestra != null) {
			popunjavanjePolja();
		}
		
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
					Uloga uloga = Uloga.MED_SESTRA;
					double plata = Double.parseDouble(txtPlata.getText().trim());
					Sluzba sluzba = (Sluzba) cmbSluzba.getSelectedItem();
					if(medicinskaSestra == null) {
						medicinskaSestra = new MedicinskaSestra(ime, prezime, jmbg, pol, adresa, brojTelefona, korisnickoIme, lozinka, uloga, plata, sluzba);
						KorisnikUtils.getMedicinskeSestre().add(medicinskaSestra);
					}else {
						medicinskaSestra.setIme(ime);
						medicinskaSestra.setPrezime(prezime);
						medicinskaSestra.setJmbg(jmbg);
						medicinskaSestra.setPol(pol);
						medicinskaSestra.setAdresa(adresa);
						medicinskaSestra.setBrojTelefona(brojTelefona);
						medicinskaSestra.setKorisnickoIme(korisnickoIme);
						medicinskaSestra.setLozinka(lozinka);
						medicinskaSestra.setUloga(uloga);
						medicinskaSestra.setPlata(plata);
						medicinskaSestra.setSluzba(sluzba);
						
					}
					KorisnikUtils.snimiMedicinskeSestre();
					MSFunkcionalnosti.this.dispose();
					MSFunkcionalnosti.this.setVisible(false);
				}
				
			}
		});
	}
	
	private void popunjavanjePolja() {
		txtIme.setText(this.medicinskaSestra.getIme());
		txtPrezime.setText(this.medicinskaSestra.getPrezime());
		txtJmbg.setText(String.valueOf(this.medicinskaSestra.getJmbg()));
		cmbPol.setSelectedItem(this.medicinskaSestra.getPol());
		txtAdresa.setText(this.medicinskaSestra.getAdresa());
		txtTelefon.setText(String.valueOf(this.medicinskaSestra.getBrojTelefona()));
		txtKorisnickoIme.setText(this.medicinskaSestra.getKorisnickoIme());
		txtLozinka.setText(this.medicinskaSestra.getLozinka());
		txtPlata.setText(String.valueOf(this.medicinskaSestra.getPlata()));
		cmbSluzba.setSelectedItem(this.medicinskaSestra.getSluzba());
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

		if (ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Greska", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}
}
