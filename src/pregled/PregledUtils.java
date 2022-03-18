package pregled;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import korisnici.Knjizica;
import korisnici.KorisnikUtils;
import korisnici.Lekar;
import korisnici.Pacijent;

public class PregledUtils {
	
	//DateFormat sourceFormat = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
	private static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
	public static ArrayList<Pregled> pregledi = new ArrayList<Pregled>();

	public static void ucitavanjePregleda() {
		pregledi.clear();
		try {
			File fajl = new File("src/fajlovi/pregledi.txt");
			BufferedReader reader = new BufferedReader(new FileReader(fajl));
			String line;
			while((line=reader.readLine()) != null) {
				String[] deo=line.split("\\|");
				Pregled p = new Pregled();
				p.setId(Integer.parseInt(deo[0]));
				p.setPacijent(KorisnikUtils.nadjiPacijenta(deo[1]));
				p.setLekar(KorisnikUtils.nadjiLekara(deo[2]));
				java.util.Date termin = DATE_TIME_FORMAT.parse(deo[3]);
				p.setTermin(termin);
				p.setSoba(deo[4]);
				p.setOpis(deo[5]);
				p.setStatus(Status.valueOf(deo[6]));
				pregledi.add(p);
			}
			reader.close();
		}catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Ne postoji fajl pregledi.txt", "Pogresno", JOptionPane.WARNING_MESSAGE);
		}
		for(Pregled pre:pregledi) {
			System.out.println(pre);
		}
	}
	
	public static void snimiPregled() {
		try {
			File preglediFajl = new File("src/fajlovi/pregledi.txt");
			String content = "";
			for(Pregled p:pregledi) {
				content += p.getId() + "|" + p.getPacijent() + "|" + p.getLekar() + "|" + DATE_TIME_FORMAT.format(p.getTermin()) + "|" +
						p.getSoba() + "|" + p.getOpis() + "|" + p.getStatus() + "\n";
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(preglediFajl));
			writer.write(content);
			writer.flush();
			writer.close();
		}catch (IOException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Greska prilikom upisa u fajla", "Greska", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	
	
	//za combobox odabir pacijenta za pregled
	public static ArrayList<String> preuzmiKorImePacijenta() throws FileNotFoundException{
		BufferedReader br = new BufferedReader(new FileReader("src/fajlovi/pacijenti.txt"));
		StringBuilder sb = new StringBuilder();
		String line;
		ArrayList<String> pacijenti = new ArrayList<String>();
		try {
			while((line = br.readLine()) != null) {
				String[] pac = line.split("\\|");
				pacijenti.add(pac[6]);
			}
			return pacijenti;
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public static Pregled najiPregled(int id) {
		for(Pregled p:pregledi) {
			if(p.getId() == id) {
				return p;
			}
		}
		return null;
	}
	
	
	
	public static void snimiPregledUListu(Pregled pregled) {
		pregledi.add(pregled);
		snimiPregled();
		
	}	

	public static ArrayList<Pregled> getPregledi() {
		return pregledi;
	}

	public static void setPregledi(ArrayList<Pregled> pregledi) {
		PregledUtils.pregledi = pregledi;
	}
	
	
}
