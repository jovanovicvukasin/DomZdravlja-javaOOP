package korisnici;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class KorisnikUtils {
	public static ArrayList<MedicinskaSestra> medicinskeSestre = new ArrayList<MedicinskaSestra>();
	public static ArrayList<Lekar> lekari = new ArrayList<Lekar>();
	public static ArrayList<Pacijent> pacijenti = new ArrayList<Pacijent>();
	public static ArrayList<Korisnik> sviKorisnici = new ArrayList<Korisnik>();
	public static ArrayList<Knjizica> knjizice = new ArrayList<Knjizica>();

	public static void ucitavanjeMedicinskihS() {
		medicinskeSestre.clear();
		try {
			File fajl = new File("src/fajlovi/medicinskaSestra.txt");
			BufferedReader reader = new BufferedReader(new FileReader(fajl));
			String line;
			while((line=reader.readLine()) != null) {
				String[] deo=line.split("\\|");
				String ime = deo[0];
				String prezime=deo[1];
				long jmbg=Long.parseLong(deo[2]);
				Pol pol = Pol.valueOf(deo[3]);
				String adresa = deo[4];
				long brojTelefona = Long.parseLong(deo[5]);
				String korisnickoIme = deo[6];
				String lozinka = deo[7];
				Uloga uloga = Uloga.valueOf(deo[8]);
				double plata = Double.parseDouble(deo[9]);
				Sluzba sluzba = Sluzba.valueOf(deo[10]);
				MedicinskaSestra ms = new MedicinskaSestra(ime, prezime, jmbg, pol, adresa, brojTelefona, korisnickoIme, lozinka, uloga, plata, sluzba);
				medicinskeSestre.add(ms);
			}
			reader.close();
		}catch(IOException e) {
			System.out.println("Greska prilikom citanja korisnika");
		}
		for (MedicinskaSestra ms:medicinskeSestre) {
			System.out.println(ms);
		}

		
	}
	
	public static void ucitavanjeLekara() {
		lekari.clear();
		try {
			File fajl = new File("src/fajlovi/lekari.txt");
			BufferedReader reader = new BufferedReader(new FileReader(fajl));
			String line;
			while((line=reader.readLine()) != null) {
				String[] deo=line.split("\\|");
				String ime = deo[0];
				String prezime=deo[1];
				long jmbg=Long.parseLong(deo[2]);
				Pol pol = Pol.valueOf(deo[3]);
				String adresa = deo[4];
				long brojTelefona = Long.parseLong(deo[5]);
				String korisnickoIme = deo[6];
				String lozinka = deo[7];
				Uloga uloga = Uloga.valueOf(deo[8]);
				double plata = Double.parseDouble(deo[9]);
				String specijalizacija = deo[10];
				Sluzba sluzba = Sluzba.valueOf(deo[11]);
				Lekar lek = new Lekar(ime, prezime, jmbg, pol, adresa, brojTelefona, korisnickoIme, lozinka, uloga, plata, specijalizacija,sluzba);
				lekari.add(lek);
			}
			reader.close();
		}catch(IOException e) {
			System.out.println("Greska");
		}
		for(Lekar lek:lekari) {
			System.out.println(lek);
		}
	
	}
	
	public static void ucitavanjePacijenata() {
		pacijenti.clear();
		ucitavanjeKnjizica();
		try {
			File fajl = new File("src/fajlovi/pacijenti.txt");
			BufferedReader reader = new BufferedReader(new FileReader(fajl));
			String line;
			while((line=reader.readLine()) != null) {
				String[] deo=line.split("\\|");
				String ime = deo[0];
				String prezime= deo[1];
				long jmbg=Long.parseLong(deo[2]);
				Pol pol = Pol.valueOf(deo[3]);
				String adresa = deo[4];
				long brojTelefona = Long.parseLong(deo[5]);
				String korisnickoIme = deo[6];
				String lozinka = deo[7];
				Uloga uloga = Uloga.valueOf(deo[8]);
				Lekar lekar = nadjiLekara(deo[9]);
				//int TipInt = Integer.parseInt(deo[10]);
				//Knjizica knjizica = nadjiKnjizicu(TipInt);
				Knjizica knjizica = nadjiKnjizicu(Integer.parseInt(deo[10]));
				Pacijent pac = new Pacijent(ime, prezime, jmbg, pol, adresa, brojTelefona, korisnickoIme, lozinka, uloga, lekar, knjizica);
				pacijenti.add(pac);
			}
			reader.close();
		}catch(IOException e) {
			System.out.println("Greska");
		}
		for(Pacijent pac:pacijenti) {
			System.out.println(pac);
		}
	}
	
	public static void ucitavanjeKnjizica() {
		knjizice.clear();
		try {
			File fajl = new File("src/fajlovi/knjizice.txt");
			BufferedReader reader = new BufferedReader(new FileReader(fajl));
			String line;
			while((line=reader.readLine()) != null) {
				String[] deo=line.split("\\|");
				int broj = Integer.parseInt(deo[0]);
				String str_datumIsteka = deo[1];
				KategorijaOsiguranja kategorijaOsiguranja = KategorijaOsiguranja.valueOf(deo[2]);
				Knjizica knj = new Knjizica(kategorijaOsiguranja);
				knjizice.add(knj);
			}
			reader.close();
		}catch (IOException e) {
			// TODO: handle exception
			System.out.println("Greska");
		}
		for(Knjizica knj:knjizice) {
			System.out.println(knj);
		}
	}
	
	public static void ucitavanjeKorisnika() {
		ucitavanjeMedicinskihS();
		ucitavanjeLekara();
		ucitavanjePacijenata();
		
		for(MedicinskaSestra ms:medicinskeSestre) {
			sviKorisnici.add(ms);
		}
		
		for (Lekar lek:lekari) {
			sviKorisnici.add(lek);
		}
		
		for(Pacijent p:pacijenti) {
			sviKorisnici.add(p);
		}
		
	}
	
	//SNIMANJE KORISNIKA
	public static void snimiLekare() {
		try {
			File lekariFajl = new File("src/fajlovi/lekari.txt");
			String content = "";
			for (Lekar l:lekari) {
				content += l.getIme() + "|" + l.getPrezime() + "|" + l.getJmbg() + "|" + l.getPol() + "|" + l.getAdresa() + "|" +
			l.getBrojTelefona() + "|" + l.getKorisnickoIme() + "|" + l.getLozinka() + "|" + l.getUloga() + "|" +
						l.getPlata() + "|" + l.getSpecijalizacija() + "|" + l.getSluzba() + "\n";
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(lekariFajl));
			writer.write(content);
			writer.flush();
			writer.close();
		}catch (IOException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Greska prilikom upisa u fajla", "Greska", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public static void snimiMedicinskeSestre() {
		try {
			File msFajl = new File("src/fajlovi/medicinskaSestra.txt");
			String content = "";
			for(MedicinskaSestra ms:medicinskeSestre) {
				content += ms.getIme() + "|" + ms.getPrezime() + "|" + ms.getJmbg() + "|" + ms.getPol() + "|" + ms.getAdresa() + "|" +
						ms.getBrojTelefona() + "|" + ms.getKorisnickoIme() + "|" + ms.getLozinka() + "|" + ms.getUloga() + "|" +
						ms.getPlata() + "|" + ms.getSluzba() + "\n";
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(msFajl));
			writer.write(content);
			writer.flush();
			writer.close();
		}catch (IOException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Greska prilikom upisa u fajla", "Greska", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public static void snimiPacijente() {
		try {
			File pFajl = new File("src/fajlovi/pacijenti.txt");
			String content = "";
			for(Pacijent p:pacijenti) {
				content += p.getIme() + "|" + p.getPrezime() + "|" + p.getJmbg() + "|" + p.getPol() + "|" + p.getAdresa() + "|" +
						p.getBrojTelefona() + "|" + p.getKorisnickoIme() + "|" + p.getLozinka() + "|" + p.getUloga() + "|" +
						p.getLekar() + "|" + p.getKnjizica().getBroj() + "\n";
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(pFajl));
			writer.write(content);
			writer.flush();
			writer.close();
		}catch (IOException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Greska prilikom upisa u fajla", "Greska", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public static void snimiKnjizicu() {
		try {
			File kFajl = new File("src/fajlovi/knjizice.txt");
			String content = "";
			for(Knjizica k:knjizice) {
				content += k.getBroj() + "|" + k.getDatumIsteka() + "|" + k.getKategorijaOsiguranja() + "\n";
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(kFajl));
			writer.write(content);
			writer.flush();
			writer.close();
		}catch (IOException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Greska prilikom upisa u fajla", "Greska", JOptionPane.WARNING_MESSAGE);

		}
	}
	
	
	//NADJI KORISNIKE ZA IZMENU i BRISANJE
	public static Lekar nadjiLekara(String korisnickoIme) {
		for(Lekar l:lekari) {
			if(l.getKorisnickoIme().equals(korisnickoIme)) {
				return l;
			}
		}
		return null;
	}
	
	public static MedicinskaSestra nadjiMS(String korisnickoIme) {
		for(MedicinskaSestra ms:medicinskeSestre) {
			if(ms.getKorisnickoIme().equals(korisnickoIme)) {
				return ms;
			}
		}
		return null;
	}
	
	
	public static Pacijent nadjiPacijenta(String korisnickoIme) {
		for(Pacijent p:pacijenti) {
			if(p.getKorisnickoIme().equals(korisnickoIme)) {
				return p;
			}
		}
		return null;
	}
	
	public static Knjizica nadjiKnjizicu(int broj) {
		for(Knjizica k:knjizice) {
			if(k.getBroj() == broj) {
				return k;
			}
		}
		return null;
	}
	
	
	//Za combobox
	public static ArrayList<String> preuzmiKorImeLekara() throws FileNotFoundException{
		BufferedReader br = new BufferedReader(new FileReader("src/fajlovi/lekari.txt"));
		StringBuilder sb = new StringBuilder();
		String line;
		ArrayList<String> lekari = new ArrayList<String>();
		try {
			while((line = br.readLine()) != null) {
				String[] kor = line.split("\\|");
				lekari.add(kor[6]);
			}
			return lekari;
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static ArrayList<MedicinskaSestra> getMedicinskeSestre() {
		return medicinskeSestre;
	}
	public static void setMedicinskeSestre(ArrayList<MedicinskaSestra> medicinskeSestre) {
		KorisnikUtils.medicinskeSestre = medicinskeSestre;
	}
	public static ArrayList<Lekar> getLekari() {
		return lekari;
	}
	public static void setLekari(ArrayList<Lekar> lekari) {
		KorisnikUtils.lekari = lekari;
	}
	public static ArrayList<Pacijent> getPacijenti() {
		return pacijenti;
	}
	public static void setPacijenti(ArrayList<Pacijent> pacijenti) {
		KorisnikUtils.pacijenti = pacijenti;
	}
	public static ArrayList<Korisnik> getSviKorisnici() {
		return sviKorisnici;
	}
	public static void setSviKorisnici(ArrayList<Korisnik> sviKorisnici) {
		KorisnikUtils.sviKorisnici = sviKorisnici;
	}
	public static ArrayList<Knjizica> getKnjizice() {
		return knjizice;
	}

	public static void setKnjizice(ArrayList<Knjizica> knjizice) {
		KorisnikUtils.knjizice = knjizice;
	}
	
	
	
}
