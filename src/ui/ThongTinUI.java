package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThongTinUI extends JDialog {

	private JPanel contentPane;
	JButton btnThoat;

	
	public ThongTinUI() {
		initControls();
		addEvents();
	}

	private void addEvents() {
		btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	private void initControls() {
		setTitle("Thông tin");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 302, 242);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);	
		
		JPanel pnThongTin = new JPanel();
		contentPane.add(pnThongTin, BorderLayout.CENTER);
		pnThongTin.setLayout(null);
		
		JLabel lblTnPhnMm = new JLabel("Tên phần mềm: uitkey");
		lblTnPhnMm.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblTnPhnMm.setBounds(10, 11, 232, 14);
		pnThongTin.add(lblTnPhnMm);
		
		JLabel lblGi = new JLabel("<html> Giáo viên hướng dẫn: <br> Thầy Nguyễn Thanh Thiện </html>");
		lblGi.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblGi.setBounds(10, 31, 232, 40);
		pnThongTin.add(lblGi);
		
		JLabel lblThnhVin = new JLabel("Thành viên:");
		lblThnhVin.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblThnhVin.setBounds(10, 82, 232, 14);
		pnThongTin.add(lblThnhVin);
		
		JLabel lblNguynVThanh = new JLabel("Nguyễn Vũ Thanh - 16521128");
		lblNguynVThanh.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblNguynVThanh.setBounds(10, 107, 232, 14);
		pnThongTin.add(lblNguynVThanh);
		
		JLabel lblBiTnDuy = new JLabel("Bùi Tấn Duy - 16520277");
		lblBiTnDuy.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblBiTnDuy.setBounds(10, 128, 232, 14);
		pnThongTin.add(lblBiTnDuy);
		
		JPanel pnButton = new JPanel();
		contentPane.add(pnButton, BorderLayout.SOUTH);
		
		btnThoat = new JButton("Đóng");
		btnThoat.setFont(new Font("Times New Roman", Font.BOLD, 15));
		pnButton.add(btnThoat);
		setModal(true);
		setLocationRelativeTo(null);
		
		
	}
	
	public void showWindow()
	{
		setVisible(true);
	}
}
