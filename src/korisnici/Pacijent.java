package korisnici;

public class Pacijent extends Korisnik{
	
	private Lekar lekar;
	private Knjizica knjizica;

	
	public Pacijent(Lekar lekar, Knjizica knjizica) {
		super();
		this.lekar = lekar;
		this.knjizica = knjizica;
		

	}
	
	public Pacijent(String ime, String prezime, long jmbg, Pol pol, String adresa, long brojTelefona,
			String korisnickoIme, String lozinka, Uloga uloga, Lekar lekar, Knjizica knjizica) {
		super(ime, prezime, jmbg, pol, adresa, brojTelefona, korisnickoIme, lozinka, uloga);
		this.lekar = lekar;
		this.knjizica = knjizica;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}
	
	

	public Knjizica getKnjizica() {
		return knjizica;
	}

	public void setKnjizica(Knjizica knjizica) {
		this.knjizica = knjizica;
	}

	@Override
	public String toString() {
		return getKorisnickoIme();
	}

	
	

}
