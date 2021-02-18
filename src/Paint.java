import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

class Point {
	int x, y;
}

// �������� �г� ����
class EditPanel extends JPanel implements MouseMotionListener, ActionListener {

	private JLabel notice;

	private JLabel testnn;

	private int sizenum = 2;
	private JButton save;
	private JButton erase;
	private JButton resetb;
	private JTextField textinput; // �Է��� �ؽ�Ʈ �ۼ��ϴ� ��
	private JButton textenter; // �ۼ��� �ؽ�Ʈ ����ϱ� ���� ������ ��ư
	private String textcontents; // �Է¹��� �ؽ�Ʈ �����ϴ� ��

	JButton findbutton;
	JFileChooser fc;
	private JButton blackpen;
	private JButton redpen;
	private JButton yellow;
	private JButton stampb1;
	private JButton stampb2;
	private JButton stampb3;
	private JButton green;
	private JButton white;
	private JSlider slider;
	private Color backgroundcolor = Color.white;
	private Color pencolor = Color.black;
	private Image istamp1;
	private Image istamp2;
	private Image istamp3;
	private JButton frame1;
	private JButton frame2;
	private JButton frame3;
	private JButton frame4;
	Color[] framecolor = { Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.black, Color.pink };
	int framenumber = 0;

	private int state = 0;
	private int stampnum = 0;
	private int indexr1 = 0;
	private int indexr2 = 0;
	private int indexr3 = 0;
	private int indexb1 = 0;
	private int indexb2 = 0;
	private int indexb3 = 0;
	private int indexs1 = 0;
	private int indexs2 = 0;
	private int indexs3 = 0;
	private int indexe = 0;
	private int indext = 0;

	private Font font = new Font("����������", Font.BOLD, 15);

	// ������ ��ġ�� ���� �迭
	Point[] black1 = new Point[1000];
	Point[] black2 = new Point[1000];
	Point[] black3 = new Point[1000];

	// ������ ��ġ�� ���� �迭
	Point[] red1 = new Point[1000];
	Point[] red2 = new Point[1000];
	Point[] red3 = new Point[1000];

	// ���� ��ġ�� ���� �迭
	Point[] stamp1 = new Point[1000];
	Point[] stamp2 = new Point[1000];
	Point[] stamp3 = new Point[1000];

	// ���찳 ��ġ�� ���� �迭
	Point[] eraser = new Point[1000];

	// �ؽ�Ʈ ��ġ�� ���� �迭
	Point[] textlocation = new Point[1000];

