import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 棋子类
 * 
 * @author ooo小6
 *
 */
public class Chess {
	public static JLabel chess[] = new JLabel[35];

	public Chess() {
		for (int i = 0; i < 35; i++) {
			chess[i] = new JLabel();
		}
	}

	/**
	 * 初始化棋子 下标1~15是红方 16~31是黑方
	 * 
	 * @return
	 */
	public JLabel[] getChess() {
		// 红车1
		Icon icon = new ImageIcon("img//RC.GIF");
		chess[0] = new JLabel(icon);
		chess[0].setBounds(6, 540, 41, 41);
		// 红车2
		chess[1] = new JLabel(icon);
		chess[1].setBounds(470, 540, 41, 41);
		// 黑车1
		icon = new ImageIcon("img//BC.GIF");
		chess[16] = new JLabel(icon);
		chess[16].setBounds(6, 36, 41, 41);
		// 黑车2
		chess[17] = new JLabel(icon);
		chess[17].setBounds(470, 36, 41, 41);

		// 红马1
		icon = new ImageIcon("img//RM.GIF");
		chess[2] = new JLabel(icon);
		chess[2].setBounds(64, 540, 41, 41);
		// 红马2
		chess[3] = new JLabel(icon);
		chess[3].setBounds(412, 540, 41, 41);
		// 黑马1
		icon = new ImageIcon("img//BM.GIF");
		chess[18] = new JLabel(icon);
		chess[18].setBounds(64, 36, 41, 41);
		// 黑马2
		chess[19] = new JLabel(icon);
		chess[19].setBounds(412, 36, 41, 41);

		// 红象1
		icon = new ImageIcon("img//RX.GIF");
		chess[4] = new JLabel(icon);
		chess[4].setBounds(122, 540, 41, 41);
		// 红象2
		chess[5] = new JLabel(icon);
		chess[5].setBounds(354, 540, 41, 41);
		// 黑象1
		icon = new ImageIcon("img//BX.GIF");
		chess[20] = new JLabel(icon);
		chess[20].setBounds(122, 36, 41, 41);
		// 黑象2
		chess[21] = new JLabel(icon);
		chess[21].setBounds(354, 36, 41, 41);

		// 红士1
		icon = new ImageIcon("img//RS.GIF");
		chess[6] = new JLabel(icon);
		chess[6].setBounds(180, 540, 41, 41);
		// 红士2
		chess[7] = new JLabel(icon);
		chess[7].setBounds(296, 540, 41, 41);
		// 黑士1
		icon = new ImageIcon("img//BS.GIF");
		chess[22] = new JLabel(icon);
		chess[22].setBounds(180, 36, 41, 41);
		// 黑士2
		chess[23] = new JLabel(icon);
		chess[23].setBounds(296, 36, 41, 41);

		// 红炮1
		icon = new ImageIcon("img//RP.GIF");
		chess[8] = new JLabel(icon);
		chess[8].setBounds(64, 428, 41, 41);
		// 红炮2
		chess[9] = new JLabel(icon);
		chess[9].setBounds(412, 428, 41, 41);

		// 黑炮1
		icon = new ImageIcon("img//BP.GIF");
		chess[24] = new JLabel(icon);
		chess[24].setBounds(64, 148, 41, 41);
		// 黑炮2
		chess[25] = new JLabel(icon);
		chess[25].setBounds(412, 148, 41, 41);

		// 红兵1
		icon = new ImageIcon("img//RB.GIF");
		chess[10] = new JLabel(icon);
		chess[10].setBounds(6, 372, 41, 41);
		// 红兵2
		chess[11] = new JLabel(icon);
		chess[11].setBounds(122, 372, 41, 41);
		// 红兵3
		chess[12] = new JLabel(icon);
		chess[12].setBounds(238, 372, 41, 41);
		// 红兵4
		chess[13] = new JLabel(icon);
		chess[13].setBounds(354, 372, 41, 41);
		// 红兵5
		chess[14] = new JLabel(icon);
		chess[14].setBounds(470, 372, 41, 41);

		// 黑卒1
		icon = new ImageIcon("img//BZ.GIF");
		chess[26] = new JLabel(icon);
		chess[26].setBounds(6, 204, 41, 41);
		// 黑卒2
		chess[27] = new JLabel(icon);
		chess[27].setBounds(122, 204, 41, 41);
		// 黑卒3
		chess[28] = new JLabel(icon);
		chess[28].setBounds(238, 204, 41, 41);
		// 黑卒4
		chess[29] = new JLabel(icon);
		chess[29].setBounds(354, 204, 41, 41);
		// 黑卒5
		chess[30] = new JLabel(icon);
		chess[30].setBounds(470, 204, 41, 41);

		// 红帅
		icon = new ImageIcon("img//RJ.GIF");
		chess[15] = new JLabel(icon);
		chess[15].setBounds(238, 540, 41, 41);
		// 黑将
		icon = new ImageIcon("img//BJ.GIF");
		chess[31] = new JLabel(icon);
		chess[31].setBounds(238, 36, 41, 41);

		// 红方走棋标志
		icon = new ImageIcon("img//RJ.GIF");
		chess[32] = new JLabel(icon);
		chess[32].setBounds(238, 615, 41, 41);

		// 黑方走棋标志
		icon = new ImageIcon("img//BJ.GIF");
		chess[33] = new JLabel(icon);
		chess[33].setBounds(238, 615, 41, 41);

		// 选中标志
		icon = new ImageIcon("img//OOS.GIF");
		chess[34] = new JLabel(icon);
		chess[34].setBounds(238, 36, 41, 41);
		return chess;
	}

}
