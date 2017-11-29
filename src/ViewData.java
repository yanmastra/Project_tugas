import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewData extends JFrame {

	private JPanel contentPane;
	private JTextField txtNama;
	private JTextField txtPembina;
	private JTextField txtThBerdiri;
	private JTable tbData;
	
	private Koneksi koneksi = new Koneksi();
	private Connection con = koneksi.getConnection();
	
	private String header[] =  {"Kode", "Nama", "Pembina", "Tahun Berdiri"};
	DefaultTableModel tbModel = new DefaultTableModel(null, header);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewData frame = new ViewData();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ViewData() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				getData("SELECT * FROM tb_pr ORDER BY th_berdiri");
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDataPr = new JLabel("Data PR ");
		lblDataPr.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataPr.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDataPr.setBounds(0, 0, 434, 20);
		contentPane.add(lblDataPr);
		
		JLabel lblNama = new JLabel("Nama ");
		lblNama.setBounds(10, 31, 90, 20);
		contentPane.add(lblNama);
		
		txtNama = new JTextField();
		txtNama.setColumns(10);
		txtNama.setBounds(100, 31, 150, 20);
		contentPane.add(txtNama);
		
		JLabel lblPembina = new JLabel("Pembina");
		lblPembina.setBounds(10, 61, 90, 20);
		contentPane.add(lblPembina);
		
		txtPembina = new JTextField();
		txtPembina.setColumns(10);
		txtPembina.setBounds(100, 61, 150, 20);
		contentPane.add(txtPembina);
		
		JLabel lblTahunBerdiri = new JLabel("Tahun Berdiri");
		lblTahunBerdiri.setBounds(10, 91, 90, 20);
		contentPane.add(lblTahunBerdiri);
		
		txtThBerdiri = new JTextField();
		txtThBerdiri.setColumns(10);
		txtThBerdiri.setBounds(100, 91, 150, 20);
		contentPane.add(txtThBerdiri);
		
		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					Statement st = con.createStatement();
					String sql = "INSERT INTO tb_pr VALUES(null,'"
							+ txtNama.getText()+ "', '" 
							+ txtPembina.getText()+ "', '" 
							+ txtThBerdiri.getText() 
							+ "')";
					int result = st.executeUpdate(sql);
					if(result>0){
						JOptionPane.showMessageDialog(null, "Data berhasil diinsert!");
						getData("SELECT * FROM tb_pr ORDER BY th_berdiri");
						resetData();
					}
				}catch(SQLException e){
					JOptionPane.showMessageDialog(null, "Data GAGAL diinsert!");
				}
			}
		});
		
		btnSimpan.setBounds(10, 122, 89, 23);
		contentPane.add(btnSimpan);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetData();
			}
		});
		btnReset.setBounds(110, 122, 89, 23);
		contentPane.add(btnReset);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 161, 414, 139);
		contentPane.add(scrollPane);

		tbData = new JTable();
		scrollPane.setViewportView(tbData);
		tbData.setModel(tbModel);

	}
	
	private void getData(String sql){
		try{
			ResultSet rs;
			Statement st = con.createStatement();
			rs = st.executeQuery(sql);
			rs.beforeFirst();
			
			tbModel.getDataVector().removeAllElements();
			while(rs.next()){
				Object obj[] = new Object[4];
				obj[0] = rs.getString(1);
				obj[1] = rs.getString(2);
				obj[2] = rs.getString(3);
				obj[3] = rs.getString(4);
				tbModel.addRow(obj);
			}
			st.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	private void resetData(){
		txtNama.setText("");
		txtPembina.setText(""); 
		txtThBerdiri.setText("");
	}
	
}
