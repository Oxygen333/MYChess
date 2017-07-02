package algorithm;

/**
 * 各种棋子的常量值,和文字规定
 */
public class Chessconst {

	public static final int NOCHESS = 0;

	public static final int B_KING = 1;
	public static final int B_CAR = 2;
	public static final int B_HORSE = 3;
	public static final int B_CANON = 4;
	public static final int B_BISHOP = 5;
	public static final int B_ELEPHANT = 6;
	public static final int B_PAWN = 7;

	public static final int R_KING = 8;
	public static final int R_CAR = 9;
	public static final int R_HORSE = 10;
	public static final int R_CANON = 11;
	public static final int R_BISHOP = 12;
	public static final int R_ELEPHANT = 13;
	public static final int R_PAWN = 14;

	public static String getChessText(int chessId) {
		switch (chessId) {
			case 2:
				return "鏉岋拷";
			case 3:
				return "妫ｏ拷";
			case 6:
				return "鐠烇拷";
			case 5:
				return "婢癸拷";
			case 1:
				return "鐏忥拷";
			case 7:
				return "閸楋拷";
			case 4:
				return "閻愶拷";
			case 11:
				return "閻愶拷";
			case 9:
				return "鏉烇拷";
			case 10:
				return "妫ｏ拷";
			case 13:
				return "閻╋拷";
			case 12:
				return "娴狅拷";
			case 8:
				return "鐢拷";
			case 14:
				return "閸忥拷";	
			default:
				break;
		}
		return "";
	}
	
}
