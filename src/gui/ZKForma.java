package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import korisnici.Knjizica;
import korisnici.KorisnikUtils;
import net.miginfocom.swing.MigLayout;

public class ZKForma extends JFrame{
	private JToolBar mainToolBar = new JToolBar();
	private JTable tableZK;
	private Knjizica knjizica;
	private JButton btnIzmeni;
	
	public ZKForma() {
		setTitle("Zdravstvene knjizice pacijenata");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(900, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		Container c = this.getContentPane();
		c.setBackground(Color.white);
		initGUI();
	}
	
	private void initGUI() {
		MigLayout layout = new MigLayout("wrap, top, left", "[]", "[]5[]");
		setLayout(layout);
		String[] header = new String[] { "Broj knjizice", "Datum isteka","Kategorija osiguranja" };
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Image img = kit.getImage("src/slike/slika.png");
		setIconImage(img);
		
		int brojac = KorisnikUtils.getKnjizice().size();
		Object[][] content = new Object[brojac][header.length];
		
		for(int i=0; i<KorisnikUtils.getKnjizice().size(); i++) {
			Knjizica knjizica = KorisnikUtils.getKnjizice().get(i);
			content[i][0] = knjizica.getBroj();
			content[i][1] = knjizica.getDatumIsteka();
			content[i][2] = knjizica.getKategorijaOsiguranja();
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		tableZK = new JTable(model);
		tableZK.setRowSelectionAllowed(true);
		tableZK.setColumnSelectionAllowed(false);
		tableZK.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableZK.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tableZK);
		scrollPane.setPreferredSize(new Dimension(900, 400));
		scrollPane.setMaximumSize(new Dimension(900, 400));
		add(scrollPane, BorderLayout.CENTER);
		btnIzmeni = new JButton("Izmeni");
		add(btnIzmeni,"right");
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tableZK.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Selektujte red za izmenu.", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel) tableZK.getModel();
					int broj = Integer.parseInt(model.getValueAt(red, 0).toString());
					//String korisnickoIme = model.getValueAt(red, 6).toString();
					Knjizica knjizica = KorisnikUtils.nadjiKnjizicu(broj);
					if(knjizica != null) {
						ZKFunkcionalnosti zkf = new ZKFunkcionalnosti(knjizica);
						zkf.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranu knjizicu.", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		
	}

}