package korisnici;

import java.time.LocalDate;

public class Knjizica {
	private static int ID_generator = 10;
	private int broj;
	private LocalDate datumIsteka;
	private String str_datumIsteka;
	private static int isteklaG = 4;
	private int isteklaD = 10;
	private KategorijaOsiguranja kategorijaOsiguranja;
	
	public Knjizica(KategorijaOsiguranja kategorijaOsiguranja) {
		super();
		broj = ID_generator++;
		datumIsteka = LocalDate.now().plusYears(isteklaG).plusDays(isteklaD);
		this.kategorijaOsiguranja = kategorijaOsiguranja;
	}
	
	public int getBroj() {
		return broj;
	}
	public void setBroj(int broj) {
		this.broj = broj;
	}	

	public LocalDate getDatumIsteka() {
		return datumIsteka;
	}

	public void setDatumIsteka(LocalDate datumIsteka) {
		this.datumIsteka = datumIsteka;
	}

	public String getStr_datumIsteka() {
		return str_datumIsteka;
	}

	public void setStr_datumIsteka(String str_datumIsteka) {
		this.str_datumIsteka = str_datumIsteka;
	}

	public KategorijaOsiguranja getKategorijaOsiguranja() {
		return kategorijaOsiguranja;
	}
	public void setKategorijaOsiguranja(KategorijaOsiguranja kategorijaOsiguranja) {
		this.kategorijaOsiguranja = kategorijaOsiguranja;
	}
	
	
	
}