	public EditPanel() {

		DrawListener draw = new DrawListener();
		BgListener bg = new BgListener();
		SizeListener size = new SizeListener();
		ResetListener reset = new ResetListener();
		CaptureListener capture = new CaptureListener();
		FrameListener frame = new FrameListener();

		setLayout(null);

		// �ȳ�����
		notice = new JLabel("�׸��� �׷�������!");
		notice.setForeground(Color.gray);
		this.add(notice);

		// �ʱ�ȭ
		resetb = new JButton("reset");
		this.add(resetb);
		resetb.addActionListener(reset);

		// ���
		blackpen = new JButton("������");
		this.add(blackpen);
		blackpen.addActionListener(draw);
		redpen = new JButton("������");
		this.add(redpen);
		redpen.addActionListener(draw);

		// ����
		setBackground(Color.white);
		yellow = new JButton("������");
		this.add(yellow);
		yellow.addActionListener(bg);
		green = new JButton("�ʷϹ��");
		this.add(green);
		green.addActionListener(bg);
		white = new JButton("�Ͼ���");
		this.add(white);
		white.addActionListener(bg);

		// �� �β�
		slider = new JSlider(1, 3, 2);
		slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(size);
		this.add(slider);

		this.addMouseMotionListener(this);
		setVisible(true);

		// ������
		stampb1 = new JButton();
		stampb1.addActionListener(draw);
		this.add(stampb1);
		stampb2 = new JButton();
		stampb2.addActionListener(draw);
		this.add(stampb2);
		stampb3 = new JButton();
		stampb3.addActionListener(draw);
		this.add(stampb3);

		// ������ �̹��� �ҷ�����
		try {
			istamp1 = ImageIO.read(new File("star.png"));
		} catch (IOException e) {

		}
		try {
			istamp2 = ImageIO.read(new File("heart.png"));
		} catch (IOException e) {

		}
		try {
			istamp3 = ImageIO.read(new File("cloud.png"));
		} catch (IOException e) {

		}

		// �����ϱ�
		save = new JButton("�����ϱ�");
		this.add(save);
		save.addActionListener(capture);

		// �����
		erase = new JButton("�����");
		this.add(erase);
		erase.addActionListener(draw);

		// �ؽ�Ʈ�׸���
		textinput = new JTextField("���ڸ� �Է��ϼ���", 10);
		this.add(textinput);
		textenter = new JButton("�߰�");
		this.add(textenter);
		textenter.addActionListener(draw);

		// file chooser
		fc = new JFileChooser();
		findbutton = new JButton("�׸��ֱ�");
		this.add(findbutton);
		findbutton.addActionListener(this);
		testnn = new JLabel("");
		this.add(testnn);

		// ����
		frame1 = new JButton();
		this.add(frame1);
		frame2 = new JButton();
		this.add(frame2);
		frame3 = new JButton();
		this.add(frame3);
		frame4 = new JButton();
		this.add(frame4);
		Border emptyBorder = BorderFactory.createEmptyBorder();
		frame1.setBorder(emptyBorder);
		frame2.setBorder(emptyBorder);
		frame3.setBorder(emptyBorder);
		frame4.setBorder(emptyBorder);
		frame1.setBounds(10, 100, 650, 40);
		frame2.setBounds(10, 140, 40, 500);
		frame3.setBounds(640, 100, 40, 540);
		frame4.setBounds(10, 600, 650, 40);
		frame1.setBackground(Color.red);
		frame2.setBackground(Color.red);
		frame3.setBackground(Color.red);
		frame4.setBackground(Color.red);
		frame1.addActionListener(frame);
		frame2.addActionListener(frame);
		frame3.addActionListener(frame);
		frame4.addActionListener(frame);

		// object ��ġ �� ����
		notice.setBounds(10, 640, 650, 50);
		notice.setHorizontalAlignment(JLabel.CENTER);
		notice.setFont(font);
		resetb.setBounds(570, 10, 100, 30);
		resetb.setBackground(Color.pink);
		erase.setBounds(460, 10, 100, 30);
		erase.setBackground(Color.pink);
		save.setBounds(10, 10, 100, 30);
		save.setBackground(Color.pink);
		textinput.setBounds(120, 10, 150, 30);
		textinput.setBackground(Color.orange);
		textenter.setBounds(280, 10, 70, 30);
		textenter.setBackground(Color.orange);
		findbutton.setBounds(360, 10, 90, 30);
		findbutton.setBackground(Color.green);
		blackpen.setBounds(10, 50, 70, 40);
		blackpen.setBackground(Color.LIGHT_GRAY);
		redpen.setBounds(85, 50, 70, 40);
		redpen.setBackground(Color.LIGHT_GRAY);
		slider.setBounds(160, 50, 80, 40);
		white.setBounds(250, 50, 90, 40);
		white.setBackground(Color.yellow);
		yellow.setBounds(345, 50, 90, 40);
		yellow.setBackground(Color.yellow);
		green.setBounds(440, 50, 90, 40);
		green.setBackground(Color.yellow);
		stampb1.setBounds(535, 50, 40, 40);
		ImageIcon icon1 = new ImageIcon("icon1.png");
		stampb1.setIcon(icon1);
		stampb1.setBackground(Color.white);
		stampb2.setBounds(580, 50, 40, 40);
		ImageIcon icon2 = new ImageIcon("icon2.png");
		stampb2.setIcon(icon2);
		stampb2.setBackground(Color.white);
		stampb3.setBounds(625, 50, 40, 40);
		ImageIcon icon3 = new ImageIcon("icon3.png");
		stampb3.setIcon(icon3);
		stampb3.setBackground(Color.white);

		testnn.setBounds(200, 200, 300, 300);

	}

	// Listener

