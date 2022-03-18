package korisnici;

public class Lekar extends Korisnik {
	
	private double plata;
	private String specijalizacija;
	private Sluzba sluzba;
	
	public Lekar(double plata, String specijalizacija, Sluzba sluzba) {
		super();
		this.plata = plata;
		this.specijalizacija = specijalizacija;
		this.sluzba = sluzba;
	}
	
	public Lekar(String ime, String prezime, long jmbg, Pol pol, String adresa, long brojTelefona,
			String korisnickoIme, String lozinka, Uloga uloga,double plata, String specijalizacija,Sluzba sluzba) {
		super(ime, prezime, jmbg, pol, adresa, brojTelefona, korisnickoIme, lozinka, uloga);
		this.plata = plata;
		this.specijalizacija = specijalizacija;
		this.sluzba = sluzba;
		
	}

	public double getPlata() {
		return plata;
	}

	public void setPlata(double plata) {
		this.plata = plata;
	}

	public String getSpecijalizacija() {
		return specijalizacija;
	}

	public void setSpecijalizacija(String specijalizacija) {
		this.specijalizacija = specijalizacija;
	}

	public Sluzba getSluzba() {
		return sluzba;
	}

	public void setSluzba(Sluzba sluzba) {
		this.sluzba = sluzba;
	}

	@Override
	public String toString() {
		return getKorisnickoIme();
	}

	
	

}
