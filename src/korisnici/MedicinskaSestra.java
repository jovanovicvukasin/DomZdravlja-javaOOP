package korisnici;

public class MedicinskaSestra extends Korisnik {
	
	private double plata;
	private Sluzba sluzba;
	
	public MedicinskaSestra(double plata, Sluzba sluzba) {
		super();
		this.plata = plata;
		this.sluzba = sluzba;
	}
	
	public MedicinskaSestra(String ime, String prezime, long jmbg, Pol pol, String adresa, long brojTelefona,
			String korisnickoIme, String lozinka, Uloga uloga,double plata, Sluzba sluzba) {
		super(ime, prezime, jmbg, pol, adresa, brojTelefona, korisnickoIme, lozinka, uloga);
		this.plata = plata;
		this.sluzba = sluzba;
		
	}

	public double getPlata() {
		return plata;
	}

	public void setPlata(double plata) {
		this.plata = plata;
	}

	public Sluzba getSluzba() {
		return sluzba;
	}

	public void setSluzba(Sluzba sluzba) {
		this.sluzba = sluzba;
	}

	

	
	
	
	
	

}
