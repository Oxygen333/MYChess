import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Stack;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import algorithm.AlphaBetaEngine;
import algorithm.Chessmov;
import algorithm.Evaluation;
import algorithm.MoveGenerator;
import algorithm.NegamaxEngine;
import algorithm.SearchEngine;

public class GameView extends Frame implements WindowListener, ActionListener, MouseListener, Runnable {
	public JButton bt_new, bt_menu, bt_exit, bt_repent;// �ײ���ť
	public JLabel chess[] = new Chess().getChess();// ����
	public JLabel jlboard, jlback;// ����,����
	public int chessindex;// ��¼��������ӵ���������
	public boolean red_black = true;// true��ʾ�췽���壬fale��ʾ�ڷ�����
	public boolean red_move = true;
	public boolean firstBoard = false;// �Ƿ�����һ������
	public int from_x, from_y, to_x, to_y;// ������������
	public Stack<repentco> repentStack = new Stack<>();// �����Ѿ��߹���ļ�¼
	public Stack<repentco> diedStack = new Stack<>();// �����Ѿ����Թ���ļ�¼
	public Stack<repentco> fromStack = new Stack<>();// �����ά����ԭ����
	public Stack<repentco> toStack = new Stack<>();// �����ά����ԭ����
	public repentco temp;
	public Point from_p, to_p;// λ������
	static GameView gv;
	Thread thread;
	MoveGenerator pMG;
	Evaluation pEvel;
	SearchEngine pSE;
	public static int chessboard[][];// ��������
	public int backupboard[][] = new int[10][9];// ��������

	public static void main(String[] args) {
		gv = new GameView("�й�����");
		gv.LaunchFrame();

	}

	public GameView(String str) {
		this.setTitle(str);
	}

	public void LaunchFrame() {
		chessboard = initChessBoard();// ��ʼ�����̶�ά����
		pMG = new MoveGenerator();// �߷�������
		pEvel = new Evaluation(); // ��ֵ����
		pSE = new AlphaBetaEngine();// Ĭ����������
		pSE.setM_nSearchDepth(3);// Ĭ���������
		pSE.setM_pMG(pMG);
		pSE.setM_pEval(pEvel);
		// ���ô����С
		this.setSize(518, 700);
		this.setLayout(null);
		/**
		 * �������̾���
		 */
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		double screenwidth = screenSize.getWidth();
		double screenheight = screenSize.getHeight();
		int px = (int) (screenwidth - 510) / 2;
		int py = (int) (screenheight - 700) / 2;
		this.setLocation(px, py);
		// ���ô���logo
		this.setIconImage(tk.getImage("img\\logo.png"));
		// ���ô����޷�����
		this.setResizable(false);
		// ��ʼ������
		for (int i = 0; i < 35; i++) {
			this.add(chess[i]);
			chess[i].addMouseListener(this);
		}
		// �췽�����־��־
		chess[32].setVisible(red_black);
		// �ڷ������־��־
		chess[33].setVisible(!red_black);
		// ѡ�б�־
		chess[34].setVisible(false);// ��ʼ���ɼ�
		// ���ñ�����
		Icon icon = new ImageIcon("img//chessboard.jpg");
		jlboard = new JLabel(icon);
		jlboard.setBounds(10, 40, 498, 537);
		jlboard.addMouseListener(this);
		this.add(jlboard);

		// ���ô���ر�
		this.addWindowListener(this);
		// "�¾�"��ť
		icon = new ImageIcon("img//new.png");
		bt_new = new JButton(icon);
		bt_new.setName("bt_new");
		bt_new.setBounds(27, 590, 172, 39);
		bt_new.addMouseListener(this);// ����¼�
		this.add(bt_new);
		// "�˵�"��ť
		icon = new ImageIcon("img//menu.png");
		bt_menu = new JButton(icon);
		bt_menu.setName("bt_menu");
		bt_menu.setBounds(27, 640, 172, 41);
		bt_menu.addMouseListener(this);// ����¼�
		this.add(bt_menu);
		// "����"��ť
		icon = new ImageIcon("img//repent.png");
		bt_repent = new JButton(icon);
		bt_repent.setName("bt_repent");
		bt_repent.setBounds(317, 590, 172, 41);
		bt_repent.addMouseListener(this);// ����¼�
		this.add(bt_repent);
		// "�˳�"��ť
		icon = new ImageIcon("img//exit.png");
		bt_exit = new JButton(icon);
		bt_exit.setName("bt_exit");
		bt_exit.setBounds(317, 640, 172, 41);
		bt_exit.addMouseListener(this);// ����¼�
		this.add(bt_exit);

		// ���ñ���
		icon = new ImageIcon("img//background.jpg");
		jlback = new JLabel(icon);
		jlback.setBounds(0, 0, 518, 700);
		this.add(jlback);
		this.setVisible(true);
	}

