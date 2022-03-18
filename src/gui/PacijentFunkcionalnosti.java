package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.TableView.TableRow;

import korisnici.KategorijaOsiguranja;
import korisnici.Knjizica;
import korisnici.KorisnikUtils;
import korisnici.Lekar;
import korisnici.Pacijent;
import korisnici.Pol;
import korisnici.Uloga;
import net.miginfocom.swing.MigLayout;

public class PacijentFunkcionalnosti extends JFrame{
	
	private Lekar lekar;
	
	private JLabel lblIme = new JLabel("Ime:");
	private JLabel lblPrezime = new JLabel("Prezime:");
	private JLabel lblJmbg = new JLabel("JMBG:");
	private JLabel lblPol = new JLabel("Pol:");
	private JLabel lblAdresa = new JLabel("Adresa:");
	private JLabel lblTelefon = new JLabel("Br. telefona:");
	private JLabel lblKorisnickoIme = new JLabel("Korisnicko ime:");
	private JLabel lblLozinka = new JLabel("Lozinka:");
	private JLabel lblLekar = new JLabel("Izabrani lekar:");
	//private JLabel LblK = new JLabel("DODAVANJE KNJIZICE ZA NOVOG PACIJENTA:");
	private JLabel lblKategorijaOsiguranja = new JLabel("Kategorija osiguranja:");
	//private JLabel lblNoviPacijent = new JLabel("DODAVANJE NOVOG PACIJENTA:");
	
	private JTextField txtIme = new JTextField(10);
	private JTextField txtPrezime = new JTextField(10);
	private JTextField txtJmbg = new JTextField(10);
	private JComboBox<Pol> cmbPol = new JComboBox<Pol>(Pol.values());
	private JTextField txtAdresa = new JTextField(10);
	private JTextField txtTelefon = new JTextField(10);
	private JTextField txtKorisnickoIme = new JTextField(10);
	private JTextField txtLozinka = new JTextField(10);
	private JComboBox cmbLekari;
	private JComboBox<KategorijaOsiguranja> cmbKategorijaOsiguranja = new JComboBox<KategorijaOsiguranja>(KategorijaOsiguranja.values());
	
	private Pacijent pacijent;
	private Knjizica knjizica;
	private JButton btnOk = new JButton("Potvrdi");
	private JButton btnOK1 = new JButton("Potvrdi izmenu");
	
	public PacijentFunkcionalnosti(Pacijent pacijent) {
		this.pacijent = pacijent;
		if(this.pacijent == null) {
			setTitle("Dodavanje pacijenta");
			btnOK1.hide();
		}else {
			setTitle("Izmena pacijenta: " + this.pacijent.getKorisnickoIme());
			cmbKategorijaOsiguranja.hide();
			lblKategorijaOsiguranja.hide();
			btnOk.hide();
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
		
		if(this.pacijent != null) {
			popunjavanjePolja();
		}
		
		//ZA COMBOBOX IZABRANOG LEKARA PACIJENTA
		ArrayList<String> lekari = new ArrayList<String>();
		try {
			lekari = KorisnikUtils.preuzmiKorImeLekara();
		}catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		this.cmbLekari = new JComboBox(lekari.toArray());
		cmbLekari.setSelectedIndex(0);
		
		//add(LblK);
		//add(new JLabel());
		add(lblKategorijaOsiguranja);
		add(cmbKategorijaOsiguranja);
		//add(new JLabel());
		//add(btnOK1);
		//add(new JLabel());
		//add(new JLabel());
		//add(lblNoviPacijent);
		//add(new JLabel());
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
		add(lblLekar);
		add(cmbLekari);
		add(new JLabel());
		add(btnOk);
		add(btnOK1);
	}
	
	private void initActions() {
		//KorisnikUtils.ucitavanjeKnjizica();
		/*btnOK1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				KategorijaOsiguranja kategorijaOsiguranja = (KategorijaOsiguranja) cmbKategorijaOsiguranja.getSelectedItem();
				if(knjizica == null) {
					knjizica = new Knjizica(kategorijaOsiguranja);
					KorisnikUtils.getKnjizice().add(knjizica);
				}else {
					knjizica.setKategorijaOsiguranja(kategorijaOsiguranja);
				}
				KorisnikUtils.snimiKnjizicu();
			}
		});*/
		
		//Dugme za Izmenu pacijenta
		btnOK1.addActionListener(new ActionListener() {
			
			
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
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
					Uloga uloga = Uloga.PACIJENT;
					Lekar lekar = KorisnikUtils.nadjiLekara(cmbLekari.getSelectedItem().toString());//(Lekar) cmbLekari.getSelectedItem();
					pacijent.setIme(ime);
					pacijent.setPrezime(prezime);
					pacijent.setJmbg(jmbg);
					pacijent.setPol(pol);
					pacijent.setAdresa(adresa);
					pacijent.setBrojTelefona(brojTelefona);
					pacijent.setKorisnickoIme(korisnickoIme);
					pacijent.setLozinka(lozinka);
					pacijent.setUloga(uloga);
					pacijent.setLekar(lekar);
					KorisnikUtils.snimiPacijente();
					PacijentFunkcionalnosti.this.dispose();
					PacijentFunkcionalnosti.this.setVisible(false);
				}
			}
		});
		
