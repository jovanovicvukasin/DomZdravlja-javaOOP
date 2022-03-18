package pregled;

import java.time.LocalDateTime;
import java.util.Date;

import korisnici.Lekar;
import korisnici.Pacijent;

public class Pregled {
	//private static int ID_generator = 10;
	private int id;
	private Pacijent pacijent;
	private Lekar lekar;
	private java.util.Date termin;
	private String str_termin;
	private String soba;
	private String opis;
	private Status status;
	
	public Pregled() {
		super();
	}

	public Pregled(int id,Pacijent pacijent, Lekar lekar, java.util.Date termin, String soba, String opis, Status status) {
		super();
		this.id = id;
		this.pacijent = pacijent;
		this.lekar = lekar;
		this.termin = termin;
		//termin = LocalDateTime.now().plusHours(3).plusMinutes(33);
		this.soba = soba;
		this.opis = opis;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	public java.util.Date getTermin() {
		return termin;
	}

	public void setTermin(java.util.Date termin) {
		this.termin = termin;
	}

	public String getStr_termin() {
		return str_termin;
	}

	public void setStr_termin(String str_termin) {
		this.str_termin = str_termin;
	}

	public String getSoba() {
		return soba;
	}

	public void setSoba(String soba) {
		this.soba = soba;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