	/**
	 * �¼�����
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(jlboard)) {// �����������
			if (firstBoard) {
				from_x = from_p.x;
				from_y = from_p.y;
				temp = new repentco(chessindex, from_x, from_y);
				int new_fx = ConvertX(from_x + 20);
				int new_fy = ConvertY(from_y + 20);
				// �ƶ����� ��ȡ�������������,������������
				from_x -= 6;
				from_y -= 36;
				int to_x = e.getX() - 16;
				int to_y = e.getY() - 16;
				if (!((Math.abs(to_x - from_x) < 29) && (Math.abs(to_y - from_y) < 28))) {// ��ֹ�ƶ�λ��Ϊԭ��λ��
					// �����������������
					int d, r;
					d = to_x / 58;
					r = to_x % 58;
					if (r >= 29) {
						to_x = 58 * (d + 1);
					} else {
						to_x = 58 * d;
					}
					d = to_y / 56;
					r = to_y % 56;
					if (r >= 28) {
						to_y = 56 * (d + 1);
					} else {
						to_y = 56 * d;
					}
					// �������ϵ�����ת��Ϊ��ά��������
					int new_tx = ConvertX(to_x + 26);
					int new_ty = ConvertY(to_y + 56);
					// �ж����Ƿ���Ϲ��� �ƶ�
					if (pMG.IsValidMove(chessboard, new_fx, new_fy, new_tx, new_ty)) {
						// �����������꣬���ڻ���
						repentStack.push(temp);
						diedStack.push(null);
						temp = new repentco(chessboard[new_fy][new_fx], new_fx, new_fy);
						fromStack.push(temp);
						temp = new repentco(chessboard[new_ty][new_tx], new_tx, new_ty);
						toStack.push(temp);
						// �������̶�ά����
						chessboard[new_ty][new_tx] = chessboard[new_fy][new_fx];
						chessboard[new_fy][new_fx] = 0;
						chess[chessindex].setLocation(to_x + 6, to_y + 36);
						red_move = false;
						WhoFistPlay();// �ķ�������ʾ
						// ���̵߳�������
						thread = new Thread(this);
						thread.start();
						red_move = true;
						firstBoard = false;
					}
				}
			}

		} else if (e.getSource().equals(bt_repent)) {// ���尴ť

			for (int i = 0; i < 2; i++) {
				if (!repentStack.isEmpty()) {
					temp = repentStack.pop();
					chess[temp.index].setLocation(temp.x, temp.y);
					temp = diedStack.pop();
					if (temp != null) {
						chess[temp.index].setLocation(temp.x, temp.y);
					}
					temp = fromStack.pop();
					chessboard[temp.y][temp.x] = temp.index;
					temp = toStack.pop();
					chessboard[temp.y][temp.x] = temp.index;
				}
			}
		} else if (e.getSource().equals(bt_menu)) {// �˵���ť
			new gameMenu(this);

		} else if (e.getSource().equals(bt_new)) {// �¾ְ�ť
			rechess();

		} else if (e.getSource().equals(bt_exit)) {// �˳���ť
			System.exit(0);
		} else {// �����������
			if (red_move) {// �췽����
				for (int i = 0; i < 32; i++) {
					if (e.getSource().equals(chess[i])) {
						if (i >= 0 && i < 16) {// ������Ǻ췽����
							from_p = chess[i].getLocation();// ������Ҫ�ƶ����ӵ�λ��
							// ����ѡ�б�־
							chess[34].setLocation(from_p);
							chess[34].setVisible(true);
							// ���汻������ӵ������±�
							chessindex = i;
							firstBoard = true;// Ϊtrueʱ������̽��������ƶ���Ч
							break;
						} else if ((i >= 16) && from_p != null) {// �췽����
							to_p = chess[i].getLocation();// ���汻�����ӵ�λ��
							// ����������ת��Ϊ��������
							int f_x = ConvertX(from_p.x + 20);
							int f_y = ConvertY(from_p.y + 20);
							int t_x = ConvertX(to_p.x + 20);
							int t_y = ConvertY(to_p.y + 20);
							if (pMG.IsValidMove(chessboard, f_x, f_y, t_x, t_y)) {// �ж��Ƿ�Ϸ����췽���ԳԺڷ�����
								// �������꣬���ڻ���
								temp = new repentco(chessindex, from_p.x, from_p.y);
								repentStack.push(temp);
								temp = new repentco(i, to_p.x, to_p.y);
								diedStack.push(temp);
								temp = new repentco(chessboard[f_y][f_x], f_x, f_y);
								fromStack.push(temp);
								temp = new repentco(chessboard[t_y][t_x], t_x, t_y);
								toStack.push(temp);
								// �޸�����
								chessboard[t_y][t_x] = chessboard[f_y][f_x];
								chessboard[f_y][f_x] = 0;
								// ����
								chess[i].setLocation(1000, 1000);
								chess[chessindex].setLocation(to_p);
								WhoWin();// �ж�˭Ӯ
								red_move = false;
								WhoFistPlay();// �ķ�������ʾ
								// �����̣߳������Է�����
								thread = new Thread(this);
								thread.start();
								red_move = true;
								break;
							}
						}
					}
				}
			}
		}
	}

	// �����̺�����ת��Ϊ��ά���������
	public int ConvertX(int x) {
		return x / 58;
	}

	// ������������ת��Ϊ��ά����������
	public int ConvertY(int y) {
		return y / 56 - 1;
	}

	// ����ά���������ת��Ϊ���̺�����
	public int ConvertBX(int x) {
		return x * 58 + 6;
	}

	// ����ά����������ת��Ϊ����������
	public int ConvertBY(int y) {
		return y * 56 + 36;
	}

	/**
	 * ��������
	 */
	public void blackturn() {
		// ��������
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				backupboard[i][j] = chessboard[i][j];
			}
		}
		pSE.SearchAGoodMove(chessboard);// ��������
		Chessmov c = pSE.getM_cmBestMove();// ���ص������������
		from_x = ConvertBX(c.From.x);
		from_y = ConvertBY(c.From.y);
		to_x = ConvertBX(c.To.x);
		to_y = ConvertBY(c.To.y);
		chess[34].setLocation(from_x, from_y);
		chess[34].setVisible(true);
		boolean flag = false;// �����Ƿ����
		for (int i = 0; i < 16; i++) {
			if (this.getComponentAt(to_x, to_y).equals(chess[i])) {
				temp = new repentco(i, to_x, to_y);
				diedStack.push(temp);
				chess[i].setLocation(1000, 1000);
				flag = true;
			}
		}
		if (!flag) {
			diedStack.push(null);
		}
		for (int i = 16; i < 32; i++) {
			if (this.getComponentAt(from_x, from_y).equals(chess[i])) {
				temp = new repentco(i, from_x, from_y);
				repentStack.push(temp);
				chess[i].setLocation(to_x, to_y);// �ڷ��ƶ�����
			}
		}
		// �������飬���ڻ���
		temp = new repentco(backupboard[c.From.y][c.From.x], c.From.x, c.From.y);
		fromStack.push(temp);
		temp = new repentco(backupboard[c.To.y][c.To.x], c.To.x, c.To.y);
		toStack.push(temp);
		WhoFistPlay();// �ķ�������ʾ
	}

	/**
	 * ���������ļ�����
	 */
	public void WhoFistPlay() {
		red_black = !red_black;
		chess[32].setVisible(red_black);
		chess[33].setVisible(!red_black);
	}

	/**
	 * �ж�˭��˭Ӯ
	 */
	public void WhoWin() {

		if (chess[15].getLocation().x == 1000) {// ��˧�������ڷ�Ӯ
			Icon icon = new ImageIcon("img//upset.png");
			JOptionPane.showMessageDialog(this, "������\n" + "���ӱ���ʮ�겻��\n" + "�ؼ��������ɡ�_��", "",
					JOptionPane.INFORMATION_MESSAGE, icon);
			rechess();// �¾�
		}
		if (chess[31].getLocation().x == 1000) {// �ڽ��������췽Ӯ
			Icon icon = new ImageIcon("img//happy.png");
			JOptionPane.showMessageDialog(this, "С�ӡѨ���\n���в�����Ӯ�ˣ�", "", JOptionPane.INFORMATION_MESSAGE, icon);
			rechess();// �¾�
		}
	}

	/**
	 * �¾�
	 * 
	 * @param chess
	 */
	public void rechess() {
		chess[0].setLocation(6, 540);
		chess[1].setLocation(470, 540);
		chess[16].setLocation(6, 36);
		chess[17].setLocation(470, 36);
		chess[2].setLocation(64, 540);
		chess[3].setLocation(412, 540);
		chess[18].setLocation(64, 36);
		chess[19].setLocation(412, 36);
		chess[4].setLocation(122, 540);
		chess[5].setLocation(354, 540);
		chess[20].setLocation(122, 36);
		chess[21].setLocation(354, 36);
		chess[6].setLocation(180, 540);
		chess[7].setLocation(296, 540);
		chess[22].setLocation(180, 36);
		chess[23].setLocation(296, 36);
		chess[8].setLocation(64, 428);
		chess[9].setLocation(412, 428);
		chess[24].setLocation(64, 148);
		chess[25].setLocation(412, 148);
		chess[10].setLocation(6, 372);
		chess[11].setLocation(122, 372);
		chess[12].setLocation(238, 372);
		chess[13].setLocation(354, 372);
		chess[14].setLocation(470, 372);
		chess[26].setLocation(6, 204);
		chess[27].setLocation(122, 204);
		chess[28].setLocation(238, 204);
		chess[29].setLocation(354, 204);
		chess[30].setLocation(470, 204);
		chess[15].setLocation(238, 540);
		chess[31].setLocation(238, 36);
		chess[32].setLocation(238, 615);
		chess[33].setLocation(238, 615);
		chess[34].setLocation(238, 36);
		for (int i = 0; i < 35; i++) {
			chess[i].setVisible(true);
		}
		chessboard = initChessBoard();
		red_black = true;
		red_move = true;
		firstBoard = false;
		repentStack.clear();
		diedStack.clear();
		fromStack.clear();
		toStack.clear();
	}

	public int[][] initChessBoard() {
		int CB[][] = { // ��ά�����ʾ����
				{ 2, 3, 6, 5, 1, 5, 6, 3, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 4, 0, 0, 0, 0, 0, 4, 0 },
				{ 7, 0, 7, 0, 7, 0, 7, 0, 7 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 14, 0, 14, 0, 14, 0, 14, 0, 14 }, { 0, 11, 0, 0, 0, 0, 0, 11, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 9, 10, 13, 12, 8, 12, 13, 10, 9 } };
		return CB;
	}

	public void run() {
		try {
			thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		blackturn();
		try {
			thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WhoWin();
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

	/**
	 * �ڲ��� ���ò˵�
	 * 
	 * @author oooС6
	 *
	 */
	public class gameMenu implements MouseListener {
		public JButton jb[] = new JButton[7];
		public int depth;
		JFrame menuframe;

		public gameMenu(Frame F) {
			Point p = F.getLocation();
			// ���ò˵�����
			menuframe = new JFrame("�˵�");
			menuframe.setBounds(p.x + 110, p.y + 170, 290, 280);
			menuframe.setResizable(false);
			menuframe.setLayout(null);
			// �㷨ѡ��
			Font font = new Font("����", Font.BOLD, 20);
			JLabel j1 = new JLabel("�㷨ѡ��");
			j1.setFont(font);
			j1.setForeground(Color.WHITE);
			j1.setBounds(40, 40, 100, 25);
			menuframe.add(j1);
			// ����ˮƽ
			JLabel j2 = new JLabel("����ˮƽ");
			j2.setFont(font);
			j2.setForeground(Color.WHITE);
			j2.setBounds(40, 110, 100, 25);
			menuframe.add(j2);
			// �㷨ѡ��
			Icon icon = new ImageIcon("img//��_��.png");
			jb[0] = new JButton(icon);
			jb[0].setBounds(140, 33, 115, 33);
			menuframe.add(jb[0]);
			icon = new ImageIcon("img//maxmin.png");
			jb[1] = new JButton(icon);
			jb[1].setBounds(140, 33, 115, 33);
			jb[1].setVisible(false);
			menuframe.add(jb[1]);
			// ����ˮƽѡ��
			icon = new ImageIcon("img//rookie.png");
			jb[2] = new JButton(icon);
			jb[2].setBounds(140, 103, 115, 33);
			menuframe.add(jb[2]);

			icon = new ImageIcon("img//primer.png");
			jb[3] = new JButton(icon);
			jb[3].setBounds(140, 103, 115, 33);
			jb[3].setVisible(false);
			menuframe.add(jb[3]);

			icon = new ImageIcon("img\\superior.png");
			jb[4] = new JButton(icon);
			jb[4].setBounds(140, 103, 115, 33);
			jb[4].setVisible(false);
			menuframe.add(jb[4]);

			// ȷ����ť
			icon = new ImageIcon("img//ensure.png");
			jb[5] = new JButton(icon);
			jb[5].setBounds(30, 180, 100, 34);
			menuframe.add(jb[5]);
			// ȡ����ť
			icon = new ImageIcon("img//cancel.png");
			jb[6] = new JButton(icon);
			jb[6].setBounds(155, 180, 100, 34);
			menuframe.add(jb[6]);

			// ����
			icon = new ImageIcon("img//menuback.jpg");
			JLabel backgroud = new JLabel(icon);
			backgroud.setBounds(0, 0, 300, 300);
			menuframe.add(backgroud);

			menuframe.setVisible(true);
			for (int i = 0; i < 7; i++) {
				jb[i].addMouseListener(this);
			}
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getSource().equals(jb[0])) {// ��_�¼�֦
				jb[0].setVisible(false);
				jb[1].setVisible(true);

			} else if (e.getSource().equals(jb[1])) {// ����Сֵ
				jb[0].setVisible(true);
				jb[1].setVisible(false);

			} else if (e.getSource().equals(jb[2])) {// ����
				jb[2].setVisible(false);
				jb[3].setVisible(true);
				jb[4].setVisible(false);

			} else if (e.getSource().equals(jb[3])) {// ����
				jb[2].setVisible(false);
				jb[3].setVisible(false);
				jb[4].setVisible(true);

			} else if (e.getSource().equals(jb[4])) {// ����
				jb[2].setVisible(true);
				jb[3].setVisible(false);
				jb[4].setVisible(false);

			} else if (e.getSource().equals(jb[5])) {// ȷ��
				// ��ȡ��������
				if (jb[0].isShowing()) {
					pSE = new AlphaBetaEngine();
				} else if (jb[1].isShowing()) {
					pSE = new NegamaxEngine();
				}
				// ��ȡ�������
				for (int i = 2; i < 5; i++) {
					if (jb[i].isShowing()) {
						depth = i;
					}
				}
				pSE.setM_nSearchDepth(depth);
				pSE.setM_pMG(pMG);
				pSE.setM_pEval(pEvel);
				// �¾�
				rechess();
				menuframe.setVisible(false);
			} else if (e.getSource().equals(jb[6])) {// ȡ��
				menuframe.setVisible(false);
			}
		}

		public void mouseEntered(MouseEvent e) {
			// ���������Ϣ��ʾ
			for (int i = 0; i < 5; i++) {
				if (e.getSource().equals(jb[i])) {
					jb[i].setToolTipText("����л�");
				}
			}
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {
		}
	}

}

/**
 * ����ÿ�������������������������±� ���ڻ���
 * 
 * @author oooС6
 *
 */
class repentco {
	public int index;// ��������
	public int x;// ��������ԭ��������
	public int y;// ��������ԭ��������

	public repentco(int in, int x, int y) {
		this.index = in;
		this.x = x;
		this.y = y;
	}
}