		//Dugme za dodavanje  NOVOG pacijenta 
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(validacija() == true && validacija2() == true) {
					KategorijaOsiguranja kategorijaOsiguranja = (KategorijaOsiguranja) cmbKategorijaOsiguranja.getSelectedItem();
					if(knjizica == null) {
						knjizica = new Knjizica(kategorijaOsiguranja);
						KorisnikUtils.getKnjizice().add(knjizica);
					}else {
						knjizica.setKategorijaOsiguranja(kategorijaOsiguranja);
					}
					KorisnikUtils.snimiKnjizicu();
					String ime = txtIme.getText().trim();
					String prezime = txtPrezime.getText().trim();
					long jmbg = Long.parseLong(txtJmbg.getText().trim());
					Pol pol = (Pol) cmbPol.getSelectedItem();
					String adresa = txtAdresa.getText().trim();
					long brojTelefona = Long.parseLong(txtTelefon.getText().trim());
					String korisnickoIme = txtKorisnickoIme.getText().trim();
					String lozinka = txtLozinka.getText().trim();
					Uloga uloga = Uloga.PACIJENT;
					Lekar lekar = KorisnikUtils.nadjiLekara(cmbLekari.getSelectedItem().toString());//(Lekar) cmbLekari.getSelectedItem();
					if(pacijent == null) {
						pacijent = new Pacijent(ime, prezime, jmbg, pol, adresa, brojTelefona, korisnickoIme, lozinka, uloga, lekar, knjizica);
						KorisnikUtils.getPacijenti().add(pacijent);
					}
					KorisnikUtils.snimiPacijente();
					PacijentFunkcionalnosti.this.dispose();
					PacijentFunkcionalnosti.this.setVisible(false);
				}
				
				
			}
		});
	}
	
	private void popunjavanjePolja() {
		txtIme.setText(this.pacijent.getIme());
		txtPrezime.setText(this.pacijent.getPrezime());
		txtJmbg.setText(String.valueOf(this.pacijent.getJmbg()));
		cmbPol.setSelectedItem(this.pacijent.getPol());
		txtAdresa.setText(this.pacijent.getAdresa());
		txtTelefon.setText(String.valueOf(this.pacijent.getBrojTelefona()));
		txtKorisnickoIme.setText(this.pacijent.getKorisnickoIme());
		txtLozinka.setText(this.pacijent.getLozinka());
		//cmbLekari.setSelectedItem(String.valueOf(this.pacijent.getLekar()));
	}
	
	/*private boolean validacija2() {
		boolean ok = true;
		boolean isNumericJMBG = txtJmbg.getText().chars().allMatch( Character::isDigit );
		boolean isNumericTelefon = txtJmbg.getText().chars().allMatch(Character::isDigit);
		String poruka = "Neispravan unos podataka:\n";
		if(isNumericJMBG!=true) {
			poruka += "JMBG mora biti broj;\n";
			ok = false;
		}
		if(isNumericTelefon != true) {
			poruka += "Br. telefona mora biti broj;\n";
			ok = false;
		}
		if(txtJmbg.getText().chars().count()!=13) {
			poruka += "JMBG mora imati 13 brojeva;\n";
		}
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Greska", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}*/
	
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