	// �� �β�
	private class SizeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				int value = (int) source.getValue();
				sizenum = value;
			}

			notice.setText("�� �β��� " + sizenum + "�ܰ�� ����Ǿ����ϴ�.");
		}
	}

	// �ʱ�ȭ
	private class ResetListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < black1.length; i++) {
				black1[i] = null;
			}
			for (int i = 0; i < black2.length; i++) {
				black2[i] = null;
			}
			for (int i = 0; i < black3.length; i++) {
				black3[i] = null;
			}
			for (int i = 0; i < red1.length; i++) {
				red1[i] = null;
			}
			for (int i = 0; i < red2.length; i++) {
				red2[i] = null;
			}
			for (int i = 0; i < red3.length; i++) {
				red3[i] = null;
			}
			for (int i = 0; i < stamp1.length; i++) {
				stamp1[i] = null;
			}
			for (int i = 0; i < stamp2.length; i++) {
				stamp2[i] = null;
			}
			for (int i = 0; i < stamp3.length; i++) {
				stamp3[i] = null;
			}
			for (int i = 0; i < eraser.length; i++) {
				eraser[i] = null;
			}
			for (int i = 0; i < textlocation.length; i++) {
				textlocation[i] = null;
			}

			notice.setText("�ʱ�ȭ �Ǿ����ϴ�");

		}
	}

	// �� ����
	private class DrawListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == redpen) {
				pencolor = Color.red;
				state = 0;
				notice.setText("�� ������ ���������� ����Ǿ����ϴ�.");
			} else if (e.getSource() == blackpen) {
				pencolor = Color.black;
				state = 1;
				notice.setText("�� ������ ���������� ����Ǿ����ϴ�.");
			} else if (e.getSource() == stampb1) {
				state = 2;
				stampnum = 0;
				notice.setText("���������� �ٲ�����ϴ�.");

			} else if (e.getSource() == stampb2) {
				state = 2;
				stampnum = 1;
				notice.setText("��Ʈ�������� �ٲ�����ϴ�.");

			} else if (e.getSource() == stampb3) {
				state = 2;
				stampnum = 2;
				notice.setText("������������ �ٲ�����ϴ�.");

			} else if (e.getSource() == erase) {
				state = 3;
				notice.setText("���찳�� �ٲ�����ϴ�.");
			} else if (e.getSource() == textenter) {
				state = 4;
				textcontents = textinput.getText();
				notice.setText(" ' " + textcontents + " ' " + "�� ����մϴ�!");
			}
		}
	}

	// ����
	private class BgListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == yellow) {
				backgroundcolor = Color.yellow;
				setBackground(backgroundcolor);
				notice.setText("��� ���� ��������� �ٲ�����ϴ�.");
			} else if (e.getSource() == green) {
				backgroundcolor = Color.green;
				setBackground(backgroundcolor);
				notice.setText("��� ���� �ʷϻ����� �ٲ�����ϴ�.");
			} else if (e.getSource() == white) {
				backgroundcolor = Color.white;
				setBackground(backgroundcolor);
				notice.setText("��� ���� �Ͼ������ �ٲ�����ϴ�.");
			}
		}
	}

	// �̹��� �ҷ�����
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == findbutton) {

			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				// ���� ������ �����Ѵ�.
				String filePath = fc.getSelectedFile().getPath(); // ���� ��θ��� �˾ƿ´�.
				testnn.setIcon(new ImageIcon(filePath));

			} else {
				// ����� ���
			}
		}

	}

	// ����
	private class FrameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == frame1 || e.getSource() == frame2 || e.getSource() == frame3
					|| e.getSource() == frame4) {

				if (framenumber == 7)
					framenumber = 0;

				frame1.setBackground(framecolor[framenumber]);
				frame2.setBackground(framecolor[framenumber]);
				frame3.setBackground(framecolor[framenumber]);
				frame4.setBackground(framecolor[framenumber]);
				notice.setText("���ڰ� ����Ǿ����ϴ�.");
				framenumber++;
			}

		}
	}

	// �����ϱ�
	int i = 1;

	private class CaptureListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == save) {
				try {
					Toolkit kit = Toolkit.getDefaultToolkit();
					Dimension screenSize = kit.getScreenSize();
					Robot robot = new Robot();
					BufferedImage bi = robot.createScreenCapture(
							new Rectangle(screenSize.width / 2 - 340, screenSize.height / 2 - 230, 680, 560));
					ImageIO.write(bi, "png", new File("c:/screenshot/" + "�׸�" + i + ".png"));

					i++;

				} catch (AWTException | IOException f) {
					f.printStackTrace();
				}
				notice.setText("�׸��� ����Ǿ����ϴ� :) ");
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (indexb1 > 1000 || indexb2 > 1000 || indexb3 > 1000 || indexr1 > 1000 || indexr2 > 1000 || indexr3 > 1000
				|| indexe > 1000 || indexs1 > 1000 || indexs2 > 1000 || indexs3 > 1000)
			return;
		switch (state) {
		case 0: // red pen
			if (sizenum == 1) {
				red1[indexr1] = new Point();
				red1[indexr1].x = e.getX();
				red1[indexr1].y = e.getY();
				indexr1++;
			} else if (sizenum == 2) {
				red2[indexr2] = new Point();
				red2[indexr2].x = e.getX();
				red2[indexr2].y = e.getY();
				indexr2++;
			} else if (sizenum == 3) {
				red3[indexr3] = new Point();
				red3[indexr3].x = e.getX();
				red3[indexr3].y = e.getY();
				indexr3++;
			}
			break;
		case 1: // black pen
			if (sizenum == 1) {
				black1[indexb1] = new Point();
				black1[indexb1].x = e.getX();
				black1[indexb1].y = e.getY();
				indexb1++;
			} else if (sizenum == 2) {
				black2[indexb2] = new Point();
				black2[indexb2].x = e.getX();
				black2[indexb2].y = e.getY();
				indexb2++;
			} else if (sizenum == 3) {
				black3[indexb3] = new Point();
				black3[indexb3].x = e.getX();
				black3[indexb3].y = e.getY();
				indexb3++;
			}
			break;
		case 2: // stamp
			if (stampnum == 0) {
				stamp1[indexs1] = new Point();
				stamp1[indexs1].x = e.getX();
				stamp1[indexs1].y = e.getY();
				indexs1++;
			} else if (stampnum == 1) {
				stamp2[indexs2] = new Point();
				stamp2[indexs2].x = e.getX();
				stamp2[indexs2].y = e.getY();
				indexs2++;
			} else if (stampnum == 2) {
				stamp3[indexs3] = new Point();
				stamp3[indexs3].x = e.getX();
				stamp3[indexs3].y = e.getY();
				indexs3++;
			}
			break;
		case 3: // eraser
			eraser[indexe] = new Point();
			eraser[indexe].x = e.getX();
			eraser[indexe].y = e.getY();
			indexe++;
			break;
		case 4: // text draw
			textlocation[indext] = new Point();
			textlocation[indext].x = e.getX();
			textlocation[indext].y = e.getY();
			indext++;
			break;
		}

		repaint();

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		for (Point p : black1)
			if (p != null) {
				g.setColor(Color.black);
				g.fillOval(p.x, p.y, 10, 10);
			}
		for (Point p : black2)
			if (p != null) {
				g.setColor(Color.black);
				g.fillOval(p.x, p.y, 20, 20);
			}
		for (Point p : black3)
			if (p != null) {
				g.setColor(Color.black);
				g.fillOval(p.x, p.y, 30, 30);
			}
		for (Point p : red1)
			if (p != null) {
				g.setColor(Color.red);
				g.fillOval(p.x, p.y, 10, 10);
			}
		for (Point p : red2)
			if (p != null) {
				g.setColor(Color.red);
				g.fillOval(p.x, p.y, 20, 20);
			}
		for (Point p : red3)
			if (p != null) {
				g.setColor(Color.red);
				g.fillOval(p.x, p.y, 30, 30);
			}
		for (Point p : stamp1)
			if (p != null) {
				g.drawImage(istamp1, p.x - 25, p.y - 25, 50, 50, null);
			}
		for (Point p : stamp2)
			if (p != null) {
				g.drawImage(istamp2, p.x - 25, p.y - 25, 50, 50, null);
			}
		for (Point p : stamp3)
			if (p != null) {
				g.drawImage(istamp3, p.x - 25, p.y - 25, 50, 50, null);
			}
		for (Point p : textlocation)
			if (p != null) {
				g.setFont(new Font("����������", Font.PLAIN, 30));
				g.setColor(Color.blue);
				g.drawString(textcontents, p.x - 10, p.y);
			}
		for (Point p : eraser)
			if (p != null) {
				g.setColor(backgroundcolor);
				g.fillOval(p.x, p.y, 30, 30);
			}

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}
}

//���� ������ ����
public class Paint extends JFrame {

	public Paint() {
		setSize(700, 720);
		setTitle("�������� �׸���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(new EditPanel(), BorderLayout.CENTER);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		setLocation(screenSize.width / 2 - 350, screenSize.height / 2 - 350);
		setVisible(true);
	}

	public static void main(String[] args) {
		Paint s = new Paint();

	}
}
