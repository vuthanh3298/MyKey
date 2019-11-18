/*
 * Lab03
 * Nguyễn Vũ Thanh - 16521128
 * Bùi Tấn Duy - 16520277
 * Các chức năng và phân chia:
 * 1. tạo frameword project: Thanh
 * 2. tạo giao diện (hàm initControls, loadData): Thanh
 * 3. xử lý sự kiện (hàm addEvents, xuLyMoRong): Duy
 * 4. xử lý hiệu ứng (hàm addHieuUng): Duy
 * 5. xử lý telex: Thanh
 * 6. xử lý vni: Duy
 * 7. xử lý lưu trạng thái: thanh
 * */

package ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import io.SeriablizeFileFactory;
import model.Config;
import model.KieuGo;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class UnikeyUI extends JFrame {

	final int width = 580;
	final int heightThuNho = 252;
	final int heightMoRong = 483;

	boolean isThuNho = true;
	ImageIcon imgMoRong = new ImageIcon(UnikeyUI.class.getResource("/images/down_img.png"));
	ImageIcon imgThuNho = new ImageIcon(UnikeyUI.class.getResource("/images/up_img.png"));

	JButton btnMoRong;
	JButton btnDong;
	JButton btnMacDinh;
	JButton btnThongTin;
	
	JRadioButton radCtrlShift;
	JRadioButton radAltZ;
	

	JTextArea textArea;
	static Color colorStandard;// = btnMoRong.getBackground();
	JComboBox<String> cboBangMa;
	JComboBox<String> cboKieuGo;
	


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	Character charOld = null;
	int indexDau = -1;
	static Config config;
	
	char[] arrdauVni = {'1','2','3','4','5'};
	char[] arrdauTelex = {'s','f','r','x','j'};
	char arrdau[] = arrdauTelex;
	char[] arre = {'é','è','ẻ','ẽ','ẹ'};
	char[] arre2 = {'ế','ề','ể','ễ','ệ'};
	char[] arra = {'á','à','ả','ã','ạ'};
	char[] arra2 = {'ắ','ằ','ẳ','ẵ','ặ'};
	char[] arra3 = {'ấ','ầ','ẩ','ẫ','ậ'};
	char[] arro = {'ó','ò','ỏ','õ','ọ'};
	char[] arro2 = {'ớ','ờ','ở','ỡ','ợ'};
	char[] arro3 = {'ố','ồ','ổ','ỗ','ộ'};
	char[] arru = {'ú','ù','ủ','ũ','ụ'};
	char[] arru2 = {'ứ','ừ','ử','ữ','ự'};
	char[] arri = {'í','ì','ỉ','ĩ','ị'};
	char[] arry = {'ý','ỳ','ỷ','ỹ','ỵ'};
	

	public UnikeyUI() {
		initControls();
		loadConfig();
		formLoad();
		addEvents();
		addHieuUng();	// thêm hiệu ứng khi di chuyển chuột qua các controls
	}

	private void loadConfig() {
		URL url = getClass().getResource("/data/data.txt");
		config = SeriablizeFileFactory.docFile(url);
		if(config == null) {
			config = new Config(KieuGo.telex, false, true);
			saveConfig();
		}
	}
	
	private void saveConfig() {
		URL path = getClass().getResource("/data/data.txt");
		SeriablizeFileFactory.luuFile(config, path);
	}

	private void formLoad() {
		cboBangMa.addItem("Unicode");
		cboKieuGo.addItem("Telex");
		cboKieuGo.addItem("Vni");
		cboKieuGo.setSelectedIndex(config.getKieuGo() == KieuGo.telex? 0 : 1);
		if(config.isCtrlShift()) {
			radCtrlShift.setSelected(true);
		} else {
			radAltZ.setSelected(true);
		}
	}

	private void addEvents() {
		btnMoRong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xuLyMoRong();
			}
		});
		btnDong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		textArea.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				char charKey = e.getKeyChar();
				if(config.getKieuGo() == KieuGo.telex)
					xuLyTelex(charKey);
				else
					xuLyVni(charKey);
			}
			public void keyPressed(KeyEvent e) {}
		});

		cboKieuGo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(cboKieuGo.getSelectedItem().toString().equals("Telex")) {
					arrdau = arrdauTelex;
					config.setKieuGo(KieuGo.telex);;
					charOld = null;
					indexDau = -1;
					config.setKieuGo(KieuGo.telex);
					saveConfig();
				}
				else if(cboKieuGo.getSelectedItem().toString().equals("Vni")) {
					arrdau = arrdauVni;
					config.setKieuGo(KieuGo.vni);;
					charOld = null;
					indexDau = -1;
					config.setKieuGo(KieuGo.vni);
					saveConfig();
				}
			}
		});
		radCtrlShift.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(radCtrlShift.isSelected()) {
					config.setCtrlShift(true);
				} else {
					config.setCtrlShift(false);
				}		
				saveConfig();
			}
		});
		
		btnThongTin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ThongTinUI ui = new ThongTinUI();
				ui.showWindow();
			}
		});
	}

	private void replace(char c) {
		int length = textArea.getText().length();
		if(length < 2) return;
		textArea.replaceRange(c + "", length - 2, length -1);
		textArea.replaceRange(null, length - 1, length);
	}

	private void replaceNguoc(char c) {
		int length = textArea.getText().length();
		if(length < 2) return;
		textArea.replaceRange(c + "", length - 2, length -1);
		textArea.replaceRange(c + "", length - 1, length);
	}
	
	private void replaceNguocVni(char c, char d) {
		int length = textArea.getText().length();
		if(length < 2) return;
		textArea.replaceRange(c + "", length - 2, length -1);
		textArea.replaceRange(d + "", length - 1, length);
	}

	private void xuLyDau(char charNew, char[] arrChu, char chuHienTai) {
		for(int i = 0; i < arrdau.length; i++) {
			if(charNew == arrdau[i]) {
				replace(arrChu[i]);
				charOld = chuHienTai;
				indexDau = i;
				return;
			}
		}
		charOld = charNew;
		indexDau = -1;
	}

	protected void xuLyTelex(char charNew) {
		if(charOld == null) {
			charOld = charNew;
			return;
		}
		switch(charOld) {
		case 'e':
			if(charNew == 'e') {
				if(indexDau != -1) {
					replace(arre2[indexDau]);
				}else {
					replace('ê');
				}
				charOld = 'ê';
				break;
			}
			xuLyDau(charNew, arre, 'e');
			break;
		case 'ê':
			//			if(charNew == 'e') {
			//				replaceNguoc('e');
			//				break;
			//			}
			xuLyDau(charNew, arre2, 'ê');
			break;
		case 'u':
			if(charNew == 'w') {
				if(indexDau != -1) {
					replace(arru2[indexDau]);
				}else {
					replace('ư');
				}
				charOld = 'ư';
				break;
			}
			xuLyDau(charNew, arru, 'u');
			break;
		case 'ư':
			if(charNew == 'u') {
				replaceNguoc('u');
				break;
			}
			xuLyDau(charNew, arru2, 'u');
			break;
		case 'a':
			if(charNew == 'a') {
				if(indexDau != -1) {
					replace(arra3[indexDau]);
				}else {
					replace('â');
				}
				charOld = 'â';
				break;
			}
			if(charNew == 'w') {
				if(indexDau != -1) {
					replace(arra2[indexDau]);
				}else {
					replace('ă');
				}
				charOld = 'ă';
				break;
			}
			xuLyDau(charNew, arra, 'a');
			break;
		case 'ă':
			if(charNew == 'a') {
				if(indexDau != -1) {
					replace(arra3[indexDau]);
				}else {
					replace('â');
				}
				charOld = 'â';
				break;
			}
			xuLyDau(charNew, arra2, 'ă');
			break;
		case 'â':
			if(charNew == 'w') {
				if(indexDau != -1) {
					replace(arra2[indexDau]);
				}else {
					replace('ă');
				}
				charOld = 'ă';
				break;
			}
			xuLyDau(charNew, arra3, 'â');
			break;
		case 'o':
			if(charNew == 'o') {
				if(indexDau != -1) {
					replace(arro3[indexDau]);
				}else {
					replace('ô');
				}
				charOld = 'ô';
				break;
			}
			if(charNew == 'w') {
				if(indexDau != -1) {
					replace(arro2[indexDau]);
				}else {
					replace('ơ');
				}
				charOld = 'ơ';
				break;
			}
			xuLyDau(charNew, arro, 'o');
			break;
		case 'ô':
			xuLyDau(charNew, arro3, 'ô');
			break;
		case 'ơ':
			xuLyDau(charNew, arro2, 'ơ');
			break;
		case 'i':
			xuLyDau(charNew, arri, 'i');
			break;
		case 'y':
			xuLyDau(charNew, arry, 'y');
			break;
		case 'd':
			if(charNew == 'd') {
				replace('đ');
				charOld = 'đ';
			} else {
				charOld = charNew;
			}
			break;
		case 'đ':
			if(charNew == 'd') {
				replaceNguoc('d');
			} 
		default:
			charOld = charNew;
			break;
		}
	}

	protected void xuLyVni(char charNew) {
		if(charOld == null) {
			charOld = charNew;
			return;
		}
		switch(charOld) {
		case 'e':
			if(charNew == '6') {
				if(indexDau != -1) {
					replace(arre2[indexDau]);
				}else {
					replace('ê');
				}
				charOld = 'ê';
				break;
			}
			xuLyDau(charNew, arre, 'e');
			break;
		case 'ê':
			//			if(charNew == 'e') {
			//				replaceNguoc('e');
			//				break;
			//			}
			xuLyDau(charNew, arre2, 'ê');
			break;
		case 'u':
			if(charNew == '7') {
				if(indexDau != -1) {
					replace(arru2[indexDau]);
				}else {
					replace('ư');
				}
				charOld = 'ư';
				break;
			}
			xuLyDau(charNew, arru, 'u');
			break;
		case 'ư':
			if(charNew == '7') {
				replaceNguocVni('u','7');
				break;
			}
			xuLyDau(charNew, arru2, 'u');
			break;
		case 'a':
			if(charNew == '6') {
				if(indexDau != -1) {
					replace(arra3[indexDau]);
				}else {
					replace('â');
				}
				charOld = 'â';
				break;
			}
			if(charNew == '8') {
				if(indexDau != -1) {
					replace(arra2[indexDau]);
				}else {
					replace('ă');
				}
				charOld = 'ă';
				break;
			}
			xuLyDau(charNew, arra, 'a');
			break;
		case 'ă':
			if(charNew == '6') {
				if(indexDau != -1) {
					replace(arra3[indexDau]);
				}else {
					replace('â');
				}
				charOld = 'â';
				break;
			}
			xuLyDau(charNew, arra2, 'ă');
			break;
		case 'â':
			if(charNew == '8') {
				if(indexDau != -1) {
					replace(arra2[indexDau]);
				}else {
					replace('ă');
				}
				charOld = 'ă';
				break;
			}
			xuLyDau(charNew, arra3, 'â');
			break;
		case 'o':
			if(charNew == '6') {
				if(indexDau != -1) {
					replace(arro3[indexDau]);
				}else {
					replace('ô');
				}
				charOld = 'ô';
				break;
			}
			if(charNew == '7') {
				if(indexDau != -1) {
					replace(arro2[indexDau]);
				}else {
					replace('ơ');
				}
				charOld = 'ơ';
				break;
			}
			xuLyDau(charNew, arro, 'o');
			break;
		case 'ô':
			xuLyDau(charNew, arro3, 'ô');
			break;
		case 'ơ':
			xuLyDau(charNew, arro2, 'ơ');
			break;
		case 'i':
			xuLyDau(charNew, arri, 'i');
			break;
		case 'y':
			xuLyDau(charNew, arry, 'y');
			break;
		case 'd':
			if(charNew == '9') {
				replace('đ');
				charOld = 'đ';
			} else {
				charOld = charNew;
			}
			break;
		case 'đ':
			if(charNew == '9') {
				replaceNguocVni('d','9');
			} 
		default:
			charOld = charNew;
			break;
		}
	}

	private void addHieuUng() {
		btnMoRong.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {			//khi di chuyển chuột ra khỏi button thì đổi lại màu cũ (colorStandard)
				btnMoRong.setBackground(colorStandard);		//colorStandard được khai báo trong hàm initControls();
			}
			public void mouseEntered(MouseEvent e) {
				btnMoRong.setBackground(Color.cyan);		//khi di chuyển vào thì sẽ đổi màu, chọn 1 màu bất kì như cyan
			}												//các trường hợp khác tương tự
			public void mouseClicked(MouseEvent e) {}
		});
	}

	protected void xuLyMoRong() {
		if(isThuNho) {
			btnMoRong.setText("Thu nhỏ");
			btnMoRong.setIcon(imgThuNho);
			setSize(width, heightMoRong);
			isThuNho = false;
		} else {
			btnMoRong.setText("Mở rộng");
			btnMoRong.setIcon(imgMoRong);
			setSize(width, heightThuNho);
			isThuNho = true;
		}
	}

	private void initControls() {
		setResizable(false);
		setTitle("Unikey");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, width, heightThuNho);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 204, 0), 2, true), "\u0110i\u1EC1u khi\u1EC3n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 407, 106);
		panel_2.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("B\u1EA3ng m\u00E3:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 27, 67, 14);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Ki\u1EC3u g\u00F5:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 52, 67, 17);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Ph\u00EDm chuy\u1EC3n:");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_2.setBounds(10, 80, 85, 19);
		panel.add(lblNewLabel_2);

		cboBangMa = new JComboBox<String>();
		cboBangMa.setFont(new Font("Times New Roman", Font.BOLD, 14));
		cboBangMa.setBounds(105, 24, 225, 20);
		panel.add(cboBangMa);

		cboKieuGo = new JComboBox<String>();
		cboKieuGo.setFont(new Font("Times New Roman", Font.BOLD, 14));
		cboKieuGo.setBounds(105, 50, 175, 20);
		panel.add(cboKieuGo);

		JButton btnTuyChon = new JButton("...");
		btnTuyChon.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnTuyChon.setBounds(290, 49, 40, 23);
		panel.add(btnTuyChon);

		radCtrlShift = new JRadioButton("CTRL + SHIFT");
		radCtrlShift.setFont(new Font("Times New Roman", Font.BOLD, 14));
		radCtrlShift.setBounds(105, 76, 119, 23);
		panel.add(radCtrlShift);

		radAltZ = new JRadioButton("ALT + Z");
		radAltZ.setFont(new Font("Times New Roman", Font.BOLD, 14));
		radAltZ.setBounds(226, 76, 85, 23);
		panel.add(radAltZ);

		ButtonGroup groupBtn = new ButtonGroup();
		groupBtn.add(radAltZ);
		groupBtn.add(radCtrlShift);

		btnDong = new JButton("\u0110\u00F3ng");
		btnDong.setHorizontalAlignment(SwingConstants.LEFT);
		btnDong.setIcon(new ImageIcon(UnikeyUI.class.getResource("/images/dong_img.png")));
		btnDong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnDong.setBounds(431, 26, 123, 23);
		panel_2.add(btnDong);

		JButton btnKetThuc = new JButton("K\u1EBFt th\u00FAc");
		btnKetThuc.setIcon(new ImageIcon(UnikeyUI.class.getResource("/images/ketthuc_img.png")));
		btnKetThuc.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnKetThuc.setBounds(431, 60, 123, 23);
		panel_2.add(btnKetThuc);

		btnMoRong = new JButton("M\u1EDF r\u1ED9ng");
		btnMoRong.setIcon(imgMoRong);
		btnMoRong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnMoRong.setBounds(431, 88, 123, 29);
		panel_2.add(btnMoRong);

		colorStandard = btnMoRong.getBackground();

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new LineBorder(new Color(51, 0, 153), 2), "Tu\u1EF3 ch\u1ECDn kh\u00E1c", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(10, 128, 544, 110);
		panel_2.add(panel_3);
		panel_3.setLayout(null);

		JCheckBox chkTuDo = new JCheckBox("Cho ph\u00E9p g\u00F5 t\u1EF1 do");
		chkTuDo.setFont(new Font("Times New Roman", Font.BOLD, 14));
		chkTuDo.setBounds(6, 28, 211, 23);
		panel_3.add(chkTuDo);

		JCheckBox chkOaUy = new JCheckBox("\u0110\u1EB7t d\u1EA5u o\u00E0, u\u00FD (thay v\u00EC \u00F2a, \u00FAy)");
		chkOaUy.setFont(new Font("Times New Roman", Font.BOLD, 14));
		chkOaUy.setBounds(6, 54, 211, 23);
		panel_3.add(chkOaUy);

		JCheckBox chkClipboard = new JCheckBox("Lu\u00F4n s\u1EED d\u1EE5ng clipboard cho unicode");
		chkClipboard.setFont(new Font("Times New Roman", Font.BOLD, 14));
		chkClipboard.setBounds(6, 80, 245, 23);
		panel_3.add(chkClipboard);

		JCheckBox chkChinhTa = new JCheckBox("B\u1EADt ki\u1EC3m tra ch\u00EDnh t\u1EA3");
		chkChinhTa.setFont(new Font("Times New Roman", Font.BOLD, 14));
		chkChinhTa.setBounds(253, 28, 235, 23);
		panel_3.add(chkChinhTa);

		JCheckBox chkKhoiPhuc = new JCheckBox("T\u1EF1 \u0111\u1ED9ng kh\u00F4i ph\u1EE5c ph\u00EDm v\u1EDBi t\u1EEB sai");
		chkKhoiPhuc.setFont(new Font("Times New Roman", Font.BOLD, 14));
		chkKhoiPhuc.setBounds(253, 54, 235, 23);
		panel_3.add(chkKhoiPhuc);

		JCheckBox chkPhanHoi = new JCheckBox("Hi\u1EC7n th\u00F4ng b\u00E1o ph\u1EA3n h\u1ED3i");
		chkPhanHoi.setFont(new Font("Times New Roman", Font.BOLD, 14));
		chkPhanHoi.setBounds(253, 80, 235, 23);
		panel_3.add(chkPhanHoi);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(new LineBorder(new Color(204, 51, 51), 2), "Tu\u1EF3 ch\u1ECDn g\u00F5 t\u1EAFt", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(10, 244, 272, 110);
		panel_2.add(panel_4);
		panel_4.setLayout(null);

		JCheckBox chkGoTat = new JCheckBox("Cho ph\u00E9p g\u00F5 t\u1EAFt");
		chkGoTat.setFont(new Font("Times New Roman", Font.BOLD, 14));
		chkGoTat.setBounds(10, 20, 254, 23);
		panel_4.add(chkGoTat);

		JCheckBox chkGoTatKhiTatTiengViet = new JCheckBox("Cho ph\u00E9p g\u00F5 t\u1EAFt c\u1EA3 khi t\u1EAFt Ti\u1EBFng Vi\u1EC7t");
		chkGoTatKhiTatTiengViet.setFont(new Font("Times New Roman", Font.BOLD, 14));
		chkGoTatKhiTatTiengViet.setBounds(10, 46, 254, 23);
		panel_4.add(chkGoTatKhiTatTiengViet);

		JButton btnBangGoTat = new JButton("B\u1EA3ng g\u00F5 t\u1EAFt");
		btnBangGoTat.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnBangGoTat.setBounds(10, 76, 119, 23);
		panel_4.add(btnBangGoTat);

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(new LineBorder(new Color(51, 51, 204), 2), "H\u1EC7 th\u1ED1ng", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_5.setBounds(286, 244, 268, 110);
		panel_2.add(panel_5);
		panel_5.setLayout(null);

		JCheckBox chkBatKhiKhoiDong = new JCheckBox("B\u1EADt h\u1ED9i tho\u1EA1i n\u00E0y khi kh\u1EDFi \u0111\u1ED9ng");
		chkBatKhiKhoiDong.setFont(new Font("Times New Roman", Font.BOLD, 14));
		chkBatKhiKhoiDong.setBounds(6, 23, 221, 23);
		panel_5.add(chkBatKhiKhoiDong);

		JCheckBox chkKhoiDongCungWindows = new JCheckBox("Kh\u1EDFi \u0111\u1ED9ng c\u00F9ng Windows");
		chkKhoiDongCungWindows.setFont(new Font("Times New Roman", Font.BOLD, 14));
		chkKhoiDongCungWindows.setBounds(6, 49, 221, 23);
		panel_5.add(chkKhoiDongCungWindows);

		JCheckBox chkVietnameseInterface = new JCheckBox("Vietnamese Interface");
		chkVietnameseInterface.setFont(new Font("Times New Roman", Font.BOLD, 14));
		chkVietnameseInterface.setBounds(6, 75, 221, 23);
		panel_5.add(chkVietnameseInterface);

		JPanel panel_6 = new JPanel();
		panel_6.setLayout(new BorderLayout());

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel_6, BorderLayout.SOUTH);

		panel_6.add(panel_1,BorderLayout.CENTER);


		JButton btnHuongDan = new JButton("H\u01B0\u1EDBng d\u1EABn");
		btnHuongDan.setIcon(new ImageIcon(UnikeyUI.class.getResource("/images/huongdan_img.png")));
		btnHuongDan.setFont(new Font("Times New Roman", Font.BOLD, 14));
		panel_1.add(btnHuongDan);

		btnThongTin = new JButton("Th\u00F4ng tin");
		btnThongTin.setFont(new Font("Times New Roman", Font.BOLD, 14));
		panel_1.add(btnThongTin);

		btnMacDinh = new JButton("M\u1EB7c \u0111\u1ECBnh");
		btnMacDinh.setFont(new Font("Times New Roman", Font.BOLD, 14));
		panel_1.add(btnMacDinh);

		getRootPane().setDefaultButton(btnDong);

		textArea = new JTextArea();
		textArea.setFont(new Font("Times New Roman", Font.BOLD, 17));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane sp = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(0,50));
		panel_6.add(sp, BorderLayout.SOUTH);
	}

	public void showWindow() {
		this.setVisible(true);
	}
}
